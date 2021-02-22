package com.supwisdom.commonlib.execption;

/**
 * @desc 设备配置错误 (重新初始化)
 */

public class TerminalConfigError extends Exception {
    public TerminalConfigError(String message, Exception ex) {
        super(message + ex.getMessage());
    }

    public TerminalConfigError(String message) {
        super(message);
    }

    public TerminalConfigError(Exception ex) {
        super(ex.getMessage());
    }
}
