package com.mfac.friendLink.controller.admin;
import com.mfac.friendLink.aop.annotation.FriendLinkEmailMessageSender;
import com.mfac.friendLink.constant.FriendLinkConstant;
import com.mfac.friendLink.pojo.PageResult;
import com.mfac.friendLink.pojo.Result;
import com.mfac.friendLink.pojo.dto.FriendLinkListDTO;
import com.mfac.friendLink.pojo.entity.FriendLink;
import com.mfac.friendLink.service.FriendLinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/admin/friendLink")
public class AdminFriendLinkController {
    @Resource
    private FriendLinkService friendLinkService;


    /**
     * 创建友链
     * @param friendLink
     * @return
     */
    @PostMapping("/create")
    public Result create(@RequestBody FriendLink friendLink) {
        friendLinkService.create(friendLink);
        return Result.success();
    }


    /**
     *  修改友链状态
     * @param friendLink
     * @return
     */
    @FriendLinkEmailMessageSender
    @PostMapping("/changeStatus")
    public Result changeStatus(@RequestBody FriendLink friendLink) {
        friendLinkService.changeStatus(friendLink);
        return Result.success();
    }


    /**
     * 获取友链列表
     * @param friendLinkListDTO
     * @return
     */
    @PostMapping("/list")
    public Result list(@RequestBody FriendLinkListDTO friendLinkListDTO) {
        PageResult page = friendLinkService.list(friendLinkListDTO);
        return Result.success(page);
    }

    /**
     * 删除友链
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        FriendLink friendLink = friendLinkService.detail(id);
        if (friendLink == null) {
            return Result.error("删除失败，友链不存在");
        }
        if (FriendLinkConstant.UP_STATUS.equals(friendLink.getStatus())) {
            return Result.error("删除失败，请先下架友链");
        }
        friendLinkService.delete(id);
        return Result.success();
    }

    /**
     * 更新友链信息
     * @param friendLink
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody FriendLink friendLink) {
        friendLinkService.update(friendLink);
        return Result.success();
    }


    /**
     * 获取友链详情
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable Long id) {
        return Result.success(friendLinkService.detail(id));
    }


}
