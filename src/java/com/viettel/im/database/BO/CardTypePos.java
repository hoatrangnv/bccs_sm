package com.viettel.im.database.BO;

/**
 * Card entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class CardTypePos implements java.io.Serializable {

    // Fields
    private String cardType;
    private String startBin;
    private String endBin;
    private String name;
    private String type;
    private Long duration;
    private Long status;

    public CardTypePos() {
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getEndBin() {
        return endBin;
    }

    public void setEndBin(String endBin) {
        this.endBin = endBin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartBin() {
        return startBin;
    }

    public void setStartBin(String startBin) {
        this.startBin = startBin;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
