package com.moaguide.refactor.payments.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moaguide.refactor.coupon.repository.CouponUserRepository;
import com.moaguide.refactor.enums.Role;
import com.moaguide.refactor.notice.entity.Notification;
import com.moaguide.refactor.notice.service.NotificationService;
import com.moaguide.refactor.payments.dto.BillingCouponUSer;
import com.moaguide.refactor.payments.dto.PaymentDto;
import com.moaguide.refactor.payments.entity.PaymentLog;
import com.moaguide.refactor.payments.entity.PaymentRequest;
import com.moaguide.refactor.payments.repository.PaymentLogRepository;
import com.moaguide.refactor.payments.repository.PaymentRequestRepository;
import com.moaguide.refactor.user.repository.UserRepository;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Profile({"blue", "green"})
public class PaymentService {

	private final CouponUserRepository couponUserRepository;
	private final PaymentRequestRepository paymentRequestRepository;
	private final PaymentLogRepository paymentLogRepository;
	private final UserRepository userRepository;
	private final NotificationService notificationService;
	@Value("${toss.secretkey}")
	private String secretkey;

	public PaymentService(CouponUserRepository couponUserRepository,
		PaymentRequestRepository paymentRequestRepository,
		PaymentLogRepository paymentLogRepository, UserRepository userRepository,
		NotificationService notificationService) {
		this.couponUserRepository = couponUserRepository;
		this.paymentRequestRepository = paymentRequestRepository;
		this.paymentLogRepository = paymentLogRepository;
		this.userRepository = userRepository;
		this.notificationService = notificationService;
	}

	//    @Scheduled(cron = "0 10,40 * * * *")
	@Scheduled(cron = "0 0 17 * * *")
	@Transactional
	public void CouponCron() {
		LocalDate now_date = LocalDate.now();
		List<String> nicknameList = paymentRequestRepository.findByDate(now_date);
		List<BillingCouponUSer> couponUserList = couponUserRepository.findAllByNickname(
			nicknameList);
		List<PaymentRequest> paymentRequests = new ArrayList<>();
		for (BillingCouponUSer couponuser : couponUserList) {
			LocalDateTime nowDate = LocalDateTime.now();
			LocalDate enddate = now_date.plusMonths(couponuser.getMonth());
			paymentLogRepository.save(
				new PaymentLog(couponuser.getCouponName(), 0, "쿠폰", nowDate, nowDate,
					4900 * couponuser.getMonth(), couponuser.getNickname()));
			notificationService.save(
				new Notification(couponuser.getNickname(), "https://moaguide.com/payment",
					"구독 정기 결제가 완료 되었습니다. 지금 확인해보세요", nowDate.toLocalDate()));
			couponUserRepository.updateRedeemedWithCouponId(true, now_date,
				couponuser.getNickname(), couponuser.getCouponId());
			userRepository.updateSubscriptByCron(couponuser.getNickname(), enddate);
			paymentRequestRepository.deletebyNicknameAndDate(couponuser.getNickname(), enddate);
			for (int i = 0; i < couponuser.getMonth(); i++) {
				LocalDate lastPaymentDay = LocalDate.now().plusMonths(12 + i);
				String orderId = UUID.randomUUID().toString(); // 첫 번째 UUID는 이미 추가
				paymentRequests.add(
					new PaymentRequest(orderId, couponuser.getNickname(), 4900, lastPaymentDay, 0));
			}
		}
		paymentRequestRepository.saveAll(paymentRequests);
	}

	//    @Scheduled(cron = "0 20,50 * * *")
	@Scheduled(cron = "0 0 18 * * *")
	@Transactional
	public void PaymentCron() {
		LocalDate now_date = LocalDate.now();
		List<PaymentDto> nicknameList = paymentRequestRepository.findByNextPaymentDate(now_date);
		List<String> deleteOrderId = new ArrayList<>();
		List<PaymentRequest> paymentRequests = new ArrayList<>();
		for (PaymentDto paymentDto : nicknameList) {
			try {
				HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(
						"https://api.tosspayments.com/v1/billing/" + paymentDto.getBillingKey()))
					.header("Authorization",
						"Basic " + Base64.getEncoder().encodeToString((secretkey + ":").getBytes()))
					.header("Content-Type", "application/json")
					.method("POST", HttpRequest.BodyPublishers.ofString(
						"{\"customerKey\":\"" + paymentDto.getCustomerKey()
							+ "\",\"amount\":4900,\"orderId\":\"" + paymentDto.getOrderId()
							+ "\",\"orderName\":\"모아가이드 1개월구독\"}"))
					.build();
				HttpResponse<String> response = HttpClient.newHttpClient()
					.send(request, HttpResponse.BodyHandlers.ofString());
				if (response.statusCode() > 200) {
					// 에러 발생 시 로그와 사용자 친화적 메시지 반환
					String errorMessage = String.format(
						"Request failed with status code %d and body: %s",
						response.statusCode(), response.body());
					throw new Exception(errorMessage);
				}
				LocalDate endDate = LocalDate.now().plusMonths(1);
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode rootNode = objectMapper.readTree(response.body());
				LocalDateTime requestedAt = LocalDateTime.parse(
					rootNode.get("requestedAt").asText(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
				LocalDateTime approvedAt = LocalDateTime.parse(rootNode.get("approvedAt").asText(),
					DateTimeFormatter.ISO_OFFSET_DATE_TIME);
				paymentLogRepository.save(
					new PaymentLog(rootNode.get("orderId").asText(), paymentDto.getNickname(),
						rootNode.get("paymentKey").asText(), rootNode.get("orderName").asText(),
						4900, "카드", requestedAt, approvedAt, 0));
				notificationService.save(
					new Notification(paymentDto.getNickname(), "https://moaguide.com/payment",
						"구독 정기 결제가 완료 되었습니다. 지금 확인해보세요", requestedAt.toLocalDate()));
				userRepository.updateSubscriptByCron(paymentDto.getNickname(), endDate);
				deleteOrderId.add(rootNode.get("orderId").asText());
				LocalDate lastPaymentDay = LocalDate.now().plusMonths(12);
				String orderId = UUID.randomUUID().toString();
				paymentRequests.add(
					new PaymentRequest(orderId, paymentDto.getNickname(), 4900, lastPaymentDay, 0));
			} catch (Exception e) {
				e.printStackTrace();
				if (paymentDto.getFailCount() != 5) {
					LocalDate date = LocalDate.now();
					LocalDate failDate = LocalDate.now().plusDays(1);
					paymentRequestRepository.updatefailList(paymentDto.getNickname(), failDate,
						date);
					userRepository.updateSubscriptByCron(paymentDto.getNickname(), failDate);
				}
			}
			paymentRequestRepository.saveAll(paymentRequests);
			paymentRequestRepository.deleteAllById(deleteOrderId);
		}
	}

	//    @Scheduled(cron = "0 0,30 * * * *")
	@Scheduled(cron = "0 30 18 * * *")
	@Transactional
	public void faillist() {
		LocalDate date = LocalDate.now();
		List<String> deleteNicknameList = paymentRequestRepository.findByFailCount(date);
		paymentRequestRepository.deleteByFailCount(deleteNicknameList);
	}

	@Scheduled(cron = "0 0 0 * * *")
	@Transactional
	public void userRoleupdate() {
		LocalDate date = LocalDate.now();
		List<String> updateNickname = userRepository.findByDate(date);
		userRepository.updateRoleByDate(Role.USER, updateNickname);
		userRepository.updateSubscriptBylist(updateNickname);
	}
}
