package com.board.student.board.dto;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class Board {
    
    private Integer no;
    private String id;
    private String title;
    private String writer;
    private String content;
    private Date createdAt;
    private Date updatedAt;

    public Board() {
        this.id = UUID.randomUUID().toString();
    }

}
