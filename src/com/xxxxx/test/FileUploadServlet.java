package com.xxxxx.test;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 */
public class FileUploadServlet extends javax.servlet.http.HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //上传文件准备
        //上传路径问题和上传文件的处理
        String webroot = this.getServletContext().getRealPath("/");
        System.out.println("当前web应用的目录：" + webroot);
        File temppath = new File(webroot + "temp");
        File uploadpath = new File(webroot + "upload");
        //保证代码的健壮性
        if (!temppath.exists()) {
            temppath.mkdirs();
        }
        if (!uploadpath.exists()) {
            uploadpath.mkdirs();
        }
        /*
        上传文件的处理：文件大小的问题；拿到输入流后的写入，普通的io
        细节处理：文件名字问题（加个时间戳）
         */
        DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 1024*1024, temppath);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(1024 * 1024 * 100);

        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            Iterator<FileItem> it = fileItems.iterator();

            String from = "524937686@qq.com";
            String to = null;
            String content = null;
            String subject = null;
            String host = "pop.qq.com";
            String pwd = "prwckdfsotdxcaaa";
            List<String> files = new ArrayList<>();

            while (it.hasNext()) {
                FileItem fi = it.next();
                if (fi.isFormField()) {
                    System.out.println("字段名：" + fi.getFieldName());
                    System.out.println("字段值：" + fi.getString("utf-8"));
                    if (fi.getFieldName().equals("to"))
                        to = fi.getString();
                    if (fi.getFieldName().equals("content"))

                        content = fi.getString("utf-8");
                    if (fi.getFieldName().equals("subject"))
                        subject = fi.getString("utf-8");
                } else {
                    InputStream inputStream = fi.getInputStream();
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(uploadpath, fi.getName()));
                    BufferedInputStream bis = new BufferedInputStream(inputStream);
                    BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
                    byte buffer[] = new byte[1024*1024*1024];
                    int len = 0;
                    while((len =bis.read(buffer))>0){
                        bos.write(buffer,0,len);
                        bos.flush();
                    }
                    bos.close();
                    bis.close();
                    inputStream.close();
                    fileOutputStream.close();
                    //删除临时文件
                    fi.delete();
                    String filename = fi.getName();
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("text/html");
                    files.add(uploadpath + File.separator + filename);
                }
            }
            if (MailHelper.sendemail(host, from, pwd, to, subject, content, files)) {
                response.sendRedirect("mail.jsp");
            }
        } catch (FileUploadException e) {
            response.getWriter().write(e.toString());
        }
    }
}
