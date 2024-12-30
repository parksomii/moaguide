package com.moaguide.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
//    public Cookie createCookie(String key, String value, long maxAge) {
//        Cookie cookie = new Cookie(key, value);
//        cookie.setMaxAge((int) (maxAge / 1000)); // 밀리초를 초 단위로 변환
//        cookie.setHttpOnly(true);
//        cookie.setSecure(true); // HTTPS 사용 시
//        // cookie.setPath("/");
//
//        return cookie;
//    }

    public Cookie createCookie(String key, String value, long maxAge) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge((int) (maxAge / 1000)); // 밀리초를 초 단위로 변환
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // HTTPS 사용 시
        cookie.setPath("/");
        return cookie;
    }
    public Cookie createLocalCookie(String key, String value, long maxAge) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge((int) (maxAge / 1000)); // 밀리초를 초 단위로 변환
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // HTTPS 사용 시
        cookie.setPath("/");
        return cookie;
    }

//    public Cookie createRememberMeCookie(boolean rememberMe, long maxAge) {
//        String value = Boolean.toString(rememberMe); // true 또는 false 값을 문자열로 변환
//        Cookie cookie = new Cookie("rememberMe", value);
//        cookie.setMaxAge((int) (maxAge / 1000)); // 밀리초를 초 단위로 변환
//        cookie.setHttpOnly(true);
//        cookie.setSecure(true); // HTTPS 사용 시
//        // cookie.setPath("/");
//        return cookie;
//    }


    public Cookie createRememberMeCookie(boolean rememberMe, long maxAge) {
        String value = Boolean.toString(rememberMe); // true 또는 false 값을 문자열로 변환
        Cookie cookie = new Cookie("rememberMe", value);
        cookie.setMaxAge((int) (maxAge / 1000)); // 밀리초를 초 단위로 변환
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // HTTPS 사용 시
        cookie.setPath("/");
        return cookie;
    }

    public Cookie createLocalRememberMeCookie(boolean rememberMe, long maxAge) {
        String value = Boolean.toString(rememberMe); // true 또는 false 값을 문자열로 변환
        Cookie cookie = new Cookie("rememberMe", value);
        cookie.setMaxAge((int) (maxAge / 1000)); // 밀리초를 초 단위로 변환
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // HTTPS 사용 시
        cookie.setPath("/");
        return cookie;
    }


    public void setCookieWithSameSite(HttpServletResponse response, String key, String value, long maxAge) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge((int) (maxAge / 1000)); // 밀리초를 초 단위로 변환
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // HTTPS 사용 시
        cookie.setPath("/");
    }

}
