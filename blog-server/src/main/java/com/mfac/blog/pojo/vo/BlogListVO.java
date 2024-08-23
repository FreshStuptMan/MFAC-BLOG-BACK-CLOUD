package com.mfac.blog.pojo.vo;

import com.mfac.blog.pojo.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogListVO
{
    private Long id;
    private String title;
    private String description;
    private String avatar;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime publishTime;
    private Long authorId;
    private Integer status;
    private Long classifyId;
    private Integer types;
    private String authorName;
    private String authorAvatar;
    private String classifyName;
    private List<Tag> tags;
}
