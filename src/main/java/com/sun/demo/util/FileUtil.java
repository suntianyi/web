package com.sun.demo.util;

import org.apache.http.util.TextUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther zhsun5@iflytek.com
 * @date 2018/1/11
 */
public class FileUtil {
    public static void main(String[] args) {
//        String path = "D:/trash";
//        String fileName = "小说.txt";
//        File file = new File(path);
//        String[] files = file.list((dir, name) -> name.endsWith(".download"));
//        mergeFiles(path, files, fileName);

        String path = "D:/音乐/";
        String file1 = path + "4 Non Blondes - What's Up.mp3";
        String file2 = path + "S9ryne - 命.mp3";
        String file3 = path + "群星 - 我在那一角落患过伤风(钢琴版).mp3";
        ArrayList<String> list = new ArrayList<>();
        list.add(file1);
        list.add(file2);
        list.add(file3);
        ZipUtils.zipFiles(list, path + "音乐.zip");
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
