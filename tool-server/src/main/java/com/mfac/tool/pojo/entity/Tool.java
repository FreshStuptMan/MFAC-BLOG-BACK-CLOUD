package com.mfac.tool.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tool implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String link;
    private String avatar;
    private String color;
    private Long creatorId;
    private Long toolTypeId;
    private LocalDateTime createTime;
}
