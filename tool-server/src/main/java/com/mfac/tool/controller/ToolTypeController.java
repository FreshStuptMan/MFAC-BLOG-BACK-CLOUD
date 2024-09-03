package com.mfac.tool.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.mfac.tool.pojo.Result;
import com.mfac.tool.pojo.entity.ToolType;
import com.mfac.tool.pojo.vo.ToolTypeDetailVO;
import com.mfac.tool.service.ToolTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/toolType")
public class ToolTypeController {
    @Resource
    private ToolTypeService toolTypeService;

    /**
     * 获取所有工具类型
     * @return
     */
    @GetMapping("/listAll")
    @SentinelResource(value = "获取所有工具类型=>/toolType/listAll", blockHandler = "listAllBlockHandler")
    public Result listAll() {
        List<ToolType> types = toolTypeService.listAll();
        return Result.success(types);
    }

    /**
     * 获取所有工具类型和其下的所有工具
     * @return
     */
    @GetMapping("/listDetailAll")
    @SentinelResource(value = "获取所有工具类型和其下的所有工具=>/toolType/listDetailAll", blockHandler = "listDetailAllBlockHandler")
    public Result listDetailAll() {
        List<ToolTypeDetailVO> list = toolTypeService.listDetailAll();
        return Result.success(list);
    }

    /**
     * 获取所有工具类型 限流 的快速失败函数
     * @param e
     * @return
     */
    public static Result listAllBlockHandler(BlockException e) {
        return Result.error("服务拥挤，请稍后再试！");
    }

    /**
     * 获取所有工具类型和其下的所有工具 限流 的快速失败函数
     * @param e
     * @return
     */
    public static Result listDetailAllBlockHandler(BlockException e) {
        return Result.error("服务拥挤，请稍后再试！");
    }

}
