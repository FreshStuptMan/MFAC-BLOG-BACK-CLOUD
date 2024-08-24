package com.mfac.user.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.mfac.user.mapper.UserMapper;
import com.mfac.user.pojo.entity.User;
import com.mfac.user.pojo.vo.UserDetailVO;
import com.mfac.user.service.UserService;
import com.mfac.user.util.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 根据账号查询用户
     * @param account
     * @return
     */
    @Override
    public User selectByAccount(String account) {
        return userMapper.selectByAccount(account);
    }


    /**
     * 获取用户信息
     * @return
     */
    @Override
    public UserDetailVO detail() {
        User user = userMapper.selectById(ThreadLocalUtil.getCurrentId());
        UserDetailVO detail = new UserDetailVO();
        BeanUtils.copyProperties(user, detail);
        return detail;
    }


    /**
     * 修改密码
     * @param user
     * @return
     */
    @Override
    public Integer changePassword(User user) {
        String newPwd = BCrypt.hashpw(user.getPassword());
        user.setPassword(newPwd);
        return userMapper.changePassword(user);
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @Override
    public Integer update(User user) {
        return userMapper.update(user);
    }
}
