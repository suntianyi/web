package com.sun.demo.webmagic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BookSpider {
    public static void main(String[] args) {
        Spider.create(new BookPageProcessor())
                .addUrl("https://www.xxbiquge.com/77_77268/336801.html")
                .addPipeline(new BookPipeline("剑来"))
                .run();
    }
}


class BookPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
        page.putField("title", page.getHtml().xpath("//h1/text()").toString());
        System.out.println(page.getResultItems().get("title").toString());
        if (page.getResultItems().get("title")==null){
            page.setSkip(true);
        }
        page.putField("content", page.getHtml().xpath("//div[@id='content']/text()").toString());
        String request = "https://www.xxbiquge.com" + page.getHtml().regex("章节目录.*?<a href=\"(.*?)\">下一章</a>").get();
        page.addTargetRequest(request);
    }

    @Override
    public Site getSite() {
        return site;
    }
}

class BookPipeline implements Pipeline {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String name;

    BookPipeline(String name) {
        this.name = name;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String path = "D:/trash/" + name + ".txt";

        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(path, true));
            printWriter.println(resultItems.get("title").toString());
            String content = resultItems.get("content").toString().replace("    ", "");
            printWriter.println(content);
            printWriter.close();
        } catch (IOException e) {
            this.logger.warn("write file error", e);
        }
    }
}