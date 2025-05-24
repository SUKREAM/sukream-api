package com.sukream.sukream.commons.config.security;

import com.sukream.sukream.domains.auth.security.JwtAuthenticationFilter;
import com.sukream.sukream.domains.auth.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Order(1)
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/swagger-ui/**", "/swagger/**", "/swagger-resources/**", "/swagger-ui.html",
                        "/v3/api-docs/**", "/css/**", "/js/**", "/img/**", "/lib/**",
                        "/configuration/ui", "/configuration/security", "/webjars/**", "/api/auth/**");
    }

    @Bean
    @Order(2)
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // 인증 필요 없는 API - GET 요청 위주로 접근 허용
                                .requestMatchers(HttpMethod.GET, "/api/product", "/api/product/*", "/api/products/*" ).permitAll()

                                // 인증 필요 - 상품 등록(POST), 수정(PUT), 삭제(DELETE), 입찰 관련 등
                                .requestMatchers(HttpMethod.POST, "/api/product").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/api/product/*").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/product/*").authenticated()

                                .requestMatchers("/api/products/*/bidders").authenticated()
                                .requestMatchers("/api/bidders/*/award").authenticated()

                                .requestMatchers("/api/mypage/orders").authenticated()
                                .requestMatchers(HttpMethod.POST, "/reviews").authenticated()
                                .requestMatchers(HttpMethod.GET, "/reviews/me/received").authenticated()

                                // 그 외는 모두 허용
                                .anyRequest().authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}