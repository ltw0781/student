package com.board.student.board.service;

import java.util.List;

import com.board.student.board.dto.Board;

public interface BoardService {
    
    // 게시글 목록
    List<Board> list() throws Exception;

    // 게시글 상세
    Board select(Integer no) throws Exception;

    // 게시글 등록
    boolean insert (Board board) throws Exception;

    // 게시글 수정
    boolean update (Board board) throws Exception;
    
    // 게시글 삭제
    boolean delete (Integer no) throws Exception;

}
