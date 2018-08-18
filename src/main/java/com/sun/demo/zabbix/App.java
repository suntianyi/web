package com.sun.demo.zabbix;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        ZabbixUtil zabbixUtil = new ZabbixUtil();
        zabbixUtil.queryHost("Zabbix server");

        JSONObject object = zabbixUtil.queryItems("Zabbix server", "vm.memory.size[available]");

//
//        zabbixUtil.queryHostInterface("10259");
//
//        zabbixUtil.queryApplications("10258", "CPU");

        zabbixUtil.queryHistoryByItems("28443", 3);

    }
}
