package com.board.student.board.dto;

public enum ParentTable {
    
    BOARD("board"),
    USER("user")
    ;

    private final String tableName;

    ParentTable(String tableName) {
        this.tableName = tableName;
    }

    public String value() {
        return tableName;
    }

}
