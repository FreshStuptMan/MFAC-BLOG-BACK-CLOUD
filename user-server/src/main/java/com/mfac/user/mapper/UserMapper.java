package com.mfac.user.mapper;


import com.mfac.user.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    /**
     * 根据账号查询用户
     * @param account
     * @return
     */
    @Select("select * from user where account = #{account}")
    User selectByAccount(String account);

    /**
     * 根据编号查询用户
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User selectById(Long id);

    /**
     * 修改密码
     * @param user
     * @return
     */
    @Update("update user set password = #{password} where id = #{id}")
    Integer changePassword(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    Integer update(User user);
}
