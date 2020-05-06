/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.database.BO.Mdf;

/**
 *
 * @author MrSun
 */
public class MdfForm {

    private Long mdfId;
    private String code;
    private String name;
    private String address;
    private String province;
    private String provinceName;
    private Long status;
    private Long maxPort;
    private Long usedPort;
    private Long reservedPort;

    public MdfForm() {
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getMaxPort() {
        return maxPort;
    }

    public void setMaxPort(Long maxPort) {
        this.maxPort = maxPort;
    }

    public Long getMdfId() {
        return mdfId;
    }

    public void setMdfId(Long mdfId) {
        this.mdfId = mdfId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Long getReservedPort() {
        return reservedPort;
    }

    public void setReservedPort(Long reservedPort) {
        this.reservedPort = reservedPort;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getUsedPort() {
        return usedPort;
    }

    public void setUsedPort(Long usedPort) {
        this.usedPort = usedPort;
    }

    public void clearForm() {
        this.setMdfId(null);
        this.setCode(null);
        this.setName(null);
        this.setProvince(null);
        this.setStatus(null);
        this.setAddress(null);
    }

    public void copyToForm(Mdf mdf) {
        this.setMdfId(mdf.getMdfId());
        this.setCode(mdf.getCode());
        this.setName(mdf.getName());
        this.setProvince(mdf.getProvince());
        this.setStatus(mdf.getStatus());
        this.setAddress(mdf.getAddress());
    }

    public void copyToBO(Mdf mdf) {
        mdf.setMdfId(this.getMdfId());
        mdf.setCode(this.getCode());
        mdf.setName(this.getName());
        mdf.setProvince(this.getProvince());
        mdf.setStatus(this.getStatus());
        mdf.setAddress(this.getAddress());
    }
}
