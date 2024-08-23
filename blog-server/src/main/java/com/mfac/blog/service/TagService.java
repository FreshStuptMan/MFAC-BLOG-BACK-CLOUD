package com.mfac.blog.service;



import com.mfac.blog.pojo.PageResult;
import com.mfac.blog.pojo.dto.TagListDTO;
import com.mfac.blog.pojo.entity.Tag;
import com.mfac.blog.pojo.vo.TagListVO;

import java.util.List;

public interface TagService {
    /**
     * 创建标签
     * @param tag
     * @return
     */
    Integer create(Tag tag);

    /**
     * 用于创建时判断标签名是否重复
     * @param name
     * @return
     */
    Integer countByNameForCreate(String name);

    /**
     * 获取标签列表
     * @param tagListDTO
     * @return
     */
    PageResult list(TagListDTO tagListDTO);

    /**
     * 更新标签
     * @param tag
     * @return
     */
    Integer update(Tag tag);

    /**
     * 用于更新时判断标签名是否重复
     * @param name
     * @return
     */
    Integer countByNameForUpdate(String name, Long id);

    /**
     * 删除标签
     * @param id
     * @return
     */
    Integer delete(Long id);

    /**
     * 获取所有标签
     * @return
     */
    List<Tag> listAll();

    /**
     * 随机获取5个标签
     * @return
     */
    List<Tag> random();

    /**
     *  获取所有标签，并包括其下的博客数
     * @return
     */
    List<TagListVO> listAllWithTotal();

    /**
     * 用于删除前判断该标签下是否有博客
     * @param id
     * @return
     */
    Integer countBlogByIdForDelete(Long id);
}
