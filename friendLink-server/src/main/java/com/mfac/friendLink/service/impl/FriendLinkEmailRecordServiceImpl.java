package com.mfac.friendLink.service.impl;

import com.mfac.friendLink.mapper.FriendLinkEmailRecordMapper;
import com.mfac.friendLink.pojo.entity.FriendLinkEmailRecord;
import com.mfac.friendLink.service.FriendLinkEmailRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FriendLinkEmailRecordServiceImpl implements FriendLinkEmailRecordService {

    @Resource
    private FriendLinkEmailRecordMapper friendLinkEmailRecordMapper;
    /**
     * 创建记录
     * @param record
     * @return
     */
    @Override
    public Integer create(FriendLinkEmailRecord record) {
        return friendLinkEmailRecordMapper.create(record);
    }

    /**
     * 修改记录状态
     * @param record
     * @return
     */
    @Override
    public Integer changeStatus(FriendLinkEmailRecord record) {
        return friendLinkEmailRecordMapper.changeStatus(record);
    }


    /**
     * 获取记录详情
     * @param id
     * @return
     */
    @Override
    public FriendLinkEmailRecord detail(Long id) {
        return friendLinkEmailRecordMapper.detail(id);
    }
}
