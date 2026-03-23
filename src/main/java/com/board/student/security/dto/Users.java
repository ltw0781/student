package com.board.student.security.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Users {
    
    private Long no;
    private String username;
    private String password;
    private String name;
    private String email;
    private Date createdAt;
    private Date updatedAt;

    private List<UserAuth> authList;
    
}
