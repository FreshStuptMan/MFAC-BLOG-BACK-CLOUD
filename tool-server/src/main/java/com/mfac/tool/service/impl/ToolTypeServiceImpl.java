package com.mfac.tool.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mfac.tool.mapper.ToolMapper;
import com.mfac.tool.mapper.ToolTypeMapper;
import com.mfac.tool.pojo.PageResult;
import com.mfac.tool.pojo.dto.ToolTypeListDTO;
import com.mfac.tool.pojo.entity.Tool;
import com.mfac.tool.pojo.entity.ToolType;
import com.mfac.tool.pojo.vo.ToolTypeDetailVO;
import com.mfac.tool.pojo.vo.ToolTypeListVO;
import com.mfac.tool.service.ToolTypeService;
import com.mfac.tool.util.SnowFlakeUtil;
import com.mfac.tool.util.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ToolTypeServiceImpl implements ToolTypeService {
    @Resource
    private ToolTypeMapper toolTypeMapper;

    @Resource
    private ToolMapper toolMapper;

    /**
     * 创建工具分类
     * @param toolType
     * @return
     */
    @Override
    public Integer create(ToolType toolType) {
        toolType.setId(SnowFlakeUtil.create());
        toolType.setCreateTime(LocalDateTime.now());
        toolType.setCreatorId(ThreadLocalUtil.getCurrentId());
        return toolTypeMapper.create(toolType);
    }


    /**
     * 获取工具类型列表
     * @param toolTypeListDTO
     * @return
     */
    @Override
    public PageResult list(ToolTypeListDTO toolTypeListDTO) {
        PageHelper.startPage(toolTypeListDTO.getPageNum(), toolTypeListDTO.getPageSize());
        Page<ToolTypeListVO> page = toolTypeMapper.list(toolTypeListDTO);
        PageResult result = new PageResult(page.getTotal(), page.getResult());
        return result;
    }


    /**
     * 更新工具类型信息
     * @param toolType
     * @return
     */
    @Override
    public Integer update(ToolType toolType) {
        return toolTypeMapper.update(toolType);
    }

    /**
     * 用于创建时判断类型名称是否重复
     * @param toolType
     * @return
     */
    @Override
    public Integer countByNameForCreate(ToolType toolType) {
        return toolTypeMapper.countByNameForCreate(toolType);
    }

    /**
     * 用于更新时判断类型名称是否重复
     * @param toolType
     * @return
     */
    @Override
    public Integer countByNameForUpdate(ToolType toolType) {
        return toolTypeMapper.countByNameForUpdate(toolType);
    }

    /**
     * 用于删除时判断该类型下是否有工具
     * @param id
     * @return
     */
    @Override
    public Integer countToolByIdForDelete(Long id) {
        return toolTypeMapper.countToolByIdForDelete(id);
    }


    /**
     * 删除工具类型
     * @param id
     * @return
     */
    @Override
    public Integer delete(Long id) {
        return toolTypeMapper.delete(id);
    }

    /**
     * 获取所有工具类型
     * @return
     */
    @Override
    public List<ToolType> listAll() {
        return toolTypeMapper.listAll();
    }


    /**
     * 获取所有工具类型和其下的所有工具
     * @return
     */
    @Override
    public List<ToolTypeDetailVO> listDetailAll() {
        List<ToolType> types = listAll();
        List<ToolTypeDetailVO> result = BeanUtil.copyToList(types, ToolTypeDetailVO.class);
        List<Tool> tools = toolMapper.listAll();
        Map<Long, List<Tool>> map = tools.stream().collect(Collectors.groupingBy(Tool::getToolTypeId));
        result.forEach(detail -> {
            detail.setTools(map.get(detail.getId()));
        });
        return result;
    }
}
