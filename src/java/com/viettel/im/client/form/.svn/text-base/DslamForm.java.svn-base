/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.client.bean.DslamBean;
import com.viettel.im.database.BO.Dslam;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author Administrator
 */
public class DslamForm extends ActionForm {

    private Long dslamId;
    private String code;
    private String name;
    private String address;
    private String province;
    //  private String provinceName;
    private String dslamIp;
    private Long brasId;
    private String status;
    private String productId;
    private String maxPort;
    private String usedPort;
    private String reservedPort;
    private String x;
    private String y;
    private String shopId;
    private String dslamMessage;
    public String dslamcode = code;

    private Long mdfId;
    private String mdfCode;
    private String mdfName;

    public String getMdfCode() {
        return mdfCode;
    }

    public void setMdfCode(String mdfCode) {
        this.mdfCode = mdfCode;
    }

    public Long getMdfId() {
        return mdfId;
    }

    public void setMdfId(Long mdfId) {
        this.mdfId = mdfId;
    }

    public String getMdfName() {
        return mdfName;
    }

    public void setMdfName(String mdfName) {
        this.mdfName = mdfName;
    }

    
    public DslamForm() {
        resetForm();
    }

    public void resetForm() {
        this.dslamId = 0L;
        this.address = "";
        this.brasId = null;
        this.code = "";
        this.dslamIp = "";
        this.maxPort = "";
        this.name = "";
        this.reservedPort = "";
        this.usedPort = "";
        this.productId = "";
        this.province = "";
        this.y = "";
        this.x = "";
        this.status = "";
        //   this.provinceName = "";
        this.shopId = "";
        this.dslamMessage = "";

        this.mdfId = null;
        this.mdfCode = "";
        this.mdfName = "";
    }

