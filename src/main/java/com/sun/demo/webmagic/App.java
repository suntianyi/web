package com.sun.demo.webmagic;

import com.sun.demo.util.HttpClient;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    private static String baseUrl = "https://www.xxbiquge.com";

    public static void main(String[] args) throws IOException {
        String name = "惊悚乐园";
        String path = "D:/trash/" + name + ".txt";
        PrintWriter printWriter = new PrintWriter(new FileWriter(path, true));
        String url = "https://www.xxbiquge.com/0_62/";
        String response = HttpClient.sendGet(url, null);
        List<Chapter> chapterList = getChapterList(response);
        int progress = 0;
        for (Chapter chapter : chapterList) {
            String content = getContent(chapter.getUrl());
            content = content.replace("&nbsp;&nbsp;&nbsp;&nbsp;", "").replace("<br /><br />", "'");
            printWriter.println(chapter.getTitle());
            printWriter.println(content);
            System.out.print("已完成：" + 100 * progress / chapterList.size() + "%" + "\r");
            progress++;
        }
        printWriter.close();
    }

    private static List<Chapter> getChapterList(String response) {
        // <dd><a href="/0_62/5280954.html">第001章</a></dd>
        Pattern p = Pattern.compile("<dd><a href=\"(.*?)\">(.*?)</a></dd>");
        Matcher m = p.matcher(response);
        List<Chapter> chapterList = new ArrayList<>();
        while (m.find()) {
            Chapter chapter = new Chapter();
            chapter.setUrl(baseUrl + m.group(1));
            chapter.setTitle(m.group(2));
            chapterList.add(chapter);
        }
        return chapterList;
    }

    private static String getContent(String url) {
        String response;
        try {
            response = HttpClient.sendGet(url, null);
        } catch (Exception e) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            response = HttpClient.sendGet(url, null);
        }
        Pattern p = Pattern.compile("<div id=\"content\">(.*?)</div>");
        Matcher m = p.matcher(response);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }
}


class Chapter {
    private String url;
    private String title;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
