package com.mfac.blog.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.mfac.blog.pojo.Result;
import com.mfac.blog.pojo.entity.Tag;
import com.mfac.blog.pojo.vo.TagListVO;
import com.mfac.blog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tag")
public class TagController {
    @Resource
    private TagService tagService;

    /**
     * 获取所有标签
     * @return
     */
    @GetMapping("/listAll")
    @SentinelResource("获取所有标签=>/tag/listAll")
    public Result listAll() {
        List<Tag> list = tagService.listAll();
        return Result.success(list);
    }

    /**
     * 随机获取5个标签
     * @return
     */
    @GetMapping("/random")
    @SentinelResource("随机获取5个标签=>/tag/random")
    public Result random() {
        List<Tag> list = tagService.random();
        return Result.success(list);
    }

    /**
     *  获取所有标签，并包括其下的博客数
     * @return
     */
    @GetMapping("/listAllWithTotal")
    @SentinelResource("获取所有标签，并包括其下的博客数=>/tag/listAllWithTotal")
    public Result listAllWithTotal() {
        List<TagListVO> list = tagService.listAllWithTotal();
        return Result.success(list);
    }
}
