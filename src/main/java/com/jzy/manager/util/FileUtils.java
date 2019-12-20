package com.jzy.manager.util;

import com.jzy.model.excel.Excel;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName FileUtils
 * @description 文件操作相关工具类
 * @date 2019/11/18 12:33
 **/
public class FileUtils {
    /**
     * 系统中存储的上传表格示例文件的名称
     */
    public static final Map<Integer, String> EXAMPLES = new HashMap<>();

    /**
     * 系统中存储的表格文件的名称
     */
    public static final Map<Integer, String> TEMPLATES = new HashMap<>();

    static {
        EXAMPLES.put(1, "助教信息.xlsx");
        EXAMPLES.put(2, "曹杨秋季助教排班.xlsx");
        EXAMPLES.put(3, "秋下花名册.xls");
        EXAMPLES.put(4, "秋季花名册_开班.xlsx");
        EXAMPLES.put(5, "座位表.xlsx");
        EXAMPLES.put(6, "学校统计.xlsx");
        EXAMPLES.put(7, "学校统计_空.xlsx");

        TEMPLATES.put(1, "教师和助教工作表5.0.xlsx");
        TEMPLATES.put(2, "座位表.xlsx");
        TEMPLATES.put(3, "补课单.xlsx");
    }

    private FileUtils() {
    }

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName 要删除的文件名
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
            } else {
                return deleteDirectory(fileName);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
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
     * @param dir 要删除的目录的文件路径
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
    public static boolean renameByAbsPath(String oldpath, String newPath) {
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
     * @param oldName  原文件的文件名
     * @param newName  新文件的文件名
     * @return
     */
    public static boolean renameByName(String rootPath, String oldName, String newName) {
        /*旧文件名*/
        File file1 = new File(rootPath + File.separator + oldName);

        /*新文件名*/
        File file2 = new File(rootPath + File.separator + newName);

        /*重命名*/
        return file1.renameTo(file2);

    }

    /**
     * 输入文件名是否是图片
     *
     * @param imgPath
     * @return
     */
    public static boolean isImage(String imgPath) {
        boolean flag = false;
        //图片格式
        final String[] fileTypes = new String[]{
                ".jpg", ".bmp", ".jpeg", ".png", ".gif",
                ".JPG", ".BMP", ".JPEG", ".PNG", ".GIF"
        };
        if (!StringUtils.isBlank(imgPath)) {
            for (int i = 0; i < fileTypes.length; i++) {
                String fileType = fileTypes[i];
                if (imgPath.endsWith(fileType)) {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }

    /**
     * 从服务器制定目录下载文件
     *
     * @param request
     * @param response
     * @param filePathAndNameToRead 服务器中被下载的文件路径
     * @param downloadFileName      下载下来的文件名称
     * @throws IOException
     */
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, String filePathAndNameToRead, String downloadFileName) throws IOException {
        // 以流的形式下载文件。
        File f = new File(filePathAndNameToRead);
        InputStream fis = new BufferedInputStream(new FileInputStream(filePathAndNameToRead));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        response.reset();
        // 设置response的Header
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.addHeader("Content-Length", "" + f.length());
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/x-download");
        String Agent = request.getHeader("User-Agent");
        if (null != Agent) {
            Agent = Agent.toLowerCase();
            if (Agent.indexOf("firefox") != -1) {
                response.addHeader("Content-Disposition", String.format("attachment;filename*=utf-8'zh_cn'%s", URLEncoder.encode(downloadFileName, "utf-8")));
            } else {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(downloadFileName, "utf-8"));
            }
        }
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
    }


    /**
     * 从服务器制定目录下载文件
     *
     * @param request
     * @param response
     * @param excelToDownload  服务器中被下载的excel文件
     * @param downloadFileName 下载下来的文件名称
     * @throws IOException
     */
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, Excel excelToDownload, String downloadFileName) throws IOException {
        response.reset(); // 清除buffer缓存
        // 指定下载的文件名
        String Agent = request.getHeader("User-Agent");
        if (null != Agent) {
            Agent = Agent.toLowerCase();
            if (Agent.indexOf("firefox") != -1) {
                response.addHeader("Content-Disposition", String.format("attachment;filename*=utf-8'zh_cn'%s", URLEncoder.encode(downloadFileName, "utf-8")));
            } else {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(downloadFileName, "utf-8"));
            }
        }
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        OutputStream output = response.getOutputStream();
        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
        bufferedOutPut.flush();
        //输出
        excelToDownload.submitWrite(bufferedOutPut);
        bufferedOutPut.close();
    }

    /**
     * 文件复制
     * @param srcPath 源文件路径
     * @param targetPath 复制后存放路径
     * @throws Exception
     */
    public static void copyFile(String srcPath, String targetPath) throws Exception {
        org.apache.commons.io.FileUtils.copyFile(new File(srcPath), new File(targetPath));
    }

    public static void main(String[] args) throws Exception {
        copyFile("C:\\Users\\92970\\Desktop\\助教信息.xlsx", ".\\a.xlsx");
    }
}
