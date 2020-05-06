package com.viettel.im.database.BO;

/**
 * Domain entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Domain implements java.io.Serializable {

	// Fields

	private Long id;
	private String domain;
	private String description;
        private Long status;

	// Constructors

	/** default constructor */
	public Domain() {
	}

	/** minimal constructor */
	public Domain(Long id, String domain) {
		this.id = id;
		this.domain = domain;
	}

	/** full constructor */
	public Domain(Long id, String domain, String description) {
		this.id = id;
		this.domain = domain;
		this.description = description;
	}

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

	// Property accessors



	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}