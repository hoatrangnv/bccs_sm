/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

import java.math.BigInteger;

/**
 *
 * @author User
 */
public class StockGoodDrawBean {


    private BigInteger startDrawNumber;


    private BigInteger endDrawNumber;


    private Long count;


    private Long notInBlockCount;


    private Long id;//id for remove

    public BigInteger getEndDrawNumber() {
        return endDrawNumber;
    }

    public void setEndDrawNumber(BigInteger endDrawNumber) {
        this.endDrawNumber = endDrawNumber;
    }

    public BigInteger getStartDrawNumber() {
        return startDrawNumber;
    }

    public void setStartDrawNumber(BigInteger startDrawNumber) {
        this.startDrawNumber = startDrawNumber;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

//    public Long getEndDrawNumber() {
//        return endDrawNumber;
//    }
//
//    public void setEndDrawNumber(Long endDrawNumber) {
//        this.endDrawNumber = endDrawNumber;
//    }
//
//    public Long getStartDrawNumber() {
//        return startDrawNumber;
//    }
//
//    public void setStartDrawNumber(Long startDrawNumber) {
//        this.startDrawNumber = startDrawNumber;
//    }

    public Long getNotInBlockCount() {
        return notInBlockCount;
    }

    public void setNotInBlockCount(Long notInBlockCount) {
        this.notInBlockCount = notInBlockCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
