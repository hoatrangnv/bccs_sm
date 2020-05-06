package com.viettel.im.database.BO;

/**
 * Bras entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Bras implements java.io.Serializable {

	// Fields

	private Long brasId;
	private Long equipmentId;
	private String code;
	private String name;
	private String ip;
	private String aaaIp;
	private Long status;
	private String description;
    private String slot;
    private String port;

	// Constructors

	/** default constructor */
	public Bras() {
	}

	/** minimal constructor */
	public Bras(Long brasId, Long equipmentId) {
		this.brasId = brasId;
		this.equipmentId = equipmentId;
	}

	/** full constructor */
	public Bras(long brasId, Long equipmentId, String code, String name,
			String ip, String aaaIp, Long status, String description, String slot, String port) {
		this.brasId = brasId;
		this.equipmentId = equipmentId;
		this.code = code;
		this.name = name;
		this.ip = ip;
		this.aaaIp = aaaIp;
		this.status = status;
		this.description = description;
        this.slot = slot;
        this.port = port;
	}

    public Long getBrasId() {
        return brasId;
    }

    public void setBrasId(Long brasId) {
        this.brasId = brasId;
    }

	// Property accessors

	

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAaaIp() {
		return this.aaaIp;
	}

	public void setAaaIp(String aaaIp) {
		this.aaaIp = aaaIp;
	}

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

}