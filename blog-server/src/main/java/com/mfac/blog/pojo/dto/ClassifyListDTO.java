package com.mfac.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassifyListDTO {
    private String name;
    private Integer pageNum;
    private Integer pageSize;
}
