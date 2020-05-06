<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listBankReceipt
    Created on : May 14, 2009, 4:09:11 PM
    Author     : User one
--%>
 
<%--
    note: danh sach giay nop xien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">


<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        if (request.getAttribute("listBankReceipt") == null) {
            request.setAttribute("listBankReceipt", request.getSession().getAttribute("listBankReceipt"));
        }
        request.setAttribute("contextPath", request.getContextPath());
%>


<s:if test="#request.listBankReceipt != null && #request.listBankReceipt.size() > 0">
    <sx:div id="displayTagFrame">
        <display:table name="listBankReceipt" id="tblListBankReceipt"
                       targets="displayTagFrame"
                       pagesize="10"
                       class="dataTable" cellpadding="1" cellspacing="1"
                       requestURI="commDocDepositAction!pageNavigator.do">
            
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center">
            ${fn:escapeXml(tblListBankReceipt_rowNum)}
            </display:column>
            <!--display:column title="Mã giấy nộp tiền" property="bankReceiptId" headerClass="tct" sortable="true"/-->            
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.RET.028'))}" property="shopCode" headerClass="tct" sortable="false"/>
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.RET.029'))}" property="shopName" headerClass="tct" sortable="false"/>
            
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.account.number'))}" property="accountNo" headerClass="tct" sortable="false"/>
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.bank'))}" property="bankName" headerClass="tct" sortable="false"/>
            
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.money.paid'))}" property="amount" format="{0,number,#,###}" headerClass="tct" sortable="false" style="text-align:right"/>
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.028'))}" property="telServiceName" headerClass="tct" sortable="false" style="text-align:center"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.date.paid'))}" property="bankDate" headerClass="tct" sortable="false" format="{0,date,dd/MM/yyyy}" style="text-align:center" />
            
            <%--
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" headerClass="tct" sortable="false" style="text-align:center">
                <s:if test="#attr.tblListBankReceipt.status == 1"><tags:label key="MSG.not.approved" required="true"/></s:if>
                <s:if test="#attr.tblListBankReceipt.status == 2"><tags:label key="MSG.approved" required="true"/></s:if>
                <s:if test="#attr.tblListBankReceipt.status == 4"><font color="red"><tags:label key="MSG.deny" required="true"/></font></s:if>
            </display:column>
            --%>
            
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.033'))}" sortable="false" headerClass="tct" style="text-align:center">
                <s:url action="commDocDepositAction!prepareEditBankReceipt" id="URL1">
                    <s:param name="bankReceiptId" value="#attr.tblListBankReceipt.bankReceiptId"/>
                </s:url>
                <s:if test="(#attr.tblListBankReceipt.status == 1 || #attr.tblListBankReceipt.status == 4)" >
                    <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true" onclick="checkAllowEdit('%{#session.userToken.getUserID()}','%{#attr.tblListBankReceipt.staffId}')">
                        <img src="${contextPath}/share/img/edit_icon.jpg" 
                             title="<s:text name="MSG.edit"/>" alt="<s:text name="edit"/>"/>
                    </sx:a>
                </s:if>
                <s:else>
                    <img src="${contextPath}/share/img/disable_edit_icon.bmp" title="<s:text name="MSG.edit"/>" alt="<s:text name="edit"/>"/>
                </s:else>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.delete'))}" sortable="false" headerClass="tct" style="text-align:center">
                <s:url action="commDocDepositAction!deleteBankReceiptB" id="URL2" >
                    <s:param name="bankReceiptId" value="#attr.tblListBankReceipt.bankReceiptId"/>
                </s:url>
                <s:if test="(#attr.tblListBankReceipt.status == 1 || #attr.tblListBankReceipt.status == 4)">
                    <sx:a targets="bodyContent" href="%{#URL2}" cssClass="cursorHand" executeScripts="true" parseContent="true" onclick="confirmDelete('%{#session.userToken.getUserID()}','%{#attr.tblListBankReceipt.staffId}')">
                        <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name="MSG.edit"/>" alt="<s:text name="edit"/>"/>
                    </sx:a>
                </s:if>
                <s:else>
                    <img src="${contextPath}/share/img/disable_delete_icon.jpg" title="<s:text name="MSG.delete"/>" alt="<s:text name="MSG.delete"/>"/>
                </s:else>
            </display:column>
        </display:table>

    </sx:div>
</s:if>
<s:else>
    <font color='red'>
        <tags:label key="MSG.blank.item"/>
    </font>
</s:else>

