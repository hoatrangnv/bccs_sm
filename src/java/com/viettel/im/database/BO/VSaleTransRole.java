package com.viettel.im.database.BO;

/**
 * VSaleTransRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class VSaleTransRole implements java.io.Serializable {

	// Fields

	private VSaleTransRoleId id;

	// Constructors

	/** default constructor */
	public VSaleTransRole() {
	}

	/** full constructor */
	public VSaleTransRole(VSaleTransRoleId id) {
		this.id = id;
	}

	// Property accessors

	public VSaleTransRoleId getId() {
		return this.id;
	}

	public void setId(VSaleTransRoleId id) {
		this.id = id;
	}

}