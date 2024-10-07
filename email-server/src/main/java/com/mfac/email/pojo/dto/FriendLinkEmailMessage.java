package com.mfac.email.pojo.dto;

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
