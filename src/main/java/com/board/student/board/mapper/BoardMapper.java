package com.board.student.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.board.student.board.dto.Board;

@Mapper
public interface BoardMapper {
    
    // 게시글 목록
    List<Board> list() throws Exception;

    // 게시글 상세
    Board select(Integer no) throws Exception;

    // 게시글 등록
    int insert (Board board) throws Exception;

    // 게시글 수정
    int update (Board board) throws Exception;
    
    // 게시글 삭제
    int delete (Integer no) throws Exception;

}
