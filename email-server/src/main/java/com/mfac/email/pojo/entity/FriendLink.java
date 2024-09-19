package com.mfac.email.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendLink implements Serializable {
    private Long id;
    private String name;
    private String link;
    private String description;
    private String avatar;
    private String author;
    private String email;
    private String color;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer status;
    private String downReason;
}
