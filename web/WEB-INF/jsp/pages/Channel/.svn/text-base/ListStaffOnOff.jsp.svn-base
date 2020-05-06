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
            request.setAttribute("listStaffonOffByFile", request.getSession().getAttribute("listStaffonOffByFile" + pageId));
%>

<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('MES.CHL.083'))}</legend>
    <div style="width:100%; height:200px; overflow:auto;">
        <display:table name="listStaffonOffByFile" id="tbllistStaffonOffByFile"
                       class="dataTable" cellpadding="1" cellspacing="1">
            <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.054'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tbllistStaffonOffByFile_rowNum)}</display:column>
            <display:column escapeXml="true"  property="shopCode" title="${fn:escapeXml(imDef:imGetText('MES.CHL.080'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="staffCode" title="${fn:escapeXml(imDef:imGetText('MES.CHL.078'))}" style="text-align:center" sortable="false" headerClass="tct"/>                        
            <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.017'))}" headerClass="tct" sortable="false" style="width:120px; text-align:center">
                <s:if test="#attr.tbllistStaffonOffByFile.channelType == 1"><tags:label key="MES.CHL.168"/></s:if>
                <s:else>
                    <s:if test="#attr.tbllistStaffonOffByFile.channelType == 2"><tags:label key="MES.CHL.169"/></s:if>
                </s:else>                
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.167'))}" headerClass="tct" sortable="false" style="width:60px; text-align:center">
                <s:if test="#attr.tbllistStaffonOffByFile.action == 0"><tags:label key="MES.CHL.170"/></s:if>
                <s:else>
                    <s:if test="#attr.tbllistStaffonOffByFile.action == 1"><tags:label key="MES.CHL.171"/></s:if>
                </s:else>
            </display:column>
        </display:table>
    </div>
</fieldset>
