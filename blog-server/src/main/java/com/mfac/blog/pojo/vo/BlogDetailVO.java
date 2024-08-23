package com.mfac.blog.pojo.vo;

import com.mfac.blog.pojo.entity.Blog;
import com.mfac.blog.pojo.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogDetailVO extends Blog {
    private String authorAvatar;
    private String classifyName;
    private String authorName;
    private List<Tag> tags;
}
