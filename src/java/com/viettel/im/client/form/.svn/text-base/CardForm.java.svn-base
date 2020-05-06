/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import org.apache.struts.action.ActionForm;
import com.viettel.im.database.BO.Card;

/**
 *
 * @author Administrator
 */
public class CardForm extends ActionForm {

    private Long cardId;
    private String code;
    private String equipmentId;
    private String name;
    private String cardType;
    private String totalPort;
    private Long status;
    private String description;

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

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getTotalPort() {
        return totalPort;
    }

    public void setTotalPort(String totalPort) {
        this.totalPort = totalPort;
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

   
    public void resetForm() {
        this.cardId = 0L;
        this.code = "";
        this.equipmentId = "";
        this.name = "";
        this.cardType = "";
        this.totalPort = "";
        this.status=null;
        this.description = "";
    }

    public void copyDataToBO(Card card) {
        card.setCardId(cardId);
        card.setCardType(cardType);
        card.setCode(code);        
        card.setEquipmentId(Long.parseLong(equipmentId));
        card.setName(name);
        card.setStatus(status);
        card.setDescription(description);

        if (totalPort != null && !totalPort.trim().equals("")){
            card.setTotalPort(Long.parseLong(totalPort));
        }
    }

    public void copyDataFromBO( Card card ) {
         this.cardId = card.getCardId();
        this.code = card.getCode();
        this.equipmentId =String.valueOf(card.getEquipmentId());
        this.name = card.getName();
        this.status=card.getStatus();
        this.cardType = card.getCardType();
        this.description = card.getDescription();

        if ( card.getTotalPort() != null){
            this.totalPort = String.valueOf(card.getTotalPort());
        }
    }
}
