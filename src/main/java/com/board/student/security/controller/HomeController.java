package com.board.student.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.board.student.security.dto.CustomUser;
import com.board.student.security.dto.Users;
import com.board.student.security.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;






@Slf4j
@Controller
public class HomeController {
    
    @Autowired
    private UserService userService;

    @GetMapping("")
    // public String home(Principal principal, Model model) throws Exception {
    public String home(@AuthenticationPrincipal CustomUser authUser, Model model) throws Exception {

        log.info("::::::::::메인화면::::::::::");

        // if ( principal != null ) {
        //     String username = principal.getName();              // 인증된 사용자 아이디
        //     log.info("- 인증된 사용자 : {}", username);
        //     Users user = userService.select(username);          // 사용자 정보 조회
        //     log.info("- 사용자 정보 : {}", user);
        //     model.addAttribute("user", user);    // 사용자 정보 모델에 등록
        // }

        // if ( authentication != null ) {
        //     User user = (User) authentication.getPrincipal();
        //     String username = user.getUsername();                               // 인증된 사용자 아이디
        //     String password = user.getPassword();                               // 인증된 사용자 비밀번호
        //     Collection<GrantedAuthority> authList = user.getAuthorities();      // 사용자 권한
        //     log.info("- 인증된 사용자 아이디 : {}", username);
        //     log.info("- 인증된 사용자 비밀번호 : {}", password);
        //     log.info("- 인증된 사용자 권한 : {}", authList);
        //     Users joinedUser = userService.select(username);          // 사용자 정보 조회
        //     log.info("- 사용자 정보 : {}", joinedUser);
        //     model.addAttribute("joinedUser", joinedUser);    // 사용자 정보 모델에 등록
        // }

        if ( authUser != null ) {

            log.info("authUser : {}", authUser);
            Users user = authUser.getUser();
            model.addAttribute("user", user);
            
        }

        return "index";
    }
    

    

    /**
     * 회원 가입 화면
     * @return
     */
    @GetMapping("/join")
    public String join() {
        return "join";
    }

    /**
     * 회원 가입 처리
     * @param user
     * @return
     */
    @PostMapping("/join")
    public String joinPost(Users user, HttpServletRequest request) throws Exception {
        // 암호화 전 비밀번호
        String plainPassword = user.getPassword();

        // 회원 가입 요청
        int result = userService.join(user);
        // 회원 가입 성공 시, 바로 로그인

        boolean loginResult = false;
        if ( result > 0 ) {
            // 암호화 전 비밀번호로 다시 세팅
            user.setPassword(plainPassword);
            loginResult = userService.login( user, request );   // 바로 로그인
            // 메인 화면으로 이동
        }
        if( loginResult )
            // 메인 화면으로 이동
            return "redirect:/";
        if( result > 0 )
            // 로그인 화면으로 이동
            return "redirect:/login";
        return "redirect:/join?error=true";
    }
    


    @GetMapping("/login")
    public String login(@CookieValue(value = "remember-id", required = false) Cookie cookie, Model model) {
        
        log.info(":::::::::: 로그인 페이지::::::::::");

        String username = "";
        boolean rememberId = false;
        if ( cookie != null ) {

            log.info("CookieName : " + cookie.getName());
            log.info("CookieValue : " + cookie.getValue());
            username = cookie.getValue();
            rememberId = true;
            
        }
        model.addAttribute("username", username);
        model.addAttribute("rememberId", rememberId); 


        return "login";
    }

    @GetMapping("/admin/AdminPage")
    public String AdminPage() {
        return "/admin/AdminPage";
    }
    
    
    

}
