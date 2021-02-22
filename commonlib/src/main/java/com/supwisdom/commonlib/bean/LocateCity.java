package com.supwisdom.commonlib.bean;

/**
 * @author gqy
 * @date 2019/8/23
 * @desc TODO
 * @see
 * @since 1.0.0
 */
public class LocateCity {
    private String city;
    private String province;
    private String cityCode;

    public LocateCity(String city, String province, String cityCode) {
        this.city = city;
        this.province = province;
        this.cityCode = cityCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
