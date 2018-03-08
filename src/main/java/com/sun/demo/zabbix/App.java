package com.sun.demo.zabbix;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        ZabbixUtil zabbixUtil = new ZabbixUtil();
        zabbixUtil.queryHost("Zabbix server");

        zabbixUtil.queryItems("Zabbix server", "agent.version");

        zabbixUtil.queryHostInterface("10258");

        zabbixUtil.queryApplications("10258", "CPU");

//        zabbixUtil.queryHistoryByHost("10258");
        List<String> itemids = new ArrayList<>(1);
        itemids.add("28394");
        zabbixUtil.queryHistoryByItems(itemids);

    }
}
