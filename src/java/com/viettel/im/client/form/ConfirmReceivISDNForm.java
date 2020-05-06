/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author ANHLT
 */
public class ConfirmReceivISDNForm extends ActionForm{
    private String[] sReceiveID;

    /**
     * @return the sReceiveID
     */
    public String[] getSReceiveID() {
        return sReceiveID;
    }

    /**
     * @param sReceiveID the sReceiveID to set
     */
    public void setSReceiveID(String[] sReceiveID) {
        this.sReceiveID = sReceiveID;
    }



}
