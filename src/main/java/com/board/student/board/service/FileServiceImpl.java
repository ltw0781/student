package com.board.student.board.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.board.student.board.dto.Files;
import com.board.student.board.dto.ParentTable;
import com.board.student.board.mapper.FileMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileMapper fileMapper;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;


    @Override
    public List<Files> list() throws Exception {
        List<Files> files = fileMapper.list();
        return files;
    }

    @Override
    public Files select(Integer no) throws Exception {
        Files file = fileMapper.select(no);
        return file;
    }

    @Override
    public Files selectById(String id) throws Exception {
        Files file = fileMapper.selectById(id);
        return file;
    }

    @Override
    public boolean insert(Files file) throws Exception {
        int result = fileMapper.insert(file);
        return result > 0;
    }

    @Override
    public boolean update(Files file) throws Exception {
        int result = fileMapper.update(file);
        return result > 0;
    }

    @Override
    public boolean updateById(Files file) throws Exception {
        int result = fileMapper.updateById(file);
        return result > 0;
    }

    @Override
    public boolean delete(Integer no) throws Exception {
        int result = fileMapper.delete(no);
        return result > 0;
    }

    @Override
    public boolean deleteById(String id) throws Exception {
        int result = fileMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public int upload(List<MultipartFile> files, ParentTable parentTable, Integer parentNo) throws Exception {
        int sortOrder = 0;
        if ( files != null ) {

            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();       // 원본 파일명
                String path = uploadPath + UUID.randomUUID().toString() + "_" + fileName;
                // 파일 저장
                File realFile = new File(path);
                byte[] fileData = file.getBytes();
                FileCopyUtils.copy(fileData, realFile);
                
                // DB 저장
                Files newFile = new Files();
                newFile.setParentNo(parentNo);
                newFile.setParentTable(parentTable.value());
                newFile.setName(fileName);
                newFile.setPath(path);
                newFile.setContentType(file.getContentType());
                newFile.setSortOrder(sortOrder++);
                if (sortOrder == 1) {
                    newFile.setIsMain(true);
                }
                fileMapper.insert(newFile);

            }

        }
        return sortOrder;
    }

}
