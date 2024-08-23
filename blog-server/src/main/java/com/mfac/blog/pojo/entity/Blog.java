package com.mfac.blog.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog implements Serializable {
    private Long id;
    private String title;
    private String description;
    private String avatar;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime publishTime;
    private Long authorId;
    private Integer status;
    private String content;
    private Long classifyId;
    private Integer types;
}
