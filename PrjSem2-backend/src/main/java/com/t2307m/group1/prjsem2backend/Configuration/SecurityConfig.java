package com.t2307m.group1.prjsem2backend.Configuration;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Tắt CSRF
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll() // Cho phép truy cập tất cả các URL mà không cần xác thực
                )
                .formLogin(form -> form.disable() // Tắt form đăng nhập
                )
                .logout(logout -> logout
                        .logoutUrl("/api/v1/accounts/logout") // URL xử lý đăng xuất
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        }) // URL chuyển hướng sau khi đăng xuất
                        .invalidateHttpSession(true) // Hủy phiên người dùng
                        .deleteCookies("JSESSIONID") // Xoá cookie JSESSIONID
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
