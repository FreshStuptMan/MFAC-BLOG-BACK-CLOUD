package com.mfac.email.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendLinkEmailRecord {
    private Long id;
    private String email;
    private Integer status;
    private Integer emailType;
    private String faultReason;
    private Long friendLinkId;
    private LocalDateTime recordTime;
}
