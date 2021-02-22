package com.supwisdom.commonlib.okhttp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Set;

/**
 * @author zzq
 * @version 1.0.1
 * @date 2018/4/16.
 * @desc http body
 */

public class WebParams {
    private HashMap<String, Object> mParam = new HashMap<>();

    public WebParams setParameter(String name, String value) {
        this.mParam.put(name, value);
        return this;
    }

    public WebParams setParameter(String name, int value) {
        this.mParam.put(name, value);
        return this;
    }

    public WebParams setParameter(String name, float value) {
        this.mParam.put(name, value);
        return this;
    }

    public String getParameterString(String name) {
        Object val = mParam.get(name);
        if (String.class.isInstance(val)) {
            return (String) val;
        } else if (Integer.class.isInstance(val)) {
            return String.valueOf(val);
        } else if (Double.class.isInstance(val)) {
            return String.valueOf(val);
        }
        return "";
    }

    public int getParameterInt(String name) {
        Object val = mParam.get(name);
        if (Integer.class.isInstance(val)) {
            return (Integer) val;
        }
        return 0;
    }

    public float getParameterFloat(String name) {
        Object val = mParam.get(name);
        if (Double.class.isInstance(val)) {
            return (Float) val;
        }
        return 0;
    }

    public void removeParameter(String name) {
        if (mParam.containsKey(name)) {
            mParam.remove(name);
        }
    }

    public String encodeURL() throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        for (String name : mParam.keySet()) {
            Object value = mParam.get(name);
            result.append(URLEncoder.encode(name, "utf-8"))
                    .append("=")
                    .append(URLEncoder.encode(value.toString(), "utf-8"))
                    .append("&");
        }
        return result.toString();
    }

    public int encodeURL(OutputStream output) throws IOException {
        DataOutputStream byteStream = new DataOutputStream(output);
        Writer writer = new OutputStreamWriter(byteStream, "utf-8");
        for (String name : mParam.keySet()) {
            Object value = mParam.get(name);
            writer.write(URLEncoder.encode(name, "utf-8"));
            writer.write("=");
            writer.write(URLEncoder.encode(value.toString(), "utf-8"));
            writer.write("&");
        }
        return byteStream.size();
    }

    public Set<String> allParameterNames() {
        return mParam.keySet();
    }
}
