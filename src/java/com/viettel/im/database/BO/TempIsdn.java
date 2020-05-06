package com.viettel.im.database.BO;

/**
 * TempIsdn entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TempIsdn implements java.io.Serializable {
	private Long id;
    private String userSessionId;
	private String isdn;

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
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

    public TempIsdn() {
    }

    public TempIsdn(Long id, String userSessionId, String isdn) {
        this.id = id;
        this.userSessionId = userSessionId;
        this.isdn = isdn;
    }

    
}