package com.viettel.im.database.BO;

/**
 * TempSim entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TempSim implements java.io.Serializable {
    private Long id;
	private String userSessionId;
	private String simSerial;
	private String simImsi;
    private String pin;
    private String puk;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPuk() {
        return puk;
    }

    public void setPuk(String puk) {
        this.puk = puk;
    }

    public String getSimImsi() {
        return simImsi;
    }

    public void setSimImsi(String simImsi) {
        this.simImsi = simImsi;
    }

    public String getSimSerial() {
        return simSerial;
    }

    public void setSimSerial(String simSerial) {
        this.simSerial = simSerial;
    }

    public String getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TempSim(Long id, String userSessionId, String simSerial, String simImsi) {
        this.id = id;
        this.userSessionId = userSessionId;
        this.simSerial = simSerial;
        this.simImsi = simImsi;
    }

    public TempSim() {
    }

    

}