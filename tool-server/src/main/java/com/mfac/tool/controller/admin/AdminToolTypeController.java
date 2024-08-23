package com.mfac.tool.controller.admin;

import com.mfac.tool.pojo.PageResult;
import com.mfac.tool.pojo.Result;
import com.mfac.tool.pojo.dto.ToolTypeListDTO;
import com.mfac.tool.pojo.entity.ToolType;
import com.mfac.tool.service.ToolTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/toolType")
@Slf4j
public class AdminToolTypeController {
    @Resource
    private ToolTypeService toolTypeService;


    /**
     * 创建工具类型
     * @param toolType
     * @return
     */
    @PostMapping("/create")
    public Result create(@RequestBody ToolType toolType) {
        if(toolTypeService.countByNameForCreate(toolType) > 0) {
            return Result.error("创建失败，该类型名称已被使用");
        }
        toolTypeService.create(toolType);
        return Result.success();
    }


    /**
     * 获取工具类型列表
     * @param toolTypeListDTO
     * @return
     */
    @PostMapping("/list")
    public Result list(@RequestBody ToolTypeListDTO toolTypeListDTO) {
        PageResult page = toolTypeService.list(toolTypeListDTO);
        return Result.success(page);
    }

    /**
     * 更新工具类型信息
     * @param toolType
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody ToolType toolType) {
        if(toolTypeService.countByNameForUpdate(toolType) > 0) {
            return Result.error("更新失败，该类型名称已被使用");
        }
        toolTypeService.update(toolType);
        return Result.success();
    }

    /**
     * 删除工具类型
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        if(toolTypeService.countToolByIdForDelete(id) > 0) {
            return Result.error("删除失败，该类型下存在工具");
        }
        toolTypeService.delete(id);
        return Result.success();
    }
}
