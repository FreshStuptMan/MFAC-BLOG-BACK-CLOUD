package com.mfac.tool.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolTypeListDTO {
    private String name;
    private Integer pageNum;
    private Integer pageSize;
}
