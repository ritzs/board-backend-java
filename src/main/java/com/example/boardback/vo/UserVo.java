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
public class UserVo  implements Serializable {
    private Long idx;
    private String userId;
    private String userPw;
    private String userName;
}
