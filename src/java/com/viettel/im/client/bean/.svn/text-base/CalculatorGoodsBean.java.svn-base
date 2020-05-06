/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

import java.io.Serializable;

/**
 *
 * @author haidd
 */
public class CalculatorGoodsBean implements Serializable{
    private static final long serialVersionUID = 1L;

    private Long receipt = 0L;
    private Long pay = 0L;

    public Long getPay() {
        return pay;
    }

    public void setPay(Long pay) {
        this.pay = pay;
    }

   

    public Long getReceipt() {
        return receipt;
    }

    public void setReceipt(Long receipt) {
        this.receipt = receipt;
    }

    
    public long calculator(){
        System.out.println("totalrec:" + receipt);
        System.out.println("totalpay : " + pay);
        System.out.println("asfsdf : " + (receipt - pay));
        return receipt - pay;
    }

}
