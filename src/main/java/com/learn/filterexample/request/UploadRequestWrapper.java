package com.learn.filterexample.request;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.util.ResourceUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadRequestWrapper extends HttpServletRequestWrapper {
    //文件类型头
    private static final String MULTIPART_HEADER = "Content-type";
    //是否为文件上传
    private boolean multipart;
    //保存提交的数据
    private Map<String,Object> params = new HashMap<>();

    public UploadRequestWrapper(HttpServletRequest request) {
        super(request);

        String contentType = request.getHeader(MULTIPART_HEADER);
        if(contentType != null && contentType.startsWith("multipart/form-data")){
            multipart = true;
        }

        if(multipart){
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024*1024); //设置为1m 默认是10k
            //解析器
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            fileUpload .setHeaderEncoding("UTF-8");
            //解析请求
            try {
                List<FileItem> fileItems = fileUpload.parseRequest(request);
                for (FileItem fileItem : fileItems) {
                    //是否为表单域
                    if(fileItem.isFormField()){
                        params.put(fileItem.getFieldName(),fileItem.getString("utf-8"));
                    }else{
                        String fileName = fileItem.getName().replace("\\", "/");
                        fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
                        File file = ResourceUtils.getFile("classpath:cache");
                        //把上传的文件保存到cache目录下
                        File uploadFile = new File(file, fileName);
                        FileOutputStream fileOutputStream = new FileOutputStream(uploadFile);
                        fileOutputStream.write(fileItem.get());

                        //文件路径保存到map
                        params.put(fileItem.getFieldName(),uploadFile.getPath());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public Object getAttribute(String name) {
        if(multipart && params.containsKey(name)){
            return params.get(name);
        }
        return super.getAttribute(name);
    }

    @Override
    public String getParameter(String name) {
        if(multipart && params.containsKey(name)){
            return params.get(name).toString();
        }
        return super.getParameter(name);
    }
}
