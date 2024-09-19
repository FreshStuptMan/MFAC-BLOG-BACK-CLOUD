package com.mfac.email.client;

import com.mfac.email.config.OpenFeignConfiguration;
import com.mfac.email.pojo.Result;
import com.mfac.email.pojo.entity.FriendLinkEmailRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "friendLink-service", configuration = OpenFeignConfiguration.class)
public interface FriendLinkClient {
    @GetMapping("/admin/friendLink/detail/{id}")
    Result friendLinkDetail(@PathVariable("id") Long id);

    @GetMapping("/admin/friendLinkEmailRecord/detail/{id}")
    Result friendLinkEmailRecordDetail(@PathVariable("id") Long id);

    @PostMapping("/admin/friendLinkEmailRecord/changeStatus")
    Result friendLinkEmailRecordChangeStatus(@RequestBody FriendLinkEmailRecord friendLinkEmailRecord);
}
