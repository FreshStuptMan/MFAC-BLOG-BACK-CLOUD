package com.mfac.blog.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.mfac.blog.constent.BlogConstant;
import com.mfac.blog.pojo.PageResult;
import com.mfac.blog.pojo.Result;
import com.mfac.blog.pojo.dto.BlogListDTO;
import com.mfac.blog.pojo.dto.BlogSearchDTO;
import com.mfac.blog.pojo.vo.BlogDetailVO;
import com.mfac.blog.pojo.vo.BlogListVO;
import com.mfac.blog.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogController {
    @Resource
    private BlogService blogService;


    /**
     * 首页获取博客列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list/{pageNum}/{pageSize}")
    @SentinelResource(value = "首页获取博客列表=>/blog/list/{pageNum}/{pageSize}", blockHandler = "listBlockHandler")
    public Result list(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        BlogListDTO blogListDTO = new BlogListDTO();
        blogListDTO.setStatus(BlogConstant.BLOG_STATUS_UP);
        blogListDTO.setPageNum(pageNum);
        blogListDTO.setPageSize(pageSize);
        PageResult page = blogService.list(blogListDTO);
        return Result.success(page);
    }

    /**
     * 获取最新的5条博客
     * @return
     */
    @GetMapping("/newest")
    @SentinelResource(value = "获取最新的5条博客=>/blog/newest", blockHandler = "newestBlockHandler")
    public Result newest() {
        List<BlogListVO> list = blogService.newest();
        return Result.success(list);
    }

    /**
     * 获取博客详情
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    @SentinelResource(value = "获取博客详情=>/blog/detail/{id}", blockHandler = "detailBlockHandler")
    public Result detail(@PathVariable Long id) {
        BlogDetailVO detail = blogService.detail(id);
        if(detail == null) {
            return Result.error("该博客不存在");
        }
        if(!detail.getStatus().equals(BlogConstant.BLOG_STATUS_UP)) {
            return Result.error("博客状态错误");
        }
        return Result.success(detail);
    }

    /**
     * 搜索博客
     * @param blogSearchDTO
     * @return
     */
    @PostMapping("/search")
    @SentinelResource( value = "搜索博客=>/blog/search", blockHandler = "searchBlockHandler")
    public Result search(@RequestBody BlogSearchDTO blogSearchDTO){
        PageResult page = blogService.search(blogSearchDTO);
        return Result.success(page);
    }

    /**
     * 首页获取博客列表 限流 的快速失败处理函数
     * @param pageNum
     * @param pageSize
     * @param e
     * @return
     */
    public static Result listBlockHandler(Integer pageNum, Integer pageSize, BlockException e) {
        return Result.error("服务拥挤，请稍后再试！");
    };

    /**
     * 获取最新的5条博客 限流 的快速失败函数
     * @param e
     * @return
     */
    public static Result newestBlockHandler(BlockException e) {
        return Result.error("服务拥挤，请稍后再试！");
    };

    /**
     * 获取博客详情 限流 的快速失败函数
     * @param id
     * @return
     */
    public static Result detailBlockHandler(Long id, BlockException e) {
        return Result.error("服务拥挤，请稍后再试！");
    };

    /**
     * 搜索博客 限流 的快速失败函数
     * @param blogSearchDTO
     * @return
     */
    public static Result searchBlockHandler(BlogSearchDTO blogSearchDTO, BlockException e) {
        return Result.error("服务拥挤，请稍后再试！");
    };

}
