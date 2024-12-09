package com.moaguide.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moaguide.domain.billding.*;
import com.moaguide.domain.card.CardRepository;
import com.moaguide.domain.coupon.CouponUserRepository;
import com.moaguide.domain.user.Role;
import com.moaguide.domain.user.UserRepository;
import com.moaguide.dto.NewDto.customDto.billingDto.BillingCouponUSer;
import com.moaguide.dto.NewDto.customDto.billingDto.PaymentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    private CouponUserRepository couponUserRepository;
    private PaymentRequestRepository paymentRequestRepository;
    private PaymentLogRepository paymentLogRepository;
    private CardRepository cardRepository;
    private UserRepository userRepository;
    @Value("${toss.secretkey}")
    private String secretkey;

    public PaymentService(CouponUserRepository couponUserRepository, PaymentRequestRepository paymentRequestRepository, PaymentLogRepository paymentLogRepository, CardRepository cardRepository, UserRepository userRepository){
        this.couponUserRepository = couponUserRepository;
        this.paymentRequestRepository = paymentRequestRepository;
        this.paymentLogRepository = paymentLogRepository;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

//    @Scheduled(cron = "0 10,40 * * * *")
    @Scheduled(cron = "0 0 17 * * *")
    @Transactional
    public void CouponCron() {
        Date now_date = Date.valueOf(LocalDate.now());
        List<String> nicknameList = paymentRequestRepository.findByDate(now_date);
        List<BillingCouponUSer> couponUserList =couponUserRepository.findAllByNickname(nicknameList);
        List<PaymentRequest> paymentRequests = new ArrayList<>();
        for(BillingCouponUSer couponuser : couponUserList ){
            LocalDateTime nowDate = LocalDateTime.now();
            LocalDate enddate = now_date.toLocalDate().plusMonths(couponuser.getMonth());
            paymentLogRepository.save(new PaymentLog(couponuser.getCouponName(),0,"쿠폰",nowDate,nowDate,4900,couponuser.getNickname()));
            couponUserRepository.updateRedeemedWithCouponId(true,now_date.toLocalDate(),couponuser.getNickname(),couponuser.getCouponId());
            cardRepository.updateSubscriptByCron(couponuser.getNickname(),Date.valueOf(enddate));
            paymentRequestRepository.deletebyNicknameAndDate(couponuser.getNickname(),enddate);
            for(int i=0; i <couponuser.getMonth();i++){
                Date lastPaymentDay = Date.valueOf(LocalDate.now().plusMonths(12+i));
                String orderId = UUID.randomUUID().toString(); // 첫 번째 UUID는 이미 추가
                paymentRequests.add(new PaymentRequest(orderId,couponuser.getNickname(),4900,lastPaymentDay,0));
            }
        }
        paymentRequestRepository.saveAll(paymentRequests);
    }

    //    @Scheduled(cron = "0 20,50 * * *")
    @Scheduled(cron = "0 0 18 * * *")
    @Transactional
    public void PaymentCron() {
        Date now_date = Date.valueOf(LocalDate.now());
        List<PaymentDto> nicknameList = paymentRequestRepository.findByNextPaymentDate(now_date);
        List<String> deleteOrderId = new ArrayList<>();
        List<PaymentRequest> paymentRequests = new ArrayList<>();
        for(PaymentDto paymentDto : nicknameList) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.tosspayments.com/v1/billing/" + paymentDto.getBillingKey()))
                        .header("Authorization", "Basic " + secretkey)
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
                Date endDate = Date.valueOf(LocalDate.now().plusMonths(1));
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.body());
                LocalDateTime requestedAt = LocalDateTime.parse(rootNode.get("requestedAt").asText(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                LocalDateTime approvedAt = LocalDateTime.parse(rootNode.get("approvedAt").asText(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                paymentLogRepository.save(new PaymentLog(rootNode.get("orderId").asText(),paymentDto.getNickname(),rootNode.get("paymentKey").asText(),rootNode.get("orderName").asText(),4900,"카드",requestedAt,approvedAt,0));
                cardRepository.updateSubscriptByCron(paymentDto.getNickname(),endDate);
                deleteOrderId.add(rootNode.get("orderId").asText());
                Date lastPaymentDay = Date.valueOf(LocalDate.now().plusMonths(12));
                String orderId = UUID.randomUUID().toString();
                paymentRequests.add(new PaymentRequest(orderId,paymentDto.getNickname(),4900,lastPaymentDay,0));
            }catch (Exception e){
                e.printStackTrace();
                if(paymentDto.getFailCount() != 5){
                    Date failDate = Date.valueOf(LocalDate.now().plusDays(1));
                    paymentRequestRepository.updatefailList(paymentDto.getNickname(),failDate);
                    cardRepository.updateSubscriptByCron(paymentDto.getNickname(),failDate);
                }
            }
            paymentRequestRepository.saveAll(paymentRequests);
            paymentRequestRepository.deleteAllById(deleteOrderId);
        }
    }

//    @Scheduled(cron = "0 0,30 * * * *")
    @Scheduled(cron = "0 30 18 * * *")
    @Transactional
    public void faillist(){
        List<String> deleteNicknameList = paymentRequestRepository.findByFailCount();
        paymentRequestRepository.deleteByFailCount(deleteNicknameList);
    }

    @Scheduled(cron= "0 0 0 * * *")
    @Transactional
    public void userRoleupdate(){
        Date date = Date.valueOf(LocalDate.now());
        List<String> updateNickname=cardRepository.findByDate(date);
        userRepository.updateRoleByDate(Role.USER,updateNickname);
        cardRepository.updateSubscriptBylist(updateNickname);
    }
}
