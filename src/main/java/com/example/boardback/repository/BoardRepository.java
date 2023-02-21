package com.example.boardback.repository;


import com.example.boardback.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    Page<BoardEntity> findAllByOrderByIdxDesc(Pageable pageable);
}