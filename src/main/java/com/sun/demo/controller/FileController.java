package com.sun.demo.controller;

import com.sun.demo.util.ZipUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther zhsun5@iflytek.com
 * @date 2018/1/13
 */
@Controller
@RequestMapping(value = "/file")
@Api(value = "文件管理")
public class FileController {

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "导出文件", notes = "导出文件", response = String.class)
    public String download(HttpServletResponse response) throws Exception {
        //定义根路径
        String path = "/temp_download";
        //创建文件
        File file = new File(path);
        //判断文件是否存在，如果不存在，则创建此文件夹
        if(!file.exists()){
            file.mkdir();
        }
        String zipFileName = "音乐.zip";

        ArrayList<String> files = getFileList();
        ZipUtils.zipFiles(files, path + zipFileName);
        File zipFile = new File(path + zipFileName);

        // 输出到客户端
        OutputStream out = response.getOutputStream();
        response.reset();
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(zipFileName.getBytes("UTF-8"), "ISO-8859-1"));
        response.setContentType("application/octet-stream; charset=utf-8");
        response.addHeader("Content-Length", String.valueOf(zipFile.length()));
        response.setCharacterEncoding("UTF-8");
        out.write(FileUtils.readFileToByteArray(zipFile));
        out.flush();
        out.close();

        // 输出客户端结束后，删除压缩包
        if (zipFile.exists()) {
            zipFile.delete();
        }
        return "succsee";
    }

    @RequestMapping(value = "/download1", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "导出文件", notes = "导出文件", response = String.class)
    public String download1(HttpServletResponse response) throws Exception {
        OutputStream out = response.getOutputStream();
        response.reset();
        response.setHeader("Content-Disposition", "attachment;filename=" + new String("音乐.zip".getBytes("UTF-8"), "ISO-8859-1"));
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        List<String> list = getFileList();
        ZipUtils.createZip(out, list.stream().map(File::new).collect(Collectors.toList()));
        return "success";
    }




    public ArrayList<String> getFileList() {
        String path = "D:/音乐/";
        String file1 = path + "4 Non Blondes - What's Up.mp3";
        String file2 = path + "S9ryne - 命.mp3";
        String file3 = path + "群星 - 我在那一角落患过伤风(钢琴版).mp3";
        ArrayList<String> files = new ArrayList<>();
        files.add(file1);
        files.add(file2);
        files.add(file3);
        return files;
    }
}
