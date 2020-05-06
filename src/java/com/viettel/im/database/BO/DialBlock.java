package com.viettel.im.database.BO;



/**
 * DialBlock entity. @author MyEclipse Persistence Tools
 */

public class DialBlock  implements java.io.Serializable {


    // Fields    

     private Long blockId;
     private Long offersDetailId;
     private String blockCode;


    // Constructors

    /** default constructor */
    public DialBlock() {
    }

	/** minimal constructor */
    public DialBlock(Long blockId) {
        this.blockId = blockId;
    }
    
    /** full constructor */
    public DialBlock(Long blockId, Long offersDetailId, String blockCode) {
        this.blockId = blockId;
        this.offersDetailId = offersDetailId;
        this.blockCode = blockCode;
    }

   
    // Property accessors

    public Long getBlockId() {
        return this.blockId;
    }
    
    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public Long getOffersDetailId() {
        return this.offersDetailId;
    }
    
    public void setOffersDetailId(Long offersDetailId) {
        this.offersDetailId = offersDetailId;
    }

    public String getBlockCode() {
        return this.blockCode;
    }
    
    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }
   








}