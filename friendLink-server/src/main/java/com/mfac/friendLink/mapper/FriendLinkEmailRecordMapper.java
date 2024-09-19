package com.mfac.friendLink.mapper;

import com.mfac.friendLink.pojo.entity.FriendLinkEmailRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FriendLinkEmailRecordMapper {
    /**
     * 创建记录
     * @param record
     * @return
     */
    Integer create(FriendLinkEmailRecord record);


    /**
     * 更新记录状态
     * @param record
     * @return
     */
    Integer changeStatus(FriendLinkEmailRecord record);

    /**
     * 获取记录详情
     * @param id
     * @return
     */
    @Select("select * from friend_link_email_record where id = #{id}")
    FriendLinkEmailRecord detail(Long id);
}
