<%--
    Document   : invoiceManagementList
    Created on : Aug 13, 2009, 8:49:24 AM
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="inventoryDisplay"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
    //request.setAttribute("lstInvoice", request.getSession().getAttribute("lstInvoice"));
%>

<div id="invoiceUsedList">
    <s:if test="#request.lstInvoice != null && #request.lstInvoice.size() != 0">
        <inventoryDisplay:table targets="invoiceUsedList" id="invoiceUsed"
                                name="lstInvoice" pagesize="50"
                                class="dataTable" cellpadding="1" cellspacing="1"
                                requestURI="InvoiceCreditManagementAction!pageNavigator.do" >
            <display:column escapeXml="true" title="${imDef:imGetText('MSG.SAE.048')}" sortable="false" headerClass="tct" style="text-align:center; width:50px" >
                ${invoiceUsed_rowNum}
            </display:column>

            <!--MSG.createBillDate Ngày lập HĐ -->
            <display:column escapeXml="false" property="createdate" title="${imDef:imGetText('MSG.createBillDate')}" sortable="false" format="{0,date,dd/MM/yyyy}" headerClass="tct" style="text-align:left;"/>
            <!--Ngày lập GD-->
            <display:column escapeXml="false" property="invoiceDate" title="${imDef:imGetText('MSG.createTransDate')}" sortable="false" format="{0,date,dd/MM/yyyy}" headerClass="tct" style="text-align:left;"/>
            <!--            Số HĐ-->
            <display:column escapeXml="true" property="invoiceNo" title="${imDef:imGetText('MSG.contractNo')}" sortable="false" style="text-align:left;" headerClass="tct"/>
            <!--            Ma TRA-->
            <%--<display:column escapeXml="true" property="esdCode" title="${imDef:imGetText('lb.tra.code')}" sortable="false" style="text-align:left;" headerClass="tct"/>--%>
            <!--            Tên khách hàng-->
            <display:column escapeXml="true" property="custName" title="${imDef:imGetText('MSG.cusName')}" sortable="false" headerClass="tct" style="text-align:left;"/>
            <!--            Trước thuế-->
            <%--<display:column escapeXml="false" property="amountNotTax" title="${imDef:imGetText('MSG.amountMoneyNotTax')}" sortable="false" headerClass="tct" style="text-align:right;" format="{0,number,#,###}" />--%>
            <display:column escapeXml="false" property="amountNotTax" title="${imDef:imGetText('MSG.amountMoneyNotTax')}" sortable="false" headerClass="tct" style="text-align:right;" />
            <!--            Thuế-->
            <display:column escapeXml="false" property="tax" title="${imDef:imGetText('MSG.tax')}" sortable="false" headerClass="tct" style="text-align:right" format="{0,number,#,###}"/>
            <!--            Khuyến mại-->
            <display:column escapeXml="false" property="promotion" title="${imDef:imGetText('MSG.SAE.080')}" sortable="false" headerClass="tct" style="text-align:right" format="{0,number,#,###}"/>
            <!--            Chiết khấu-->
            <display:column escapeXml="false" property="discount" title="${imDef:imGetText('MSG.SAE.081')}" sortable="false" headerClass="tct" style="text-align:right" format="{0,number,#,###}"/>
            <!--            Tổng tiền-->
            <%--<display:column escapeXml="false" property="amountTax" title="${imDef:imGetText('MSG.SAE.145')}" sortable="false" headerClass="tct" style="text-align:right;" format="{0,number,#,###}"/>--%>
            <display:column escapeXml="false" property="amountTax" title="${imDef:imGetText('MSG.SAE.145')}" sortable="false" headerClass="tct" style="text-align:right;"/>
            <!--            Trạng thái-->
            <display:column escapeXml="false" title="${imDef:imGetText('MSG.SIK.002')}" sortable="false" headerClass="tct" style="text-align:left;">
                <s:if test = "#attr.invoiceUsed.invoiceStatus*1 == 4">
                    &nbsp; 
                    <s:property value ="getText('MSG.GOD.196')"/>
                    <!--                    Đã huỷ-->
                </s:if>
                <s:else>
                    &nbsp;
                    <s:property value ="getText('MSG.active')"/>
                    <!--                    Hiệu lực-->
                </s:else>
            </display:column>
            <display:column escapeXml="false" title="${imDef:imGetText('MSG.listTrans')}" sortable="false" style="text-align:center; width:50px;" headerClass="tct">
                <!--                MSG.listTrans
                                Danh sách GD-->
                <s:if test = "#attr.invoiceUsed.invoiceStatus*1 == 1">
                    <s:url action="InvoiceManagementAction!searchSaleTransList" id="URL1">
                        <s:param name="invoiceUsedId" value="#attr.invoiceUsed.invoiceUsedId"/>
                    </s:url>
                    <sx:a targets="invoiceSaleTransSearchlResultArea" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/accept.png" 
                             title="<s:property value ="getText('MSG.listTrans')"/>"
                             alt="<s:property value ="getText('MSG.listTrans')"/>"
                             />
                    </sx:a>
                </s:if>
                <%--<s:else>-</s:else>--%>
            </display:column>
            <display:column title="${imDef:imGetText('MSG.requestInvoice')}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
                <!--                  Yeu cau in lai-->
                <div id="a<s:property value="#attr.invoiceUsed.invoiceUsedId"/>">
                    <s:if test = "#attr.invoiceUsed.checkRequestInvoice*1 == 0">
                        <a href="#" onclick="requestInvoice('<s:property value="#attr.invoiceUsed.invoiceUsedId"/>')">
                            <img src="${contextPath}/share/img/accept.png" title="${imDef:imGetText('MSG.requestInvoice')}" alt="${imDef:imGetText('MSG.requestInvoice')}"/>
                        </a>
                    </s:if>
                    <s:elseif test = "#attr.invoiceUsed.checkRequestInvoice*1 == 1">
                        <s:property escapeJavaScript="true"  value ="getText('MSG.requested')"/>
                    </s:elseif>
                    <s:elseif test = "#attr.invoiceUsed.checkRequestInvoice*1 == 2">
                        <s:property escapeJavaScript="true"  value ="getText('MSG.approved')"/>
                    </s:elseif>
                </div>

            </display:column>

            <display:column escapeXml="false" title="${imDef:imGetText('MSG.detailContract')}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
                <!--                Chi tiết HĐ-->
                <s:a href="#" onclick="aOnclick('%{#attr.invoiceUsed.invoiceUsedId}')">
                    <img src="<%=request.getContextPath()%>/share/img/edit_icon.jpg" title="${imDef:imGetText('MSG.detailContract')}" alt="${imDef:imGetText('MSG.detail')}" />
                </s:a>
            </display:column>
            <display:footer> <tr> <td colspan="17" style="color:green">
                        <s:property value ="getText('MSG.totalRecord')"/>
                        <!--                        MSG.totalRecord
                                                Tổng số bản ghi-->
                        :<s:property value="%{#request.lstInvoice.size()}" /></td> <tr> </display:footer>
                </inventoryDisplay:table>        
            </s:if>
