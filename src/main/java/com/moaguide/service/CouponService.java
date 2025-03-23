package com.moaguide.service;


import com.moaguide.domain.coupon.CouponAdmin;
import com.moaguide.domain.coupon.CouponAdminRepository;
import com.moaguide.domain.coupon.CouponUser;
import com.moaguide.domain.coupon.CouponUserRepository;
import com.moaguide.domain.user.UserRepository;
import com.moaguide.dto.NewDto.customDto.Coupon.CouponAdminDto;
import com.moaguide.dto.NewDto.customDto.Coupon.CouponUserDto;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CouponService {
    private final CouponAdminRepository couponAdminRepository;
    private final CouponUserRepository couponUserRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;


    @Transactional
    public void firstCreate(String email) {
        try {
            LocalDate today = LocalDate.now();
            String couponNumber = "FIRST1";
            String couponname ="모아가이드 첫 구독 1개월 무료이용권";
            String nickname = userRepository.findUserByEmail(email);
            couponAdminRepository.save(new CouponAdmin(null,couponname,couponNumber,today,1,nickname));
            CouponAdmin Coupon = couponAdminRepository.findByCodeAndNickname(couponNumber,nickname).orElseThrow();
            couponUserRepository.save(new CouponUser(nickname,Coupon.getId(),false));
        }catch (Exception e){
            log.info("쿠폰생성시 오류 발생 :{}",e);
        }
    }

    @Transactional
    public String create(int month, String nickname) {
        try {
            LocalDate today = LocalDate.now();
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            int couponLength = 6;
            SecureRandom secureRandom = new SecureRandom();
            StringBuilder couponNumber = new StringBuilder();
            String couponname ="모아가이드 "+month+"개월 무료이용권";
            // 쿠폰 생성 반복
            for (int i = 0; i < couponLength; i++) {
                int randomIndex = secureRandom.nextInt(characters.length()); // 문자 집합에서 랜덤 인덱스 생성
                couponNumber.append(characters.charAt(randomIndex)); // 랜덤 문자 추가
            }
            String email= userRepository.findEmail(nickname);
            String result = emailService.sendCoupon(email,couponNumber.toString());
            couponAdminRepository.save(new CouponAdmin(null,couponname,couponNumber.toString(),today,month,nickname));
            return result;
        }catch (Exception e){
            return "쿠폰생성시 오류 발생";
        }
    }

    public int valid(String code, String nickname) {
        CouponAdmin coupon = couponAdminRepository.findByCodeAndNickname(code,nickname).orElse(null);
        if(coupon == null){
            return -1;
        }else{
            try {
                couponUserRepository.save(new CouponUser(null,nickname,coupon.getId(),false,null));
                return 1;
            } catch (ConstraintViolationException | DataIntegrityViolationException e) {
                return -2; // 중복 예외
            } catch (Exception e) {
                return -1; // 기타 예외
            }
        }
    }

    public List<CouponUserDto> findByNickname(String nickname) {
        return couponUserRepository.findByNickname(nickname,false);
    }

    public Page<CouponAdminDto> findByAdmin(Pageable pages) {
        return couponAdminRepository.findByAll(pages);
    }

    public List<String> findUser() {
        return userRepository.findUser();
    }
}
