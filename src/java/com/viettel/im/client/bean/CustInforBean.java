/*
 * Copyright 2014 Viettel Software. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.im.client.bean;

import java.util.Date;

/**
 *
 * @author Toancx
 * @version
 * @since Mar 24, 2015
 */
public class CustInforBean {
    private String isdn;
    private String name;
    private Date birthday;
    private String idNo;
    private String precinctCode;
    private String districtCode;
    private String provinceCode;

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getPrecinctCode() {
        return precinctCode;
    }

    public void setPrecinctCode(String precinctCode) {
        this.precinctCode = precinctCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
    
    
}
