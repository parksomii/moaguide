package com.moaguide.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moaguide.domain.billding.*;
import com.moaguide.domain.coupon.CouponUserRepository;
import com.moaguide.domain.user.Role;
import com.moaguide.domain.user.User;
import com.moaguide.domain.user.UserRepository;
import com.moaguide.dto.NewDto.customDto.billingDto.SubscriptDateDto;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BillingService {
    private final BillingInfoRepository billingInfoRepository;
    private final CouponUserRepository couponUserRepository;
    private final PaymentRequestRepository paymentRequestRepository;
    private final PaymentLogRepository paymentLogRepository;
    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public void delete(String nickname) throws Exception{
        userRepository.deleteByNickname(nickname);
        billingInfoRepository.deleteByNickname(nickname);
    }

    public User findByNickanme(String nickname) {
        return userRepository.findByNickname(nickname).orElse(null);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(String nickname, String cardCompany, Integer cardNumber, String customerKey, String billingKey) throws Exception{
        int cardline = userRepository.updateByCard(nickname,cardCompany,cardNumber);
        int billingline = billingInfoRepository.update(customerKey,billingKey,nickname,new Date(System.currentTimeMillis()));
        if(billingline == 0){
            throw new Exception();
        }
        if(cardline == 0){
            throw new Exception();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(String nickname, String cardCompany, Integer cardNumber, String customerKey, String billingKey) throws DuplicateKeyException{
        billingInfoRepository.save(new BillingInfo(customerKey, billingKey,nickname,LocalDate.now()));
        userRepository.updateByCard(nickname,cardCompany,cardNumber);
    }

    @Transactional
    public void start(String nickname, String secretkey) throws Exception{
        List<PaymentRequest> paymentRequests = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            LocalDate endDate = LocalDate.now().plusMonths(1+i);
            String uniqueKey = UUID.randomUUID().toString(); // 첫 번째 UUID는 이미 추가
            paymentRequests.add(new PaymentRequest(uniqueKey,nickname,4900,endDate,0));
        }
        paymentRequestRepository.saveAll(paymentRequests);

s        String orderId = UUID.randomUUID().toString(); // 첫 번째 UUID는 이미 추가
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.tosspayments.com/v1/billing/"+billingInfo.getBillingKey()))
                .header("Authorization", "Basic "+secretkey)
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"customerKey\":\""+billingInfo.getCustomerKey()+"\",\"amount\":4900,\"orderId\":\""+orderId+"\",\"orderName\":\"모아가이드 1개월구독\"}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() > 200) {
            // 에러 발생 시 로그와 사용자 친화적 메시지 반환
            String errorMessage = String.format("Request failed with status code %d and body: %s",
                    response.statusCode(), response.body());
            throw new Exception(errorMessage);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());
        LocalDateTime requestedAt = LocalDateTime.parse(rootNode.get("requestedAt").asText(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        LocalDateTime approvedAt = LocalDateTime.parse(rootNode.get("approvedAt").asText(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        paymentLogRepository.save(new PaymentLog(rootNode.get("orderId").asText(),nickname,rootNode.get("paymentKey").asText(),rootNode.get("orderName").asText(),4900,"카드",requestedAt,approvedAt,0));
        userRepository.updateRole(nickname, Role.VIP);
        userRepository.updateSubscript(nickname,requestedAt.toLocalDate(),paymentRequests.get(0).getNextPaymentDate());
    }

    @Transactional
    public void startWithCoupon(String nickname, Long couponId) throws Exception{
        int couponmonth= couponUserRepository.findByNicknameAndCouponId(nickname,couponId).orElseThrow(()->new NoSuchElementException("Coupon not found for nickname: " + nickname));
        List<PaymentRequest> paymentRequests = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            LocalDate endDate = LocalDate.now().plusMonths(couponmonth+i);
            String uniqueKey = UUID.randomUUID().toString(); // 첫 번째 UUID는 이미 추가
            paymentRequests.add(new PaymentRequest(uniqueKey,nickname,4900,endDate,0));
        }
        paymentRequestRepository.saveAll(paymentRequests);
        LocalDateTime now_date = LocalDateTime.now();
        paymentLogRepository.save(new PaymentLog("모아가이드 1개월구독",0,"쿠폰",now_date,now_date,4900,nickname));
        couponUserRepository.updateRedeemedWithCouponId(true,LocalDate.now(),nickname,couponId);
        userRepository.updateRole(nickname, Role.VIP);
        userRepository.updateSubscript(nickname,now_date.toLocalDate(),paymentRequests.get(0).getNextPaymentDate());
    }


    public List<PaymentLog> findPayment(String nickname) {
        return paymentLogRepository.findAll(nickname);
    }

    public SubscriptDateDto findDate(String nickname) {
        return userRepository.findDate(nickname);
    }

    @Transactional
    public void stop(String nickname) {
        paymentRequestRepository.deletebyNickname(nickname);
    }

    @Transactional
    public void developstop(String nickname) {
        userRepository.deleteByNicknameDate(nickname);
        paymentRequestRepository.deletebyNickname(nickname);
        paymentLogRepository.deleteByNickname(nickname);
        userRepository.updateRole(nickname,Role.USER);
        couponUserRepository.updateRedeemed(false,null,nickname);
    }

    public void startWithDate(String nickname, LocalDate nextPaymentDay) {
        List<PaymentRequest> paymentRequests = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            LocalDate endDate = nextPaymentDay.plusMonths(i);
            String uniqueKey = UUID.randomUUID().toString(); // 첫 번째 UUID는 이미 추가
            paymentRequests.add(new PaymentRequest(uniqueKey,nickname,4900,endDate,0));
        }
        paymentRequestRepository.saveAll(paymentRequests);
    }
}
