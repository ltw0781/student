package com.board.student.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.board.student.board.dto.Files;
import com.board.student.board.dto.ParentTable;

public interface FileService {
    
    // 파일 목록
    List<Files> list() throws Exception;

    // 파일 상세
    Files select(Integer no) throws Exception;
    Files selectById(String id) throws Exception;

    // 파일 등록
    boolean insert (Files file) throws Exception;

    // 파일 수정
    boolean update (Files file) throws Exception;
    boolean updateById (Files file) throws Exception;
    
    // 파일 삭제
    boolean delete (Integer no) throws Exception;
    boolean deleteById (String id) throws Exception;
    // 파일 업로드
    int upload(List<MultipartFile> files, ParentTable parentTable, Integer parentNo) throws Exception;

}
