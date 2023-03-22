package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션
//스프링 시큐리티에 의해 CSRF 토큰이 자동으로 생성
public class SecurityConfig {
@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http.authorizeHttpRequests().requestMatchers(
            new AntPathRequestMatcher("/**")).permitAll()
            .and()
            .formLogin()
            .loginPage("/user/login") //로그인 성공시 이동하는 디폴트 페이지는 루트
            .defaultSuccessUrl("/")
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
            .logoutSuccessUrl("/") //로그아웃 성공시 루트페이지 이동, 로그아웃시 생성된 사용자 세션 삭제
            .invalidateHttpSession(true)

    ;
    return http.build();
}

@Bean
    PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
   }
   @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
    return authenticationConfiguration.getAuthenticationManager();
   }
}
