/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

/**
 *
 * @author NamNX
 * phuc vu kiem tra serial/Nhap hang tu doi tac
 */
public class CheckSerialBean {
    private Long fromSerial;
    private Long toSerial;
    private Long serialQuantity;

    public Long getFromSerial() {
        return fromSerial;
    }

    public void setFromSerial(Long fromSerial) {
        this.fromSerial = fromSerial;
    }

    public Long getSerialQuantity() {
        return serialQuantity;
    }

    public void setSerialQuantity(Long serialQuantity) {
        this.serialQuantity = serialQuantity;
    }

    public Long getToSerial() {
        return toSerial;
    }

    public void setToSerial(Long toSerial) {
        this.toSerial = toSerial;
    }

    public CheckSerialBean(Long fromSerial, Long toSerial, Long serialQuantity) {
        this.fromSerial = fromSerial;
        this.toSerial = toSerial;
        this.serialQuantity = serialQuantity;
    }



}
