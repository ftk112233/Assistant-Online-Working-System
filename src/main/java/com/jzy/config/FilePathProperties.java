package com.jzy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName FilePathProperties
 * @description 读取自定义资源文件FilePath.properties的配置类
 * @date 2019/11/18 10:37
 **/
@Configuration
@PropertySource("classpath:myConfig/filePath.properties")
public class FilePathProperties {
    @Value("${upload.userIcon.path}")
    private String uploadUserIconPath;

    public String getUploadUserIconPath() {
        return uploadUserIconPath;
    }

    public void setUploadUserIconPath(String uploadUserIconPath) {
        this.uploadUserIconPath = uploadUserIconPath;
    }
}
