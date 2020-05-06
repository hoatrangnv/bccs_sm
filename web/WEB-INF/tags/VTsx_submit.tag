<%-- 
    Document   : VTsx_submit
    Created on : May 14, 2009, 10:36:28 AM
    Author     : ThanhDat
--%>

<%@tag display-name="Viettel's submit tag" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="parseContent"%>
<%@attribute name="executeScripts"%>
<%@attribute name="formId"%>
<%@attribute name="loadingText" %>
<%@attribute name="showLoadingText" %>
<%@attribute name="value" %>
<%@attribute name="beforeNotifyTopics" %>
<%@attribute name="afterNotifyTopics" %>
<%@attribute name="id" %>
<%@attribute name="disabled" %>
<%@attribute name="href" %>
<%@attribute name="validate" %>
<%@attribute name="targets" %>
<%@attribute name="errorNotifyTopics" %>

<%
com.viettel.database.DAO.BaseDAOAction baseDAOAction = new com.viettel.database.DAO.BaseDAOAction();

    String s = "";
    if (parseContent != null)
    {
        s = s + "parseContent=\"" + parseContent + "\" ";
    }
    if (executeScripts != null)
    {
        s = s + "executeScripts=\"" + executeScripts + "\" ";
    }
    if (formId != null)
    {
        s = s + "formId=\"" + formId + "\" ";
    }
    if (loadingText != null)
    {
        s = s + "loadingText=\"" + loadingText + "\" ";
    }
    if (showLoadingText != null)
    {
        s = s + "showLoadingText=\"" + showLoadingText + "\" ";
    }
    if (value != null)
    {
        //s = s + "value=\"" + baseDAOAction.getText(value) + "\" ";
        s = s + "value=\"" + baseDAOAction.getText(value) + "\" ";
    }
    if (beforeNotifyTopics != null)
    {
        s = s + "beforeNotifyTopics=\"" + beforeNotifyTopics + "\" ";
    }
    if (afterNotifyTopics != null)
    {
        s = s + "afterNotifyTopics=\"" + afterNotifyTopics + "\" ";
    }
    if (id != null)
    {
        s = s + "id=\"" + id + "\" ";
    }
    if (disabled != null && disabled.equals("true"))
    {
        s = s + "disabled=\"" + disabled + "\" ";
    }
    if (href != null)
    {
        s = s + "parseContent=\"" + href + "\" ";
    }
    if (validate != null)
    {
        s = s + "validate=\"" + validate + "\" ";
    }
    if (targets != null)
    {
        s = s + "targets=\"" + targets + "\" ";
    }
    if (errorNotifyTopics != null)
    {
        s = s + "errorNotifyTopics=\"" + errorNotifyTopics + "\" ";
    }


    request.setAttribute("attributes",s);
%>
    <input ${attributes} events="onclick" dojotype="struts:Bind" type="submit"/>

<%-- any content can be specified here e.g.: --%>
