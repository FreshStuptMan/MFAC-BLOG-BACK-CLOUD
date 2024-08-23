package com.mfac.blog.controller.admin;

import com.mfac.blog.pojo.PageResult;
import com.mfac.blog.pojo.Result;
import com.mfac.blog.pojo.dto.TagListDTO;
import com.mfac.blog.pojo.entity.Tag;
import com.mfac.blog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/admin/tag")
public class AdminTagController {
    @Resource
    private TagService tagService;


    /**
     * 创建标签
     * @param tag
     * @return
     */
    @PostMapping("/create")
    public Result create(@RequestBody Tag tag) {
        if(tagService.countByNameForCreate(tag.getName()) > 0) {
            return Result.error("创建失败，该标签已存在");
        }
        tagService.create(tag);
        return Result.success();
    }

    /**
     * 获取标签列表
     * @param tagListDTO
     * @return
     */
    @PostMapping("/list")
    public Result list(@RequestBody TagListDTO tagListDTO) {
        PageResult page = tagService.list(tagListDTO);
        return Result.success(page);
    }

    /**
     * 更新标签
     * @param tag
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody Tag tag){
        if(tagService.countByNameForUpdate(tag.getName(), tag.getId()) > 0) {
            return Result.error("更新失败，该标签名已被使用");
        }
        tagService.update(tag);
        return Result.success();
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        if(tagService.countBlogByIdForDelete(id) > 0) {
            return Result.error("删除失败，该标签下存在博客");
        }
        tagService.delete(id);
        return Result.success();
    }
}
