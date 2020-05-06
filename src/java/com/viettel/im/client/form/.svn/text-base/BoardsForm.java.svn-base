package com.viettel.im.client.form;

import com.viettel.common.util.DateTimeUtils;
import com.viettel.im.database.BO.Boards;
import com.viettel.im.database.BO.Dslam;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts.action.ActionForm;


public class BoardsForm extends ActionForm {
    private Long boardId;
    private String province;
    private String DSLName;
	private Long dslamId;
	private String code;
	private String name;
	private String address;
	private String maxPorts;
	private String usedPorts;
	private Long status;
	private String x;
	private String y;
	private String reservedPort;
    private String message;
    private String dslamcode;
    private String provinceName;

    /**
     * @return the boardId
     */

    public Long getBoardId() {
        return boardId;
    }

    /**
     * @param boardId the boardId to set
     */
    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    /**
     * @return the dslamId
     */
       public String getProvince() {
        return province;
    }

    /**
     * @param Province the Province to set
     */
    public void setProvince(String Province) {
        this.province = Province;
    }

    /**
     * @return the DSLName
     */
    public String getDSLName() {
        return DSLName;
    }

    /**
     * @param DSLName the DSLName to set
     */
    public void setDSLName(String DSLName) {
        this.DSLName = DSLName;
    }


    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the maxPorts
     */
    public String getMaxPorts() {
        return maxPorts;
    }

    /**
     * @param maxPorts the maxPorts to set
     */
    public void setMaxPorts(String maxPorts) {
        this.maxPorts = maxPorts;
    }

    /**
     * @return the usedPorts
     */
    public String getUsedPorts() {
        return usedPorts;
    }

    /**
     * @param usedPorts the usedPorts to set
     */
    public void setUsedPorts(String usedPorts) {
        this.usedPorts = usedPorts;
    }

    /**
     * @return the status
     */
    public Long getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Long status) {
        this.status = status;
    }

    /**
     * @return the x
     */
    public String getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(String x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public String getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(String y) {
        this.y = y;
    }

    /**
     * @return the reservedPort
     */
    public String getReservedPort() {
        return reservedPort;
    }

    /**
     * @param reservedPort the reservedPort to set
     */
    public void setReservedPort(String reservedPort) {
        this.reservedPort = reservedPort;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * @return the dslamId
     */
    public Long getDslamId() {
        return dslamId;
    }

    /**
     * @param dslamId the dslamId to set
     */
    public void setDslamId(Long dslamId) {
        this.dslamId = dslamId;
    }

    public String getDslamcode() {
        return dslamcode;
    }

    public void setDslamcode(String dslamcode) {
        this.dslamcode = dslamcode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
    

    public void resetForm() {
        this.setBoardId(0L);
        this.setDslamId(0L);
        this.code = "";
        this.name = "";
        this.address = "";
        this.setMaxPorts("");
        this.setUsedPorts("");
        this.setStatus(null);
        this.setX("");
        this.setY("");
        this.setReservedPort("");
        this.setMessage("");
        this.province="";
        this.DSLName="";
        this.setProvinceName(""); 

    }
 //   <!-- Break here -->


    public void copyDataToBO(Boards Boards) {
  //      CommCounters.setCounterId(this.getCounterID());
        Boards.setBoardId(this.getBoardId());
        Boards.setCode(this.getCode().trim());
        Boards.setName(this.getName().trim());
        Boards.setAddress(this.getAddress().trim());
        if (this.getMaxPorts() != null && !this.getMaxPorts().trim().equals("")) {
            Boards.setMaxPorts(Long.parseLong(this.getMaxPorts().trim()));
        }
        if (this.getUsedPorts() != null && !this.getUsedPorts().trim().equals("")) {
            Boards.setUsedPorts(Long.parseLong(this.getUsedPorts().trim()));
        }
        Boards.setStatus(this.getStatus());
        if (this.getX() != null && !this.getX().trim().equals("")) {
            Boards.setX(Long.parseLong(this.getX().trim()));
        }
        if (this.getY() != null && !this.getY().trim().equals("")) {
            Boards.setY(Long.parseLong(this.getY().trim()));
        }
        if (this.getReservedPort() != null && !this.getReservedPort().trim().equals("")) {
            Boards.setReservedPort(Long.parseLong(this.getReservedPort().trim()));
        }
        Boards.setDslamcode(this.getDslamcode());

    }
     public void copyDataFromBO(Boards Boards) {
        this.setBoardId(Boards.getBoardId());
        this.setDslamId(Boards.getDslamId());
        this.setCode(Boards.getCode());
        this.setName(Boards.getName());
        this.setAddress(Boards.getAddress());
        this.setMaxPorts((Boards.getMaxPorts() == null) ? "" : String.valueOf(Boards.getMaxPorts()));
        this.setUsedPorts((Boards.getUsedPorts() == null) ? "":String.valueOf(Boards.getUsedPorts()));
        this.setStatus(Boards.getStatus());
        this.setX((Boards.getX() == null) ? "" : String.valueOf(Boards.getX()));
        this.setY((Boards.getY() == null) ? "" : String.valueOf(Boards.getY()));
        this.setReservedPort((Boards.getReservedPort() == null) ? "" : String.valueOf(Boards.getReservedPort())); 
        this.setDslamcode(Boards.getDslamcode());

    }
}
    /**
     * @return the Province
     */

