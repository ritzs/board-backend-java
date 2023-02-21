package com.example.boardback.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user")
@Entity
public class UserEntity {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idx;
        private String userId;
        private String userPw;
        private String userName;

}
