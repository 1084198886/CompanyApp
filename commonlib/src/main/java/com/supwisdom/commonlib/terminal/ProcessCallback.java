package com.supwisdom.commonlib.terminal;

/**
 * @author zzq
 * @version 1.0.1
 * @date 2018/4/17.
 * @desc 进度回调
 */

public interface ProcessCallback {
    void onProcess(int process, String message);

    void onFinish(String message);

    void onCancel(int exitCode, String message);
}
