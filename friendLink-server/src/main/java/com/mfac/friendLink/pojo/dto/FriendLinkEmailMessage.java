package com.mfac.friendLink.pojo.dto;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendLinkEmailMessage {
    private Long id;
    private Long userId;
}
