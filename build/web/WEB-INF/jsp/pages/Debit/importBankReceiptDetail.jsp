<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : importBankReceiptDetail
    Created on : Nov 1, 2010, 2:46:53 PM
    Author     : tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>


<s:if test="#request.lstBankReceiptTransSussess != null && #request.lstBankReceiptTransSussess.size() != 0">
    <tags:imPanel title="MSG.DET.140">
        <display:table name="lstBankReceiptTransSussess" targets="searchResultArea" id="transSussess" pagesize="1000" class="dataTable" cellpadding="1" cellspacing="1" requestURI="saleManagmentAction!pageNavigator.do">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.DET.078'))}" sortable="false" headerClass="tct">
                <div align="center">
                    <s:property escapeJavaScript="true"  value="%{#attr.transSussess_rowNum}"/>
                </div>
            </display:column>
            <display:column escapeXml="true"  property="bankAccountNo" title="${fn:escapeXml(imDef:imGetText('MSG.DET.120'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="bankAccountGroupCode" title="${fn:escapeXml(imDef:imGetText('MSG.DET.129'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="ownerCode" title="${fn:escapeXml(imDef:imGetText('MSG.RET.028'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="ownerName" title="${fn:escapeXml(imDef:imGetText('MSG.RET.029'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="bankReceiptCode" title="${fn:escapeXml(imDef:imGetText('MSG.DET.073'))}" sortable="false" headerClass="sortable"/>
            <display:column property="bankReceiptDate" title="${fn:escapeXml(imDef:imGetText('MSG.DET.070'))}" sortable="false" headerClass="sortable" format="{0,date,dd/MM/yyyy}" />            
            <display:column property="amount" title="${fn:escapeXml(imDef:imGetText('MSG.DET.121'))}" sortable="false" headerClass="sortable"  format="{0,number,#,###.00}" />
            <display:column escapeXml="true"  property="content" title="${fn:escapeXml(imDef:imGetText('MSG.DET.076'))}" sortable="false" headerClass="sortable"/>
            <display:footer> <tr> <td colspan="9" style="color:green">${fn:escapeXml(imDef:imGetText('MSG.SAE.147'))}:<s:property escapeJavaScript="true"  value="%{#request.lstBankReceiptTransSussess.size()}" /></td> <tr> </display:footer>
            </display:table>
        </tags:imPanel>
    </s:if>
    <s:elseif test="#request.lstBankReceiptTransError != null && #request.lstBankReceiptTransError.size() != 0">>
        <tags:imPanel title="MSG.DET.140">
            <display:table name="lstBankReceiptTransError" targets="searchResultArea" id="transError" pagesize="1000" class="dataTable" cellpadding="1" cellspacing="1" requestURI="saleManagmentAction!pageNavigator.do">
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.DET.078'))}" sortable="false" headerClass="tct">
                <div align="center">
                    <s:property escapeJavaScript="true"  value="%{#attr.transError_rowNum}"/>
                </div>
            </display:column>
            <display:column escapeXml="true"  property="bankReceiptDate" title="${fn:escapeXml(imDef:imGetText('MSG.DET.070'))}" sortable="false" headerClass="sortable"/>            
            <display:column escapeXml="true"  property="content" title="${fn:escapeXml(imDef:imGetText('MSG.DET.076'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="amount" title="${fn:escapeXml(imDef:imGetText('MSG.DET.121'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="errorCode" title="${fn:escapeXml(imDef:imGetText('MSG.DET.141'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="errorDes" title="${fn:escapeXml(imDef:imGetText('MSG.DET.142'))}" sortable="false" headerClass="sortable"/>

            <display:footer> <tr> <td colspan="6" style="color:green">${fn:escapeXml(imDef:imGetText('MSG.SAE.147'))}:<s:property escapeJavaScript="true"  value="%{#request.lstBankReceiptTransError.size()}" /></td> <tr> </display:footer>
            </display:table>
        </tags:imPanel>
    </s:elseif>
