package com.liwy.commons;

import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpURLConnectionTest {
    private final String URLSTR = "https://open.leshui365.com/api/receiveDeclareData";

    /**
     * 测试HttpURLConnection的Get方法
     */
    @Test
    public void testGet() {
        try {
            // 获取连接对象
            URLConnection urlConnection = new URL(URLSTR).openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlConnection;
            // 处理设置参数和一般请求属性
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setDoOutput(false);
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setRequestProperty("Accept-Charset", "utf-8");
            // 连接url，getInputStream等方法会自动调用connect方法，所以可以省略此步
            httpUrlConnection.connect();

            // 获取返回状态码
            int responseCode = httpUrlConnection.getResponseCode();
            System.out.println("返回码：" + responseCode);
            if (responseCode > 300) {
                System.out
                        .println("HTTP Request is not success, Response code is "
                                + responseCode);
            }

            // 从输入流中获取返回信息
            StringBuffer result = new StringBuffer();
            InputStream is = httpUrlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String buffLine = "";
            while ((buffLine = br.readLine()) != null) {
                result.append(buffLine + "\n");
            }
            System.out.println(result.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试HttpURLConnection的Post请求
     */
    @Test
    public void testPost() {
        // 1. 准备请求参数
        String parameter = getJsonStr();

        try {
            // 2. 获取连接对象
            System.out.println(parameter);
            URLConnection urlConnection = new URL(URLSTR).openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlConnection;

            // 3. 处理设置参数和一般请求属性
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setRequestProperty("Accept-Charset", "utf-8");
            httpUrlConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            httpUrlConnection.setRequestProperty("Content-Length",
                    String.valueOf(parameter.length()));

            // 4. 连接url，getInputStream等方法会自动调用connect方法，所以可以省略此步
            httpUrlConnection.connect();

            // 5. 设置输出流传入参数
            OutputStream os = httpUrlConnection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            osw.write(parameter.toString());
            osw.flush();

            // 6. 获取返回状态码
            int responseCode = httpUrlConnection.getResponseCode();
            System.out.println("返回码：" + responseCode);
            if (responseCode > 300) {
                System.out
                        .println("HTTP Request is not success, Response code is "
                                + responseCode);
            }

            // 7. 接收响应流
            StringBuffer result = new StringBuffer();
            InputStream is = httpUrlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String buffLine = "";
            while ((buffLine = br.readLine()) != null) {
                result.append(buffLine + "\n");
            }
            System.out.println(result.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getJsonStr() {
        return "{\"head\": {\"reportRegoinCode\": \"310000\", \"customerName\": \"上海涵画商务咨询中心（有限合伙）\", \"taxTypeCode\": \"008\", \"declareMonth\": \"2016-12\", \"taxpayerNumber\": \"91310118MA1JLF2478\", \"declarePeriodEnd\": \"2016-11-30\", \"declarePeriodStart\": \"2016-11-01\", \"dateTime\": \"20161130102130\", \"userName\": \"wangkeTaxer\"}, \"body\": [{\"reportType\": \"\", \"data\": [] }, {\"reportType\": \"\", \"data\": [] } ] }";
    }

}
