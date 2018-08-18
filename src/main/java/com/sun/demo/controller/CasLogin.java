package com.sun.demo.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author zhsun5@iflytek.com
 */
public class CasLogin {
    public static void main(String... args) {
        String username = "admin";
        String password = "111111";
        validateFromCAS(username, password);
    }

    public static String validateFromCAS(String username, String password) {
        String url = "http://localhost:8080/cas/v1/tickets";
        try {
            HttpURLConnection hsu = openConn(url);
            // 注意=不用反编译
            String s = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            s += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            OutputStreamWriter out = new OutputStreamWriter(hsu.getOutputStream());
            BufferedWriter bwr = new BufferedWriter(out);
            bwr.write(s);
            bwr.flush();
            bwr.close();
            out.close();

            String tgt = hsu.getHeaderField("location");
            System.out.println(hsu.getResponseCode());
            if (tgt != null && hsu.getResponseCode() == 201) {
                System.out.println(tgt);
                System.out.println("Tgt is : " + tgt.substring(tgt.lastIndexOf("/") + 1));
                tgt = tgt.substring(tgt.lastIndexOf("/") + 1);
                bwr.close();
                hsu.disconnect();

                String serviceURL = "http://172.31.6.77:8000";
                String encodedServiceURL = URLEncoder.encode("service","utf-8") +"=" + URLEncoder.encode(serviceURL,"utf-8");
                System.out.println("Service url is : " + encodedServiceURL);


                String myURL = url + "/" + tgt;
                System.out.println(myURL);
                hsu = openConn(myURL);
                out = new OutputStreamWriter(hsu.getOutputStream());
                bwr = new BufferedWriter(out);
                bwr.write(encodedServiceURL);
                bwr.flush();
                bwr.close();
                out.close();

                System.out.println("Response code is:  " + hsu.getResponseCode());
                if(404 != hsu.getResponseCode()){
                    BufferedReader isr = new BufferedReader(new InputStreamReader(hsu.getInputStream()));
                    String line;
                    System.out.println(hsu.getResponseCode());
                    if ((line = isr.readLine()) != null) {
                        System.out.println(line);
                        return line;
                    }
                    isr.close();
                }
                hsu.disconnect();

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    static HttpURLConnection openConn(String urlk) throws IOException {
        URL url = new URL(urlk);
        HttpURLConnection hsu = (HttpURLConnection) url.openConnection();
        hsu.setDoInput(true);
        hsu.setDoOutput(true);
        hsu.setRequestMethod("POST");
        return hsu;
    }
}

