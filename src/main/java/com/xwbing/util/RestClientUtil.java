package com.xwbing.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 说明: HttpClientUtil
 * 创建日期: 2017年2月23日 下午12:44:52
 * 作者: xwb
 */
public class RestClientUtil {
    private static Logger logger = LoggerFactory.getLogger(RestClientUtil.class);
    private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;
    private static final String APPLICATION_JSON = "application/json";
    private static final String URL_ERROR = "Request Url can not be empty";
    private static final String PARAM_ERROR = "Request Params is null";

    /***
     * 默认连接配置参数
     */
    private static final RequestConfig defaultRequestConfig = RequestConfig
            .custom().setSocketTimeout(600000).setConnectTimeout(600000)
            .build();
    // Request retry handler
    private static HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
        @Override
        public boolean retryRequest(IOException exception, int executionCount,
                                    HttpContext context) {
            logger.info("retryRequest-->");
            if (executionCount > 5) {
                return false;
            }
            if (exception instanceof InterruptedIOException) {
                // Timeout
                return false;
            }
            if (exception instanceof UnknownHostException) {
                // Unknown host
                return false;
            }
            if (exception instanceof ConnectTimeoutException) {
                // Connection refused
                return false;
            }
            if (exception instanceof SSLException) {
                // SSL handshake exception
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
            if (idempotent) {
                // Retry if the request is considered idempotent
                return true;
            }

            return false;
        }
    };

    static {
        poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        // 将最大连接数增加到200
        poolingHttpClientConnectionManager.setMaxTotal(200);
        // 将每个路由基础的连接数增加到20
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(20);
    }

    private static CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setDefaultRequestConfig(defaultRequestConfig)
                .setRetryHandler(retryHandler).setConnectionManagerShared(true)
                .build();
    }

    /**
     * put请求
     *
     * @param url
     * @param param
     * @return
     */
    public static JSONObject put(String url, JSONObject param) {
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException(URL_ERROR);
        }
        if (param == null || param.size() == 0) {
            throw new IllegalArgumentException(PARAM_ERROR);
        }
        logger.info("PUT request URL:" + url);
        JSONObject jsonResult = null;
        CloseableHttpClient client = getHttpClient();
        HttpPut put = new HttpPut(url);
        try {
            put.setEntity(new StringEntity(param.toString(), "UTF-8"));
            put.addHeader("Content-type", APPLICATION_JSON);
            CloseableHttpResponse response = client.execute(put);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity, "UTF-8");
                    jsonResult = JSONObject.parseObject(result);
                }
            }
            response.close();
        } catch (IOException e) {
            // result.setSuccess(false);
            // result.setMsg(e.getMessage());
            logger.error(e.getMessage());
        } finally {
            poolingHttpClientConnectionManager.closeExpiredConnections();
            poolingHttpClientConnectionManager.closeIdleConnections(120,
                    TimeUnit.MILLISECONDS);
        }
        return jsonResult;
    }

    /**
     * post请求 json
     *
     * @param url
     * @param param
     * @return
     */
    public static JSONObject postByJson(String url, JSONObject param) {
        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException(URL_ERROR);
        }
        if (param == null) {
            throw new IllegalArgumentException(PARAM_ERROR);
        }
        logger.info("postByJson request URL:" + url);
        JSONObject jsonResult = null;
        CloseableHttpClient httpClient = getHttpClient();// 创建HttpClient的实例
        HttpPost post = new HttpPost(url);// 创建HttpPost的实例
        try {
            post.setEntity(new StringEntity(param.toString(), "UTF-8"));// 设置参数到请求对象中
            post.addHeader("Content-Type", APPLICATION_JSON);// 发送json数据需要设置contentType
            CloseableHttpResponse response = httpClient.execute(post);// 执行post请求
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// 判断网络连接状态码是否正常(0-200都数正常)
                HttpEntity entity = response.getEntity();// 获取结果实体
                if (entity != null) {
                    String result = EntityUtils.toString(entity, "UTF-8");
                    jsonResult = JSONObject.parseObject(result);
                }
            }
            response.close();
        } catch (IOException e) {
            // result.setSuccess(false);
            // result.setMsg(e.getMessage());
            logger.error(e.getMessage());
        } finally {
            poolingHttpClientConnectionManager.closeExpiredConnections();
            poolingHttpClientConnectionManager.closeIdleConnections(120,
                    TimeUnit.MILLISECONDS);
        }
        return jsonResult;
    }

    /**
     * post请求 form
     *
     * @param url
     * @param param
     * @return
     */
    public static JSONObject postByForm(String url, Map<String, Object> param) {
        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException(URL_ERROR);
        }
        if (param == null || param.size() == 0) {
            throw new IllegalArgumentException(PARAM_ERROR);
        }
        logger.info("postByForm request URL:" + url);
        JSONObject jsonResult = null;
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost post = new HttpPost(url);
        try {
            // 创建参数队列
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (Entry<String, Object> keys : param.entrySet()) {
                params.add(new BasicNameValuePair(keys.getKey(), Objects
                        .toString(keys.getValue())));
            }
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            System.out.println("executing request " + post.getURI());
            CloseableHttpResponse response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity, "UTF-8");
                    jsonResult = JSONObject.parseObject(result);
                }
            }
            response.close();
        } catch (IOException e) {
            // result.setSuccess(false);
            // result.setMsg(e.getMessage());
            logger.error(e.getMessage());
        } finally {
            poolingHttpClientConnectionManager.closeExpiredConnections();
            poolingHttpClientConnectionManager.closeIdleConnections(120,
                    TimeUnit.MILLISECONDS);
        }
        return jsonResult;
    }

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public static JSONObject get(String url) {
        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException(URL_ERROR);
        }
        logger.info("GET request URL:" + url);
        CloseableHttpClient client = getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonResult = null;
        try {
            CloseableHttpResponse response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity, "UTF-8");
                    jsonResult = JSONObject.parseObject(result);
                }
            }
            response.close();
        } catch (IOException e) {
            // result.setSuccess(false);
            // result.setMsg(e.getMessage());
            logger.error(e.getMessage());
        } finally {
            poolingHttpClientConnectionManager.closeExpiredConnections();
            poolingHttpClientConnectionManager.closeIdleConnections(120,
                    TimeUnit.MILLISECONDS);
        }
        return jsonResult;

    }

    /**
     * delete请求
     *
     * @param url
     * @return
     */
    public static JSONObject delete(String url) {
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException(URL_ERROR);
        }
        logger.info("delete request URL:" + url);
        JSONObject jsonResult = null;
        CloseableHttpClient client = getHttpClient();
        HttpDelete delete = new HttpDelete(url);
        try {
            CloseableHttpResponse response = client.execute(delete);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity, "UTF-8");
                    jsonResult = JSONObject.parseObject(result);
                }
            }
            response.close();
        } catch (IOException e) {
            // result.setSuccess(false);
            // result.setMsg(e.getMessage());
            logger.error(e.getMessage());
        } finally {
            poolingHttpClientConnectionManager.closeExpiredConnections();
            poolingHttpClientConnectionManager.closeIdleConnections(120,
                    TimeUnit.MILLISECONDS);
        }
        return jsonResult;
    }

    public static void main(String[] args) {
        String url = "http://label.drore.com//gis/mapMain/find.json";
        JSONObject j = new JSONObject();
        JSONObject jr = new JSONObject();
        j.put("pageNo", 1);
        j.put("pageSize", 10);
        jr.put("name", "千岛湖");
        j.put("fields", jr);
        String ret = postByJson(url, j).toString();
        System.out.println(ret);
    }
}
