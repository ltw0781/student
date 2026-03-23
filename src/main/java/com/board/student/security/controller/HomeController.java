package com.board.student.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.board.student.security.dto.Users;
import com.board.student.security.service.UserService;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Controller
public class HomeController {
    
    @Autowired
    private UserService userService;

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
    public String joinPost(Users user) throws Exception {

        // 회원 가입 요청
        int result = userService.join(user);

        if ( result > 0 ) {
            return "redirect:/";
        }
        
        return "redirect:/join?error=true";
    }
    
    

}
