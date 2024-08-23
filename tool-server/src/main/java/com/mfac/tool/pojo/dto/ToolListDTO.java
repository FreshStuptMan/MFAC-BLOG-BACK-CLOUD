package com.mfac.tool.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolListDTO {
    private String name;
    private Long toolTypeId;
    private Integer pageNum;
    private Integer pageSize;
}
