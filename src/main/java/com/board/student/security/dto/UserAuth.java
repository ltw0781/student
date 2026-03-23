package com.board.student.security.dto;

import lombok.Data;

@Data
public class UserAuth {

    private Long no;
    private String username;
    private String auth;
    
}
