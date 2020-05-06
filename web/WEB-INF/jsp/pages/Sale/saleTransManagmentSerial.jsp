<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : saleTransManagmentSerial.jsp
    Created on : Mar 28, 2009, 2:36:44 PM
    Author     : tuannv
    Desc       : danh sach chi tiet serial trong giao dich ban hang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('MSG.SAE.159'))}</legend>
    <tags:displayResult id="resultViewSaleDetailSerialClient" property="resultViewSaleDetailSerial" type="key"/>
    <s:if test="#request.lstSaleTransDetailSerial != null && #request.lstSaleTransDetailSerial.size() != 0">
        <display:table name="lstSaleTransDetailSerial" id="trans"  class="dataTable" >
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}" sortable="false" headerClass="tct">
                <div align="center">
                    <s:property escapeJavaScript="true"  value="%{#attr.trans_rowNum}"/>
                </div>
            </display:column>
            <display:column escapeXml="true" property="fromSerial" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.049'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true" property="toSerial" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.050'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.051'))}" property="quantity" sortable="false" headerClass="sortable"/>

            <display:footer> <tr> <td colspan="4" style="color:green">${fn:escapeXml(imDef:imGetText('MSG.SAE.147'))}:<s:property escapeJavaScript="true"  value="%{#request.lstSaleTransDetailSerial.size()}" /></td> <tr> </display:footer>
            </display:table>

        </s:if>
</fieldset>

