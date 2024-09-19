package com.mfac.friendLink.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendLinkListDTO {
    private Integer pageNum;
    private Integer pageSize;
    private Integer status;
    private String author;
    private String name;
}

