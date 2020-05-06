<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listDepositSlipInternal
    Created on : Oct 27, 2010, 10:29:53 AM
    Author     : Doan Thanh 8
    Desc       : danh sach giay nop tien (noi bo)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@page import="com.viettel.im.database.BO.UserToken, com.viettel.im.common.Constant" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listBankReceiptInternalForm", request.getSession().getAttribute("listBankReceiptInternalForm"));
            UserToken userToken = (UserToken) request.getSession().getAttribute(Constant.USER_TOKEN);
            request.setAttribute("userId", userToken.getUserID());
%>


<display:table id="tblBankReceiptInternalForm" name="listBankReceiptInternalForm"
               pagesize="10" targets="divListDepositSlipInternal" requestURI="depositSlipInternalAction!pageNavigator.do"
               class="dataTable" cellpadding="1" cellspacing="1" >
    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.DET.078'))}" style="text-align:center" headerClass="tct" sortable="false">
        ${fn:escapeXml(tblBankReceiptInternalForm_rowNum)}
    </display:column>
    <display:column escapeXml="true"  property="ownerCode" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.069'))}"/>
    <display:column escapeXml="true"  property="ownerName" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.088'))}"/>
    <display:column property="bankReceiptDate" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.070'))}" format="{0,date,dd/MM/yyyy}"/>
    <display:column escapeXml="true"  property="bankName" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.071'))}"/>
    <display:column escapeXml="true"  property="bankAccountGroupName" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.072'))}"/>
    <display:column escapeXml="true"  property="bankReceiptCode" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.073'))}"/>
    <display:column property="amountInFigure" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.074'))}" format="{0,number,#,###.00}" style="text-align:right; "/>
    <display:column escapeXml="true"  property="content" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.076'))}"/>
    <display:column escapeXml="true"  property="createdUser" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.creater'))}"/>
    <display:column headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}">
        <s:if test="#attr.tblBankReceiptInternalForm.status==1">
            ${fn:escapeXml(imDef:imGetText('MSG.DET.089'))}
        </s:if>
        <s:elseif test="#attr.tblBankReceiptInternalForm.status==2">
            ${fn:escapeXml(imDef:imGetText('MSG.DET.090'))}
        </s:elseif>
        <s:elseif test="#attr.tblBankReceiptInternalForm.status==3">
            ${fn:escapeXml(imDef:imGetText('MSG.DET.091'))}
        </s:elseif>
        <s:elseif test="#attr.tblBankReceiptInternalForm.status==4">
            ${fn:escapeXml(imDef:imGetText('MSG.DET.092'))}
        </s:elseif>
        <s:else>
            Undefined - <s:property escapeJavaScript="true"  value="#attr.tblBankReceiptInternalForm.status"/>
        </s:else>
    </display:column>
    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.DET.079'))}" sortable="false" headerClass="tct" style="text-align:center; width:80px;">
        <!-- chi cho phep sua, xoa doi voi cac truong hop chua duyet -->
        <s:if test="#attr.tblBankReceiptInternalForm.status.equals(1L) && #attr.tblBankReceiptInternalForm.createdUserId.equals(#request.userId)">
            <!-- TuTM1 04/03/2012 Fix ATTT CSRF -->
            <s:url action="depositSlipInternalAction!prepareEditDepositSlipInternal" id="URL1">
                <s:token/>
                <s:param name="bankReceiptInternalForm.bankReceiptInternalId" value="#attr.tblBankReceiptInternalForm.bankReceiptInternalId"/>
                <s:param name="struts.token.name" value="'struts.token'"/>
                <s:param name="struts.token" value="struts.token"/>
            </s:url>
            <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="MSG.generates.edit"/>" alt="<s:text name="MSG.generates.edit"/>"/>
            </sx:a>
            &nbsp;|&nbsp;
            <s:url action="depositSlipInternalAction!delDepositSlipInternal" id="URL2">
                <!-- TuTM1 04/03/2012 Fix ATTT CSRF -->
                <s:token/>
                <s:param name="bankReceiptInternalForm.bankReceiptInternalId" value="#attr.tblBankReceiptInternalForm.bankReceiptInternalId"/>
                <s:param name="struts.token.name" value="'struts.token'"/>
                <s:param name="struts.token" value="struts.token"/>
            </s:url>
            <sx:a onclick="delDepositSlipInternal('%{#attr.tblBankReceiptInternalForm.bankReceiptInternalId}')" >
                <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name="MSG.delete"/>" alt="<s:text name="MSG.delete"/>"/>
            </sx:a>
        </s:if>
        <s:else>
            <a>
                <img src="${contextPath}/share/img/edit_icon_1.jpg" title="<s:text name="MSG.generates.edit"/>" alt="<s:text name="MSG.generates.edit"/>"/>
            </a>
            &nbsp;|&nbsp;
            <a>
                <img src="${contextPath}/share/img/delete_icon_1.jpg" title="<s:text name="MSG.delete"/>" alt="<s:text name="MSG.delete"/>"/>
            </a>
        </s:else>


    </display:column>
</display:table>

<script>
    delDepositSlipInternal = function(bankReceiptInternalId) {
        var strConfirm = getUnicodeMsg('<s:text name="MSG.DET.005"/>');
        if (confirm(strConfirm)) {
            // TuTM1 04/03/2012 Fix ATTT CSRF
            gotoAction('bodyContent','depositSlipInternalAction!delDepositSlipInternal.do?bankReceiptInternalForm.bankReceiptInternalId=' 
                + bankReceiptInternalId + "&" + token.getTokenParamString());
        }
    }
</script>
