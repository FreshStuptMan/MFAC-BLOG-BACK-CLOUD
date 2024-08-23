package com.mfac.tool.pojo.vo;

import com.mfac.tool.pojo.entity.Tool;
import com.mfac.tool.pojo.entity.ToolType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolTypeDetailVO extends ToolType {
    private List<Tool> tools;
}
