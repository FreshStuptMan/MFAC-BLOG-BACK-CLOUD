package com.mfac.tool.mapper;

import com.github.pagehelper.Page;
import com.mfac.tool.pojo.dto.ToolTypeListDTO;
import com.mfac.tool.pojo.entity.ToolType;
import com.mfac.tool.pojo.vo.ToolTypeListVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ToolTypeMapper {
    /**
     * 创建工具类型
     * @param toolType
     * @return
     */
    Integer create(ToolType toolType);

    /**
     * 获取工具类型列表
     * @param toolTypeListDTO
     * @return
     */
    Page<ToolTypeListVO> list(ToolTypeListDTO toolTypeListDTO);

    /**
     * 更新工具类型信息
     * @param toolType
     * @return
     */
    Integer update(ToolType toolType);

    /**
     * 用于创建时判断类型名称是否重复
     * @param toolType
     * @return
     */
    @Select("select COUNT(id) from tool_type where name = #{name}")
    Integer countByNameForCreate(ToolType toolType);


    /**
     * 用于更新时判断类型名称是否重复
     * @param toolType
     * @return
     */
    @Select("select COUNT(id) from tool_type where name = #{name} and id != #{id}")
    Integer countByNameForUpdate(ToolType toolType);

    /**
     * 用于删除时判断该类型下是否有工具
     * @param id
     * @return
     */
    @Select("select COUNT(id) from tool where tool_type_id = #{id}")
    Integer countToolByIdForDelete(Long id);

    /**
     * 删除工具类型
     * @param id
     * @return
     */
    @Delete("delete from tool_type where id = #{id}")
    Integer delete(Long id);

    /**
     * 获取所有工具类型
     * @return
     */
    @Select("select * from tool_type")
    List<ToolType> listAll();
}
