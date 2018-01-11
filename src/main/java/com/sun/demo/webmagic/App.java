package com.sun.demo.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class App {
    public static void main(String[] args) {
        Spider spider = new Spider(new PageProcessor() {
            private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

            @Override
            public void process(Page page) {
                page.putField("title", page.getHtml().xpath("//h1/text()").toString());
                if (page.getResultItems().get("title")==null){
                    page.setSkip(true);
                }
            }

            @Override
            public Site getSite() {
                return site;
            }
        });
    }
}
