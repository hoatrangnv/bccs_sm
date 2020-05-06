/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

/**
 *
 * @author NamNX
 * Phuc vu xem thong tin cam ket
 */
public class PledgeInfoBean {

    private Double price;// tutm1 14/03/2012 Gia doi sang kieu Double
    private Long pledgeAmount;// So luong cam ket
    private Long pledgeTime;//Thời gian cam kết
    private Long priorPay;//So thang cam ket

    public Long getPledgeAmount() {
        return pledgeAmount;
    }

    public void setPledgeAmount(Long pledgeAmount) {
        this.pledgeAmount = pledgeAmount;
    }

    public Long getPledgeTime() {
        return pledgeTime;
    }

    public void setPledgeTime(Long pledgeTime) {
        this.pledgeTime = pledgeTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getPriorPay() {
        return priorPay;
    }

    public void setPriorPay(Long priorPay) {
        this.priorPay = priorPay;
    }

    public PledgeInfoBean(Double price, Long pledgeAmount, Long pledgeTime, Long priorPay) {
        this.price = price;
        this.pledgeAmount = pledgeAmount;
        this.pledgeTime = pledgeTime;
        this.priorPay = priorPay;
    }
}
