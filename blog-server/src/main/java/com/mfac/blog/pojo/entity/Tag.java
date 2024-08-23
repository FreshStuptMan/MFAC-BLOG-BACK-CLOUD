package com.mfac.blog.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag implements Serializable {
    private Long id;
    private String name;
    private String color;
    private Long creatorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
