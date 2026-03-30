package com.board.student.security.service;

import com.board.student.security.dto.UserAuth;
import com.board.student.security.dto.Users;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    
    // 회원 가입
    public int join(Users user) throws Exception;

    // 회원 권한 등록
    public int insertAuth(UserAuth userAuth) throws Exception;

    // 바로 로그인
    public boolean login(Users user, HttpServletRequest request);

    // 회원 조회
    public Users select(String username) throws Exception;

}
