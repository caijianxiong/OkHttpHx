package com.zhy.http.okhttp.log;

import android.text.TextUtils;
import android.util.Log;

import com.zhy.http.okhttp.utils.logUtil;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by zhy on 16/3/1.
 */
public class LoggerInterceptor implements Interceptor {
    public static final String TAG = "OkHttpUtils";
    private String tag;
    private boolean showResponse;

    public LoggerInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }

    public LoggerInterceptor(String tag) {
        this(tag, false);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private Response logForResponse(Response response) {
        try {
            //===>response log
            logUtil.d_2(tag, "========response'log=======");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            logUtil.d_2(tag, "url : " + clone.request().url());
            logUtil.d_2(tag, "code : " + clone.code());
            logUtil.d_2(tag, "protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message()))
                logUtil.d_2(tag, "message : " + clone.message());

            if (showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        logUtil.d_2(tag, "responseBody's contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            String resp = body.string();
                            logUtil.d_2(tag, "responseBody's content : " + resp);

                            body = ResponseBody.create(mediaType, resp);
                            return response.newBuilder().body(body).build();
                        } else {
                            logUtil.d_2(tag, "responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }

            logUtil.d_2(tag, "========response'log=======end");
        } catch (Exception e) {
            logUtil.d_2(tag, "exception---"+e.getMessage());
//            e.printStackTrace();
        }

        return response;
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            logUtil.d_2(tag, "========request'log=======");
            logUtil.d_2(tag, "method : " + request.method());
            logUtil.d_2(tag, "url : " + url);
            if (headers != null && headers.size() > 0) {
                logUtil.d_2(tag, "headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    logUtil.d_2(tag, "requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        logUtil.d_2(tag, "requestBody's content : " + bodyToString(request));
                    } else {
                        logUtil.d_2(tag, "requestBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
//            logUtil.d_2(tag, "========request'log=======end");
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
