package com.supwisdom.commonlib.execption;

/**
 * @desc 心跳错误
 */

public class TerminalHeartBeatError extends Exception {
    public TerminalHeartBeatError(String message, Exception ex) {
        super(message + ex.getMessage());
    }

    public TerminalHeartBeatError(String message) {
        super(message);
    }

    public TerminalHeartBeatError(Exception ex) {
        super(ex.getMessage());
    }
}
