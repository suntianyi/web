package com.sun.demo.zabbix;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;

public class ZabbixUtil {
    private static final Logger logger = LoggerFactory.getLogger(ZabbixUtil.class);

    private String ZABBIX_URL = "http://192.168.170.129:9090/zabbix/api_jsonrpc.php";
    private String ZABBIX_UNAME = "Admin";
    private String ZABBIX_UPWD = "zabbix";

    private String auth;

    public String getAuth() {
        return auth;
    }

    private void setAuth(String auth) {
        this.auth = auth;
    }

    /**
     * 登陆
     */
    public boolean login() {
        ZabbixRequest request = ZabbixRequestBuilder.newBuilder().paramEntry("user", ZABBIX_UNAME)
                .paramEntry("password", ZABBIX_UPWD).method("user.login").build();
        logger.info("ZabbixRequest: " + request.toString());
        JSONObject response = call(request);
        String auth = response.getString("result");
        if (auth != null && !auth.isEmpty()) {
            this.setAuth(auth);
            logger.info("login zabbix success!");
            return true;
        }
        logger.info("login zabbix failed!");
        return false;
    }

    /**
     * 查询ITEMS
     *
     * @param hostName  hostIP
     * @param searchKey 查询KEY
     * @return item列表
     */
    public JSONObject queryItems(String hostName, String searchKey) {
        JSONObject search = new JSONObject();
        logger.info("queryItems--searchKey: " + searchKey + " hostName:" + hostName);
        search.put("key_", searchKey);
        ZabbixRequest request = ZabbixRequestBuilder.newBuilder().method("item.get")
                .paramEntry("output", "extend")
                .paramEntry("host", hostName)
                .paramEntry("search", search)
                .build();
        logger.info(String.format("queryItems >>>> request:%s", request.toString()));
        JSONObject json = call(request);
        logger.info(String.format("queryItems <<<< response:%s", json.toJSONString()));
        return json;
    }

    /**
     * 查询HOST信息
     *
     * @param hostName
     * @return HOST列表
     */
    public JSONObject queryHost(String hostName) {
        if (login()) {
            logger.info("queryHost--hostName: " + hostName);
            JSONObject search = new JSONObject();
            search.put("host", hostName);
            ZabbixRequest request = ZabbixRequestBuilder.newBuilder().method("host.get")
                    .paramEntry("output", "extend")
                    .paramEntry("filter", search)
                    .build();
            logger.info(String.format("queryHost >>>> request:%s", request.toString()));
            JSONObject json = call(request);
            logger.info(String.format("queryHost <<<< response:%s", json.toJSONString()));
            return json;
        }
        return null;
    }

    /**
     * 查询Applications
     *
     * @param hostId hostId
     * @param name   查询名
     * @return Applications列表
     */
    public JSONObject queryApplications(String hostId, String name) {
        JSONObject search = new JSONObject();
        search.put("key_", name);
        logger.info("queryApplications--hostId: " + hostId);
        ZabbixRequest request = ZabbixRequestBuilder.newBuilder().method("application.get")
                .paramEntry("output", "extend")
                .paramEntry("hostids", hostId)
                .paramEntry("filter", search)
                .build();
        logger.info(String.format("queryApplications >>>> request:%s", request.toString()));
        JSONObject json = call(request);
        logger.info(String.format("queryApplications <<<< response:%s", json.toJSONString()));
        return json;
    }

    /**
     * 查询主机接口信息
     *
     * @param hostId hostid
     * @return hostinterface list
     */
    public JSONObject queryHostInterface(String hostId) {
        logger.info("queryHostInterface--hostId: " + hostId);
        ZabbixRequest request = ZabbixRequestBuilder.newBuilder().method("hostinterface.get")
                .paramEntry("output", "extend")
                .paramEntry("hostids", hostId)
                .build();
        logger.info(String.format("queryHostInterface >>>> request:%s", request.toString()));
        JSONObject json = call(request);
        logger.info(String.format("queryHostInterface <<<< response:%s", json.toJSONString()));
        return json;
    }


