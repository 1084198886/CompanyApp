package com.supwisdom.commonlib.okhttp;

/**
 * @author zzq
 * @version 1.0.1
 * @date 2018/4/16.
 * @desc 异步回调
 */

public interface CallBackok<T> {
    void callback(T t);
}
