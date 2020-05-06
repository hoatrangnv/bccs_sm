<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listSerialRange
    Created on : Feb 5, 2010, 4:30:56 PM
    Author     : NamNX
    Hien thi cac khoang Serial
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            //request.setAttribute("listSerialRange", request.getAttribute("listSerialRange"));
            request.getAttribute("listSerialRange");
//out.print(StringEscapeUtils.escapeHtml("ukie"));
%>
<s:if test="#request.ListSerialRangeMessage == null || #request.ListSerialRangeMessage == ''">
    <script type="text/javascript">
        $('btnCheckSerial').style.visibility  = 'hidden';
        $('btnImportFromPartner').style.visibility  = 'visible';
    </script>
</s:if>
<s:else>
    <script type="text/javascript">
        $('btnCheckSerial').style.visibility  = 'visible';
        $('btnImportFromPartner').style.visibility  = 'hidden';
    </script>
</s:else>
<div style="width:100%">
    <tags:displayResult id="ListSerialRangeMessage" property="ListSerialRangeMessage" propertyValue="messageParam" type="key"/>
</div>
<sx:div id="displayTagFrame">
    <tags:imPanel title="MSG.list.range.serial">

        <display:table id="tblListSerialRange" name="listSerialRange"
                       class="dataTable" targets="displayTagFrame"
                       cellpadding="1" cellspacing="1" pagesize="40" requestURI="importFromPartnerAction!checkSerialRange.do">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListSerialRange_rowNum)}</display:column>
            <display:column escapeXml="true"  property="fromSerial" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.227'))}" style=" text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="toSerial" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.228'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="serialQuantity" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.179'))}"  style="text-align:center" sortable="false" headerClass="tct"/>
        </display:table>
    </tags:imPanel>
</sx:div>


