/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

/**
 *
 * @author AnDv
 */
import org.apache.struts.action.ActionForm;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.Slot;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SlotForm extends ActionForm {

    private Long slotId;
    private Long chassicId;
    private String slotPosition;
    private String freePort;
    private Long status;
    private String cardInstalledDate;
    private String slotType;
    private String chassicCode;
    private String maxPort;
    private String usedPort;
    private String invalidPort;
    private String staPortPosition;
    private Long cardId;
    private String message;

    private String vlanStart;
    private String vlanStop;

    public String getVlanStart() {
        return vlanStart;
    }

    public void setVlanStart(String vlanStart) {
        this.vlanStart = vlanStart;
    }

    public String getVlanStop() {
        return vlanStop;
    }

    public void setVlanStop(String vlanStop) {
        this.vlanStop = vlanStop;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardInstalledDate() {
        return cardInstalledDate;
    }

    public void setCardInstalledDate(String cardInstalledDate) {
        this.cardInstalledDate = cardInstalledDate;
    }

    public String getChassicCode() {
        return chassicCode;
    }

    public void setChassicCode(String chassicCode) {
        this.chassicCode = chassicCode;
    }

    public Long getChassicId() {
        return chassicId;
    }

    public void setChassicId(Long chassicId) {
        this.chassicId = chassicId;
    }

    public String getFreePort() {
        return freePort;
    }

    public void setFreePort(String freePort) {
        this.freePort = freePort;
    }

    public String getInvalidPort() {
        return invalidPort;
    }

    public void setInvalidPort(String invalidPort) {
        this.invalidPort = invalidPort;
    }

    public String getMaxPort() {
        return maxPort;
    }

    public void setMaxPort(String maxPort) {
        this.maxPort = maxPort;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public String getSlotPosition() {
        return slotPosition;
    }

    public void setSlotPosition(String slotPosition) {
        this.slotPosition = slotPosition;
    }

    public String getSlotType() {
        return slotType;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }

    public String getStaPortPosition() {
        return staPortPosition;
    }

    public void setStaPortPosition(String staPortPosition) {
        this.staPortPosition = staPortPosition;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getUsedPort() {
        return usedPort;
    }

    public void setUsedPort(String usedPort) {
        this.usedPort = usedPort;
    }

    public void copyDataToBO(Slot slot) {
        slot.setCardId(this.cardId);
        slot.setChassicId(this.chassicId);
        if (this.getFreePort() != null && !this.getFreePort().trim().equals("")) {
            slot.setFreePort(Long.parseLong(this.freePort));
        }
        if (this.getInvalidPort() != null && !this.getInvalidPort().trim().equals("")) {
            slot.setInvalidPort(Long.parseLong(this.invalidPort));
        }
        if (this.getMaxPort() != null && !this.getMaxPort().trim().equals("")) {
            slot.setMaxPort(Long.parseLong(this.maxPort));
        }
        slot.setSlotId(this.slotId);
        slot.setSlotType(this.slotType);
        if (this.getSlotPosition() != null && !this.getSlotPosition().trim().equals("")) {
            slot.setSlotPosition(Long.parseLong(this.slotPosition));
        }
        if (this.getStaPortPosition() != null && !this.getStaPortPosition().trim().equals("")) {
            slot.setStaPortPosition(Long.parseLong(this.staPortPosition));
        }
        if (this.getUsedPort() != null && !this.getUsedPort().trim().equals("")) {
            slot.setUsedPort(Long.parseLong(this.usedPort));
        }
        slot.setStatus(this.status);
        try {
            slot.setCardInstalledDate(DateTimeUtils.convertStringToDate(this.cardInstalledDate));

        } catch (Exception ex) {

            Logger.getLogger(PartnerForm.class.getName()).log(Level.SEVERE, null, ex);
        }


        if (this.getVlanStart() != null && !this.getVlanStart().trim().equals("")) {
            slot.setVlanStart(Long.parseLong(this.vlanStart));
        }
        if (this.getVlanStop() != null && !this.getVlanStop().trim().equals("")) {
            slot.setVlanStop(Long.parseLong(this.vlanStop));
        }

    }

    public void copyDataFromBO(Slot slot) {
        this.cardId = slot.getCardId();
        this.slotId = slot.getSlotId();
        this.chassicId = slot.getChassicId();
        this.slotPosition = (slot.getSlotPosition() == null) ? "" :String.valueOf(slot.getSlotPosition());
        this.freePort = (slot.getFreePort() == null) ? "" :String.valueOf(slot.getFreePort());

        this.slotType = slot.getSlotType();
        this.maxPort = (slot.getMaxPort() == null) ? "" :String.valueOf(slot.getMaxPort());
        this.usedPort = (slot.getUsedPort() == null) ? "" :String.valueOf(slot.getUsedPort());
        this.invalidPort = (slot.getInvalidPort() == null) ? "" :String.valueOf(slot.getInvalidPort());
        this.staPortPosition = (slot.getStaPortPosition() == null) ? "" :String.valueOf(slot.getStaPortPosition());
        this.status = slot.getStatus();


        try {
            this.cardInstalledDate = DateTimeUtils.convertDateToString(slot.getCardInstalledDate());

        } catch (Exception ex) {
            Logger.getLogger(PartnerForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.vlanStart = (slot.getVlanStart() == null) ? "" : String.valueOf(slot.getVlanStart());
        this.vlanStop = (slot.getVlanStop() == null) ? "" : String.valueOf(slot.getVlanStop());

    }

    public void resetForm() {
        this.cardId = 0L;
        this.slotId = 0L;
        this.chassicId = 0L;
        this.slotPosition = "";
        this.chassicCode = "";
        this.freePort = "";
        this.slotType = "";
        this.maxPort = "";
        this.usedPort = "";
        this.invalidPort = "";
        this.cardInstalledDate = "";
        this.staPortPosition = "";
        this.status = null;

        this.vlanStart = "";
        this.vlanStop = "";

    }
}
