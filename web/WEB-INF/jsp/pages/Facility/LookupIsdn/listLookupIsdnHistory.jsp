<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listLookupIsdnHistory
    Created on : Sep 10, 2010, 10:25:03 AM
    Author     : TheTM
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
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("listLookupIsdnHistory", request.getAttribute("listLookupIsdnHistory"));
%>

<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('MSG.search.lookup'))}</legend>
        <div style="width:100%; height:300px; overflow:auto;">
            <s:if test="#request.actionType == 1">
                <display:table name="listLookupIsdnHistory" id="tblListLookupIsdnHistory"
                               class="dataTable" cellpadding="1" cellspacing="1">
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListLookupIsdnHistory_rowNum)}</display:column>
                    <display:column escapeXml="true" property="actionUser" title="${fn:escapeXml(imDef:imGetText('MSG.actionUser'))}" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true" property="actionIpAddress" title="${fn:escapeXml(imDef:imGetText('MSG.actionIpAddress'))}" sortable="false" style="text-align:right" headerClass="tct"/>
                    <display:column property="actionTime" title="${fn:escapeXml(imDef:imGetText('MSG.actionTime'))}" sortable="false" format="{0,date,dd/MM/yyyy kk:mm:ss}" headerClass="tct"/>
                    <display:column property="oldValue" title="${fn:escapeXml(imDef:imGetText('MSG.priceOld'))}" format="{0,number,#,###}" sortable="false" style="text-align:right" headerClass="tct"/>
                    <display:column property="newValue" title="${fn:escapeXml(imDef:imGetText('MSG.priceNew'))}" format="{0,number,#,###}" sortable="false" style="text-align:right" headerClass="tct"/>
                </display:table>
            </s:if>
            <s:if test="#request.actionType == 2">
                <display:table name="listLookupIsdnHistory" id="tblListLookupIsdnHistory"
                               class="dataTable" cellpadding="1" cellspacing="1">
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListLookupIsdnHistory_rowNum)}</display:column>
                    <display:column escapeXml="true" property="actionUser" title="${fn:escapeXml(imDef:imGetText('MSG.actionUser'))}" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true" property="actionIpAddress" title="${fn:escapeXml(imDef:imGetText('MSG.actionIpAddress'))}" sortable="false" headerClass="tct"/>
                    <display:column property="actionTime" title="${fn:escapeXml(imDef:imGetText('MSG.actionTime'))}" sortable="false" format="{0,date,dd/MM/yyyy kk:mm:ss}" headerClass="tct"/>
                    <display:column escapeXml="true" property="ownerCodeOld" title="${fn:escapeXml(imDef:imGetText('MSG.ownerCodeOld'))}" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true" property="ownerNameOld" title="${fn:escapeXml(imDef:imGetText('MSG.ownerNameOld'))}" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true" property="ownerCodeNew" title="${fn:escapeXml(imDef:imGetText('MSG.ownerCodeNew'))}" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true" property="ownerNameNew" title="${fn:escapeXml(imDef:imGetText('MSG.ownerNameNew'))}" sortable="false" headerClass="tct"/>
                </display:table>
            </s:if>
        </div>
</fieldset>
