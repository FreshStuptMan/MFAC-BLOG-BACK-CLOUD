package com.mfac.user.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailVO {
    private Long id;
    private String account;
    private String name;
    private String avatar;
}
