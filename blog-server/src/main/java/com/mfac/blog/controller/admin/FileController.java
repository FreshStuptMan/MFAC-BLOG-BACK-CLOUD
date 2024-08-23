package com.mfac.blog.controller.admin;

import com.mfac.blog.pojo.Result;
import com.mfac.blog.properties.AliOssProperties;
import com.mfac.blog.util.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admin/file")
public class FileController {

    @Resource
    private AliOssProperties aliOssProperties;

    /**
     * 图像上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/image")
    public Result image(MultipartFile file) throws IOException {
        //传阿里云
        String originalName = file.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf("."));
        String uploadName = UUID.randomUUID().toString()+extension;
        String filePath = AliOssUtil.upload(file.getBytes(), uploadName,
                aliOssProperties.getBucketName(),
                aliOssProperties.getEndpoint(), aliOssProperties.getAccessKeyId(), aliOssProperties.getAccessKeySecret());
        return Result.success(filePath);
    }

    /**
     * 接收批量上传的图片
     * @param files
     * @return
     */
    @PostMapping("/batchImages")
    public Result batchImages(@RequestParam Map<String, MultipartFile> files) {
        HashMap<Integer, String> resultMap = new HashMap<>();
        for (int i=1;i<=files.size();i++) {
            MultipartFile file = files.get(String.valueOf(i));
            if (file.isEmpty()) {
                return Result.error("图片上传异常，请重试");
            }
            try {
                String originalName = file.getOriginalFilename();
                String extension = originalName.substring(originalName.lastIndexOf("."));
                String uploadName = UUID.randomUUID().toString()+extension;
                String filePath = AliOssUtil.upload(file.getBytes(), uploadName,
                        aliOssProperties.getBucketName(),
                        aliOssProperties.getEndpoint(), aliOssProperties.getAccessKeyId(), aliOssProperties.getAccessKeySecret());
                resultMap.put(i, filePath);
            } catch (Exception e) {
                log.info("FileController => batchImages : {}", e);
                return Result.error("图片上传异常，请重试");
            }
        }
        return Result.success(resultMap);
    }
}

