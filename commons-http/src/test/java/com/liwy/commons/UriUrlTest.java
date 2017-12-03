package com.liwy.commons;

import org.junit.Test;

import java.io.*;
import java.net.*;

/**
 * URL与URI测试
 *
 * <pre>
 * URI: 统一资源标识符
 * URL: 统一资源定位符
 * </pre>
 *
 * <pre>
 * <p>
 * URI是个纯粹的句法结构，用于指定标识Web资源的字符串的各个不同部分。
 * URL是URI的一个特例，它包含了用于定位Web资源的足够信息。
 * </p>
 * <p>
 * URI是一种语义上的抽象概念，可以是绝对的，也可以是相对的，而URL则必须提供足够的信息来定位，所以，是绝对的，
 * 而通常说的relative URL，则是针对另一个absolute URL，本质上还是绝对的。
 * </p>
 *
 * <pre>
 * URI语法：
 * 		[scheme:]scheme-specific-part[#fragment]
 * 		scheme-specific-part为 [//authority][path][?query]
 * 		authority为 [user-info@]host[:port]
 * </pre>
 *
 * URI可以自动编解码 URL无法自动编解码
 *
 * @author liwy
 *
 */
public class UriUrlTest {
    /**
     * 测试URI
     *
     */
    @Test
    public void testURI() {
        try {
            // 1. 解析URI
            // [scheme:][//[user-info@]host[:port]][path][?query][#fragment]
            URI uri = new URI(
                    "http://liwy:123@docs.mycompany.com:8080/api/net/socket.html?name=liwy&bb%3D22#Socket()");
            System.out.println(uri.toString());
            System.out.println(uri.toASCIIString());
            System.out.println("Scheme: " + uri.getScheme());
            System.out.println("SchemeSpecificPart: "
                    + uri.getSchemeSpecificPart());
            System.out.println("\tAuthority: " + uri.getAuthority());
            System.out.println("\t\tUserInfo: " + uri.getUserInfo());
            System.out.println("\t\tHost: " + uri.getHost());
            System.out.println("\t\tPort: " + uri.getPort());
            System.out.println("\tPath: " + uri.getPath());
            System.out.println("\tQuery: " + uri.getQuery());
            System.out.println("Fragment: " + uri.getFragment());

            // 2. URI相对与绝对路径转换
            URI base = new URI(
                    "http://docs.mycompany.com/api/java/net/ServerSocket.html");
            URI relative = new URI("../../java/net/Socket.html#socket()");
            System.out.println(base.resolve(relative));

            URI base1 = new URI("http://docs.mycompany.com/api");
            URI combined = new URI(
                    "http://docs.mycompany.com/api/java/lang/String.html");
            System.out.println(base1.relativize(combined));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试URL
     *
     * @throws URISyntaxException
     *
     */
    @Test
    public void testURL() {
        try {

            // 编码查询条件，将不安全字符转码
            String query = URLEncoder.encode("name=liwy&bb=22&username=李文",
                    "utf-8");
            System.out.println("query: " + query + "#Socket()");

            // 1. 解析URL
            URL url = new URL(
                    "http://liwy:123@docs.mycompany.com:8080/api/net/socket.html?"
                            + query + "#Socket()");
            System.out.println(url.toString());
            System.out.println(url.toExternalForm());
            System.out.println(url.toURI().toString());
            System.out.println(url.hashCode());

            System.out.println("Protocol: " + url.getProtocol()); // 协议名称
            System.out.println("DefPort: " + url.getDefaultPort()); // 协议的默认端口号
            System.out.println("Authority: " + url.getAuthority()); // 授权部分
            System.out.println("\tUserInfo: " + url.getUserInfo()); // 用户信息
            System.out.println("\tHost: " + url.getHost()); // 主机名
            System.out.println("\tPort: " + url.getPort()); // 实际端口号
            System.out.println("File: " + url.getFile()); // 文件名
            System.out.println("\tPath: " + url.getPath()); // 路径部分
            System.out.println("\tQuery: " + url.getQuery()); // 查询部分
            // 解码查询部分
            System.out.println("\tQuery decoder："
                    + URLDecoder.decode(url.getQuery(), "utf-8"));
            System.out.println("Ref: " + url.getRef()); // 锚点部分

            url = new URL("https://open.leshui365.com/api/receiveDeclareData");
            // 2. 获取正文对象
            URLConnection urlConnection = url.openConnection();
            System.out
                    .println("ContentType: " + urlConnection.getContentType());
            System.out.println("ContentLength: "
                    + urlConnection.getContentLength());
            System.out.println("ContentEncoding: "
                    + urlConnection.getContentEncoding());
            System.out.println("ContentTimeout: "
                    + urlConnection.getConnectTimeout());

            // 3. 通过自定义的正文处理器，处理正文
            ContentHandlerFactory fac = new MyContentHandlerFactory();
            fac.createContentHandler(urlConnection.getContentType());
            URLConnection.setContentHandlerFactory(fac);

            // System.out.println("Content: " + (String)
            // urlConnection.getContent());
            System.out.println("Content: " + (String) url.getContent()); // openConnection().getContent()的缩写

            // 4. 通过流获取正文内容
            // InputStream is = urlConnection.getInputStream();
            InputStream is = url.openStream(); // openConnection().getInputStream()的缩写
            PrintStream ps = new PrintStream(System.out);
            int read = 0;
            byte[] buff = new byte[1024];
            while ((read = is.read(buff)) != -1) {
                ps.write(buff, 0, read);
            }
            ps.close();
            is.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 实现ContentHandlerFactory接口
 *
 * @author liwy
 *
 */
class MyContentHandlerFactory implements ContentHandlerFactory {
    @Override
    public ContentHandler createContentHandler(String mimetype) {
        if (mimetype.equals("text/html")) {
            return new MyHtmlContentHandler();
        } else {
            return null;
        }
    }
}

/**
 * 定义一个自己的内容处理器
 *
 * @author liwy
 *
 */
class MyHtmlContentHandler extends ContentHandler {
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
