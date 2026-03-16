package com.board.student.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.board.student.board.dto.Files;

@Mapper
public interface FileMapper {
    
    // 파일 목록
    List<Files> list() throws Exception;

    // 파일 상세
    Files select(Integer no) throws Exception;
    Files selectById(String id) throws Exception;

    // 파일 등록
    int insert (Files file) throws Exception;

    // 파일 수정
    int update (Files file) throws Exception;
    int updateById (Files file) throws Exception;
    
    // 파일 삭제
    int delete (Integer no) throws Exception;
    int deleteById (String id) throws Exception;

}
