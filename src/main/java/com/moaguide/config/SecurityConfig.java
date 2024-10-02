package com.moaguide.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.moaguide.config.handler.CustomAccessDeniedHandler;
import com.moaguide.config.handler.CustomAuthenticationEntryPointHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.moaguide.config.handler.CustomLogoutSuccessHandler;
import com.moaguide.jwt.JWTFilter;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.oauth2.CustomClientRegistrationRepo;
import com.moaguide.security.LoginFilter;
import com.moaguide.service.CookieService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final CookieService cookieService;
    private final CustomClientRegistrationRepo customClientRegistrationRepo ;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {
                    cors.configurationSource(corsConfigurationSource());
                });
        //csrf disable
        http
                .csrf((auth) -> auth.disable());
        //From 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());
        //http basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());
        http.
                authorizeHttpRequests(request -> request
                            .requestMatchers("/logout","/update/phone", "/user/update/nickname"). authenticated() // 로그아웃 요청도 인증 필요
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().permitAll())
                .exceptionHandling(auth -> auth
                .authenticationEntryPoint(new CustomAuthenticationEntryPointHandler())
                .accessDeniedHandler(accessDeniedHandler())); // 접근 거부 핸들러
        http.
                addFilterBefore(new CorsFilter(corsConfigurationSource()), ChannelProcessingFilter.class);
        http.
                addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        http.
                addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),jwtUtil,cookieService), UsernamePasswordAuthenticationFilter.class);

//        http
//                .oauth2Login((oauth2) -> oauth2
//                        .clientRegistrationRepository(customClientRegistrationRepo.clientRegistrationRepository())
//                        .userInfoEndpoint((userInfoEndpointConfig) ->
//                                userInfoEndpointConfig.userService(customOAuth2UserService)));
        http.
                sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .logout(logout -> logout
                .logoutUrl("/logout") // 로그아웃 요청 URL
                .deleteCookies("JSESSIONID", "refresh", "rememberMe")
                .logoutSuccessHandler(new CustomLogoutSuccessHandler()) // 로그아웃 성공 후 메시지 반환

        );
        return http.build();
    }

    private AccessDeniedHandler accessDeniedHandler() {
        return  new CustomAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*")); // 모든 도메인 패턴 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // 허용 메서드
        configuration.setAllowedHeaders(Arrays.asList("Authorization","verify", "Content-Type","cookie")); // 허용 헤더
        configuration.setExposedHeaders(Arrays.asList("Authorization","verify","cookie")); // 클라이언트가 접근할 수 있는 응답 헤더
        configuration.setAllowCredentials(true); // 자격증명 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}