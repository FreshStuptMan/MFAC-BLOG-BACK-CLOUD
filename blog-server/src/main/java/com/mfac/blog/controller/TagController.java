package com.mfac.blog.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
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
    @SentinelResource(value = "获取所有标签=>/tag/listAll", blockHandler = "listAllBlockHandler")
    public Result listAll() {
        List<Tag> list = tagService.listAll();
        return Result.success(list);
    }

    /**
     * 随机获取5个标签
     * @return
     */
    @GetMapping("/random")
    @SentinelResource(value = "随机获取5个标签=>/tag/random", blockHandler = "randomBlockHandler")
    public Result random() {
        List<Tag> list = tagService.random();
        return Result.success(list);
    }

    /**
     *  获取所有标签，并包括其下的博客数
     * @return
     */
    @GetMapping("/listAllWithTotal")
    @SentinelResource(value = "获取所有标签，并包括其下的博客数=>/tag/listAllWithTotal", blockHandler = "listAllWithTotalBlockHandler")
    public Result listAllWithTotal() {
        List<TagListVO> list = tagService.listAllWithTotal();
        return Result.success(list);
    }

    /**
     * 获取所有标签 限流 的快速失败函数
     * @param e
     * @return
     */
    public static Result listAllBlockHandler(BlockException e) {
        return Result.error("服务拥挤，请稍后再试");
    }

    /**
     * 随机获取5个标签 限流 的快速失败函数
     * @param e
     * @return
     */
    public static Result randomBlockHandler(BlockException e) {
        return Result.error("服务拥挤，请稍后再试");
    }

    /**
     * 获取所有标签，并包括其下的博客数 限流 的快速失败函数
     * @param e
     * @return
     */
    public static Result listAllWithTotalBlockHandler(BlockException e) {
        return Result.error("服务拥挤，请稍后再试");
    }

}
