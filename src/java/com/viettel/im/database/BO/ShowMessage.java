/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ThanhNC
 */
public class ShowMessage {

    private String message;
    private List params = new ArrayList<String>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List getParams() {
        return params;
    }

    public void setParams(List params) {
        this.params = params;
    }

    public ShowMessage() {
    }

    public ShowMessage(String message) {
        this.message = message;
    }

    public ShowMessage(String message, List params) {
        this.message = message;
        this.params = params;
    }
}
