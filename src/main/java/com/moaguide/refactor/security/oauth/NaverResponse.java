package com.moaguide.refactor.security.oauth;

import java.util.Map;

public class NaverResponse implements OAuth2ResponseDto {

	private final Map<String, Object> attribute;

	public NaverResponse(Map<String, Object> attribute) {

		this.attribute = (Map<String, Object>) attribute.get("response");
	}

	@Override
	public String getProvider() {

		return "naver";
	}

	@Override
	public String getProviderId() {

		return attribute.get("id").toString();
	}

	@Override
	public String getEmail() {

		return attribute.get("email").toString();
	}

}