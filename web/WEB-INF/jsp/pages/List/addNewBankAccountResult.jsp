<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : bankAccountResult
    Created on : Aug 24, 2009, 2:54:54 PM
    Author     : NamNX
--%>
<%--
    danh muc ngan hang
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
    request.setAttribute("listBankAccount", request.getSession().getAttribute("listBankAccount"));
%>


<display:table id="tblListBankAccount" name="listBankAccount" 
               class="dataTable" cellpadding="1" cellspacing="1"
               pagesize="10" targets="listBankAccount" requestURI="bankAccountAction!pageNavigator.do">
    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListBankAccount_rowNum)}</display:column>
    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.bank.code'))}" headerClass="tct">
        <s:property escapeJavaScript="true"  value="#attr.tblListBankAccount.bankCode" />
    </display:column>
    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.bank.name'))}" headerClass="tct">
        <s:property escapeJavaScript="true"  value="#attr.tblListBankAccount.bankName" />
    </display:column>
    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.LST.819'))}" headerClass="tct">
        <s:property escapeJavaScript="true"  value="#attr.tblListBankAccount.bankAccountGroupCode" />
    </display:column>
    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.LST.820'))}" headerClass="tct">
        <s:property escapeJavaScript="true"  value="#attr.tblListBankAccount.bankAccountGroupName" />
    </display:column>
    <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.account.number'))}" property="accountNo" headerClass="tct" style="text-align:right"/>
    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" headerClass="tct" >
        <s:if test="#attr.tblListBankAccount.status == 1"><tags:label key="MSG.active" /></s:if>
        <s:elseif test="#attr.tblListBankAccount.status == 0"><tags:label key="MSG.inactive" /></s:elseif>
    </display:column>
    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.copy'))}" sortable="false" headerClass="sortable" style="text-align:center; width:50px;">
        <sx:a onclick="copyBankAccount('%{#attr.tblListBankAccount.bankAccountId}')">
            <img src="${contextPath}/share/img/clone.jpg" title="<s:text name="MSG.copy" />" alt="<s:text name="MSG.copy" />"/>
        </sx:a>
    </display:column>
    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
        <s:url action="bankAccountAction!prepareEditBankAccount" id="URL1">
            <s:param name="bankAccountId" value="#attr.tblListBankAccount.bankAccountId"/>
        </s:url>
        <sx:a onclick="editBankAccount('%{#attr.tblListBankAccount.bankAccountId}')" >
            <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="MSG.generates.edit" />" alt="<s:text name="MSG.generates.edit" />"/>
        </sx:a>
    </display:column>
    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.delete'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
        <s:url action="bankAccountAction!deleteBankAccount" id="URL2">
            <s:token/>
            <s:param name="bankAccountId" value="#attr.tblListBankAccount.bankAccountId"/>
            <s:param name="struts.token.name" value="'struts.token'"/>
            <s:param name="struts.token" value="struts.token"/>


        </s:url>
        <sx:a onclick="delBankAccount('%{#attr.tblListBankAccount.bankAccountId}')" >
            <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name="MSG.generates.delete" />" alt="<s:text name="MSG.generates.delete" />"/>
        </sx:a>
    </display:column>
</display:table>

