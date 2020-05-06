/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import java.util.ArrayList;
import java.util.List;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author tuan
 */
public class CalculateFeesForm extends ActionForm {

    private String objectCode;
    private String objectName;
    private String shopCode;
    private String shopName;
    private String billcycle;
    private int criterion;
    private Long payTypeCode;
    private List arrCOMMTRANSID = new ArrayList();

    public void ResetFormSearch() {
        setObjectCode(null);
        setObjectName(null);
        setShopCode(null);
        setShopName(null);
        setBillcycle(null);
        setPayTypeCode(null);
        setArrCOMMTRANSID(null);
    }

    /**
     * @return the objectCode
     */
    public String getObjectCode() {
        return objectCode;
    }

    /**
     * @param objectCode the objectCode to set
     */
    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    /**
     * @return the objectName
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * @param objectName the objectName to set
     */
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    /**
     * @return the shopCode
     */
    public String getShopCode() {
        return shopCode;
    }

    /**
     * @param shopCode the shopCode to set
     */
    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    /**
     * @return the shopName
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * @param shopName the shopName to set
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getBillcycle() {
        return billcycle;
    }

    public void setBillcycle(String billcycle) {
        this.billcycle = billcycle;
    }

    /**
     * @return the criterion
     */
    public int getCriterion() {
        return criterion;
    }

    /**
     * @param criterion1 the criterion1 to set
     */
    public void setCriterion(int criterion) {
        this.criterion = criterion;
    }

    /**
     * @return the payTypeCode
     */
    public Long getPayTypeCode() {
        return payTypeCode;
    }

    /**
     * @param payTypeCode the payTypeCode to set
     */
    public void setPayTypeCode(Long payTypeCode) {
        this.payTypeCode = payTypeCode;
    }

    /**
     * @return the arrCOMMTRANSID
     */
    public List getArrCOMMTRANSID() {
        return arrCOMMTRANSID;
    }

    /**
     * @param arrCOMMTRANSID the arrCOMMTRANSID to set
     */
    public void setArrCOMMTRANSID(List arrCOMMTRANSID) {
        this.arrCOMMTRANSID = arrCOMMTRANSID;
    }
}
