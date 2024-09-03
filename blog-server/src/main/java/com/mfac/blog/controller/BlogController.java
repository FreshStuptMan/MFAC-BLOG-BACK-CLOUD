package com.mfac.blog.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
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
    @SentinelResource("首页获取博客列表=>/blog/list/{pageNum}/{pageSize}")
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
    @SentinelResource("获取最新的5条博客=>/blog/newest")
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
    @SentinelResource("获取博客详情=>/blog/detail/{id}")
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
    @SentinelResource("搜索博客=>/blog/search")
    public Result search(@RequestBody BlogSearchDTO blogSearchDTO){
        PageResult page = blogService.search(blogSearchDTO);
        return Result.success(page);
    }
}
