package com.board.student.security.service;

import com.board.student.security.dto.UserAuth;
import com.board.student.security.dto.Users;

public interface UserService {
    
    // 회원 가입
    public int join(Users user) throws Exception;

    // 회원 권한 등록
    public int insertAuth(UserAuth userAuth) throws Exception;

}
