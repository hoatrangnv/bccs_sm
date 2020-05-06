package com.viettel.im.database.BO;

/**
 * ProfileDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ProfileDetail implements java.io.Serializable {

	// Fields

	private Long profileDetailId;
	private Long profileId;
	private String columnName;
	private Long columnOrder;
    private Long status;

	// Constructors

	/** default constructor */
	public ProfileDetail() {
	}

	/** full constructor */
	public ProfileDetail(Long profileDetailId,
			Long profileId, String columnName,
			Long columnOrder, Long status) {
		this.profileDetailId = profileDetailId;
		this.profileId = profileId;
		this.columnName = columnName;
		this.columnOrder = columnOrder;
        this.status = status;
	}

    

    // Property accessors

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
    
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Long getColumnOrder() {
        return columnOrder;
    }

    public void setColumnOrder(Long columnOrder) {
        this.columnOrder = columnOrder;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getProfileDetailId() {
        return profileDetailId;
    }

    public void setProfileDetailId(Long profileDetailId) {
        this.profileDetailId = profileDetailId;
    }

    

	

	

}