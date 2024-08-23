package com.mfac.tool.controller.admin;

import com.mfac.tool.pojo.PageResult;
import com.mfac.tool.pojo.Result;
import com.mfac.tool.pojo.dto.ToolListDTO;
import com.mfac.tool.pojo.entity.Tool;
import com.mfac.tool.service.ToolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/tool")
@Slf4j
public class AdminToolController {
    @Resource
    private ToolService toolService;

    /**
     * 创建工具
     * @param tool
     * @return
     */
    @PostMapping("/create")
    public Result create(@RequestBody Tool tool) {
        toolService.create(tool);
        return Result.success();
    }


    /**
     * 获取工具列表
     * @param toolListDTO
     * @return
     */
    @PostMapping("/list")
    public Result list(@RequestBody ToolListDTO toolListDTO) {
        PageResult page = toolService.list(toolListDTO);
        return Result.success(page);
    }

    /**
     * 删除工具
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        toolService.delete(id);
        return Result.success();
    }

    /**
     * 更新工具信息
     * @param tool
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody Tool tool) {
        toolService.update(tool);
        return Result.success();
    }
}
