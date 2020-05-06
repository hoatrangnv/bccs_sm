/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.server.action;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import java.io.PrintWriter;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author haidd
 */
public class JSONResult implements Result {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final String DEFAULT_PARAM = "classAlias";

	String classAlias;

	public String getClassAlias() {
		return classAlias;
	}

	public void setClassAlias(String classAlias) {
		this.classAlias = classAlias;
	}

	public void execute(ActionInvocation invocation) throws Exception {

		ServletActionContext.getResponse().setContentType("text/plain");
		PrintWriter responseStream = ServletActionContext.getResponse()
				.getWriter();

		/* Retrieve Objects to Serialize to JSON from ValueStack */
		ValueStack valueStack = invocation.getStack();
		Object jsonModel = valueStack.findValue("jsonModel");

		XStream xstream = new XStream(new JettisonMappedXmlDriver());

		/*
		 * If there's no parameter passed in, just write the objects under a
		 * default name.
		 */
		if (classAlias == null) {
			classAlias = "object";
		}
		xstream.alias(classAlias, jsonModel.getClass());

		/* Write to the response stream */
		responseStream.println(xstream.toXML(jsonModel));

	}


}