    public JSONObject call(ZabbixRequest request) {
        if (request.getAuth() == null) {
            request.setAuth(auth);
        }
        try {
            HttpUriRequest httpRequest = org.apache.http.client.methods.RequestBuilder
                    .post().setUri(new URI(ZABBIX_URL))
                    .addHeader("Content-Type", "application/json-rpc")
                    .setEntity(new StringEntity(JSON.toJSONString(request), "utf-8"))
                    .build();
            CloseableHttpClient httpClient = HttpClients.custom().build();
            CloseableHttpResponse response = httpClient.execute(httpRequest);
            HttpEntity entity = response.getEntity();
            byte[] data = EntityUtils.toByteArray(entity);
            return (JSONObject) JSON.parse(data);
        } catch (Exception e) {
            logger.warn("Default ZabbixApi call exception!", e);
        }
        return null;
    }

    /**
     * 将入参转为需要的格式
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static String dateToTimestamp(String date) throws Exception {
        Calendar calendar = Calendar.getInstance();
        switch (date) {
            case "d":
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                break;
            case "w":
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                break;
            case "m":
                calendar.add(Calendar.MONTH, -1);
                break;
            case "h":
                calendar.add(Calendar.HOUR, -12);
                break;
            default:
                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
                break;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = sdf.parse(sdf.format(calendar.getTime()));
        return String.valueOf(d.getTime()).substring(0, 10);
    }

    /**
     * 根据监控项id和主机id查询监控项
     *
     * @param monitorKeys (必须)监控项keys
     * @param hostids     主机ids
     * @param outputs     输出栏位名称
     * @return 符合条件的items json
     */
    public JSONObject queryItemsByKeysHostids(Collection<String> monitorKeys, Collection<String> hostids, String... outputs) {

        try {
            JSONObject filter = new JSONObject();
            filter.put("key_", monitorKeys);
            if (CollectionUtils.isNotEmpty(hostids)) {
                filter.put("hostid", hostids);
            }
            ZabbixRequestBuilder builder = ZabbixRequestBuilder.newBuilder().method("item.get")//
                    .paramEntry("output", ArrayUtils.isEmpty(outputs) ? "extend" : outputs)//
                    .paramEntry("filter", filter)//
                    .paramEntry("selectInterfaces", new String[]{"interfaceid", "hostid", "ip", "type"})
                    .paramEntry("selectTriggers", new String[]{"triggerid", "expression", "state", "status", "value"})
                    .paramEntry("selectHosts", new String[]{"host", "hostid"});//
            ZabbixRequest request = builder.build();
            logger.info(String.format("queryItemsByKeysHostids >>>> request:%s", request.toString()));
            JSONObject json = call(request);
            logger.info(String.format("queryItemsByKeysHostids <<<< response:%s", json.toJSONString()));
            return json;
        } catch (Exception e) {
            logger.warn("查询监控信息失败！", e);
        }
        return null;
    }

    /**
     * 根据监控项key查询模板监控项
     *
     * @param monitorKeys
     * @param outputs
     * @return
     */
    public JSONObject queryTemplateItemsByKeys(Collection<String> monitorKeys, String... outputs) {
        try {
            JSONObject filter = new JSONObject();
            filter.put("key_", monitorKeys);
            ZabbixRequestBuilder builder = ZabbixRequestBuilder.newBuilder().method("item.get")//
                    .paramEntry("output", ArrayUtils.isEmpty(outputs) ? "extend" : outputs)//
                    .paramEntry("filter", filter)//
                    .paramEntry("templated", true);//
            ZabbixRequest request = builder.build();
            logger.info(String.format("queryTemplateItemsByKeys >>>> request:%s", request.toString()));
            JSONObject json = call(request);
            logger.info(String.format("queryTemplateItemsByKeys <<<< response:%s", json.toJSONString()));
            return json;
        } catch (Exception e) {
            logger.error("查询监控信息失败！", e);
        }
        return null;
    }