    public void copyDataToBO(Dslam dslam) {
        try {
            dslam.setDslamId(this.getDslamId());
            dslam.setAddress(this.getAddress());
            dslam.setBrasId(this.getBrasId());
            dslam.setCode(this.getCode());
            dslam.setDslamcode(this.getCode());
            dslam.setDslamIp(this.getDslamIp());
            if (this.getMaxPort() != null && !this.getMaxPort().trim().equals("")) {
                dslam.setMaxPort(Long.parseLong(this.getMaxPort()));
            }
            dslam.setName(this.getName());
            if (this.getReservedPort() != null && !this.getReservedPort().trim().equals("")) {
                dslam.setReservedPort(Long.parseLong(this.getReservedPort()));
            }
            if (this.getUsedPort() != null && !this.getUsedPort().trim().equals("")) {
                dslam.setUsedPort(Long.parseLong(this.getUsedPort()));
            }
            if (this.getProductId() != null && !this.getProductId().trim().equals("")) {
                dslam.setProductId(Long.parseLong(this.getProductId()));
            }
            dslam.setProvince(this.getProvince());
            if (this.getX() != null && !this.getX().trim().equals("")) {
                dslam.setX(Long.parseLong(this.getX()));
            }
            if (this.getY() != null && !this.getY().trim().equals("")) {
                dslam.setY(Long.parseLong(this.getY()));
            }
            if (this.getShopId() != null && !this.getShopId().trim().equals("")) {
                dslam.setShopId(Long.parseLong(this.getShopId()));
            }
            if (this.getStatus() != null && !this.getStatus().trim().equals("")) {
                dslam.setStatus(Long.parseLong(this.getStatus()));
            }

            dslam.setMdfId(this.getMdfId());

        } catch (Exception ex) {
            Logger.getLogger(PartnerForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void copyDataFromBO(Dslam dslam) {

        try {
            this.dslamId = dslam.getDslamId();
            this.shopId = (dslam.getShopId() == null) ? "" : String.valueOf(dslam.getShopId());

            this.address = dslam.getAddress();
            this.brasId = dslam.getBrasId();
            this.code = dslam.getCode();
            this.dslamcode = dslam.getCode();
            this.dslamIp = (dslam.getDslamIp() == null) ? "" : String.valueOf(dslam.getDslamIp());
            this.maxPort = (dslam.getMaxPort() == null) ? "" : String.valueOf(dslam.getMaxPort());
            this.name = dslam.getName();
            this.reservedPort = (dslam.getReservedPort() == null) ? "" : String.valueOf(dslam.getReservedPort());
            this.usedPort = (dslam.getUsedPort() == null) ? "" : String.valueOf(dslam.getUsedPort());
            this.productId = (dslam.getProductId() == null) ? "" : String.valueOf(dslam.getProductId());
            this.province = dslam.getProvince();
            this.x = (dslam.getX() == null) ? "" : String.valueOf(dslam.getX());
            this.y = (dslam.getY() == null) ? "" : String.valueOf(dslam.getY());
            this.status = String.valueOf(dslam.getStatus());

            this.mdfId = dslam.getMdfId();
        } catch (Exception ex) {
            Logger.getLogger(PartnerForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void copyDataToBean(DslamBean dslam) {
        try {
            dslam.setDslamId(this.getDslamId());
            dslam.setAddress(this.getAddress());
            dslam.setBrasId(this.getBrasId());
            dslam.setCode(this.getCode());
            dslam.setDslamIp(this.getDslamIp());
            if (this.getMaxPort() != null && !this.getMaxPort().trim().equals("")) {
                dslam.setMaxPort(Long.parseLong(this.getMaxPort()));
            }
            dslam.setName(this.getName());
            if (this.getReservedPort() != null && !this.getReservedPort().trim().equals("")) {
                dslam.setReservedPort(Long.parseLong(this.getReservedPort()));
            }
            if (this.getUsedPort() != null && !this.getUsedPort().trim().equals("")) {
                dslam.setUsedPort(Long.parseLong(this.getUsedPort()));
            }
            if (this.getProductId() != null && !this.getProductId().trim().equals("")) {
                dslam.setProductId(Long.parseLong(this.getProductId()));
            }
            dslam.setProvince(this.getProvince());
            if (this.getX() != null && !this.getX().trim().equals("")) {
                dslam.setX(Long.parseLong(this.getX()));
            }
            if (this.getY() != null && !this.getY().trim().equals("")) {
                dslam.setY(Long.parseLong(this.getY()));
            }
            if (this.getShopId() != null && !this.getShopId().trim().equals("")) {
                dslam.setShopId(Long.parseLong(this.getShopId()));
            }
            if (this.getStatus() != null && !this.getStatus().trim().equals("")) {
                dslam.setStatus(Long.parseLong(this.getStatus()));
            }

            dslam.setMdfId(this.mdfId);

        } catch (Exception ex) {
            Logger.getLogger(PartnerForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void copyDataFromBean(DslamBean dslamBean) {
        try {
            this.dslamId = dslamBean.getDslamId();
            this.address = dslamBean.getAddress();
            this.brasId = dslamBean.getBrasId();
            this.code = dslamBean.getCode();
            this.dslamcode = dslamBean.getCode();
            this.dslamIp = (dslamBean.getDslamIp() == null) ? "" : String.valueOf(dslamBean.getDslamIp());
            this.maxPort = (dslamBean.getMaxPort() == null) ? "" : String.valueOf(dslamBean.getMaxPort());
            this.name = dslamBean.getName();
            this.reservedPort = (dslamBean.getReservedPort() == null) ? "" : String.valueOf(dslamBean.getReservedPort());
            this.usedPort = (dslamBean.getUsedPort() == null) ? "" : String.valueOf(dslamBean.getUsedPort());
            this.productId = (dslamBean.getProductId() == null) ? "" : String.valueOf(dslamBean.getProductId());
            this.province = dslamBean.getProvince();
            this.x = (dslamBean.getX() == null) ? "" : String.valueOf(dslamBean.getX());
            this.y = (dslamBean.getY() == null) ? "" : String.valueOf(dslamBean.getY());
        // this.provinceName = (dslamBean.getProvinceName() == null) ? "" : String.valueOf(dslamBean.getProvinceName());

            this.mdfId = dslamBean.getMdfId();
            
        } catch (Exception ex) {
            Logger.getLogger(PartnerForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getDslamMessage() {
        return dslamMessage;
    }

    public void setDslamMessage(String dslamMessage) {
        this.dslamMessage = dslamMessage;
    }

    /* public String getProvinceName() {
    return provinceName;
    }

    public void setProvinceName(String provinceName) {
    this.provinceName = provinceName;
    }*/
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

    public String getMaxPort() {
        return maxPort;
    }

    public void setMaxPort(String maxPort) {
        this.maxPort = maxPort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getReservedPort() {
        return reservedPort;
    }

    public void setReservedPort(String reservedPort) {
        this.reservedPort = reservedPort;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsedPort() {
        return usedPort;
    }

    public void setUsedPort(String usedPort) {
        this.usedPort = usedPort;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getDslamcode() {
        return dslamcode;
    }

    public void setDslamcode(String dslamcode) {
        this.dslamcode = code;
    }
}

