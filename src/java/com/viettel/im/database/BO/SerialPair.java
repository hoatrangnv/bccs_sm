/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.database.BO;

import java.math.BigDecimal;

/**
 *
 * @author Cty-129
 */
public class SerialPair {
    private BigDecimal fromSerial;
    private BigDecimal toSerial;

    public BigDecimal getFromSerial() {
        return fromSerial;
    }

    public void setFromSerial(BigDecimal fromSerial) {
        this.fromSerial = fromSerial;
    }

    public BigDecimal getToSerial() {
        return toSerial;
    }

    public void setToSerial(BigDecimal toSerial) {
        this.toSerial = toSerial;
    }

}
