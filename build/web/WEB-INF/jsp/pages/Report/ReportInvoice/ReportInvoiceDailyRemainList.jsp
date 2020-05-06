<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ReportInvoiceDailyRemainList
    Created on : May 7, 2010, 11:49:10 AM
    Author     : tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<div id="reportResultArea">
    <display:table name="reportResultList" id="tblReportResult"
                   class="dataTable" cellpadding="1" cellspacing="1">
        <display:column title="MSG.RET.049" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblReportResult_rowNum)}</display:column>
        <display:column escapeXml="true" property="shopName" title="MSG.RET.050" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" property="objName" title="MSG.RET.051" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" property="blockNo" title="MSG.RET.052" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" property="serialNo" title="MSG.RET.053" sortable="false" headerClass="tct"/>
        <display:column property="fromInvoice" title="MSG.RET.054" format="{0,number,#,###}" sortable="false" headerClass="tct"/>
        <display:column property="toInvoice" title="MSG.RET.055" format="{0,number,#,###}" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" property="quantity" title="MSG.RET.056" sortable="false" headerClass="tct"/>
    </display:table>

</div>
