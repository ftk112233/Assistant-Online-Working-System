package com.jzy.manager.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName FileUtils
 * @description 文件操作相关工具类
 * @date 2019/11/18 12:33
 **/
public class FileUtils {

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName
     *            要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile()) {
                return deleteFile(fileName);
            }
            else {
                return deleteDirectory(fileName);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir
     *            要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 重命名文件，通过绝对路径
     *
     * @param oldpath 原文件绝对路径
     * @param newPath 新文件绝对路径
     * @return
     */
    public static boolean renameByAbsPath(String oldpath,String newPath){
        /*旧文件名*/
        File file1 = new File(oldpath);

        /*新文件名*/
        File file2 = new File(newPath);

        /*重命名*/
        return file1.renameTo(file2);

    }

    /**
     * 重命名文件，通过目录和文件名
     *
     * @param rootPath 原文件所在目录绝对路径
     * @param oldName 原文件的文件名
     * @param newName 新文件的文件名
     * @return
     */
    public static boolean renameByName(String rootPath, String oldName, String newName){
        /*旧文件名*/
        File file1 = new File(rootPath+File.separator+oldName);

        /*新文件名*/
        File file2 = new File(rootPath+File.separator+newName);

        /*重命名*/
        return file1.renameTo(file2);

    }

    public static boolean isImage(String imgPath) {
        Boolean flag =false;
        //图片格式
        final String[] FILETYPES = new String[]{
                ".jpg", ".bmp", ".jpeg", ".png", ".gif",
                ".JPG", ".BMP", ".JPEG", ".PNG", ".GIF"
        };
        if(!StringUtils.isBlank(imgPath)){
            for (int i = 0; i < FILETYPES.length; i++) {
                String fileType = FILETYPES[i];
                if (imgPath.endsWith(fileType)) {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }
}
