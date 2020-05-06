<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : viewItemDetailHistory
    Created on : Sep 5, 2012, 4:30:02 PM
    Author     : os_hoangpm3
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
    request.setAttribute("listLookupItemDetailHistory", request.getAttribute("listLookupItemDetailHistory"));
%>
<tags:imPanel title="L.200044">
    <div class="divHasBorder">
        <s:form action="lookupItemHistoryAction" theme="simple" method="post" id="lookupItemHistoryForm">
<s:token/>

            <table class="inputTbl6Col">
                <tr>
                    <td><tags:label key="MSG.items"/></td><td><s:textfield name="lookupItemHistoryForm.itemName" readonly="true"/></td>

                <td><tags:label key="MSG.impact.type"/></td><td><s:textfield name="lookupItemHistoryForm.actionTypeItem" readonly="true"/></td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.impact.date"/></td><td><s:textfield name="lookupItemHistoryForm.actionDate" readonly="true"/></td>

                <td><tags:label key="MSG.impact.user"/></td><td><s:textfield name="lookupItemHistoryForm.actionUser" readonly="true"/></td>
                <td><tags:label key="L.200042"/></td><td><s:textfield name="lookupItemHistoryForm.actionIp" readonly="true"/></td>
                </tr>
            </table>

            <!-- phan hien thi thong tin can tra cuu -->

            <fieldset class="imFieldset">
                <legend>${fn:escapeXml(imDef:imGetText('MSG.SIK.267'))}</legend>
                <div style="width:100%; height:300px; overflow:auto;">
                    <display:table name="listLookupItemDetailHistory" id="tblListLookupItemDetailHistory"
                                   class="dataTable" cellpadding="1" cellspacing="1">
                        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListLookupItemDetailHistory_rowNum)}</display:column>
                        <display:column escapeXml="true" property="tableName" title="${fn:escapeXml(imDef:imGetText('L.200046'))}" sortable="false" headerClass="tct"/>
                        <display:column escapeXml="true" property="columnName" title="${fn:escapeXml(imDef:imGetText('L.200047'))}" sortable="false" headerClass="tct"/>
                        <display:column escapeXml="true"  property="oldValue" title="${fn:escapeXml(imDef:imGetText('L.200049'))}" sortable="false" headerClass="tct"/>
                        <display:column escapeXml="true"  property="newValue" title="${fn:escapeXml(imDef:imGetText('L.200050'))}" sortable="false" headerClass="tct"/>
                        <display:column escapeXml="true"  property="objectId" title="${fn:escapeXml(imDef:imGetText('L.200048'))}" sortable="false" headerClass="tct"/>
                    </display:table>
                </div>
            </fieldset>
        </s:form>
    </div>
</tags:imPanel>
