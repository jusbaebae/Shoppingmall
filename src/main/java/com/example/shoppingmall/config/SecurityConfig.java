package com.example.shoppingmall.config;

import com.example.shoppingmall.global.security.filter.JwtAuthenticationFilter;
import com.example.shoppingmall.global.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter)
            throws Exception{

        //REST API에서는 CSRF보호가 필요하지 않음
        http.csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                //JWT사용 -> 세션 사용하지 않음
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .authorizeHttpRequests(auth -> auth

                        //인증 없이 접근 가능
                        .requestMatchers(
                                "/api/members/signup",
                                "/api/members/login",
                                "/uploads/**"
                        )
                        .permitAll()

                        //상품 조회 - 로그인 필요 없음
                        .requestMatchers(HttpMethod.GET, "/api/products/**")
                        .permitAll()

                        //관리자만 접근가능
                        .requestMatchers(HttpMethod.POST,
                                "/api/products"
                        )
                        .permitAll()

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/products/**"
                        )
                        .hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/products/**"
                        )
                        .hasRole("ADMIN")

                        //나머지는 인증필요
                        .anyRequest()
                        .authenticated()
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtTokenProvider jwtTokenProvider
    ) {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }
}
