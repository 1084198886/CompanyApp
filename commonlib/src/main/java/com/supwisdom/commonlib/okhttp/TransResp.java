package com.supwisdom.commonlib.okhttp;

/**
 * @author zzq
 * @version 1.0.1
 * @date 2018/4/16.
 * @desc http body
 */

public class TransResp {
    public TransResp(int retcode, String retmsg) {
        this.retcode = retcode;
        this.retmsg = retmsg;
    }

    /**
     * HTTP返回码
     */
    private int retcode;

    /**
     * 返回消息，如果是200 则为空，非200则为返回错误信息
     */
    private String retmsg;
    /**
     * 返回消息实体,string
     */
    private String retjson;
    /**
     * 返回消息实体,bytes
     */
    private byte[] retbyte;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }

    public String getRetjson() {
        return retjson;
    }

    public void setRetjson(String retjson) {
        this.retjson = retjson;
    }

    public byte[] getRetbyte() {
        return retbyte;
    }

    public void setRetbyte(byte[] retbyte) {
        this.retbyte = retbyte;
    }
}
