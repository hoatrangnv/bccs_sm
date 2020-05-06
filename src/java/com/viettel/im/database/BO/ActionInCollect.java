package com.viettel.im.database.BO;

/**
 * ActionInCollect entity.
 *
 * @author MyEclipse Persistence Tools
 */
public class ActionInCollect implements java.io.Serializable {

    // Fields
    private Long id;
    private Long collecterId;
    private Long collectType;

    // Constructors
    /** default constructor */
    public ActionInCollect() {
    }

    public ActionInCollect(Long id, Long collecterId, Long collectType) {
        this.id = id;
        this.collecterId = collecterId;
        this.collectType = collectType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCollectType() {
        return collectType;
    }

    public void setCollectType(Long collectType) {
        this.collectType = collectType;
    }

    public Long getCollecterId() {
        return collecterId;
    }

    public void setCollecterId(Long collecterId) {
        this.collecterId = collecterId;
    }
   
}