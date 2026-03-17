package com.board.student.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.board.student.board.dto.Board;
import com.board.student.board.dto.ParentTable;
import com.board.student.board.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;

    private final FileService fileService;

    @Override
    public List<Board> list() throws Exception {
        List<Board> boardList = boardMapper.list();
        return boardList;
    }

    @Override
    public Board select(Integer no) throws Exception {
        Board board = boardMapper.select(no);
        return board;
    }

    @Override
    public boolean insert(Board board) throws Exception {
        // 게시글 등록
        int result = boardMapper.insert(board);
        int parentNo = board.getNo();

        // 파일 업로드
        int fileResult = fileService.upload(board.getFiles(), ParentTable.BOARD, parentNo);
        log.info("파일 업로드 - {}개 파일 등록 : ", fileResult);
        return result > 0;
    }

    @Override
    public boolean update(Board board) throws Exception {
        int result = boardMapper.update(board);
        return result > 0;
    }

    @Override
    public boolean delete(Integer no) throws Exception {
        int result = boardMapper.delete(no);
        return result > 0;
    }
    
}
