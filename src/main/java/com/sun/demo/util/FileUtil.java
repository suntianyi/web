package com.sun.demo.util;

import org.apache.http.util.TextUtils;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * @auther zhsun5@iflytek.com
 * @date 2018/1/11
 */
public class FileUtil {
    public static void main(String[] args) {
        String path = "D:/trash";
        String fileName = "小说.txt";
        File file = new File(path);
        String[] files = file.list((dir, name) -> name.endsWith(".download"));
        mergeFiles(path, files, fileName);
    }

    private static void deleteFiles(String path, String[] files) {
        if (files != null){
            for (String s : files) {
                File file = new File(path + s);
                file.delete();
            }
        }
    }

    public static boolean mergeFiles(String path, String[] fpaths, String filename) {
        String resultPath = path + "/" + filename;
        if (fpaths == null || fpaths.length < 1 || TextUtils.isEmpty(resultPath)) {
            return false;
        }
        if (fpaths.length == 1) {
            return new File(fpaths[0]).renameTo(new File(resultPath));
        }

        File[] files = new File[fpaths.length];
        for (int i = 0; i < fpaths.length; i ++) {
            files[i] = new File(path + "/" + fpaths[i]);
            if (TextUtils.isEmpty(fpaths[i]) || !files[i].exists() || !files[i].isFile()) {
                return false;
            }
        }
        File resultFile = new File(resultPath);
        try {
            FileChannel resultFileChannel = new FileOutputStream(resultFile, true).getChannel();
            for (int i = 0; i < fpaths.length; i ++) {
                FileChannel blk = new FileInputStream(files[i]).getChannel();
                resultFileChannel.transferFrom(blk, resultFileChannel.size(), blk.size());
                blk.close();
            }
            resultFileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        for (int i = 0; i < fpaths.length; i ++) {
            files[i].delete();
        }

        return true;
    }
}
