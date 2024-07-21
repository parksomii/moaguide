package com.moaguide.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.moaguide.config.handler.CustomAccessDeniedHandler;
import com.moaguide.config.handler.CustomAuthenticationEntryPointHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                        .requestMatchers("/hc","/env")
                        .permitAll()
                        //.antMatchers("/summary/**").hasAuthority("VIP")
                        .anyRequest().permitAll())
                .exceptionHandling(auth -> auth
                .authenticationEntryPoint(new CustomAuthenticationEntryPointHandler())
                .accessDeniedHandler(accessDeniedHandler())); // 접근 거부 핸들러
        http
                .csrf(csrf -> csrf.disable());
        http
                .formLogin(login ->
                        login.loginPage("/user/login")
                                .loginProcessingUrl("/user/login")
                                .defaultSuccessUrl("/", true)
                                .failureUrl("/user/login?error=Disagreexment")

                );
        http
                .logout(logout ->
                        logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/?seccess=logout")
                                .invalidateHttpSession(true));
        http
                .headers(headers -> {
                            headers
                                    .frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin());
                        }
                );
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    private AccessDeniedHandler accessDeniedHandler() {
        return  new CustomAccessDeniedHandler();
    }

    // JSON 변환을 위한 ObjectMapper
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Java 8 날짜/시간 모듈 등록
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper;
    }

    // EntityManagerFactory 빈 재정의
/*    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        // JPA 설정을 담을 객체 생성
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource); // 데이터 소스를 설정합니다.
        factory.setPackagesToScan("com.moaguide.domain"); // 엔티티 클래스를 스캔할 패키지를 설정합니다.
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter()); // JPA 벤더 어댑터를 설정합니다.

        return factory;
    }*/

    // 트랜잭션 관리자 빈을 생성
    /*@Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        // EntityManagerFactory를 파라미터로 받아 JpaTransactionManager 생성
        return new JpaTransactionManager(entityManagerFactory);
    }*/
}