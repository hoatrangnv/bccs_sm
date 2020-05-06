package com.viettel.im.database.BO;

/**
 * PortFormat entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class PortFormat implements java.io.Serializable {

    // Fields
    private Long id;
    private Long eqId;
    private String portFormat;
    private Long status;
    private String type;

    // Constructors
    /** default constructor */
    public PortFormat() {
    }

    // Property accessors
    public Long getEqId() {
        return this.eqId;
    }

    public void setEqId(Long eqId) {
        this.eqId = eqId;
    }

    public String getPortFormat() {
        return this.portFormat;
    }

    public void setPortFormat(String portFormat) {
        this.portFormat = portFormat;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
