package com.mfac.user.controller;

import cn.hutool.crypto.digest.BCrypt;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.mfac.user.pojo.Result;
import com.mfac.user.pojo.dto.LoginDTO;
import com.mfac.user.pojo.entity.User;
import com.mfac.user.pojo.vo.LoginVO;
import com.mfac.user.properties.JWTProperties;
import com.mfac.user.service.UserService;
import com.mfac.user.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class LoginController {
    @Resource
    private UserService userService;
    @Resource
    private JWTProperties jwtProperties;

    /**
     * 登录
     * @param loginDTO
     * @return
     */
    @PostMapping("/login")
    @SentinelResource(value = "登录=>/login", blockHandler = "loginBlockHandler")
    public Result login(@RequestBody LoginDTO loginDTO) {
        User user = userService.selectByAccount(loginDTO.getAccount());
        if(user == null) {
            return Result.error("登录失败，该账户不存在");
        }
        if(!BCrypt.checkpw(loginDTO.getPassword(), user.getPassword())) {
            return Result.error("登录失败，密码错误");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put(jwtProperties.getUserId(),user.getId());
        String token = JWTUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);
        LoginVO loginVO = new LoginVO(token);
        return Result.success(loginVO);
    }

    /**
     * 登录 限流 的快速失败函数
     * @param loginDTO
     * @param e
     * @return
     */
    public static Result loginBlockHandler(LoginDTO loginDTO, BlockException e) {
        return Result.error("服务拥挤，请1稍后再试！");
    }
}
