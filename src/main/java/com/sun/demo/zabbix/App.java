package com.sun.demo.zabbix;

import com.alibaba.fastjson.JSONObject;

public class App {
    public static void main(String[] args) {
        ZabbixUtil zabbixUtil = new ZabbixUtil();
        JSONObject host = zabbixUtil.queryHost("Zabbix server");

        zabbixUtil.queryItems("Zabbix server", "agent.version");

        zabbixUtil.queryHostInterface("10084");

        zabbixUtil.queryApplications("10084", "CPU");
    }
}
