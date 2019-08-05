package com.zhy.sample_okhttp;

import com.zhy.http.okhttp.utils.Exceptions;

/**
 * @author caicai
 * @create 2019/8/5
 * @Describe
 */
public interface MyCallBack<T> {
    void onSucess(T t);
    void onError(Exceptions exceptions);
}
