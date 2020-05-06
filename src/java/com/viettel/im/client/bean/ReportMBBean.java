/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

/**
 *
 * @author MrSun
 */
public class ReportMBBean {

    private Long index;
    private String cardType;
    private Long countTransMB;
    private Long sumMoneyMB;
    private Long countTransBCCS;
    private Long sumMoneyBCCS;

    public ReportMBBean() {
    }

    public ReportMBBean(String cardType, Long countTransMB, Long sumMoneyMB, Long countTransBCCS, Long sumMoneyBCCS) {
        this.cardType = cardType;
        this.countTransMB = countTransMB;
        this.sumMoneyMB = sumMoneyMB;
        this.countTransBCCS = countTransBCCS;
        this.sumMoneyBCCS = sumMoneyBCCS;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Long getCountTransBCCS() {
        return countTransBCCS;
    }

    public void setCountTransBCCS(Long countTransBCCS) {
        this.countTransBCCS = countTransBCCS;
    }

    public Long getCountTransMB() {
        return countTransMB;
    }

    public void setCountTransMB(Long countTransMB) {
        this.countTransMB = countTransMB;
    }

    public Long getSumMoneyBCCS() {
        return sumMoneyBCCS;
    }

    public void setSumMoneyBCCS(Long sumMoneyBCCS) {
        this.sumMoneyBCCS = sumMoneyBCCS;
    }

    public Long getSumMoneyMB() {
        return sumMoneyMB;
    }

    public void setSumMoneyMB(Long sumMoneyMB) {
        this.sumMoneyMB = sumMoneyMB;
    }
}
