package com.supwisdom.commonlib.execption;

/**
 * @desc 后台数据同步异常
 */
public class TerminalBackgroundTaskError extends Exception {
    public TerminalBackgroundTaskError(String message, Exception ex) {
        super(message + ex.getMessage());
    }

    public TerminalBackgroundTaskError(String message) {
        super(message);
    }

    public TerminalBackgroundTaskError(Exception ex) {
        super(ex.getMessage());
    }
}
