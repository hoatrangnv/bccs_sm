<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : searchStaffStockTypeLimit
    Created on : 15/08/2011
    Author     : TuTM1
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
                           requestURI="staffStockTypeLimitAction!pageNavigator.do"
                           class="dataTable" cellpadding="1" cellspacing="1" >
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.chanel.type.name'))}" sortable="false" style="text-align:center" headerClass="tct">${fn:escapeXml(tblListChannelType_rowNum)}</display:column>
                <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.140'))}" property="name" sortable="false" headerClass="sortable"/>
                <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.174'))}" property="debitDefaultStr" sortable="false" headerClass="sortable"/>
                <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.175'))}" property="rateDebitStr" sortable="false" headerClass="sortable"/>
                <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.062'))}" sortable="false" headerClass="sortable" style="text-align:center">
                    <s:url action="staffStockTypeLimitAction!prepareEditChannelType" id="URL1">
                        <s:param name="channelTypeId" value="#attr.tblListChannelType.channelTypeId"/>
                    </s:url>
                    <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="MSG.generates.edit" />" alt="<s:text name="MSG.generates.edit" />"/>
                    </sx:a>
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