    /**
     * 根据主机名称查询主机
     *
     * @param hostNames 主机名称
     * @param outputs   输出栏位名称
     * @return 符合条件的主机
     */
    public JSONObject queryHostsByHostnames(Collection<String> hostNames, String... outputs) {
        try {
            JSONObject jsonHostInterfaces = queryInterfacesByHostnames(hostNames, "hostid");
            assert jsonHostInterfaces != null;
            JSONArray hostArray = jsonHostInterfaces.getJSONArray("result");
            Set<String> hostids = new HashSet<>();
            for (int i = 0; i < hostArray.size(); i++) {
                hostids.add(hostArray.getJSONObject(i).getString("hostid"));
            }
            if (CollectionUtils.isEmpty(hostids)) {
                logger.warn("主机不存在，HostNames : " + hostNames);
                return null;
            }
            JSONObject filter = new JSONObject();
            filter.put("hostid", hostids);
            ZabbixRequest request = ZabbixRequestBuilder.newBuilder().method("host.get")//
                    .paramEntry("filter", filter)//
                    .paramEntry("selectInterfaces", new String[]{"interfaceid", "ip", "type", "hostid"})//
                    .paramEntry("output", ArrayUtils.isEmpty(outputs) ? "extend" : outputs)//
                    .build();
            logger.info(String.format("queryHostsByHostnames >>>> request:%s", request.toString()));
            JSONObject json = call(request);
            logger.info(String.format("queryHostsByHostnames <<<< response:%s", json.toJSONString()));
            return json;
        } catch (Exception e) {
            logger.error("host不存在：" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 通过name，查询模板详情
     *
     * @param name
     * @return
     */
    public JSONObject queryTemplateByName(String name) {
        JSONObject response = null;
        if (login()) {
            List<String> out = Collections.singletonList(name);
            List<String> output = Arrays.asList("itemid", "name", "key_");
            Map<String, Object> filter = new HashMap<>();
            filter.put("host", out);
            ZabbixRequest request = ZabbixRequestBuilder.newBuilder().method("template.get")
                    .paramEntry("output", "extend")
                    .paramEntry("selectItems", output)
                    .paramEntry("filter", filter)
                    .build();
            logger.info(String.format("queryTemplateByItemids >>>> request:%s", request.toString()));
            response = call(request);
            logger.info(String.format("queryTemplateByItemids <<<< response:%s", response.toJSONString()));
        }
        return response;
    }

    /**
     * 通过itemid，查询模板详情
     *
     * @param itemids
     * @param hostids
     * @param outputs
     * @return
     */
    public JSONObject queryTemplateByItemids(List<String> itemids, Collection<String> hostids, String... outputs) {
        JSONObject response = null;
        ZabbixRequest request = ZabbixRequestBuilder.newBuilder().method("template.get")
                .paramEntry("itemids", itemids)
                .paramEntry("hostids", hostids)
                .paramEntry("selectHosts", new String[]{"hostid", "host"})
                .paramEntry("output", ArrayUtils.isEmpty(outputs) ? "extend" : outputs)
                .build();
        logger.info(String.format("queryTemplateByItemids >>>> request:%s", request.toString()));
        response = call(request);
        logger.info(String.format("queryTemplateByItemids <<<< response:%s", response.toJSONString()));
        return response;
    }

    /**
     * 根据主机名查询接口
     *
     * @param hostNames
     * @param outputs
     * @return
     */
    private JSONObject queryInterfacesByHostnames(Collection<String> hostNames, String... outputs) {
        try {
            JSONObject filter = new JSONObject();
            filter.put("ip", hostNames);
            ZabbixRequest request = ZabbixRequestBuilder.newBuilder().method("hostinterface.get")//
                    .paramEntry("filter", filter)//
                    .paramEntry("output", ArrayUtils.isEmpty(outputs) ? "extend" : outputs)//
                    .build();
            logger.info(String.format("queryInterfacesByHostnames >>>> request:%s", request.toString()));
            JSONObject json = call(request);
            logger.info(String.format("queryInterfacesByHostnames <<<< response:%s", json.toJSONString()));
            return json;
        } catch (Exception e) {
            logger.error("host ip不存在：" + e.getMessage(), e);
        }
        return null;
    }
}
