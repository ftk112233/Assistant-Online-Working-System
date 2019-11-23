package com.jzy.config;

import com.jzy.manager.util.FileUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.File;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName FilePathProperties
 * @description 读取自定义资源文件FilePath.properties的配置类
 * @date 2019/11/18 10:37
 **/
@Data
@Configuration
@PropertySource("classpath:myConfig/filePath.properties")
public class FilePathProperties {
    private static final String SEPARATOR =File.separator;

    @Value("${project.root.path}")
    private String root;

    @Value("${upload.userIcon.directory}")
    private String uploadUserIconDirectory;

    @Value("${toolbox.directory}")
    private String toolboxDirectory;

    @Value("${toolbox.example.directory}")
    private String toolboxExampleDirectory;

    /**
     * 返回用户上传头像所存储目录
     *
     * @return
     */
    public String getUploadUserIconPath() {
        return root+ SEPARATOR +uploadUserIconDirectory+ SEPARATOR;
    }

    /**
     * 工具箱上传表格实例的目录
     *
     * @return
     */
    public String getToolboxExamplePath() {
        return root+ SEPARATOR +toolboxDirectory+ SEPARATOR +toolboxExampleDirectory+ SEPARATOR;
    }

    /**
     * 工具箱上传表格实例的全路径，根据key确定具体文件
     *
     * @return
     */
    public String getToolboxExamplePathAndNameByKey(Integer key) {
        return getToolboxExamplePath()+FileUtils.FILE_NAMES.get(key);
    }
}
