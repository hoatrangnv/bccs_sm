package com.viettel.im.database.BO;

import com.viettel.database.BO.BasicBO;
import java.util.Date;

/**
 * StockTransSerial entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class StockTransSerial extends BasicBO {

    // Fields
    //So thu tu khi xuat ra file excel
    private Long stt;
    private Long stockTransSerialId;
    private Long stateId;
    private Long stockTransId;
    private Long stockModelId;
    private String fromSerial;
    private String toSerial;
    private Long quantity;
    private Date createDatetime;
    //Them checkDial de xu ly trong truong hop cap nhat dai hang boc tham
    private Long checkDial;

    //tamdt1, start, 21/09/2010, bo sung de xem chi tiet serial theo kenh
    private Long channelTypeId;
    private String channelTypeName;

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public StockTransSerial(Long channelTypeId, String fromSerial, String toSerial, Long quantity) {
        this.channelTypeId = channelTypeId;
        this.fromSerial = fromSerial;
        this.toSerial = toSerial;
        this.quantity = quantity;
    }

    //tamdt1, end, 21/09/2010

    // Constructors
    /** default constructor */
    public StockTransSerial() {
    }

    /** minimal constructor */
    public StockTransSerial(Long stockTransSerialId) {
        this.stockTransSerialId = stockTransSerialId;
    }

    /** full constructor */
    public StockTransSerial(Long stockTransSerialId, Long stateId,
            Long stockTransId, Long stockModelId, String fromSerial,
            String toSerial, Long quantity, Date createDatetime) {
        this.stockTransSerialId = stockTransSerialId;
        this.stateId = stateId;
        this.stockTransId = stockTransId;
        this.stockModelId = stockModelId;
        this.fromSerial = fromSerial;
        this.toSerial = toSerial;
        this.quantity = quantity;
        this.createDatetime = createDatetime;
    }

    public StockTransSerial(String fromSerial, String toSerial, Long quantity) {
        this.fromSerial = fromSerial;
        this.toSerial = toSerial;
        this.quantity = quantity;
    }

    
    // Property accessors
    public Long getStockTransSerialId() {
        return this.stockTransSerialId;
    }

    public void setStockTransSerialId(Long stockTransSerialId) {
        this.stockTransSerialId = stockTransSerialId;
    }

    public Long getStateId() {
        return this.stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getStockTransId() {
        return this.stockTransId;
    }

    public void setStockTransId(Long stockTransId) {
        this.stockTransId = stockTransId;
    }

    public Long getStockModelId() {
        return this.stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public String getFromSerial() {
        return this.fromSerial;
    }

    public void setFromSerial(String fromSerial) {
        this.fromSerial = fromSerial;
    }

    public String getToSerial() {
        return this.toSerial;
    }

    public void setToSerial(String toSerial) {
        this.toSerial = toSerial;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Date getCreateDatetime() {
        return this.createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getCheckDial() {
        return checkDial;
    }

    public void setCheckDial(Long checkDial) {
        this.checkDial = checkDial;
    }

    public Long getStt() {
        return stt;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }

    
}
