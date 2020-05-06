/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;


/**
 *
 * @author AnDV
 */
public class CardBean implements java.io.Serializable {
    	private Long cardId;
	private String code;
	private Long equipmentId;
	private String name;
	private String cardType;
	private Long totalPort;
	private Long status;
	private String description;
        private String equipmentName;
        private String cardTypeName;

    public CardBean() {
    }


    public CardBean(Long cardId, String code, Long equipmentId, String name, String cardType, Long totalPort, Long status, String description, String equipmentName, String cardTypeName) {
        this.cardId = cardId;
        this.code = code;
        this.equipmentId = equipmentId;
        this.name = name;
        this.cardType = cardType;
        this.totalPort = totalPort;
        this.status = status;
        this.description = description;
        this.equipmentName = equipmentName;
        this.cardTypeName = cardTypeName;
    }

    

        

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getTotalPort() {
        return totalPort;
    }

    public void setTotalPort(Long totalPort) {
        this.totalPort = totalPort;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }




}
