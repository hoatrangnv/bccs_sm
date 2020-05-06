package com.viettel.im.database.BO;

/**
 * ViewDepositGeneral entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class ViewDepositGeneral implements java.io.Serializable {

    // Fields
    private ViewDepositGeneralId id;
    private Long amountset;
    private Long amountget;

    // Constructors
    /** default constructor */
    public ViewDepositGeneral() {
    }

    public ViewDepositGeneral(ViewDepositGeneralId id, Long amountset, Long amountget) {
        this.id = id;
        this.amountset = amountset;
        this.amountget = amountget;
    }    

    // Property accessors
    public ViewDepositGeneralId getId() {
        return this.id;
    }

    public void setId(ViewDepositGeneralId id) {
        this.id = id;
    }

    public Long getAmountget() {
        return amountget;
    }

    public void setAmountget(Long amountget) {
        this.amountget = amountget;
    }

    public Long getAmountset() {
        return amountset;
    }

    public void setAmountset(Long amountset) {
        this.amountset = amountset;
    }
    
    
}
