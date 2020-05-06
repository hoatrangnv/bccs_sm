/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;

/**
 *
 * @author thonv
 */
import org.apache.struts.action.ActionForm;
public class BasicForm extends ActionForm
{
    private  String mstrClassName;
    private  String mstrFunctionName;
    public void setClassName(String pstrClassName)
    {
        this.mstrClassName = pstrClassName;
    }
    public void setFunctionName(String pstrClassName)
    {
        this.mstrFunctionName = pstrClassName;
    }
    public String getClassName()
    {
        return mstrClassName;
    }
    public String getFunctionName()
    {
        return mstrFunctionName;
    }
}
