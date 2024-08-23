package com.mfac.blog.mapper;

import com.github.pagehelper.Page;
import com.mfac.blog.pojo.dto.TagListDTO;
import com.mfac.blog.pojo.entity.Tag;
import com.mfac.blog.pojo.vo.TagListVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagMapper {
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
    @Select("select COUNT(id) from tag where name = #{name}")
    Integer countByNameForCreate(String name);

    /**
     * 获取标签列表
     * @param tagListDTO
     * @return
     */
    Page<TagListVO> list(TagListDTO tagListDTO);

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
    @Select("select COUNT(id) from tag where name = #{id} and id != #{id}")
    Integer countByNameForUpdate(String name, Long id);


    /**
     * 删除标签
     * @param id
     * @return
     */
    @Delete("delete from tag where id = #{id}")
    Integer delete(Long id);

    /**
     * 获取所有标签
     * @return
     */
    @Select("select * from tag")
    List<Tag> listAll();

    /**
     * 随机获取5个标签
     * @return
     */
    @Select("select * from tag order by RAND() limit 5")
    List<Tag> random();

    /**
     *  获取所有标签，并包括其下的博客数
     * @return
     */
    List<TagListVO> listAllWithTotal();




    /**
     * 博客-标签关系表相关
     */


    /**
     * 向博客-标签关系表中插入数据
     * @param tagIds
     * @param blogId
     * @return
     */
    Integer createBlogTagBatch(List<Long> tagIds, Long blogId);

    /**
     * 根据博客编号查询标签
     * @param blogId
     * @return
     */
    List<Tag> listFromBlogTagByBlogId(Long blogId);

    /**
     * 根据博客编号删除
     * @param blogId
     * @return
     */
    @Delete("delete from blog_tag where blog_id = #{blogId}")
    Integer deleteBlogTagByBlogId(Long blogId);

    /**
     * 用于删除前判断该标签下是否有博客
     * @param id
     * @return
     */
    @Select("select COUNT(blog_id) from blog_tag where tag_id = #{id}")
    Integer countBlogByIdForDelete(Long id);

}
