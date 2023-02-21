package com.example.boardback.service;


import com.example.boardback.entity.BoardEntity;
import com.example.boardback.repository.BoardRepository;
import com.example.boardback.vo.BoardVo;
import com.example.boardback.vo.Header;
import com.example.boardback.vo.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 게시글 목록 가져오기
     */
    public Header<List<BoardVo>> getBoardList(Pageable pageable) {
        List<BoardVo> vos = new ArrayList<>();

        Page<BoardEntity> boardEntities = boardRepository.findAllByOrderByIdxDesc(pageable);
        for (BoardEntity entity : boardEntities) {
            BoardVo vo = BoardVo.builder()
                    .idx(entity.getIdx())
                    .author(entity.getAuthor())
                    .title(entity.getTitle())
                    .contents(entity.getContents())
                    .createdAt(entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                    .build();

            vos.add(vo);
        }

        Pagination pagination = new Pagination(
                (int) boardEntities.getTotalElements()
                , pageable.getPageNumber() + 1
                , pageable.getPageSize()
                , 10
        );

        return Header.OK(vos, pagination);
    }

    /**
     * 게시글 가져오기
     */
    public BoardVo getBoard(Long id) {
        BoardEntity entity = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        return BoardVo.builder()
                .idx(entity.getIdx())
                .title(entity.getTitle())
                .contents(entity.getContents())
                .author(entity.getAuthor())
                .createdAt(entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                .build();
    }

    /**
     * 게시글 등록
     */
    public BoardEntity create(BoardVo boardDto) {
        BoardEntity entity = BoardEntity.builder()
                .title(boardDto.getTitle())
                .contents(boardDto.getContents())
                .author(boardDto.getAuthor())
                .createdAt(LocalDateTime.now())
                .build();
        return boardRepository.save(entity);
    }

    /**
     * 게시글 수정
     */
    public BoardEntity update(BoardVo boardDto) {
        BoardEntity entity = boardRepository.findById(boardDto.getIdx()).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        entity.setTitle(boardDto.getTitle());
        entity.setContents(boardDto.getContents());
        return boardRepository.save(entity);
    }

    /**
     * 게시글 삭제
     */
    public void delete(Long id) {
        BoardEntity entity = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        boardRepository.delete(entity);
    }
}