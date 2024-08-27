package com.moaguide.config;

import org.apache.http.HttpHost;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.security.KeyStore;

@Configuration
public class ElasticsearchConfig {

    @Bean
    public RestHighLevelClient client() throws Exception {
        // SSLContextBuilder를 사용해 SSL 컨텍스트 생성
        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(KeyStore.getInstance(KeyStore.getDefaultType()), (chain, authType) -> true) // 모든 인증서 신뢰
                .build();

        RestClientBuilder builder = RestClient.builder(
                        new HttpHost("localhost", 9201, "https"))
                .setHttpClientConfigCallback(httpAsyncClientBuilder ->
                        httpAsyncClientBuilder.setSSLContext(sslContext)
                );

        return new RestHighLevelClient(builder);
    }
}
