package com.liwy.commons;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * HttpClient测试
 * <p>
 * 注意Jboss下默认3.0.1版本
 *
 * @author liwy
 */
public class HttpClientTest {
    private final String URLSTR = "https://open.leshui365.com/api/receiveDeclareData";

    // private final String URLSTR = "http://www.oscafe.net";

//    @Test
    public void testHttpClient() throws IOException {
        //1. URIBuilder工具类
        try {
            URI uri = new URIBuilder().setScheme("http").setHost("www.baidu.com").setPath("/search")
                    .setParameter("q", "httpclient").setParameter("aq", "f").build();
            System.out.println(uri.toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // 2. HttpClient 的Entity类型
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://www.oscafe.net");

        // FileEntity
        FileEntity entity = new FileEntity(new File(""), ContentType.create("text/plain", "utf-8"));
        post.setEntity(entity);
        // UrlEncodedFormEntity
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("param1", "value1"));
        formparams.add(new BasicNameValuePair("param2", "value2"));
        UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(formparams);
        post.setEntity(entity1);
        // StringEntity
        StringEntity entity2 = new StringEntity("important message", ContentType.create("plain/text", Consts.UTF_8));
        entity2.setChunked(true);//通知HttpClient可以优先使用块编码

        // 3. 通过ResponseHandler处理器处理响应
        ResponseHandler<String> rh = new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                //对HttpResponse 进行处理
                StatusLine statusLine = httpResponse.getStatusLine();
                HttpEntity entity = httpResponse.getEntity();
                System.out.println(statusLine);
                if (statusLine.getStatusCode() >= 300) {
                    throw new HttpResponseException(
                            statusLine.getStatusCode(),
                            statusLine.getReasonPhrase());
                }
                if (entity == null) {
                    throw new ClientProtocolException("Response contains no content");
                }

                ContentType contentType = ContentType.getOrDefault(entity);//获取Content-type中的编码
                Charset charset = contentType.getCharset();
                Reader reader = new InputStreamReader(entity.getContent(), charset);//获取字符流
                BufferedReader br = new BufferedReader(reader);
                String result = "";
                String buff = "";
                while ((buff = br.readLine()) != null) {
                    result += buff;
                }
                return result;
            }
        };

        String str = httpclient.execute(post, rh);
        System.out.println("响应正文：" + str);
        httpclient.close();

        //4. 设置连接KeepAlive策略
        ConnectionKeepAliveStrategy keepAliveStrategy = new DefaultConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                long keeAlive = super.getKeepAliveDuration(response, context);
                if (keeAlive == -1) {
                    // 如果服务器没有显示的设置 keep-alive的时间，就设置为保持连接5秒
                    keeAlive = 5000;
                }
                return keeAlive;
            }
        };
        //5. 配置Request的拦截器
        HttpRequestInterceptor httpRequestInterceptor = new HttpRequestInterceptor() {
            @Override
            public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
                AtomicInteger count = (AtomicInteger) httpContext.getAttribute("count");
                String val = Integer.toString(count.getAndIncrement());
                System.out.println(val);
                httpRequest.addHeader("Count", val);
            }
        };
        CloseableHttpClient httpClient = HttpClients.custom().setKeepAliveStrategy(keepAliveStrategy)
                .addInterceptorLast(httpRequestInterceptor).build();

        //设置上下文
