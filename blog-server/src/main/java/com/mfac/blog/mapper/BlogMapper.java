package com.mfac.blog.mapper;

import com.github.pagehelper.Page;
import com.mfac.blog.pojo.dto.BlogListDTO;
import com.mfac.blog.pojo.dto.BlogSearchDTO;
import com.mfac.blog.pojo.entity.Blog;
import com.mfac.blog.pojo.vo.BlogDetailVO;
import com.mfac.blog.pojo.vo.BlogListVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BlogMapper {

    /**
     * 创建博客
     * @param blog
     * @return
     */
    Integer create(Blog blog);

    /**
     * 获取博客列表
     * @param blogListDTO
     * @return
     */
    Page<BlogListVO> list(BlogListDTO blogListDTO);

    /**
     * 修改博客状态
     * @param blog
     * @return
     */
    Integer changeStatus(Blog blog);

    /**
     * 删除博客
     * @param id
     * @return
     */
    @Delete("delete from blog where id = #{id}")
    Integer delete(Long id);


    /**
     * 获取博客详情
     * @param id
     * @return
     */
    BlogDetailVO detail(Long id);

    /**
     * 更新博客
     * @param blog
     * @return
     */
    Integer update(Blog blog);

    /**
     * 获取最新的5条博客
     * @return
     */
    List<BlogListVO> newest();

    /**
     * 搜索博客
     * @param blogSearchDTO
     * @return
     */
    Page<BlogListVO> search(BlogSearchDTO blogSearchDTO);

    /**
     * 浏览量批量落库
     * @param data
     * @return
     */
    Integer updateViewBatch(@Param("data") Map<Long, Integer> data);
}
