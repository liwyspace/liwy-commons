package com.liwy.commons;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * URLConnection测试类
 *
 * @author liwy
 */
public class URLConnectionTest {
    //	private final String URLSTR = "https://open.leshui365.com/api/receiveDeclareData";
    private final String URLSTR = "http://www.oscafe.net";

    @Test
    public void testURLConnection() {

        try {
            URL url = new URL(URLSTR);
            // 1. 通过在 URL 上调用 openConnection 方法创建连接对象。
            URLConnection urlConnection = url.openConnection();
            // 1.1  通过代理连接
//			String proxyHost = "";
//			int proxyPort = 0;
//			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
//			URLConnection urlConnection = url.openConnection(proxy);

            // 2. 处理设置参数和一般请求属性。
            // 设置是否从httpUrlConnection读入(是否接收数据)，默认情况下是true;
            urlConnection.setDoInput(true);
            // 是否启用输入流（即是否发送数据）, 默认情况下是false; post请求必须为true
            urlConnection.setDoOutput(false);
            // 是否使用客户端缓存；Post 请求不能使用缓存
            urlConnection.setUseCaches(false);

            //设置一般请求属性
            urlConnection.setRequestProperty("Accept-Charset", "utf-8");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


            // 3. 使用 connect 方法建立到远程对象的实际连接。
            urlConnection.connect();

            // 4. 远程对象变为可用。远程对象的头字段和内容变为可访问

            // 4.1 获取响应头信息
            System.out.println("Date: " + urlConnection.getDate());
            //变量头字段
            Map<String, List<String>> header = urlConnection.getHeaderFields();
            Iterator<Entry<String, List<String>>> it = header.entrySet()
                    .iterator();
            while (it.hasNext()) {
                Map.Entry<String, List<String>> entry = it.next();
                System.out.println(entry.getKey() + ":"
                        + entry.getValue().toString());
            }
            // 4.2 获取响应正文属性
            System.out.println("正文类型：" + urlConnection.getContentType());
            System.out.println("正文长度：" + urlConnection.getContentLength());
            System.out.println("编码方式：" + urlConnection.getContentEncoding());

            // 4.3 通过自定义处理器处理正文新
            ContentHandlerFactory fac = new LiwyContentHandlerFactory();
            fac.createContentHandler(urlConnection.getContentType());
            URLConnection.setContentHandlerFactory(fac);
            String htmlStr = (String) urlConnection.getContent();
            System.out.println("正文内容：" + htmlStr);

			/* 4.3 通过流打印正文
            Scanner sc = new Scanner(urlConnection.getInputStream());
			System.out.println("流：");
			while (sc.hasNextLine()) {
				System.out.println(sc.nextLine());
			}
			sc.close();
			//*/


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

/**
 * 实现ContentHandlerFactory接口
 *
 * @author liwy
 */
class LiwyContentHandlerFactory implements ContentHandlerFactory {
    @Override
    public ContentHandler createContentHandler(String mimetype) {
        System.out.println(mimetype);
        if (mimetype.equals("text/html")) {
            return new LiwyHtmlContentHandler();
        } else {
            return null;
        }
    }
}

/**
 * 定义一个自己的内容处理器
 *
 * @author liwy
 */
class LiwyHtmlContentHandler extends ContentHandler {
    /*
     * 读取服务器发送的一行数据，把它转换为字符串对象
     */
    @Override
    public Object getContent(URLConnection connection) throws IOException {
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String content = "";
        String buff = "";
        while ((buff = reader.readLine()) != null) {
            content += (buff + "\n");
        }
        reader.close();
        is.close();
        return content;
    }
}