//        HttpContext context = <...>;
//        HttpClientContext clientContext = HttpClientContext.adapt(context);
//        HttpHost target = clientContext.getTargetHost();
//        HttpRequest request = clientContext.getRequest();
//        HttpResponse response = clientContext.getResponse();
//        RequestConfig config = clientContext.getRequestConfig();
        HttpClientContext localContext = HttpClientContext.create();
        AtomicInteger count = new AtomicInteger(1);
        localContext.setAttribute("count", count);

        //设置请求配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(1000)
                .setConnectTimeout(1000).build();

        HttpPost httpPost = new HttpPost("http://www.baidu.com");
        httpPost.setConfig(requestConfig);
        for (int i = 0; i < 10; i++) {
            CloseableHttpResponse response = httpClient.execute(httpPost, localContext);
            try {
                HttpEntity entity3 = response.getEntity();
            } finally {
                response.close();
            }
        }

        httpClient.close();

        //6. 请求重试处理器
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
                if (i >= 5) {
                    // 超过设置的最大次数则不再重试
                    return false;
                }
                if (e instanceof InterruptedIOException) {
                    // Timeout
                    return false;
                }
                if (e instanceof UnknownHostException) {
                    // Unknown host
                    return false;
                }
                if (e instanceof ConnectTimeoutException) {
                    // Connection refused
                    return false;
                }
                if (e instanceof SSLException) {
                    // SSL handshake exception
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(httpContext);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // Retry if the request is considered idempotent
                    return true;
                }
                return false;
            }
        };


        CloseableHttpClient httpclient2 = HttpClients.custom()
                .setRetryHandler(httpRequestRetryHandler)
                .build();

        HttpClientContext localContext2 = HttpClientContext.create();

        HttpPost httpPost2=new HttpPost("http://www.baidu.com");
        httpPost2.setConfig(requestConfig);
        for(int i=0;i<10;i++) {
            httpPost2.abort();//终止请求
            CloseableHttpResponse response=httpclient.execute(httpPost2,localContext2);
            try {
                HttpEntity entity4=response.getEntity();
            }finally {
                response.close();
            }
        }

    }

    /**
     * 测试get请求
     */
    @Test
    public void testGet() {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(6000)
                .setSocketTimeout(6000).setCookieSpec(CookieSpecs.STANDARD)
                .build(); // 设置超时及cookie策略

        // 1. 创建httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 2. 创建get请求
        HttpGet httpget = new HttpGet(URLSTR);
        httpget.setConfig(config); // 设置请求基本属性参数
        System.out.println(httpget.getMethod());
        System.out.println(httpget.getURI());

        try {
            // 3. 执行get请求
            CloseableHttpResponse response = httpClient.execute(httpget);
            // 4. 打印响应状态
            System.out.println(response.getProtocolVersion());
            System.out.println(response.getStatusLine());
            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println(response.getStatusLine().getReasonPhrase());
            // 5. 获取响应头信息
            Header h1 = response.getFirstHeader("Set-Cookie");
            System.out.println(h1);
            Header h2 = response.getLastHeader("Set-Cookie");
            System.out.println(h2);
            Header[] hs = response.getHeaders("Set-Cookie");
            System.out.println(hs.length);
            // 6. 获取响应实体
            HttpEntity entity = response.getEntity();
            System.out.println("正文类型：" + entity.getContentType());
            System.out.println("正文编码：" + entity.getContentEncoding());
            System.out.println("正文长度：" + entity.getContentLength());
            //强烈建议不要使用EntityUtils，除非响应实体来自受信任的HTTP服务器，并且已知其长度有限。
//            System.out.println("正文：" + EntityUtils.toString(entity));
            if (entity != null) {
                //会将entity的内容缓存到内存中，允许entity进行多次访问
                entity = new BufferedHttpEntity(entity);

                long len = entity.getContentLength();
                //在entity内容非空且<2k的时候可以使用EntityUtils,否则使用流
                if (len != -1 && len < 2048) {
                    System.out.println("正文：" + EntityUtils.toString(entity));
                } else {
                    InputStream is = entity.getContent();
                    try {
                        StringBuffer result = new StringBuffer();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String buffLine = "";
                        while ((buffLine = br.readLine()) != null) {
                            result.append(buffLine + "\n");
                        }
                        System.out.println("正文：" + result.toString());
                    } finally {
                        //关闭entity的输入流
                        is.close();
                    }
                }
            }
            response.close();
            httpClient.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试https的get请求
     */
//    @Test
    public void testHttpsGet() {

        try {
            // 1. 设置keyStore
            KeyStore trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());
            FileInputStream instream = new FileInputStream(new File(
                    "d:\\tomcat.keystore"));
            trustStore.load(instream, "123456".toCharArray());

            // 相信自己的CA和所有自签名的证书
            SSLContext sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();

            // 只允许使用TLSv1协议
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());

            /*
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain,
                                         String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            */

            // 2. 创建httpClient
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf).build();

            // 3. 创建get请求
            HttpGet httpget = new HttpGet(URLSTR);
            System.out.println(httpget.getMethod());
            System.out.println(httpget.getURI());

            // 4. 执行get请求
            CloseableHttpResponse response = httpClient.execute(httpget);
            // 5. 打印响应状态
            System.out.println(response.getStatusLine());
            // 6. 获取响应实体
            HttpEntity entity = response.getEntity();
            System.out.println("内容长度：" + entity.getContentLength());
            System.out.println("内容：" + EntityUtils.toString(entity));

            response.close();
            httpClient.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    @Test
    public void testPost() {

        // 1. 创建httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 2. 创建post请求
        HttpPost httpPost = new HttpPost(URLSTR);
        System.out.println(httpPost.getMethod());
        System.out.println(httpPost.getURI());

        try {
            // 3. 设置表单实体
            BasicNameValuePair bnvp = new BasicNameValuePair("type", "house");
            List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
            formparams.add(bnvp);
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(
                    formparams, "UTF-8");
            httpPost.setEntity(uefEntity);

            // 4. 执行post请求
            CloseableHttpResponse response = httpClient.execute(httpPost);

            // 5. 打印响应状态
            System.out.println(response.getStatusLine());
            // 6. 获取响应实体
            HttpEntity entity = response.getEntity();
            System.out.println("内容长度：" + entity.getContentLength());
            System.out.println("内容：" + EntityUtils.toString(entity));

            response.close();
            httpClient.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试上传文件
     */
//    @Test
    public void testUpload() {
        // 1. 创建httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            // 2. 创建post请求
            HttpPost httpPost = new HttpPost(URLSTR);
            System.out.println(httpPost.getMethod());
            System.out.println(httpPost.getURI());

            // 3. 设置Http实体
            MultipartEntityBuilder meb = MultipartEntityBuilder.create();
            File file = new File("");
            String name = "";
            meb.addTextBody("name", name);
            meb.addBinaryBody("file", file);
            // meb.addPart("file", new FileBody(file));

            HttpEntity httpEntity = meb.build();
            httpPost.setEntity(httpEntity);

            // 4. 执行post请求
            CloseableHttpResponse response = httpClient.execute(httpPost);

            // 5. 打印响应状态
            System.out.println(response.getStatusLine());
            // 6. 获取响应实体
            HttpEntity entity = response.getEntity();
            System.out.println("内容长度：" + entity.getContentLength());
            System.out.println("内容：" + EntityUtils.toString(entity));

            response.close();
            httpClient.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
