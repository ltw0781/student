package com.board.student.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.board.student.board.dto.Board;
import com.board.student.board.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;

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
        int result = boardMapper.insert(board);
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
