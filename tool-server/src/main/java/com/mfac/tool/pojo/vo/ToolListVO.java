package com.mfac.tool.pojo.vo;
import com.mfac.tool.pojo.entity.Tool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolListVO extends Tool {
    private String creatorName;
    private String toolTypeName;
}
