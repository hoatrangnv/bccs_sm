/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

/**
 *
 * @author Administrator
 */
public class Dslam implements java.io.Serializable {

    private String code;
    private String name;
    private String address;
    private String province;
    private String dslamIp;
    //private String brasIp;//Not used
    private Long brasId;//thay brasip
    private Long dslamId;
    private Long status;
    private Long productId;
    private Long maxPort;
    private Long usedPort;
    private Long reservedPort;
    private Long x;
    private Long y;
    private Long shopId;
    private String dslamcode;
    private String provinceName;

    private String brasIp;//theo brasId
    private Long mdfId;

    public Long getMdfId() {
        return mdfId;
    }

    public void setMdfId(Long mdfId) {
        this.mdfId = mdfId;
    }
    


    public Dslam() {
    }

    public Dslam(Long dslamId) {
        this.dslamId = dslamId;
    }

    public String getBrasIp() {
        return brasIp;
    }

    public void setBrasIp(String brasIp) {
        this.brasIp = brasIp;
    }

    public Dslam(String code, String name, String address, String province, String dslamIp,
            Long brasId, Long dslamId, Long status, Long productId, Long maxPort, Long usedPort,
            Long reservedPort, Long x, Long y, Long shopId) {
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
    }

    public Dslam(String code, String name, String address, String province, String dslamIp, Long brasIp, Long dslamId, Long status, Long productId, Long maxPort, Long usedPort, Long reservedPort, Long x, Long y, Long shopId, String dslamcode, String provinceName) {
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
        this.dslamcode = dslamcode;
        this.provinceName = provinceName;
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

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
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

    public String getDslamcode() {
        return dslamcode;
    }

    public void setDslamcode(String dslamcode) {
        this.dslamcode = code;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }



}
