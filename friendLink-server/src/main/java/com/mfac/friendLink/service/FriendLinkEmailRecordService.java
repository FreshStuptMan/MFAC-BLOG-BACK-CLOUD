package com.mfac.friendLink.service;

import com.mfac.friendLink.pojo.entity.FriendLinkEmailRecord;

public interface FriendLinkEmailRecordService {
    /**
     * 创建记录
     * @param record
     * @return
     */
    Integer create(FriendLinkEmailRecord record);

    /**
     * 修改邮件记录状态
     * @param record
     * @return
     */
    Integer changeStatus(FriendLinkEmailRecord record);

    /**
     * 获取记录详情
     * @param id
     * @return
     */
    FriendLinkEmailRecord detail(Long id);
}
