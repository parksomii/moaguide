package com.moaguide;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class WebClientConcurrentRequestTest {

    public static void main(String[] args) {
        WebClient webClient = WebClient.create("https://api.moaguide.com");

        // 1부터 100까지 페이지별로 100명의 사용자가 접근하도록 설정
        for (int pageNumber = 1; pageNumber <= 100; pageNumber++) {
            final int currentPage = pageNumber;
            List<Mono<String>> requests = new ArrayList<>();
            long pageStartTime = System.currentTimeMillis();  // 각 페이지 요청 시작 시간 기록

            // 동일한 페이지에 대한 100개의 요청 생성
            for (int i = 0; i < 100; i++) {
                Mono<String> request = webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/summary/list")
                                .queryParam("category", "all")
                                .queryParam("subcategory", "trade")
                                .queryParam("sort", "price")
                                .queryParam("page", currentPage)
                                .queryParam("size", "10")
                                .build())
                        .header("Cache-Control", "no-cache, no-store, must-revalidate")
                        .header("Pragma", "no-cache")
                        .header("Expires", "0")
                        .retrieve()
                        .bodyToMono(String.class)
                        .onErrorResume(e -> {
                            System.out.println("Request for page " + currentPage + " failed: " + e.getMessage());
                            return Mono.just("Request failed");
                        });

                requests.add(request);
            }

            // 현재 페이지에 대한 100개의 요청을 병렬로 실행하고, 모든 요청이 완료될 때까지 대기
            Mono.when(requests)
                    .doOnTerminate(() -> {
                        long pageEndTime = System.currentTimeMillis();
                        double totalElapsedTimeInSeconds = (pageEndTime - pageStartTime) / 1000.0;
                        System.out.println("page " + currentPage + ": " + totalElapsedTimeInSeconds + " seconds");
                    })
                    .block();  // 각 페이지의 모든 요청이 완료될 때까지 대기
        }
    }
}
