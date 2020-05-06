/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

import com.viettel.im.common.util.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author Administrator
 */
public class SlotBean implements java.io.Serializable{
    private Long slotId;
	private Long chassicId;
	private Long slotPosition;
	private Long freePort;
	private Long status;
	private Date cardInstalledDate;
	private String slotType;
	private Long maxPort;
	private Long usedPort;
	private Long invalidPort;
	private Long staPortPosition;
	private Long cardId;
    
    private String chassicName;
    private String cardName;

    public SlotBean(Long slotId, Long chassicId, Long slotPosition, Long freePort, Long status, Date cardInstalledDate, String slotType, Long maxPort, Long usedPort, Long invalidPort, Long staPortPosition, Long cardId, String chassicName, String cardName) {
        this.slotId = slotId;
        this.chassicId = chassicId;
        this.slotPosition = slotPosition;
        this.freePort = freePort;
        this.status = status;
        this.cardInstalledDate = cardInstalledDate;
        this.slotType = slotType;
        this.maxPort = maxPort;
        this.usedPort = usedPort;
        this.invalidPort = invalidPort;
        this.staPortPosition = staPortPosition;
        this.cardId = cardId;
        this.chassicName = chassicName;
        this.cardName = cardName;
    }

    public SlotBean() {
    }
    

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Date getCardInstalledDate() {
        return cardInstalledDate;
    }

    public void setCardInstalledDate(Date cardInstalledDate) {
        this.cardInstalledDate = cardInstalledDate;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Long getChassicId() {
        return chassicId;
    }

    public void setChassicId(Long chassicId) {
        this.chassicId = chassicId;
    }

    public String getChassicName() {
        return chassicName;
    }

    public void setChassicName(String chassicName) {
        this.chassicName = chassicName;
    }

    public Long getFreePort() {
        return freePort;
    }

    public void setFreePort(Long freePort) {
        this.freePort = freePort;
    }

    public Long getInvalidPort() {
        return invalidPort;
    }

    public void setInvalidPort(Long invalidPort) {
        this.invalidPort = invalidPort;
    }

    public Long getMaxPort() {
        return maxPort;
    }

    public void setMaxPort(Long maxPort) {
        this.maxPort = maxPort;
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public Long getSlotPosition() {
        return slotPosition;
    }

    public void setSlotPosition(Long slotPosition) {
        this.slotPosition = slotPosition;
    }

    public String getSlotType() {
        return slotType;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }

    public Long getStaPortPosition() {
        return staPortPosition;
    }

    public void setStaPortPosition(Long staPortPosition) {
        this.staPortPosition = staPortPosition;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getUsedPort() {
        return usedPort;
    }

    public void setUsedPort(Long usedPort) {
        this.usedPort = usedPort;
    }
    
    


}
