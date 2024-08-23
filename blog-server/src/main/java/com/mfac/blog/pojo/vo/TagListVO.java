package com.mfac.blog.pojo.vo;

import com.mfac.blog.pojo.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagListVO extends Tag {
    private Integer totalBlog;
    private String creatorName;
}
