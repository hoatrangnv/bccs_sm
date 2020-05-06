/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import java.util.List;
import org.apache.struts.action.ActionForm;

/**
 * 
 * @author truongnq5
 */
public class MappingDslamSaleServiceForm extends ActionForm{
    private String dslamCode;
    private String dslamName;
    private String saleServiceCode;
    private String saleServiceName;
    private String dslam;
    private String saleService;
    private Long status;
    private Long mappingType;
    private String serverFileName; //ten file tren server trong truogn hop upload
    private String clientFileName; //ten file tren client trong truogn hop upload
    private String resultMsg; 

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public List<String> getDslamCodeList() {
        return dslamCodeList;
    }

    public void setDslamCodeList(List<String> dslamCodeList) {
        this.dslamCodeList = dslamCodeList;
    }

    public List<String> getSaleServiceCodeList() {
        return saleServiceCodeList;
    }

    public void setSaleServiceCodeList(List<String> saleServiceCodeList) {
        this.saleServiceCodeList = saleServiceCodeList;
    }

    private List<String> dslamCodeList;
    private List<String> saleServiceCodeList;

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public String getClientFileName() {
        return clientFileName;
    }

    public void setClientFileName(String clientFileName) {
        this.clientFileName = clientFileName;
    }

    public Long getMappingType() {
        return mappingType;
    }

    public void setMappingType(Long mappingType) {
        this.mappingType = mappingType;
    }

    public String getDslamCode() {
        return dslamCode;
    }

    public void setDslamCode(String dslamCode) {
        this.dslamCode = dslamCode;
    }

    public String getDslamName() {
        return dslamName;
    }

    public void setDslamName(String dslamName) {
        this.dslamName = dslamName;
    }

    public String getSaleServiceCode() {
        return saleServiceCode;
    }

    public void setSaleServiceCode(String saleServiceCode) {
        this.saleServiceCode = saleServiceCode;
    }

    public String getSaleServiceName() {
        return saleServiceName;
    }

    public void setSaleServiceName(String saleServiceName) {
        this.saleServiceName = saleServiceName;
    }

    public String getDslam() {
        return dslam;
    }

    public void setDslam(String dslam) {
        this.dslam = dslam;
    }

    public String getSaleService() {
        return saleService;
    }

    public void setSaleService(String saleService) {
        this.saleService = saleService;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
    public MappingDslamSaleServiceForm(){
        this.dslamCode = "";
        this.dslamName = "";
        this.saleServiceCode = "";
        this.saleServiceName = "";
        this.dslam = "";
        this.saleService = "";
        this.status = null;
    }
}
