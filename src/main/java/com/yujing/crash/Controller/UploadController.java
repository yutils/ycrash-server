package com.yujing.crash.Controller;

import com.yujing.crash.model.Resp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/upload")
public class UploadController {
    //项目路径
    @Value("${server.servlet.context-path}")
    private String contextPath;

    //虚拟路径
    @Value("${file.virtualPath}")
    private String virtualPath;
    //磁盘路径
    @Value("${file.physicsPath}")
    private String physicsPath;

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @ResponseBody
    public Resp file(HttpServletRequest request, HttpServletResponse response) {
        //工程URL
        String contextUrl = request.getRequestURL().delete(request.getRequestURL().length() - request.getRequestURI().length(), request.getRequestURL().length()).toString();
        String projectUrl = contextUrl + (contextPath.equals("/") ? "" : contextPath);
        //遍历
        List<FileInfo> fileInfos = new ArrayList<>();
        try {
            String[] contentTypes = {"multipart/form-data", "multipart/mixed"};
            if (!request.getContentType().contains(contentTypes[0]) && !request.getContentType().contains(contentTypes[1])) {
                return new Resp(-1, "contentType未发现multipart", null);
            }
            // 保存
            Collection<Part> parts = request.getParts();
            if (parts.size() == 0) {
                return new Resp(-2, "没有发现文件", null);
            }
            for (Part part : parts) {
                String filename = getFileName(part);
                if (filename == null || filename.isEmpty())
                    continue;
                //改名
                String newFileName = UUID.randomUUID().toString().replace("-", "").toLowerCase() + "." + getExtensionName(filename);
                //File属性
                FileInfo fileInfo = new FileInfo();
                //物理路径:物理路径+新文件名
                fileInfo.filePath = physicsPath + newFileName;
                //相对路径:虚拟路径+新文件名
                fileInfo.relativeUrl = "/" + virtualPath.replace("*", "") + newFileName;
                //url:地址+项目名+"/"+虚拟路径+新文件名
                fileInfo.absoluteUrl = projectUrl + fileInfo.relativeUrl;
                //保存
                saveFile(fileInfo.filePath, part.getInputStream());
                //打印
                System.out.println("磁盘路径:\t" + fileInfo.filePath);
                System.out.println("URL:\t" + fileInfo.absoluteUrl);
                fileInfos.add(fileInfo);
            }
            if (fileInfos.size()==0){
                return new Resp(-3, "没有发现文件", null);
            }
        } catch (Exception e) {
            return new Resp(-4, "上传失败", e.getMessage());
        }
        return new Resp(0, "上传成功", fileInfos);
    }

    /**
     * 获取part中的文件名
     */
    private String getFileName(Part part) {
        String filename = null;
        String[] cds = part.getHeader("Content-Disposition").split(";");
        for (int i = 0; i < cds.length; i++) {
            if (cds[i].contains("filename")) {
                filename = cds[i].substring(cds[i].indexOf("=") + 1).substring(cds[i].lastIndexOf("//") + 1).replace("\"", "");
            }
        }
        return filename;
    }

    /**
     * 保存文件
     */
    private void saveFile(String filePath, InputStream is) throws IOException {
        File file = new File(filePath);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(file);
        byte[] b = new byte[1024];
        int n;
        while ((n = is.read(b)) != -1) {
            fos.write(b, 0, n);
        }
        fos.close();
    }

    /**
     * Java文件操作 获取文件扩展名
     */
    private String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     */
    private String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    public static class FileInfo {
        String filePath;//文件物理路径
        String absoluteUrl;//url绝对路径
        String relativeUrl;//url相对路径

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getAbsoluteUrl() {
            return absoluteUrl;
        }

        public void setAbsoluteUrl(String absoluteUrl) {
            this.absoluteUrl = absoluteUrl;
        }

        public String getRelativeUrl() {
            return relativeUrl;
        }

        public void setRelativeUrl(String relativeUrl) {
            this.relativeUrl = relativeUrl;
        }
    }

    /**
     * 文件上传，要求每个文件参数都要为files
     */
    @RequestMapping(value = "/files", method = RequestMethod.POST)
    @ResponseBody
    public Resp files(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
        //工程URL
        String contextUrl = request.getRequestURL().delete(request.getRequestURL().length() - request.getRequestURI().length(), request.getRequestURL().length()).toString();
        String projectUrl = contextUrl + (contextPath.equals("/") ? "" : contextPath);
        if (files.length== 0) {
            return new Resp(-1, "没有发现文件", "检查每个文件参数是否都为‘files’");
        }
        //遍历
        List<FileInfo> fileInfos = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            //设置新的名称,file.getOriginalFilename();//获取上传文件的原名
            String newFileName = UUID.randomUUID().toString().replace("-", "").toLowerCase() + "." + getExtensionName(file.getOriginalFilename());
            //File属性
            FileInfo fileInfo = new FileInfo();
            //物理路径:物理路径+新文件名
            fileInfo.filePath = physicsPath + newFileName;
            //相对路径:虚拟路径+新文件名
            fileInfo.relativeUrl = "/" + virtualPath.replace("*", "") + newFileName;
            //url:地址/项目名+虚拟路径+新文件名
            fileInfo.absoluteUrl = projectUrl + fileInfo.relativeUrl;
            // 转存文件
            try {
                file.transferTo(new File(fileInfo.filePath));
            } catch (IOException e) {
                return new Resp(-2, "上传失败", null);
            }
            //打印
            System.out.println("磁盘路径:\t" + fileInfo.filePath);
            System.out.println("URL:\t" + fileInfo.absoluteUrl);
            fileInfos.add(fileInfo);
        }
        return new Resp(0, "上传成功", fileInfos);
    }
}
