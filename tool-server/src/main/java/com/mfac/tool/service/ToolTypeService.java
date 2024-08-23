package com.mfac.tool.service;


import com.mfac.tool.pojo.PageResult;
import com.mfac.tool.pojo.dto.ToolTypeListDTO;
import com.mfac.tool.pojo.entity.ToolType;
import com.mfac.tool.pojo.vo.ToolTypeDetailVO;

import java.util.List;

public interface ToolTypeService {

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
    PageResult list(ToolTypeListDTO toolTypeListDTO);

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
    Integer countByNameForCreate(ToolType toolType);


    /**
     * 用于更新时判断类型名称是否重复
     * @param toolType
     * @return
     */
    Integer countByNameForUpdate(ToolType toolType);


    /**
     * 用于删除时判断该类型下是否有工具
     * @param id
     * @return
     */
    Integer countToolByIdForDelete(Long id);

    /**
     * 删除工具类型
     * @param id
     * @return
     */
    Integer delete(Long id);

    /**
     * 获取所有工具类型
     * @return
     */
    List<ToolType> listAll();

    /**
     * 获取所有工具类型和其下的所有工具
     * @return
     */
    List<ToolTypeDetailVO> listDetailAll();
}
