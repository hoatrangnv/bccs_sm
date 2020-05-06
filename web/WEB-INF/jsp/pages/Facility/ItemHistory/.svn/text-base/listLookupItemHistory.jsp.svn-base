<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listLookupItemHistory
    Created on : Sep 5, 2012, 4:33:56 PM
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
            request.setAttribute("listLookupItemHistory", request.getAttribute("listLookupItemHistory"));
%>

<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('MSG.SIK.112'))}</legend>
    <div style="width:100%; height:300px; overflow:auto;">
        <display:table name="listLookupItemHistory" id="tblListLookupItemHistory"
                       class="dataTable" cellpadding="1" cellspacing="1">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListLookupItemHistory_rowNum)}</display:column>
            <display:column escapeXml="true" property="itemName" title="${fn:escapeXml(imDef:imGetText('MSG.items'))}" sortable="false" headerClass="tct"/>
            <display:column property="actionDate" title="${fn:escapeXml(imDef:imGetText('MES.ANYPAY.005'))}" sortable="false" format="{0,date,dd/MM/yyyy kk:mm:ss}"  style="text-align:center"  headerClass="tct"/>
            <display:column escapeXml="true" property="actionType" title="${fn:escapeXml(imDef:imGetText('MSG.impact.type'))}" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="actionUser" title="${fn:escapeXml(imDef:imGetText('MSG.impact.user'))}" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="actionIp" title="${fn:escapeXml(imDef:imGetText('L.200042'))}" sortable="false" headerClass="tct"/>

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.detail'))}" sortable="false" headerClass="tct" style="width:80px; text-align:center; ">
                <s:a href="" onclick="aOnclick('%{#attr.tblListLookupItemHistory.actionId}')">
                    <img src="${contextPath}/share/img/accept.png"
                         title="${fn:escapeXml(imDef:imGetText('MSG.generates.commitment.view.info'))}"
                         alt="${fn:escapeXml(imDef:imGetText('MSG.generates.commitment.view.info'))}"/>
                </s:a>
            </display:column>
        </display:table>
    </div>
</fieldset>
<script language="javascript">
    aOnclick = function(actionId) {
        //openDialog('lookupSerialAction!viewLookUpSerialHistory.do?serial='+serial+'&stockModelCode='+ stockModelCode, 800, 600, true);
        openPopup('lookupItemHistoryAction!viewLookUpItemDetailHistory.do?actionId=' + actionId, 900, 700);
    }
</script>
