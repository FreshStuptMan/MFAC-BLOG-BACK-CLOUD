package com.mfac.tool.pojo.vo;

import com.mfac.tool.pojo.entity.ToolType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolTypeListVO extends ToolType {
    private String creatorName;
    private Integer totalTool;
}
