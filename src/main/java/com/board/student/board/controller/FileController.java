package com.board.student.board.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.board.student.board.dto.Files;
import com.board.student.board.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    /**
     * 파일 뷰어 ( 썸네일 )
     * 
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/{id}")
    public ResponseEntity<Resource> viewFile(@PathVariable("id") String id) throws Exception {
        Files file = fileService.selectById(id);

        FileSystemResource resource = new FileSystemResource(file.getPath());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();

        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename\"" + file.getName() + "\"")
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(resource);
    }

}
