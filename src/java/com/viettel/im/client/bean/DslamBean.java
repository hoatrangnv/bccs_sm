/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

/**
 *
 * @author User
 */
public class DslamBean implements java.io.Serializable {

    private String code;
    private String name;
    private String address;
    private String province;
    private String dslamIp;
    private Long brasId;
    private Long dslamId;
    private Long status;
    private Long productId;
    private Long maxPort;
    private Long usedPort;
    private Long reservedPort;
    private Long x;
    private Long y;
    private Long shopId;
    private String provinceName;

    private Long mdfId;
    private String mdfName;
    private String mdfCode;

    public String getMdfCode() {
        return mdfCode;
    }

    public void setMdfCode(String mdfCode) {
        this.mdfCode = mdfCode;
    }

    public String getMdfName() {
        return mdfName;
    }

    public void setMdfName(String mdfName) {
        this.mdfName = mdfName;
    }


    public Long getMdfId() {
        return mdfId;
    }

    public void setMdfId(Long mdfId) {
        this.mdfId = mdfId;
    }

    //show in list
    private String brasIp;//theo bras ip

    public DslamBean() {
    }
    

    public DslamBean(String code, String name, String address, String province, String dslamIp, Long brasId, Long dslamId, Long status, Long productId, Long maxPort, Long usedPort, Long reservedPort, Long x, Long y, Long shopId, String provinceName) {
        this.code = code;
        this.name = name;
        this.address = address;
        this.province = province;
        this.dslamIp = dslamIp;
        this.brasId = brasId;
        this.dslamId = dslamId;
        this.status = status;
        this.productId = productId;
        this.maxPort = maxPort;
        this.usedPort = usedPort;
        this.reservedPort = reservedPort;
        this.x = x;
        this.y = y;
        this.shopId = shopId;
        this.provinceName = provinceName;
    }

    public DslamBean(String code, String name, String address, String province, String dslamIp, Long brasId, String brasIp, Long dslamId, Long status, Long productId, Long maxPort, Long usedPort, Long reservedPort, Long x, Long y, Long shopId, String provinceName) {
        this.code = code;
        this.name = name;
        this.address = address;
        this.province = province;
        this.dslamIp = dslamIp;
        this.brasId = brasId;
        this.brasIp = brasIp;
        this.dslamId = dslamId;
        this.status = status;
        this.productId = productId;
        this.maxPort = maxPort;
        this.usedPort = usedPort;
        this.reservedPort = reservedPort;
        this.x = x;
        this.y = y;
        this.shopId = shopId;
        this.provinceName = provinceName;
    }


    public DslamBean(String code, String name, String address, String province, String dslamIp, Long brasId, String brasIp, Long dslamId, Long status, Long productId, Long maxPort, Long usedPort, Long reservedPort, Long x, Long y, Long shopId, String provinceName, Long mdfId, String mdfCode, String mdfName) {
        this.code = code;
        this.name = name;
        this.address = address;
        this.province = province;
        this.dslamIp = dslamIp;
        this.brasId = brasId;
        this.brasIp = brasIp;
        this.dslamId = dslamId;
        this.status = status;
        this.productId = productId;
        this.maxPort = maxPort;
        this.usedPort = usedPort;
        this.reservedPort = reservedPort;
        this.x = x;
        this.y = y;
        this.shopId = shopId;
        this.provinceName = provinceName;

        this.mdfId = mdfId;
        this.mdfCode = mdfCode;
        this.mdfName = mdfName;
    }


    public String getBrasIp() {
        return brasIp;
    }

    public void setBrasIp(String brasIp) {
        this.brasIp = brasIp;
    }

    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getBrasId() {
        return brasId;
    }

    public void setBrasId(Long brasId) {
        this.brasId = brasId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getDslamId() {
        return dslamId;
    }

    public void setDslamId(Long dslamId) {
        this.dslamId = dslamId;
    }

    public String getDslamIp() {
        return dslamIp;
    }

    public void setDslamIp(String dslamIp) {
        this.dslamIp = dslamIp;
    }

    

    public Long getMaxPort() {
        return maxPort;
    }

    public void setMaxPort(Long maxPort) {
        this.maxPort = maxPort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Long getReservedPort() {
        return reservedPort;
    }

    public void setReservedPort(Long reservedPort) {
        this.reservedPort = reservedPort;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
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

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    

}

  
