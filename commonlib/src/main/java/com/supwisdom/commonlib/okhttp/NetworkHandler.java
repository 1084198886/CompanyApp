package com.supwisdom.commonlib.okhttp;

import android.text.TextUtils;

import com.supwisdom.commonlib.utils.LogUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author zzq
 * @version 1.0.1
 * @date 2018/4/16.
 * @desc okhttp 单例
 */

public class NetworkHandler {
    private static final String TAG = NetworkHandler.class.getSimpleName();

    private static NetworkHandler instance = null;
    private OkHttpClient client;
    private OkHttpClient clientLong;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType FORM_ENCODE = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
    private int communicateTime = 3; //default
    private int longCommunicateTime = 35; //default

    private NetworkHandler() {
        client = getClient(communicateTime);
        clientLong = getClient(longCommunicateTime);
    }

    private OkHttpClient getClient(int timeout) {
        return new OkHttpClient()
                .newBuilder()
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(false)
                .cache(null)
                .retryOnConnectionFailure(false)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(1, TimeUnit.SECONDS)
                .connectTimeout(1, TimeUnit.SECONDS)
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .sslSocketFactory(createSSLSocketFactory())
                .build();
    }

    private static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssfFactory;
    }

    public static NetworkHandler getInstance() {
        if (instance == null) {
            synchronized (NetworkHandler.class) {
                if (instance == null) {
                    instance = new NetworkHandler();
                }
            }
        }
        return instance;
    }

    public void setCommunicateTime(int communicateTime) {
        if (this.communicateTime != communicateTime) {
            this.communicateTime = communicateTime;
            client = getClient(communicateTime);
        }
    }

    public void setLongCommunicateTime(int longCommunicateTime) {
        if (this.longCommunicateTime != communicateTime) {
            this.longCommunicateTime = longCommunicateTime;
            clientLong = getClient(longCommunicateTime);
        }
    }

    public TransResp post(String url, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Connection", "close")
                .post(body)
                .build();
        return getTransResp(request);
    }

    /**
     * @param url
     * @param json
     * @param signedTenantId aes加密后的租户id；
     * @return
     */
    public TransResp post(String url, String json, String signedTenantId) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Connection", "close")
                .addHeader("X-TENANT-ID", signedTenantId)
                .post(body)
                .build();
        return getTransResp(request);
    }

    public TransResp post(String url) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Connection", "close")
                .build();
        return getByteTransResp(request);
    }


    /**
     * @param url
     * @param params
     * @param token
     * @param multiBuilder
     * @return
     */
    public TransResp post(String url, Map<String, String> params, String token, MultipartBody.Builder multiBuilder) {
        if (multiBuilder == null) {
            multiBuilder = new MultipartBody.Builder();
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            multiBuilder.addFormDataPart(entry.getKey(), entry.getValue());
        }

        RequestBody multipartBody = multiBuilder.setType(MultipartBody.FORM).build();
        Request.Builder builder = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Connection", "close")
                .post(multipartBody);

        if (!TextUtils.isEmpty(token)) {
            builder.addHeader("access_token", token);
        }
        return getTransResp(builder.build());
    }

    public TransResp post(String url, WebParams params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String name : params.allParameterNames()) {
            builder.add(name, params.getParameterString(name));
        }
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Connection", "close")
                .post(builder.build())
                .build();
        return getTransResp(request);
    }

    public TransResp postLong(String url, WebParams params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String name : params.allParameterNames()) {
            builder.add(name, params.getParameterString(name));
        }
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Connection", "close")
                .post(builder.build())
                .build();
        return getLongTransResp(request);
    }

    public TransResp get(String url, WebParams params) {
        Request request = new Request.Builder()
                .url(geturl(url, params))
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Connection", "close")
                .build();
        return getTransResp(request);
    }

    public void post(String url, String json, final CallBackok callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Connection", "close")
                .post(body)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.callback(null);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String content = response.body().string();
                    TransResp resp = new TransResp(response.code(), response.message());
                    if (response.isSuccessful()) {
                        resp.setRetjson(content);
                    }
                    callback.callback(resp);
                } catch (Exception e) {
                    callback.callback(null);
                }
            }
        });
    }

    public void post(String url, WebParams params, final CallBackok callback) {
        Request request = new Request.Builder()
                .url(geturl(url, params))
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Connection", "close")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.callback(null);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String content = response.body().string();
                    TransResp resp = new TransResp(response.code(), response.message());
                    if (response.isSuccessful()) {
                        resp.setRetjson(content);
                    }
                    callback.callback(resp);
                } catch (IOException e) {
                    callback.callback(null);
                }
            }
        });
    }

    private TransResp getTransResp(Request request) {
        try {
            Response response = client.newCall(request).execute();
            /**
             * 响应主体只能被消耗一次
             */
            String content = response.body().string();
            TransResp resp = new TransResp(response.code(), response.message());
            if (response.isSuccessful()) {
                resp.setRetjson(content);
            }
            return resp;
        } catch (Exception e) {
            LogUtil.e(TAG, "响应异常:" + e.getMessage());
        }
        return null;
    }

    private TransResp getByteTransResp(Request request) {
        try {
            Response response = client.newCall(request).execute();
            /*响应主体只能被消耗一次*/
            byte[] content = response.body().bytes();
            TransResp resp = new TransResp(response.code(), response.message());
            if (response.isSuccessful()) {
                resp.setRetbyte(content);
            }
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private TransResp getLongTransResp(Request request) {
        try {
            Response response = clientLong.newCall(request).execute();
            /*响应主体只能被消耗一次*/
            String content = response.body().string();
            TransResp resp = new TransResp(response.code(), response.message());
            if (response.isSuccessful()) {
                resp.setRetjson(content);
            }
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String geturl(String url, WebParams params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (params != null) {
            try {
                sb.append(params.encodeURL());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
