<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ListStaffOnOff
    Created on : Sep 24, 2010, 8:34:35 AM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    String pageId = request.getParameter("pageId");
    request.setAttribute("contextPath", request.getContextPath());
    request.setAttribute("listConvertChannelByFile", request.getSession().getAttribute("listConvertChannelByFile" + pageId));
%>

<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('MES.CHL.083'))}</legend>
    <div style="width:100%; height:200px; overflow:auto;">
        <display:table name="listConvertChannelByFile" id="tbllistConvertChannelByFile"
                       class="dataTable" cellpadding="1" cellspacing="1">
            <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.054'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tbllistStaffonOffByFile_rowNum)}</display:column>
            <display:column escapeXml="true"  property="channelCode" title="${fn:escapeXml(imDef:imGetText('SHOP.BRANCH'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="channelType" title="${fn:escapeXml(imDef:imGetText('MSG.channel'))}" style="text-align:center" sortable="false" headerClass="tct"/>                        
        </display:table>
    </div>
</fieldset>
