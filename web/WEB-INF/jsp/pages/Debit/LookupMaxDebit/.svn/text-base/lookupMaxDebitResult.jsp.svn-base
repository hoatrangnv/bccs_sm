<%--
    Document   : searchChannelTypeResult
    Created on : Feb 18, 2009, 10:51:45 AM
    Author     : TungTV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>


<sx:div id="displayTagFrame">
    <tags:imPanel title="MSG.channel.list">
        <display:table id="tblListChannelType" name="sessionScope.listChannel"
                       pagesize="10" targets="displayTagFrame"
                       requestURI="lookupMaxDebitAction!pageNavigator.do"
                       class="dataTable" cellpadding="1" cellspacing="1" >
            <display:column escapeXml="true"  title="Mã kênh" property="channelCode" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  title="Tên kênh" property="channelName" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  title="Giá trị kho" property="currentDebit" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  title="Hạn mức" property="maxDebit" sortable="false" headerClass="sortable"/>
        </display:table>
    </tags:imPanel>
</sx:div>


