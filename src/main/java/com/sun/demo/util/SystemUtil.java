package com.sun.demo.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhsun5@iflytek.com
 * @date 2018/3/14
 */
public class SystemUtil {
    /**
     * 执行一个命令行，出错时返回false
     */
    public static boolean execCmd(String[] cmd) {
        Thread consoleThread = null;
        int ret = 0;
        try {
            ProcessBuilder pb = new ProcessBuilder(cmd);
            Process p = pb.start();
            ret = p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != consoleThread && consoleThread.isAlive()) {
            consoleThread.interrupt();
        }
        return ret == 0;
    }

    private static Thread createReadThread(final Process process) {
        return new Thread(() -> {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    process.getErrorStream()));
            try {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    Thread.sleep(5);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static List<String> getPunctuationList() {
        return new ArrayList<String>() {{
            add("？");
            add("。");
            add("，");
            add("！");
        }};
    }

    /**
     * 调用本地cmd执行命令
     *
     * @param cmd
     * @return boolean
     */
    public static boolean execCmd(String cmd) {
        Thread consoleThread;
        int ret;
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            consoleThread = createReadThread(proc);
            consoleThread.start();
            ret = proc.waitFor();
            return ret == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
