package com.moaguide.refactor.util;

import java.util.Arrays;

public class EnumUtils {
    public static <E extends Enum<E>> E findByName(Class<E> enumClass, String name) {
        return Arrays.stream(enumClass.getEnumConstants()) // Enum 값들의 배열을 스트림으로 변환
                .filter(e -> e.name().equalsIgnoreCase(name)) // 이름 비교 (대소문자 무시)
                .findFirst() // 첫 번째 매칭되는 값 검색
                .orElseThrow(() -> new IllegalArgumentException("Invalid name: " + name)); // 없으면 예외 발생
    }

}