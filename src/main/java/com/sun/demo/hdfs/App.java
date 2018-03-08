package com.sun.demo.hdfs;

import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.io.compress.DefaultCodec;

import java.io.*;

/**
 * @author zhsun5@iflytek.com
 * @date 2018/3/5
 */
public class App {
    public static void main(String[] args) {
        DefaultCodec codec = new DefaultCodec();
        try {
            CompressionOutputStream out = codec.createOutputStream(new FileOutputStream(new File("")));
            IOUtils.copyBytes(new FileInputStream(new File("")), out, 4096, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
