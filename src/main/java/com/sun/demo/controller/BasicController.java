package com.sun.demo.controller;

import com.sun.demo.elasticsearch.entity.User;
import com.sun.demo.service.OrgService;
import com.sun.demo.util.HttpClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhsun5@iflytek.com
 */
@Controller
@RequestMapping(value = "/web")
@Api(value = "基础测试")
public class BasicController {
    @Autowired
    private OrgService orgService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "测试ModelAttribute", notes = "测试ModelAttribute", response = String.class)
    public String hello(@ModelAttribute User user, @RequestParam String aaa) {
        return "Hello，" + aaa;
    }

    @RequestMapping(value = "/queryOrg", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询机构", notes = "查询机构", response = List.class)
    public List queryOrg() {
        return orgService.queryOrg();
    }


}
