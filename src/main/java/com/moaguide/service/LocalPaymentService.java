package com.moaguide.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moaguide.domain.billing.*;
import com.moaguide.domain.billing.localbilling.LocalPaymentRequest;
import com.moaguide.domain.billing.localbilling.LocalPaymentRequestRepository;
import com.moaguide.domain.card.CardRepository;
import com.moaguide.domain.coupon.CouponUserRepository;
import com.moaguide.domain.user.Role;
import com.moaguide.domain.user.UserRepository;
import com.moaguide.dto.NewDto.customDto.billingDto.BillingCouponUSer;
import com.moaguide.dto.NewDto.customDto.billingDto.PaymentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
@Profile("local")
public class LocalPaymentService {
    private CardRepository cardRepository;
    private CouponUserRepository couponUserRepository;
    private LocalPaymentRequestRepository localPaymentRequestRepository;
    private PaymentLogRepository paymentLogRepository;
    private UserRepository userRepository;
    @Value("${toss.secretkey}")
    private String secretkey;

    public LocalPaymentService(CouponUserRepository couponUserRepository, LocalPaymentRequestRepository localPaymentRequestRepository, PaymentLogRepository paymentLogRepository, UserRepository userRepository, CardRepository cardRepository){
        this.couponUserRepository = couponUserRepository;
        this.localPaymentRequestRepository = localPaymentRequestRepository;
        this.paymentLogRepository = paymentLogRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    //    @Scheduled(cron = "0 10,40 * * * *")
    @Scheduled(cron = "0 20 0/1 * * *")
    @Transactional
    public void CouponCron() {
        LocalDateTime nowDate = LocalDateTime.now().plusHours(0).withMinute(30).withSecond(0).withNano(0);
        List<String> nicknameList = localPaymentRequestRepository.findByDate(nowDate);
        List<BillingCouponUSer> couponUserList =couponUserRepository.findAllByNickname(nicknameList);
        List<LocalPaymentRequest> paymentRequests = new ArrayList<>();
        for(BillingCouponUSer couponuser : couponUserList ){
            LocalDateTime enddate = LocalDateTime.now().plusDays(couponuser.getMonth()).withMinute(30).withSecond(0).withNano(0);
            paymentLogRepository.save(new PaymentLog(couponuser.getCouponName(),0,"쿠폰",nowDate,nowDate,4900,couponuser.getNickname()));
            couponUserRepository.updateRedeemedWithCouponId(true,nowDate.toLocalDate(),couponuser.getNickname(),couponuser.getCouponId());
            cardRepository.updateSubscriptByCron(couponuser.getNickname(),enddate);
            localPaymentRequestRepository.deletebyNicknameAndDate(couponuser.getNickname(),nowDate);
            for(int i=0; i <couponuser.getMonth();i++){
                LocalDateTime lastPaymentDay = LocalDateTime.now().plusDays(12+i).plusHours(0).withMinute(30).withSecond(0).withNano(0);
                String orderId = UUID.randomUUID().toString(); // 첫 번째 UUID는 이미 추가
                paymentRequests.add(new LocalPaymentRequest(orderId,couponuser.getNickname(),4900,lastPaymentDay,0));
            }
        }
        localPaymentRequestRepository.saveAll(paymentRequests);
    }

    //    @Scheduled(cron = "0 20,50 * * *")
    @Scheduled(cron = "0 30 0/1 * * *")
    @Transactional
    public void PaymentCron() {
        LocalDateTime now_date = LocalDateTime.now().plusHours(0).withMinute(30).withSecond(0).withNano(0);
        List<PaymentDto> nicknameList = localPaymentRequestRepository.findByNextPaymentDate(now_date);
        List<String> deleteOrderId = new ArrayList<>();
        List<LocalPaymentRequest> paymentRequests = new ArrayList<>();
        for(PaymentDto paymentDto : nicknameList) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.tosspayments.com/v1/billing/" + paymentDto.getBillingKey()))
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((secretkey + ":").getBytes()))
                        .header("Content-Type", "application/json")
                        .method("POST", HttpRequest.BodyPublishers.ofString("{\"customerKey\":\"" + paymentDto.getCustomerKey() + "\",\"amount\":4900,\"orderId\":\"" + paymentDto.getOrderId() + "\",\"orderName\":\"모아가이드 1개월구독\"}"))
                        .build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() > 200) {
                    // 에러 발생 시 로그와 사용자 친화적 메시지 반환
                    String errorMessage = String.format("Request failed with status code %d and body: %s",
                            response.statusCode(), response.body());
                    throw new Exception(errorMessage);
                }
                LocalDateTime endDate = LocalDateTime.now().plusDays(1).withMinute(30).withSecond(0).withNano(0);;
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.body());
                LocalDateTime requestedAt = LocalDateTime.parse(rootNode.get("requestedAt").asText(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                LocalDateTime approvedAt = LocalDateTime.parse(rootNode.get("approvedAt").asText(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                paymentLogRepository.save(new PaymentLog(rootNode.get("orderId").asText(),paymentDto.getNickname(),rootNode.get("paymentKey").asText(),rootNode.get("orderName").asText(),4900,"카드",requestedAt,approvedAt,0));
                cardRepository.updateSubscriptByCron(paymentDto.getNickname(),endDate);
                deleteOrderId.add(rootNode.get("orderId").asText());
                LocalDateTime lastPaymentDay = LocalDateTime.now().plusDays(12).plusHours(0).withMinute(30).withSecond(0).withNano(0);
                String orderId = UUID.randomUUID().toString();
                paymentRequests.add(new LocalPaymentRequest(orderId,paymentDto.getNickname(),4900,lastPaymentDay,0));
            }catch (Exception e){
                e.printStackTrace();
                if(paymentDto.getFailCount() != 5){
                    LocalDateTime date = LocalDateTime.now().plusHours(0).withMinute(30).withSecond(0).withNano(0);
                    LocalDateTime failDate = LocalDateTime.now().plusHours(1).withMinute(30).withSecond(0).withNano(0);
                    localPaymentRequestRepository.updatefailList(paymentDto.getNickname(),failDate,date);
                    cardRepository.updateSubscriptByCron(paymentDto.getNickname(),failDate);
                }
            }
            localPaymentRequestRepository.saveAll(paymentRequests);
            localPaymentRequestRepository.deleteAllById(deleteOrderId);
        }
    }

    //    @Scheduled(cron = "0 0,30 * * * *")
    @Scheduled(cron = "0 40 0/1 * * *")
    @Transactional
    public void faillist(){
        LocalDateTime date = LocalDateTime.now().plusHours(0).withMinute(30).withSecond(0).withNano(0);
        List<String> deleteNicknameList = localPaymentRequestRepository.findByFailCount(date);
        localPaymentRequestRepository.deleteByFailCount(deleteNicknameList);
    }

    @Scheduled(cron= "0 0 0/1 * * *")
    @Transactional
    public void userRoleupdate(){
        LocalDateTime date = LocalDateTime.now().plusHours(0).withMinute(0).withSecond(0).withNano(0);
        List<String> updateNickname=cardRepository.findByDate(date);
        userRepository.updateRoleByDate(Role.USER,updateNickname);
        cardRepository.updateSubscriptBylist(updateNickname);
    }
}
