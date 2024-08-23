package com.mfac.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogSearchDTO {
    private String key;
    private Long classifyId;
    private Long tagId;
    private Integer pageNum;
    private Integer pageSize;
}
