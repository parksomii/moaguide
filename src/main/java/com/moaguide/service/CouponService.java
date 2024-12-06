package com.moaguide.service;


import com.moaguide.domain.coupon.CouponAdmin;
import com.moaguide.domain.coupon.CouponAdminRepository;
import com.moaguide.domain.coupon.CouponUser;
import com.moaguide.domain.coupon.CouponUserRepository;
import com.moaguide.dto.NewDto.customDto.Coupon.CouponAdminDto;
import com.moaguide.dto.NewDto.customDto.Coupon.CouponUserDto;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class CouponService {
    private final CouponAdminRepository couponAdminRepository;
    private final CouponUserRepository couponUserRepository;

    public boolean create(int month, String nickname) {
        try {
            LocalDate today = LocalDate.now();
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            int couponLength = 6;
            SecureRandom secureRandom = new SecureRandom();
            StringBuilder result = new StringBuilder();
            String couponname = month+"개월 무료이용권";
            // 쿠폰 생성 반복
            for (int i = 0; i < couponLength; i++) {
                int randomIndex = secureRandom.nextInt(characters.length()); // 문자 집합에서 랜덤 인덱스 생성
                result.append(characters.charAt(randomIndex)); // 랜덤 문자 추가
            }

            couponAdminRepository.save(new CouponAdmin(null,couponname,result.toString(),today,month,nickname));
            return true;
        }catch (Exception e){
            return false;
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

}
