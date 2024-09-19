package com.mfac.friendLink.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendLinkListVO {
    private Long id;
    private String name;
    private String link;
    private String description;
    private String avatar;
    private String author;
    private String color;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer status;
    private String downReason;
}
