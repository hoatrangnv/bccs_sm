/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

/**
 *
 * @author TungTV
 */
public class SplitRangeToDrawViewHelper {


    private String stockModelName;


    private String stockModelCode;


    /** Search condition */
    private Long stockModelId;


    private String blockTypeName1;


    private String blockTypeName2;


    private String blockTypeName3;


    private String blockTypeName4;


    public static final String SPLIT_RANGE_TO_DRAW = "splitRangeToDraw";


    public String getStockModelCode() {
        return stockModelCode;
    }


    public void setStockModelCode(String stockModelCode) {
        this.stockModelCode = stockModelCode;
    }


    public String getStockModelName() {
        return stockModelName;
    }


    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public String getBlockTypeName1() {
        return blockTypeName1;
    }

    public void setBlockTypeName1(String blockTypeName1) {
        this.blockTypeName1 = blockTypeName1;
    }

    public String getBlockTypeName2() {
        return blockTypeName2;
    }

    public void setBlockTypeName2(String blockTypeName2) {
        this.blockTypeName2 = blockTypeName2;
    }

    public String getBlockTypeName3() {
        return blockTypeName3;
    }

    public void setBlockTypeName3(String blockTypeName3) {
        this.blockTypeName3 = blockTypeName3;
    }

    public String getBlockTypeName4() {
        return blockTypeName4;
    }

    public void setBlockTypeName4(String blockTypeName4) {
        this.blockTypeName4 = blockTypeName4;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }
}
