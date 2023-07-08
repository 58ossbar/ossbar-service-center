package com.ossbar.platform.sys.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 创蓝图片上传组件文件上传处理类
 * @author zhuq
 * @create 2022-08-25 14:16
 * @email zhuqiang@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@RestController
@RequestMapping("/api/cbupload")
public class UploaderController {

    @Autowired
    private UploadFileUtils uploadFileUtils;

    /**
     * 上传图片文件
     * @param picture
     * @param type
     * @return
     */
    @PostMapping("/uploadPic")
    public R uploadPic(@RequestPart(value = "file") MultipartFile picture, @RequestParam(value = "type", defaultValue = "0") String type) {
        return uploadFileUtils.uploadImage(picture, type);
    }


}
