package com.viettel.im.database.BO;

import java.util.ArrayList;
import java.util.List;

/**
 * StockTypeProfile entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class StockTypeProfile implements java.io.Serializable {

	// Fields

	private Long profileId;
	private Long stockTypeId;
	private String profileName;
	private Long status;
	private String description;
	private Long startLine;
	private String separatedChar;

    //tamdt1 - start
    private List<String> fieldList = new ArrayList<String>(); //thu tu cot, ten cot
    private String tableName = ""; //ten cua bang canchen du lieu
    private String linePattern; //mau du lieu

    public List<String> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<String> fieldList) {
        this.fieldList = fieldList;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getLinePattern() {
        return linePattern;
    }

    public void setLinePattern(String linePattern) {
        this.linePattern = linePattern;
    }


    //tamdt1 - end

	// Constructors

	/** default constructor */
	public StockTypeProfile() {
	}

	/** minimal constructor */
	public StockTypeProfile(Long profileId, Long stockTypeId,
			String profileName) {
		this.profileId = profileId;
		this.stockTypeId = stockTypeId;
		this.profileName = profileName;
	}

	/** full constructor */
	public StockTypeProfile(Long profileId, Long stockTypeId,
			String profileName, Long status, String description,
			Long startLine, String separatedChar) {
		this.profileId = profileId;
		this.stockTypeId = stockTypeId;
		this.profileName = profileName;
		this.status = status;
		this.description = description;
		this.startLine = startLine;
		this.separatedChar = separatedChar;
	}


    // Property accessors
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getSeparatedChar() {
        return separatedChar;
    }

    public void setSeparatedChar(String separatedChar) {
        this.separatedChar = separatedChar;
    }

    public Long getStartLine() {
        return startLine;
    }

    public void setStartLine(Long startLine) {
        this.startLine = startLine;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }


}