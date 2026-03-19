package com.board.student.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 👮‍♂️🔐사용자 인증 관리 메소드
    // 인메모리 방식으로 인증
    // @Bean
    // public UserDetailsService userDetailsService() {

    //     // user 123456
    //     UserDetails user = User.builder()
    //                             .username("user")
    //                             .password(passwordEncoder.encode("123456"))
    //                             .roles("USER")
    //                             .build();

    //     // admin 123456
    //     UserDetails admin = User.builder()
    //                             .username("admin")
    //                             .password(passwordEncoder.encode("123456"))
    //                             .roles("USER", "ADMIN")
    //                             .build();


    //     return new InMemoryUserDetailsManager( user, admin );
    //     // return new JdbcUserDetailsManager( ... );
        
    // }


    // JDBC 인증 방식
    // ✅ 데이터 소스 (URL, ID, PW) - application.properties
    // ✅ SQL 쿼리 등록
    // ⭐ 사용자 인증 쿼리
    // ⭐ 사용자 권한 쿼리
    @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        // 사용자 인증 쿼리
        String sql1 = " SELECT username as username, password as password, enabled "
        + " FROM user "
        + " WHERE username = ? "
        ;
        // 사용자 권한 쿼리
        String sql2 = " SELECT username as username, auth "
        + " FROM user_auth "
        + " WHERE username  = ? "
        ;
        userDetailsManager.setUsersByUsernameQuery(sql1);
        userDetailsManager.setAuthoritiesByUsernameQuery(sql2);
        return userDetailsManager;
    }


    /**
     * 🍀 AuthenticationManager  인증 관리자 빈 등록
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(
                                    AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
