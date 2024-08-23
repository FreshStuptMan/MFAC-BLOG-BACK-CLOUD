package com.mfac.blog.mapper;

import com.github.pagehelper.Page;
import com.mfac.blog.pojo.dto.ClassifyListDTO;
import com.mfac.blog.pojo.entity.Classify;
import com.mfac.blog.pojo.vo.ClassifyListVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassifyMapper {
    /**
     * 创建分类
     * @param classify
     * @return
     */
    Integer create(Classify classify);

    /**
     * 用于创建时判断分类名是否重复
     * @param name
     * @return
     */
    @Select("select COUNT(id) from classify where name = #{name}")
    Integer countByNameForCreate(String name);


    /**
     * 获取分类列表
     * @param classifyListDTO
     * @return
     */
    Page<ClassifyListVO> list(ClassifyListDTO classifyListDTO);


    /**
     * 用于更新时判断分类名是否重复
     * @param name
     * @param id
     * @return
     */
    @Select("select COUNT(id) from classify where name = #{name} and id != #{id}")
    Integer countByNameForUpdate(String name, Long id);

    /**
     * 更新分类
     * @param classify
     * @return
     */
    Integer update(Classify classify);

    /**
     * 删除分类
     * @param id
     * @return
     */
    @Delete("delete from classify where id = #{id}")
    Integer delete(Long id);

    /**
     * 获取所有分类
     * @return
     */
    @Select("select * from classify")
    List<Classify> listAll();

    /**
     * 随机获取5个分类
     * @return
     */
    @Select("select * from classify order by RAND() limit 5")
    List<Classify> random();

    /**
     * 获取所有分类，并且包括相关的博客数
     * @return
     */
    List<ClassifyListVO> listAllWithTotal();

    /**
     * 用于删除前判断该分类下是否有标签
     * @param id
     * @return
     */
    @Select("select COUNT(id) from blog where blog.classify_id = #{id}")
    Integer countBlogByIdForDelete(Long id);
}
