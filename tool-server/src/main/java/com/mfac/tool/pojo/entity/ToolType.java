package com.mfac.tool.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolType implements Serializable {
    private Long id;
    private String name;
    private Long creatorId;
    private LocalDateTime createTime;
}
