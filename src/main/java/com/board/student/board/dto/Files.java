package com.board.student.board.dto;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class Files {
    
    private Integer no;
    private String id;
    private String parentTable;         // 부모 테이블
    private Integer ParentNo;           // 부모 PK
    private String name;                // 파일명
    private String path;                // 파일경로
    private Long size;                  // 용량
    private String contentType;         // 컨텐츠 타입
    private Integer sortOrder;          // 순서 : 0 ~
    private Boolean isMain;             // 대표 여부 (썸네일)
    private Date createdAt;
    private Date updatedAt;

    public Files() {
        this.id = UUID.randomUUID().toString();
    }


}
