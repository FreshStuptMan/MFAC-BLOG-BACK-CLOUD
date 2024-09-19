package com.mfac.friendLink.controller.admin;

import com.mfac.friendLink.pojo.Result;
import com.mfac.friendLink.pojo.entity.FriendLink;
import com.mfac.friendLink.pojo.entity.FriendLinkEmailRecord;
import com.mfac.friendLink.service.FriendLinkEmailRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/admin/friendLinkEmailRecord")
public class AdminFriendLinkEmailRecordController {
    @Resource
    private FriendLinkEmailRecordService friendLinkEmailRecordService;


    /**
     * 修改邮件记录状态
     * @param friendLinkEmailRecord
     * @return
     */
    @PostMapping("/changeStatus")
    public Result changeStatus(@RequestBody FriendLinkEmailRecord friendLinkEmailRecord) {
        friendLinkEmailRecordService.changeStatus(friendLinkEmailRecord);
        return Result.success();
    }

    /**
     * 获取记录详情
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable Long id) {
        return Result.success(friendLinkEmailRecordService.detail(id));
    }


}
