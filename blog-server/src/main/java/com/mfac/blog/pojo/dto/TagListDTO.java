package com.mfac.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagListDTO {
    private Integer pageNum;
    private Integer pageSize;
    private String name;
}