</div>
<script>
    selectCbAll = function () {
        selectAll("checkAllID", "form.invoiceUsedIdList", "checkBoxID");
    }
    checkSelectCbAll = function () {
        checkSelectAll("checkAllID", "form.invoiceUsedIdList", "checkBoxID");
    }
</script>


<script type="text/javascript" language="javascript">

    validateDelete = function () {
        if (!isCheckedBox()) {
//            $( 'returnMsgClient' ).innerHTML='Chưa chọn hoá đơn nào!';
            $('returnMsgClient').innerHTML = '<s:property value="getText('MSG.sale.037')"/>';
            return false;
        }
        if ($('reasonId').value.trim() == '') {
//            $( 'returnMsgClient' ).innerHTML='Chưa chọn lý do huỷ HĐ!';
            $('returnMsgClient').innerHTML = '<s:property value="getText('MSG.sale.038')"/>';
            return false;
        }
        return true;
    };

    isCheckedBox = function () {
        var tbl = $('invoiceUsed');
        inputs = tbl.getElementsByTagName("input");
        for (i = 0; i < inputs.length; i++) {
            if (inputs[i].type == "checkbox" && inputs[i].checked == true) {
                return true;
            }
        }
        return false;
    };

    aOnclick = function (invoiceUsedId) {
        openDialog('InvoiceCreditManagementAction!getInvoiceInfo.do?invoiceUsedId=' + invoiceUsedId, 1024, 768, false);
    };

    requestInvoice = function (invoiceUsedId) {
        var strConfirm = getUnicodeMsg('<s:property value="getText('MSG.requestInvoice')"/>');
        if (confirm(strConfirm)) {
            document.getElementById("a" + invoiceUsedId).style.display = 'none';
            gotoAction('CreditInvoiceArea', "${contextPath}/InvoiceCreditManagementAction!requestCreditInvoice.do?invoiceUsedId=" + invoiceUsedId + "&" + token.getTokenParamString());
        }
    };

</script>