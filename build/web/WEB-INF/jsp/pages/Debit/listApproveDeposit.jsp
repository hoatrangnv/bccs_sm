<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listApproveDeposit
    Created on : May 14, 2009, 4:35:35 PM
    Author     : User one

Duyet phieu thu tien

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
        <display:table name="listBankReceipt" id="tblListBankReceipt" targets="displayTagFrame" pagesize="10" class="dataTable" cellpadding="1" cellspacing="1" requestURI="approveDepositAction!pageNavigator.do">
<!--            STT-->
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center">
                ${fn:escapeXml(tblListBankReceipt_rowNum)}
            </display:column>
            <!--display:column title="Mã giấy nộp tiền" property="bankReceiptId" headerClass="tct" sortable="true"/-->
<!--            Mã cửa hàng-->
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.SAE.109'))}" property="shopCode" headerClass="tct" sortable="false"/>
<!--            Tên cửa hàng-->
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.SAE.110'))}" property="shopName" headerClass="tct" sortable="false"/>
<!--Ngân hàng-->
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.bank'))}" property="bankName" headerClass="tct" sortable="false"/>
<!--            Số tài khoản-->
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.account.number'))}" property="accountNo" headerClass="tct" sortable="false"/>

<!--            Số tiền nộp-->
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.money.paid'))}" property="amount" headerClass="tct" format="{0,number,#,###}" sortable="false" style="text-align:right"/>
<!--            Loại dịch vụ-->
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.028'))}" property="telServiceName" headerClass="tct" sortable="false" style="text-align:center"/>

<!--            Trạng thái-->
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.094'))}" headerClass="tct" sortable="false" style="text-align:center">
                <s:if test="#attr.tblListBankReceipt.status == 1">${fn:escapeXml(imDef:imGetText('MSG.not.approved'))}
<!--                    Chưa duyệt-->
                </s:if>
                <s:if test="#attr.tblListBankReceipt.status == 2">
                    ${fn:escapeXml(imDef:imGetText('MSG.approved'))}
<!--                    Đã duyệt-->
                </s:if>
                <s:if test="#attr.tblListBankReceipt.status == 4"><font color="red">
                        ${fn:escapeXml(imDef:imGetText('MSG.deny'))}
<!--                        Từ chối-->
                    </font></s:if>
                <s:hidden name="approveDepositForm.listBankReceiptId1" id="listBankReceiptId1" value="_%{#attr.tblListBankReceipt.status}"></s:hidden>
            </display:column>
<!--Chọn-->
            <display:column title="${fn:escapeXml(imDef:imGetText('tbl.th.imSearchPopup.select'))}" headerClass="tct" sortable="false" style="text-align:center">
                <s:checkbox theme="simple" name="approveDepositForm.listBankReceiptId" fieldValue="%{#attr.tblListBankReceipt.bankReceiptId}" id="listBankReceiptId" />
            </display:column>
        </display:table>
    </sx:div>
</s:if>

