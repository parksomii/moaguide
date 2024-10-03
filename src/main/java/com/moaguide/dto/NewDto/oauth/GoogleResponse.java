package com.moaguide.dto.NewDto.oauth;

import java.util.Map;

public class GoogleResponse implements OAuth2ResponseDto {

    private final Map<String, Object> attributes;

    public GoogleResponse(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attributes.get("sub").toString();  // 구글의 고유 사용자 ID
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    @Override
    public String getPhone() {
        return attributes.get("phone_number") != null ? attributes.get("phone_number").toString() : "";
    }
}