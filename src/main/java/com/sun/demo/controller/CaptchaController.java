package com.sun.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.demo.util.Captcha;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhsun5@iflytek.com
 */
@Controller
public class CaptchaController {

    @RequestMapping(value = "captcha", method = RequestMethod.GET)
    public void handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        OutputStream out = null;
        try {
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("image/png");
            //生成验证码
            Captcha code = new Captcha(100, 38, 4, 10);
            //存储验证码到session
            request.getSession().setAttribute("captcha", code.getCode());
            //写文件到客户端
            out = response.getOutputStream();
            code.write(out);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    @RequestMapping(value = "/chkCode", method = RequestMethod.POST)
    public void checkJSON(String code, HttpServletRequest req, HttpServletResponse resp) {
        //获取session中的验证码
        String storeCode = (String) req.getSession().getAttribute("captcha");
        Map<String, Object> map = new HashMap<>();
        //验证
        if (!StringUtils.isEmpty(storeCode) && code.equalsIgnoreCase(storeCode)) {
            map.put("success", true);
            map.put("msg", "验证成功");
        } else if (StringUtils.isEmpty(code)) {
            map.put("success", false);
            map.put("msg", "验证码不能为空");
        } else {
            map.put("success", false);
            map.put("msg", "验证码错误");
        }
        this.writeJSON(resp, map);
    }

    /**
     * 在SpringMvc中获取到Session
     *
     * @return
     */
    private void writeJSON(HttpServletResponse response, Object object) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            ObjectMapper mapper = new ObjectMapper(); //转换器
            String json = mapper.writeValueAsString(object);
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
