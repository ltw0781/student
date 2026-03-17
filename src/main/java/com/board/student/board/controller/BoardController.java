package com.board.student.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.student.board.dto.Board;
import com.board.student.board.service.BoardService;

import lombok.extern.slf4j.Slf4j;




@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
    
    @Autowired
    private BoardService boardService;

    /**
     * 게시글 목록 화면
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public String list(Model model) throws Exception {

        // 데이터 요청
        List<Board> boardList = boardService.list();
        log.info("### 게시글 목록 ###");
        log.info("#" + boardList);

        // 모델 등록
        model.addAttribute("boardList", boardList);

        // 뷰 지정
        return "board/list";

    }

    /**
     * 게시글 조회 화면
     * - board/detail?no= 💎
     * @param no
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/detail")
    public String detail(@RequestParam("no") Integer no, Model model) throws Exception{

        // 데이터 요청
        Board board = boardService.select(no);

        // 모델 등록
        model.addAttribute("board", board);

        // 뷰 지정
        return "board/detail";

    }
    
    /**
     * 게시글 등록 화면
     * @return
     */
    @GetMapping("/create")
    public String create(){
        return "board/create";
    }

    /**
     * 게시글 등록 처리
     * @param board
     * @return
     * @throws Exception
     */
    @PostMapping("/create")
    public String postMethodName(Board board) throws Exception {

        // 데이터 요청
        Boolean result = boardService.insert(board);

        // 리다이렉트
        // ⭕ 데이터 처리 성공
        if ( result )
            return "redirect:/board/list";
        // ❌ 데이터 처리 실패
        return "redirect:/board/create?error";
    }

    /**
     * 게시글 수정 화면
     * @param no
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/update")
    public String update(@RequestParam("no") Integer no, Model model) throws Exception {

        // 데이터 요청
        Board board = boardService.select(no);

        // 모델 등록
        model.addAttribute("board", board);

        // 뷰 지정
        return "board/update";

    }

    /**
     * 게시글 수정 처리
     * @param board
     * @return
     * @throws Exception
     */
    @PostMapping("/update")
    public String update(Board board) throws Exception {
        
        // 데이터 요청
        boolean result = boardService.update(board);

        // 리다이렉트
        if ( result )
            return "redirect:/board/list";
        int no = board.getNo();
        return "redirect:/board/update?no=" + no + "&error";
    }
    

    /**
     * 게시글 삭제 처리
     * @param no
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    public String delete(@RequestParam("no") Integer no) throws Exception {
        
        // 데이터 요청
        boolean result = boardService.delete(no);

        // 리다이렉트
        if ( result )
            return "redirect:/board/list";
        return "redirect:/board/update?no=" + no + "&error";
    }
    
    

}
