package com.mfac.tool.mapper;

import com.github.pagehelper.Page;
import com.mfac.tool.pojo.dto.ToolListDTO;
import com.mfac.tool.pojo.entity.Tool;
import com.mfac.tool.pojo.vo.ToolListVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ToolMapper {

    /**
     * 创建工具
     * @param tool
     * @return
     */
    Integer create(Tool tool);

    /**
     * 获取工具列表
     * @param toolListDTO
     * @return
     */
    Page<ToolListVO> list(ToolListDTO toolListDTO);


    /**
     * 删除工具
     * @param id
     * @return
     */
    @Delete("delete from tool where id = #{id}")
    Integer delete(Long id);

    /**
     * 更新工具信息
     * @param tool
     * @return
     */
    Integer update(Tool tool);

    /**
     * 获取所有工具
     * @return
     */
    @Select("select * from tool")
    List<Tool> listAll();
}
