package com.sun.demo.pack1;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.apache.commons.codec.binary.Base64;

/**
 * token获取
 */
public class SoapToken {

    public String getSoapTokenKey() {
        String sequence = "";			//请求时间年月日 改为 毫秒数
        try {
            String userName = "soapuser";	//用户名默认
            String userpwd = "soappasswd";	//密码默认
            Date today = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            today = sdf.parse(sdf.format(today));
            sequence = String.valueOf(today.getTime());
            String key = "kMCPjf/KlgLbnc7U2KboAgykiaaHGMTnAdPA5RGWpwA=";	//密钥文本默认
            byte[] keyBase64 = Base64.decodeBase64(key.getBytes("UTF-8"));

            String token = "";				//拼接

            byte[] userNameArr = userName.getBytes();
            byte[] userpwdArr = userpwd.getBytes();
            byte[] sequenceArr = sequence.getBytes();
            byte[] keyArr = keyBase64;

            byte[] tokenArr = new byte[userNameArr.length + userpwdArr.length + sequenceArr.length + keyArr.length];

            for(int i=0;i<userNameArr.length;i++){
                tokenArr[i] = userNameArr[i];
            }

            for(int i=0;i<userpwdArr.length;i++){
                tokenArr[userNameArr.length + i] = userpwdArr[i];
            }
            for(int i=0;i<sequenceArr.length;i++){
                tokenArr[userNameArr.length + userpwdArr.length +i] = sequenceArr[i];
            }
            for(int i=0;i<keyArr.length;i++){
                tokenArr[userNameArr.length + userpwdArr.length + sequenceArr.length + i] = keyArr[i];
            }

            //HASH-SHA256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(tokenArr);

            byte[] bt = md.digest();
            token = "";
            String tmp = null;
            for (int i = 0; i < bt.length; i++) {
                tmp = (Integer.toHexString(bt[i] & 0xFF));
                if (tmp.length() == 1) {
                    token += "0";
                }
                token += tmp;
            }

            //token字符串 从16制进制 改为 10进制字符串 改为byte格式 存入 byteArr
            byte[] byteArr = new byte[32];
            for(int i=0;i<32;i++){
                byteArr[i]= (byte)(0xff & Integer.parseInt(token.substring(i*2,i*2+2),16));
            }

            token = new String(Base64.encodeBase64(byteArr));
            return "<root><public><success>1</success><desc>成功</desc></public><data><token>"+token+"</token>"
                    + "<Sequence>" + sequence + "</Sequence></data></root>";
        } catch (Exception e) {
            e.printStackTrace();
            return "<root><public><success>0</success><desc>失败</desc></public><data><token></token>"
                    + "<Sequence>" + sequence + "</Sequence></data></root>";
        }
    }

    /**
     * 测试main方法
     */
    public static void main(String[] args) throws Exception {
        String userName = "soapuser";	//用户名默认
        System.out.println("Token获取-用户名：" + userName);

        String userpwd = "soappasswd";	//密码默认
        System.out.println("Token获取-密码：" + userpwd);

        String sequence = "1480607000000";	//请求时间年月日 改为 毫秒数
//		Date today = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		today = sdf.parse(sdf.format(today));
//		sequence = String.valueOf(today.getTime());
        System.out.println("Token获取-时间戳：" + sequence);

        String key = "kMCPjf/KlgLbnc7U2KboAgykiaaHGMTnAdPA5RGWpwA=";	//密钥文本
        System.out.println("Token获取-密钥文本：" + key);
        byte[] keyBase64 = Base64.decodeBase64(key.getBytes("UTF-8"));

        String token = "";				//拼接

        byte[] userNameArr = userName.getBytes();
        byte[] userpwdArr = userpwd.getBytes();
        byte[] sequenceArr = sequence.getBytes();
        byte[] keyArr = keyBase64;

        byte[] tokenArr = new byte[userNameArr.length + userpwdArr.length + sequenceArr.length + keyArr.length];

        for(int i=0;i<userNameArr.length;i++){
            tokenArr[i] = userNameArr[i];
        }

        for(int i=0;i<userpwdArr.length;i++){
            tokenArr[userNameArr.length + i] = userpwdArr[i];
        }
        for(int i=0;i<sequenceArr.length;i++){
            tokenArr[userNameArr.length + userpwdArr.length +i] = sequenceArr[i];
        }
        for(int i=0;i<keyArr.length;i++){
            tokenArr[userNameArr.length + userpwdArr.length + sequenceArr.length + i] = keyArr[i];
        }

        //HASH-SHA256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(tokenArr);

        byte[] bt = md.digest();
        token = "";
        String tmp = null;
        for (int i = 0; i < bt.length; i++) {
            tmp = (Integer.toHexString(bt[i] & 0xFF));
            if (tmp.length() == 1) {
                token += "0";
            }
            token += tmp;
        }
        System.out.println("Token获取-SHA-256加密：" + token);

        //token字符串 从16制进制 改为 10进制字符串 改为byte格式 存入 byteArr
        byte[] byteArr = new byte[32];
        for(int i=0;i<32;i++){
            byteArr[i]= (byte)(0xff & Integer.parseInt(token.substring(i*2,i*2+2),16));
        }

        token = new String(Base64.encodeBase64(byteArr));
        System.out.println("Token获取-BASE64编码(最终值)：" + token);
    }
}
