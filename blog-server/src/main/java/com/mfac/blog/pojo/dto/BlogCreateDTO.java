package com.mfac.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogCreateDTO {
    private String title;
    private Integer types;
    private String content;
    private String description;
    private Long classifyId;
    private String avatar;
    private Integer status;
    private List<Long> tagIds;
}
