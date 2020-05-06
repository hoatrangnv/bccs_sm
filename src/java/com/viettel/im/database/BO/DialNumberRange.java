package com.viettel.im.database.BO;



/**
 * DialNumberRange entity. @author MyEclipse Persistence Tools
 */

public class DialNumberRange  implements java.io.Serializable {


    // Fields    

     private Long id;
     private Long dialId;
     private String fromNumber;
     private String toNumber;


    // Constructors

    /** default constructor */
    public DialNumberRange() {
    }

	/** minimal constructor */
    public DialNumberRange(Long id) {
        this.id = id;
    }
    
    /** full constructor */
    public DialNumberRange(Long id, Long dialId, String fromNumber, String toNumber) {
        this.id = id;
        this.dialId = dialId;
        this.fromNumber = fromNumber;
        this.toNumber = toNumber;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getDialId() {
        return this.dialId;
    }
    
    public void setDialId(Long dialId) {
        this.dialId = dialId;
    }

    public String getFromNumber() {
        return this.fromNumber;
    }
    
    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public String getToNumber() {
        return this.toNumber;
    }
    
    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }
   








}