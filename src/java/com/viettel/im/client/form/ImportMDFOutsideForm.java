/**
 * Copyright YYYY Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.im.client.form;
import org.apache.struts.action.ActionForm;
/**
 * 
 * @author haint
 * @date 06/10/2011
 */
public class ImportMDFOutsideForm extends ActionForm{
    
    private String serverFileName; //ten file tren server trong truogn hop upload
    private String clientFileName; //ten file tren client trong truogn hop upload
    private String province;
    private String dslamCode;
    private String boardsCode;
    private String cableBoxCode;
    private String cableNo;
    private String errorMessage; //chuoi luu loi

    public String getBoardsCode() {
        return boardsCode;
    }

    public void setBoardsCode(String boardsCode) {
        this.boardsCode = boardsCode;
    }

    public String getCableBoxCode() {
        return cableBoxCode;
    }

    public void setCableBoxCode(String cableBoxCode) {
        this.cableBoxCode = cableBoxCode;
    }

    public String getCableNo() {
        return cableNo;
    }

    public void setCableNo(String cableNo) {
        this.cableNo = cableNo;
    }

    public String getDslamCode() {
        return dslamCode;
    }

    public void setDslamCode(String dslamCode) {
        this.dslamCode = dslamCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    
    public String getClientFileName() {
        return clientFileName;
    }

    public void setClientFileName(String clientFileName) {
        this.clientFileName = clientFileName;
    }

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public void resetForm() {
        this.serverFileName = "";
        this.clientFileName = "";
        this.province = "";
        this.dslamCode = "";
        this.boardsCode = "";
        this.cableBoxCode = "";
        this.cableNo = "";
        this.errorMessage = "";
    }
}
    