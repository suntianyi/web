package com.sun.demo.util;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class MulThreadDownload {

    public static void main(String[] args) throws Exception {
        String path = "http://mirror.bit.edu.cn/apache/maven/maven-3/3.5.2/binaries/apache-maven-3.5.2-bin.zip";
        new MulThreadDownload().download(path, 5);
    }

    /**
     * 下载文件  
     */
    private void download(String path, int threadSize) throws Exception {
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        if (connection.getResponseCode() == 200) {
            int length = connection.getContentLength();// 获取网络文件长度
            File file = new File(getFileName(path));
            // 在本地生成一个长度与网络文件相同的文件
            RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
            accessFile.setLength(length);
            accessFile.close();

            // 计算每条线程负责下载的数据量
            int block = length % threadSize == 0 ? length / threadSize : length
                    / threadSize + 1;
            for (int threadId = 0; threadId < threadSize; threadId++) {
                new DownloadThread(threadId, block, url, file).start();
            }
        } else {
            System.out.println("download fail");
        }
    }


    private class DownloadThread extends Thread {
        private int threadId;
        private int block;
        private URL url;
        private File file;

        public DownloadThread(int threadId, int block, URL url, File file) {
            this.threadId = threadId;
            this.block = block;
            this.url = url;
            this.file = file;
        }

        @Override
        public void run() {
            int start = threadId * block; // 计算该线程从网络文件什么位置开始下载
            int end = (threadId + 1) * block - 1; // 计算下载到网络文件什么位置结束
            try {
                RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
                accessFile.seek(start); //从start开始

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                //设置获取资源数据的范围，从start到end
                connection.setRequestProperty("Range", "bytes=" + start + "-" + end);
                //注意多线程下载状态码是 206 不是200
                if (connection.getResponseCode() == 206) {
                    InputStream inputStream = connection.getInputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        accessFile.write(buffer, 0, len);
                    }
                    accessFile.close();
                    inputStream.close();
                }
                System.out.println("第" + (threadId + 1) + "条线程已经下载完成");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取文件名称
     * @param path
     * @return
     */
    private String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }
}
