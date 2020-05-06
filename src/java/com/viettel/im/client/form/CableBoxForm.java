/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionForm;
import com.viettel.im.database.BO.CableBox;
/**
 *
 * @author TheTM
 */
public class CableBoxForm extends ActionForm {
    private Long cableBoxId;
	private Long boardId;
	private String code;
	private String name;
	private String address;
	private String maxPorts;
	private String usedPorts;
    private Long status;
    private String x;
    private String y;
    private Long dslamId;
    private String reservedPort;
    private String type;
    private String message;
    private String DSLName;
    private String BOARDName;
    private String dslamcode;
    private String boardscode;
    private String BoardsName;
 
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Long getCableBoxId() {
        return cableBoxId;
    }

    public void setCableBoxId(Long cableBoxId) {
        this.cableBoxId = cableBoxId;
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

    public String getMaxPorts() {
        return maxPorts;
    }

    public void setMaxPorts(String maxPorts) {
        this.maxPorts = maxPorts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReservedPort() {
        return reservedPort;
    }

    public void setReservedPort(String reservedPort) {
        this.reservedPort = reservedPort;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsedPorts() {
        return usedPorts;
    }

    public void setUsedPorts(String usedPorts) {
        this.usedPorts = usedPorts;
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
    public String getDSLName() {
        return DSLName;
    }
    public void setDSLName(String DSLName) {
        this.DSLName = DSLName;
    }
    public String getBOARDName() {
        return BOARDName;
    }
    public void setBOARDName(String BOARDName) {
        this.BOARDName = BOARDName;
    }

    public String getDslamcode() {
        return dslamcode;
    }

    public void setDslamcode(String dslamcode) {
        this.dslamcode = dslamcode;
    }

    

    public String getBoardscode() {
        return boardscode;
    }

    public void setBoardscode(String boardscode) {
        this.boardscode = boardscode;
    }

    public String getBoardsName() {
        return BoardsName;
    }

    public void setBoardsName(String BoardsName) {
        this.BoardsName = BoardsName;
    }


    public void copyDataToBO(CableBox CableBox) {
        CableBox.setAddress(this.getAddress().trim());
        CableBox.setStatus(this.getStatus());
        CableBox.setCode(this.getCode().trim());
        if (this.getMaxPorts() != null && !this.getMaxPorts().trim().equals("")) {
            CableBox.setMaxPorts(Long.parseLong(this.getMaxPorts().trim()));
        }
        CableBox.setName(this.getName().trim());
         if (this.getUsedPorts() != null && !this.getUsedPorts().trim().equals("")) {
            CableBox.setUsedPorts(Long.parseLong(this.getUsedPorts().trim()));
        }
        if (this.getX() != null && !this.getX().trim().equals("")) {
            CableBox.setX(Long.parseLong(this.getX().trim()));
        }
        if (this.getY() != null && !this.getY().trim().equals("")) {
            CableBox.setY(Long.parseLong(this.getY().trim()));
        }
        if (this.getReservedPort() != null && !this.getReservedPort().trim().equals("")) {
            CableBox.setReservedPort(Long.parseLong(this.getReservedPort().trim()));
        }
        CableBox.setBoardId(this.getBoardId()); 
        
    }

    public void copyDataFromBO(CableBox CableBox) {
        this.setCableBoxId(CableBox.getCableBoxId());
        this.setAddress(CableBox.getAddress().trim());
        this.setBoardId(CableBox.getBoardId());
        this.setStatus(CableBox.getStatus());
        this.setCode(CableBox.getCode().trim());
        this.setDslamId(CableBox.getDslamId());
        this.setMaxPorts((CableBox.getMaxPorts() == null) ? "" : String.valueOf(CableBox.getMaxPorts()));
        this.setName(CableBox.getName().trim());
        this.setReservedPort((CableBox.getReservedPort() == null) ? "" : String.valueOf(CableBox.getReservedPort()));
        this.setUsedPorts((CableBox.getUsedPorts() == null) ? "" : String.valueOf(CableBox.getUsedPorts()));
        this.setX((CableBox.getX() == null) ? "" : String.valueOf(CableBox.getX()));
        this.setX((CableBox.getY() == null) ? "" : String.valueOf(CableBox.getY()));
    }

    public void resetForm() {
        this.cableBoxId = 0L;
        this.code = "";
        this.name = "";
        this.status = null;
        this.address="";
        this.setMaxPorts("");
        this.setUsedPorts("");
        this.setReservedPort("");
        this.setDslamId(0L);
        this.type = "";
        this.setX("");
        this.setY("");
        this.setMessage("");
        this.setBoardscode("");
        this.setDslamcode("");
        this.setBoardsName(""); 


    }
      public void resetForm1() {
        this.cableBoxId = 0L;
        this.code = "";
        this.name = "";
        this.status = null;
        this.address="";
        this.setMaxPorts("");
        this.setUsedPorts("");
        this.setReservedPort("");
     //   this.setDslamId(0L);
        this.type = "";
        this.setX("");
        this.setY("");
        this.setMessage("");
        this.setBoardscode("");
        this.setDslamcode("");
        this.setBoardsName("");


    }



}
    
  