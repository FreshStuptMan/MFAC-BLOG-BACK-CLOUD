package com.mfac.blog.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.mfac.blog.mapper.TagMapper;
import com.mfac.blog.pojo.PageResult;
import com.mfac.blog.pojo.dto.TagListDTO;
import com.mfac.blog.pojo.entity.Tag;
import com.mfac.blog.pojo.vo.TagListVO;
import com.mfac.blog.service.TagService;
import com.mfac.blog.util.SnowFlakeUtil;
import com.mfac.blog.util.ThreadLocalUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagMapper tagMapper;

    /**
     * 创建标签
     * @param tag
     * @return
     */
    @Override
    public Integer create(Tag tag) {
        Long id = SnowFlakeUtil.create();
        tag.setId(id);
        tag.setCreatorId(ThreadLocalUtil.getCurrentId());
        tag.setCreateTime(LocalDateTime.now());
        tag.setUpdateTime(LocalDateTime.now());
        return tagMapper.create(tag);
    }

    /**
     * 用于创建时判断标签名是否重复
     * @param name
     * @return
     */
    @Override
    public Integer countByNameForCreate(String name) {
        return tagMapper.countByNameForCreate(name);
    }

    /**
     * 获取标签列表
     * @param tagListDTO
     * @return
     */
    @Override
    public PageResult list(TagListDTO tagListDTO) {
        PageHelper.startPage(tagListDTO.getPageNum(), tagListDTO.getPageSize());
        Page<TagListVO> page = tagMapper.list(tagListDTO);
        PageResult result = new PageResult(page.getTotal(), page.getResult());
        return result;
    }

    /**
     * 更新标签
     * @param tag
     * @return
     */
    @Override
    public Integer update(Tag tag) {
        tag.setUpdateTime(LocalDateTime.now());
        return tagMapper.update(tag);
    }

    /**
     * 用于更新时判断标签名是否重复
     * @param name
     * @return
     */
    @Override
    public Integer countByNameForUpdate(String name, Long id) {
        return tagMapper.countByNameForUpdate(name, id);
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Integer delete(Long id) {
        return tagMapper.delete(id);
    }

    /**
     * 获取所有标签
     * @return
     */
    @Override
    public List<Tag> listAll() {
        return tagMapper.listAll();
    }

    /**
     *  随机获取5个标签
     * @return
     */
    @Override
    public List<Tag> random() {
        return tagMapper.random();
    }

    /**
     *  获取所有标签，并包括其下的博客数
     * @return
     */
    @Override
    public List<TagListVO> listAllWithTotal() {
        return tagMapper.listAllWithTotal();
    }

    /**
     * 用于删除前判断该标签下是否有博客
     * @param id
     * @return
     */
    @Override
    public Integer countBlogByIdForDelete(Long id) {
        return tagMapper.countBlogByIdForDelete(id);
    }
}
