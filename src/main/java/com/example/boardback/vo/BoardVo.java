package com.example.boardback.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardVo implements Serializable {
    private Long idx;
    private String title;
    private String contents;
    private String author;
    private String createdAt;
}