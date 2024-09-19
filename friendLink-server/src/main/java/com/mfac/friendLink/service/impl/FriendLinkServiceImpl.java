package com.mfac.friendLink.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mfac.friendLink.constant.FriendLinkConstant;
import com.mfac.friendLink.mapper.FriendLinkMapper;
import com.mfac.friendLink.pojo.PageResult;
import com.mfac.friendLink.pojo.dto.FriendLinkListDTO;
import com.mfac.friendLink.pojo.entity.FriendLink;
import com.mfac.friendLink.pojo.vo.FriendLinkListVO;
import com.mfac.friendLink.service.FriendLinkService;
import com.mfac.friendLink.util.SnowFlakeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class FriendLinkServiceImpl implements FriendLinkService {
    @Resource
    private FriendLinkMapper friendLinkMapper;


    /**
     * 创建友链
     * @param friendLink
     * @return
     */
    @Override
    public Integer create(FriendLink friendLink) {
        friendLink.setId(SnowFlakeUtil.create());
        friendLink.setStatus(FriendLinkConstant.DEFAULT_STATUS);
        friendLink.setCreateTime(LocalDateTime.now());
        friendLink.setUpdateTime(LocalDateTime.now());
        return friendLinkMapper.create(friendLink);
    }

    /**
     * 修改友链状态
     * @param friendLink
     * @return
     */
    @Override
    public Integer changeStatus(FriendLink friendLink) {
        return friendLinkMapper.changeStatus(friendLink);
    }


    /**
     * 获取友链列表
     * @param friendLinkListDTO
     * @return
     */
    @Override
    public PageResult list(FriendLinkListDTO friendLinkListDTO) {
        PageHelper.startPage(friendLinkListDTO.getPageNum(), friendLinkListDTO.getPageSize());
        Page<FriendLinkListVO> page = friendLinkMapper.list(friendLinkListDTO);
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRecords(page.getResult());
        return result;
    }

    /**
     * 获取友链详情
     * @param id
     * @return
     */
    @Override
    public FriendLink detail(Long id) {
        return friendLinkMapper.detail(id);
    }

    /**
     * 删除友链
     * @param id
     * @return
     */
    @Override
    public Integer delete(Long id) {
        return friendLinkMapper.delete(id);
    }


    /**
     * 更新友链
     * @param friendLink
     * @return
     */
    @Override
    public Integer update(FriendLink friendLink) {
        friendLink.setUpdateTime(LocalDateTime.now());
        return friendLinkMapper.update(friendLink);
    }
}
