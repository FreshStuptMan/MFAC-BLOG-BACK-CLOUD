package com.mfac.blog.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mfac.blog.mapper.ClassifyMapper;
import com.mfac.blog.pojo.PageResult;
import com.mfac.blog.pojo.dto.ClassifyListDTO;
import com.mfac.blog.pojo.entity.Classify;
import com.mfac.blog.pojo.vo.ClassifyListVO;
import com.mfac.blog.service.ClassifyService;
import com.mfac.blog.util.SnowFlakeUtil;
import com.mfac.blog.util.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ClassifyServiceImpl implements ClassifyService {
    @Resource
    private ClassifyMapper classifyMapper;

    /**
     * 创建分类
     * @param classify
     * @return
     */
    @Override
    public Integer create(Classify classify) {
        Long id = SnowFlakeUtil.create();
        classify.setId(id);
        classify.setCreatorId(ThreadLocalUtil.getCurrentId());
        classify.setCreateTime(LocalDateTime.now());
        classify.setUpdateTime(LocalDateTime.now());
        return classifyMapper.create(classify);
    }

    /**
     * 用于创建时判断分类名是否重复
     * @param name
     * @return
     */
    @Override
    public Integer countByNameForCreate(String name) {
        return classifyMapper.countByNameForCreate(name);
    }

    /**
     * 获取分类列表
     * @param classifyListDTO
     * @return
     */
    @Override
    public PageResult list(ClassifyListDTO classifyListDTO) {
        PageHelper.startPage(classifyListDTO.getPageNum(), classifyListDTO.getPageSize());
        Page<ClassifyListVO> page = classifyMapper.list(classifyListDTO);
        PageResult result = new PageResult(page.getTotal(), page.getResult());
        return result;
    }


    /**
     * 用于更新时判断分类是否重复
     * @param name
     * @param id
     * @return
     */
    @Override
    public Integer countByNameForUpdate(String name, Long id) {
        return classifyMapper.countByNameForUpdate(name, id);
    }

    /**
     * 更新分类
     * @param classify
     * @return
     */
    @Override
    public Integer update(Classify classify) {
        classify.setUpdateTime(LocalDateTime.now());
        return classifyMapper.update(classify);
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @Override
    public Integer delete(Long id) {
        return classifyMapper.delete(id);
    }


    /**
     * 获取所有分类
     * @return
     */
    @Override
    public List<Classify> listAll() {
        return classifyMapper.listAll();
    }


    /**
     * 随机获取5个分类
     * @return
     */
    @Override
    public List<Classify> random() {
        return classifyMapper.random();
    }

    /**
     * 获取所有分类，并且包括相关的博客数
     * @return
     */
    @Override
    public List<ClassifyListVO> listAllWithTotal() {
        return classifyMapper.listAllWithTotal();
    }

    /**
     * 用于删除前判断该分类下是否有标签
     * @param id
     * @return
     */
    @Override
    public Integer countBlogByIdForDelete(Long id) {
        return classifyMapper.countBlogByIdForDelete(id);
    }
}
