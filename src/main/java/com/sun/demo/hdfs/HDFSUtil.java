package com.sun.demo.hdfs;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;

public class HDFSUtil {
    public static void main(String[] args) throws Exception{
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://localhost:9000"), new Configuration());
        // 创建，重命名，删除
        fileSystem.create(new Path(""));
        fileSystem.rename(new Path(""), new Path(""));
        // 文件夹不为空时，是否递归删除
        fileSystem.delete(new Path(""), false);
        // 查看文件
        fileSystem.listFiles(new Path(""), true);
        // 上传
        fileSystem.copyFromLocalFile(new Path("D:/trash/1.txt"), new Path("/sun"));
        // 下载
        fileSystem.copyToLocalFile(new Path(""), new Path(""));

        // 文件流下载文件
        FSDataInputStream in = fileSystem.open(new Path(""));
        FileOutputStream out=new FileOutputStream("D:/trash/1.txt");
        IOUtils.copyBytes(in,out,new Configuration());
        IOUtils.closeStream(in);
        IOUtils.closeStream(out);

//        文件流上传文件
//        FileInputStream in=new FileInputStream("D:/trash/1.txt");
//        FSDataOutputStream out = fileSystem.create(new Path(""));
//        IOUtils.copyBytes(in, out, new Configuration());
//        IOUtils.closeStream(in);
//        IOUtils.closeStream(out);
    }

}
