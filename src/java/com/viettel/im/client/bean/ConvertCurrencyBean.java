/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

import com.viettel.im.common.util.NumberUtils;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 *
 * @author haidd
 */
public class ConvertCurrencyBean implements Serializable{
    private static final long serialVersionUID = 1L;
    private Double currency =0.0;

    public Double getCurrency() {
        return currency;
    }

    public void setCurrency(Double currency) {
        this.currency = currency;      
    }

    public String startConvertCurrency() throws ParseException{
        return NumberUtils.rounđAndFormatAmount(currency);
//        Locale locale = com.viettel.database.DAO.BaseDAOAction.locale;
//        if (locale == null){
//            locale = Locale.JAPAN;
//        }
//        NumberFormat nf = NumberFormat.getInstance(locale);
//        return nf.format(currency);
    }

}
