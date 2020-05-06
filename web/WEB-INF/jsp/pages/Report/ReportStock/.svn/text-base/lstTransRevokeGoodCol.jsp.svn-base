<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%-- 
    Document   : lstTransCTV
    Created on : Nov 9, 2009, 1:54:52 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%
    int stt = 1;
%>
<!--tags:imPanel title="Danh sách các giao dịch" -->
    <display:table id="trans" name="lstTrans" class="dataTable" requestURI="javascript: void(0)" cellpadding="1" cellspacing="1">
        <display:column  title="MSG.RET.049" sortable="false" headerClass="tct">
            <div align="center"><%=StringEscapeUtils.escapeHtml(stt++)%></div>
        </display:column>
        <display:column escapeXml="true" property="transId" title="MSG.RET.103" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" property="ctvId" title="MSG.RET.104" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" property="issueDate" title="MSG.RET.105" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" property="status" title="MSG.RET.039" sortable="false" headerClass="tct"/>
    </display:table>
<!--/tags:imPanel -->
