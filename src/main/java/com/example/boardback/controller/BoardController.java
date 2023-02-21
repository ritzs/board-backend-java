package com.example.boardback.controller;


import com.example.boardback.entity.BoardEntity;
import com.example.boardback.service.BoardService;
import com.example.boardback.vo.BoardVo;
import com.example.boardback.vo.Header;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/board/list")
    public Header<List<BoardVo>> boardList(@PageableDefault(sort = {"idx"}) Pageable pageable) {
        return boardService.getBoardList(pageable);
    }

    @GetMapping("/board/{id}")
    public BoardVo getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @PostMapping("/board")
    public BoardEntity create(@RequestBody BoardVo boardVo) {
        return boardService.create(boardVo);
    }

    @PatchMapping("/board")
    public BoardEntity update(@RequestBody BoardVo boardVo) {
        return boardService.update(boardVo);
    }

    @DeleteMapping("/board/{id}")
    public void delete(@PathVariable Long id) {
        boardService.delete(id);
    }
}
