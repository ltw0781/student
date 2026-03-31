package com.board.student.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.board.student.security.security.CustomAccessDeniedHandler;
import com.board.student.security.security.LoginFailureHandler;
import com.board.student.security.security.LoginSuccessHandler;
import com.board.student.security.service.UserDetailServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity          // 해당 클래스를 스프링 시큐리티 설정 빈으로 등록
                            // @Secured, @PreAuthorize, @PostAuthorize 으로 메서드 권한 제어 활성화
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    // 🔐 스프링 시큐리티 설정 메소드
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 인가 설정
        http.authorizeHttpRequests( auth -> auth 
            .requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
            .requestMatchers("/user", "/user/**", "/board", "/board/**").hasAnyRole("USER", "ADMIN")
            .requestMatchers("/**").permitAll()
                .anyRequest().permitAll()
        );

        // 폼 로그인 설정
        // http.formLogin(login -> login.permitAll());

        // ✅ 커스텀 로그인 페이지
        http.formLogin( login -> login
                                    //.usernameParameter("id")                              // 아이디 파라미터
                                    //.passwordParameter("pw")                              // 비밀`번호 파라미터
                                      .loginPage("/login")                       // 로그인 페이지 경로
                                      .loginProcessingUrl("/login")     // 로그인 요청 경로
                                    //.defaultSuccessUrl("/?=true")                         // 로그인 성공 후 리다이렉트 할 경로
                                      .successHandler(loginSuccessHandler)                  // 로그인 성공 후 이벤트 핸들러 등록
                                      .failureHandler(loginFailureHandler)                  // 로그인 실패 핸들러 설정
    
                        );

        http.exceptionHandling( exception -> exception
                                            // 예외 처리 페이지 설정
                                            .accessDeniedPage("/exception")
                                            // 접근 거부 핸들러 설정
                                            .accessDeniedHandler(customAccessDeniedHandler)

                              );

        // 사용자 정의 인증
        http.userDetailsService(userDetailServiceImpl);

        // 자동 로그인
        http.rememberMe(me -> me
                .key("aloha")
                .tokenRepository(tokenRepository())
                .tokenValiditySeconds(60 * 60 * 24 * 7));

        // 🔓 로그아웃 설정
        http.logout( logout -> logout 
                              .logoutUrl("/logout")                             // 로그아웃 요청 경로
                              .logoutSuccessUrl("/login?logout=true")    // 로그아웃 성공 시 URL
                              .invalidateHttpSession(true)          // 세션 초기화
                              .deleteCookies("remember-id")         // 로그아웃 시, 아이디 저장 쿠키 삭제
                            //.logoutSuccessHandler(null)            // 로그아웃 성공 핸들러 설정
                    );



        return http.build();
    }

    // PersistentRepository 토큰정보 객체 - 빈 등록
    @Bean
    public PersistentTokenRepository tokenRepository() {
        // JdbcTokenRepositoryImpl : 토큰 저장 데이터 베이스를 등록하는 객체
        JdbcTokenRepositoryImpl repositoryImpl = new JdbcTokenRepositoryImpl();
        // 토큰 저장소를 사용하는 데이터 소스 지정
        repositoryImpl.setDataSource(dataSource);
        // persistent_logins 테이블 자동 생성
        // repositoryImpl.setCreateTableOnStartup(true);
        try {
            repositoryImpl.getJdbcTemplate().execute(JdbcTokenRepositoryImpl.CREATE_TABLE_SQL);
        } catch (Exception e) {
            log.error("persistent_logins 테이블이 이미 생성되었습니다.");
        }
        return repositoryImpl;
    }

    // 👮‍♂️🔐사용자 인증 관리 메소드
    // 인메모리 방식으로 인증
    // @Bean
    // public UserDetailsService userDetailsService() {

    // // user 123456
    // UserDetails user = User.builder()
    // .username("user")
    // .password(passwordEncoder.encode("123456"))
    // .roles("USER")
    // .build();

    // // admin 123456
    // UserDetails admin = User.builder()
    // .username("admin")
    // .password(passwordEncoder.encode("123456"))
    // .roles("USER", "ADMIN")
    // .build();

    // return new InMemoryUserDetailsManager( user, admin );
    // // return new JdbcUserDetailsManager( ... );

    // }

    // JDBC 인증 방식
    // ✅ 데이터 소스 (URL, ID, PW) - application.properties
    // ✅ SQL 쿼리 등록
    // ⭐ 사용자 인증 쿼리
    // ⭐ 사용자 권한 쿼리
    // @Bean
    // public UserDetailsService userDetailsService() {
    //     JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

    //     // 사용자 인증 쿼리
    //     String sql1 = " SELECT username as username, password as password, enabled "
    //             + " FROM user "
    //             + " WHERE username = ? ";
    //     // 사용자 권한 쿼리
    //     String sql2 = " SELECT username as username, auth "
    //             + " FROM user_auth "
    //             + " WHERE username  = ? ";
    //     userDetailsManager.setUsersByUsernameQuery(sql1);
    //     userDetailsManager.setAuthoritiesByUsernameQuery(sql2);
    //     return userDetailsManager;
    // }

    /**
     * 🍀 AuthenticationManager 인증 관리자 빈 등록
     * 
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
