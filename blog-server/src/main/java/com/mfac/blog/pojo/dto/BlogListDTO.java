package com.mfac.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogListDTO {
    private String title;
    private Long classifyId;
    private Long tagId;
    private Integer status;
    private Integer pageNum;
    private Integer pageSize;
}
