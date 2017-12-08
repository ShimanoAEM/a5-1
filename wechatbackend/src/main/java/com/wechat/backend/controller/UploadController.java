package com.wechat.backend.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;

@RestController
public class UploadController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${spring.file.imageupload}")
	private String imageuploadpath;
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/upload/picture", method = { RequestMethod.GET, RequestMethod.POST })
	public void uploadPicture(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取文件需要上传到的路径
        String path = request.getRealPath(imageuploadpath) + "/";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        logger.debug("path=" + path);

        request.setCharacterEncoding("utf-8");  //设置编码
        //获得磁盘文件条目工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();

        //如果没以下两行设置的话,上传大的文件会占用很多内存，
        //设置暂时存放的存储室,这个存储室可以和最终存储文件的目录不同
        /**
         * 原理: 它是先存到暂时存储室，然后再真正写到对应目录的硬盘上，
         * 按理来说当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的
         * 然后再将其真正写到对应目录的硬盘上
         */
        factory.setRepository(dir);
        //设置缓存的大小，当上传文件的容量超过该缓存时，直接放到暂时存储室
        factory.setSizeThreshold(1024 * 1024);
        //高水平的API文件上传处理
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            /*List<FileItem> list = upload.parseRequest(request);
            FileItem picture = null;
            for (FileItem item : list) {
                //获取表单的属性名字
                String name = item.getFieldName();
                //如果获取的表单信息是普通的 文本 信息
                if (item.isFormField()) {
                    //获取用户具体输入的字符串
                    String value = item.getString();
                    request.setAttribute(name, value);
                    logger.debug("name=" + name + ",value=" + value);
                } else {
                    picture = item;
                }
            }*/
        		StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
            Iterator<String> iterator = req.getFileNames();
            MultipartFile multipartfile = null;
            String prefixName = null;
	    		String name = iterator.next();
	        multipartfile = req.getFile(name);
	        prefixName = multipartfile.getOriginalFilename();            
            String fileName = prefixName;
            String destPath = path + fileName;
            logger.debug("destPath=" + destPath);

            //真正写到磁盘上
            File file = new File(destPath);
            OutputStream  out = new FileOutputStream (file);
            InputStream in = multipartfile.getInputStream();
            int length = 0;
            byte[] buf = new byte[10485760];
            // in.read(buf) 每次读到的数据存放在buf 数组中
            while ((length = in.read(buf)) != -1) {
                //在buf数组中取出数据写到（输出流）磁盘上
                out.write(buf, 0, length);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            logger.error("", e);
        }


        PrintWriter printWriter = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("success", true);
        printWriter.write(JSON.toJSONString(res));
        printWriter.flush();
	}
}
