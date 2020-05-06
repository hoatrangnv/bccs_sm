<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
        if (request.getAttribute("listChannelType") == null) {
            request.setAttribute("listChannelType", request.getSession().getAttribute("listChannelType"));
        }
        request.setAttribute("contextPath", request.getContextPath());
%>


<sx:div id="displayTagFrame">
    <tags:imPanel title="MSG.channel.list">
        <s:if test= "#request.listChannelType != null && #request.listChannelType.size() > 0">

            <display:table id="tblListChannelType" name="listChannelType"
                           pagesize="10" targets="displayTagFrame"
                           requestURI="saleChannelTypeAction!pageNavigator.do"
                           class="dataTable" cellpadding="1" cellspacing="1" >
                <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.054'))}" sortable="false" style="text-align:center" headerClass="tct">${fn:escapeXml(tblListChannelType_rowNum)}</display:column>
                <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MES.CHL.140'))}" property="name" sortable="false" headerClass="sortable"/>
                <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.060'))}" headerClass="tct" sortable="false">
                    <s:if test="#attr.tblListChannelType.status == 1"><tags:label key="MSG.active" /></s:if>
                    <s:elseif test="#attr.tblListChannelType.status == 0"><tags:label key="MSG.inactive" /></s:elseif>
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.141'))}" headerClass="tct" sortable="false">
                    <s:if test="#attr.tblListChannelType.objectType == 1">Shop</s:if>
                    <s:elseif test="#attr.tblListChannelType.objectType == 2">Staff</s:elseif>
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.142'))}" headerClass="tct" sortable="false">
                    <s:if test="#attr.tblListChannelType.isVtUnit == 1"><tags:label key="MSG.belong.viettel" /></s:if>
                    <s:elseif test="#attr.tblListChannelType.isVtUnit == 2"><tags:label key="MSG.not.belong.viettel" /></s:elseif>
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.143'))}" headerClass="tct" sortable="false">
                    <s:if test="#attr.tblListChannelType.checkComm == 1"><tags:label key="MSG.have.bonus" /></s:if>
                    <s:elseif test="#attr.tblListChannelType.checkComm == 2"><tags:label key="MSG.not.have.bonus" /></s:elseif>
                </display:column>
                <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MES.CHL.144'))}" property="stockReportTemplate" sortable="false" headerClass="sortable"/>
                <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.107'))}" sortable="false" headerClass="sortable" style="text-align:center; width:50px;">
                    <sx:a onclick="copyChannelType('%{#attr.tblListChannelType.channelTypeId}')">
                        <img src="${contextPath}/share/img/clone.jpg" title="<s:text name="MSG.copy" />" alt="<s:text name="MSG.copy" />"/>
                    </sx:a>
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.062'))}" sortable="false" headerClass="sortable" style="text-align:center">
                    <s:url action="saleChannelTypeAction!prepareEditChannelType" id="URL1">
                        <s:param name="channelTypeId" value="#attr.tblListChannelType.channelTypeId"/>
                    </s:url>
                    <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="MSG.generates.edit" />" alt="<s:text name="MSG.generates.edit" />"/>
                    </sx:a>
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.145'))}" sortable="false" headerClass="sortable" style="text-align:center">
                    <s:url action="saleChannelTypeAction!deleteChannelType" id="URL2">
                        <s:param name="channelTypeId" value="#attr.tblListChannelType.channelTypeId"/>
                    </s:url>
                    <s:a href="" onclick="delChannelType('%{#attr.tblListChannelType.channelTypeId}')">
                        <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name="MSG.generates.delete" />" alt="<s:text name="MSG.generates.delete" />"/>
                    </s:a>
                </display:column>
            </display:table>
        </s:if>
        <s:else>
            <font color='red'>
                <tags:label key="MSG.blank.item" />
            </font>
        </s:else>
    </tags:imPanel>
</sx:div>


