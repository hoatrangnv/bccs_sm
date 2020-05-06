<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : createStockIsdnSearchResult
    Created on : Jan 15, 2008, 2:54:01 PM
    Author     : Tuannv
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        request.setAttribute("importHistoryList", request.getSession().getAttribute("importHistoryList"));
%>

<sx:div id="divListRange">
    <fieldset class="imFieldset">
        <legend>
            ${fn:escapeXml(imDef:imGetText('MSG.history.create.range.isdn'))}
        </legend>
        <display:table name="importHistoryList" id="importHistory"
                       pagesize="15" targets="divListRange"
                       class="dataTable" cellpadding="1" cellspacing="1"
                       requestURI="createStockIsdnAction!pageNavigator.do">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="text-align:center" headerClass="tct">
            ${fn:escapeXml(importHistory_rowNum)}
            </display:column>
            <display:column escapeXml="true"  property="fromIsdn" title="${fn:escapeXml(imDef:imGetText('MSG.fromIsdn'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="toIsdn" title="${fn:escapeXml(imDef:imGetText('MSG.toIsdn'))}" sortable="false" headerClass="sortable"/>
            <display:column property="quantity" title="${fn:escapeXml(imDef:imGetText('MSG.quantity'))}" format="{0,number,#,###}" style="text-align:right; " sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="lastUpdateUser" title="${fn:escapeXml(imDef:imGetText('MSG.creater'))}" sortable="false" headerClass="sortable"/>
            <display:column property="lastUpdateTime" title="${fn:escapeXml(imDef:imGetText('MSG.create.date'))}" format="{0,date,dd/MM/yyyy}" sortable="false" headerClass="sortable" style="width:100px; text-align:center; "/>
        </display:table>
    </fieldset>
</sx:div>
    
