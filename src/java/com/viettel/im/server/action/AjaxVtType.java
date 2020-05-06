/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.server.action;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author thanhnv
 */
public class AjaxVtType implements Result {

    public void execute(ActionInvocation invocation) throws Exception {

        ServletActionContext.getResponse().setContentType("text/plain");
        HttpServletResponse response = ServletActionContext.getResponse();
        PrintWriter responseStream = response.getWriter();
        ValueStack valueStack = invocation.getStack();
        Object buider = valueStack.findValue("vtAjaxValue");
        response.setContentType("text/xml; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        responseStream.write(buider.toString());
        responseStream.close();
    }
}
