<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listBankReceiptExternal
    Created on : Oct 28, 2010, 4:49:46 PM
    Author     : Doan Thanh 8
    Desc       : danh sach cac giay nop tien co the dieu chinh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listBankReceiptExternalForm", request.getSession().getAttribute("listBankReceiptExternalForm"));
%>


<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('MSG.DET.077'))}</legend>
    <display:table id="tblBankReceiptExternalForm" name="listBankReceiptExternalForm"
                   pagesize="10" targets="divAdjustBankReceipt" requestURI="adjustBankReceiptAction!pageNavigator.do"
                   class="dataTable" cellpadding="1" cellspacing="1" >
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.DET.078'))}" style="text-align:center" headerClass="tct" sortable="false">
            ${fn:escapeXml(tblBankReceiptExternalForm_rowNum)}
        </display:column>
        <display:column escapeXml="true"  property="shopCode" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.069'))}"/>
        <display:column escapeXml="true"  property="shopName" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.088'))}"/>
        <display:column property="bankReceiptDate" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.070'))}" format="{0,date,dd/MM/yyyy}"/>
        <display:column escapeXml="true"  property="bankName" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.071'))}"/>
        <display:column escapeXml="true"  property="bankAccountGroupName" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.072'))}"/>
        <display:column escapeXml="true"  property="bankReceiptCode" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.073'))}"/>
        <display:column property="amount" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.110'))}" format="{0,number,#,##0.00}" style="text-align:right; "/>
        <display:column property="amountAfterAdjustment" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.111'))}" format="{0,number,#,##0.00}" style="text-align:right; "/>
        <display:column escapeXml="true"  property="content" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.076'))}"/>
        <display:column headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}">
            <s:if test="#attr.tblBankReceiptExternalForm.status==1">
                ${fn:escapeXml(imDef:imGetText('MSG.DET.089'))}
            </s:if>
            <s:elseif test="#attr.tblBankReceiptExternalForm.status==2">
                ${fn:escapeXml(imDef:imGetText('MSG.DET.090'))}
            </s:elseif>
            <s:elseif test="#attr.tblBankReceiptExternalForm.status==3">
                ${fn:escapeXml(imDef:imGetText('MSG.DET.091'))}
            </s:elseif>
            <s:elseif test="#attr.tblBankReceiptExternalForm.status==4">
                ${fn:escapeXml(imDef:imGetText('MSG.DET.092'))}
            </s:elseif>
            <s:else>
                Undefined - <s:property escapeJavaScript="true"  value="#attr.tblBankReceiptExternalForm.status"/>
            </s:else>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.DET.079'))}" sortable="false" headerClass="tct" style="text-align:center; width:80px;">
            <!-- chi cho phep sua, xoa doi voi cac truong hop chua duyet -->
            <s:if test="#attr.tblBankReceiptExternalForm.status==1 || #attr.tblBankReceiptExternalForm.status==2">
                <s:url action="adjustBankReceiptAction!prepareAdjustBankReceipt" id="URL2">
                    <!-- TuTM1 04/03/2012 Fix ATTT CSRF -->
                    <s:token/>
                    <s:param name="bankReceiptExternalForm.bankReceiptExternalId" value="#attr.tblBankReceiptExternalForm.bankReceiptExternalId"/>
                    <s:param name="struts.token.name" value="'struts.token'"/>
                    <s:param name="struts.token" value="struts.token"/>
                </s:url>
                <sx:a onclick="prepareAdjustBankReceipt('%{#attr.tblBankReceiptExternalForm.bankReceiptExternalId}')" >
                    <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="MSG.DET.104"/>" alt="<s:text name="MSG.DET.104"/>"/>
                </sx:a>
            </s:if>
            <s:else>
                <a>
                    <img src="${contextPath}/share/img/edit_icon_1.jpg" title="<s:text name="MSG.DET.104"/>" alt="<s:text name="MSG.DET.104"/>"/>
                </a>
            </s:else>
        </display:column>
    </display:table>

</fieldset>


<script>
    prepareAdjustBankReceipt = function(bankReceiptExternalId) {
        // TuTM1 04/03/2012 Fix ATTT CSRF
        gotoAction('divAdjustBankReceipt','adjustBankReceiptAction!prepareAdjustBankReceipt.do?bankReceiptExternalForm.bankReceiptExternalId=' 
            + bankReceiptExternalId + "&" + token.getTokenParamString());
    }
</script>
