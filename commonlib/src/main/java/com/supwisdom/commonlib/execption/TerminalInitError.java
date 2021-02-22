package com.supwisdom.commonlib.execption;

/**
 * @desc 设备初始化错误
 */

public class TerminalInitError extends Exception {
    public TerminalInitError(String message, Exception ex) {
        super(message + ex.getMessage());
    }

    public TerminalInitError(String message) {
        super(message);
    }

    public TerminalInitError(Exception ex) {
        super(ex.getMessage());
    }
}
