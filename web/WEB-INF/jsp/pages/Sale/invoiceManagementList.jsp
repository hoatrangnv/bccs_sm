<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

        <div style="width:100%; height:500px; overflow:auto;">
            <inventoryDisplay:table targets="invoiceUsedList" id="invoiceUsed"
                                    name="lstInvoice" pagesize="500"
                                    class="dataTable" cellpadding="1" cellspacing="1"
                                    requestURI="InvoiceManagementAction!pageNavigator.do" >

                <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px" >
                    ${fn:escapeXml(invoiceUsed_rowNum)}
                </display:column>

                <!--MSG.createBillDate Ngày lập HĐ -->
                <display:column property="createdate" title="${fn:escapeXml(imDef:imGetText('MSG.createBillDate'))}" sortable="false" format="{0,date,dd/MM/yyyy}" headerClass="tct" style="text-align:left;"/>
                <!--Ngày lập GD-->
                <display:column property="invoiceDate" title="${fn:escapeXml(imDef:imGetText('MSG.createTransDate'))}" sortable="false" format="{0,date,dd/MM/yyyy}" headerClass="tct" style="text-align:left;"/>
                <!--            Số HĐ gốc-->
                <!--display:column escapeXml="true" property="originalInvoice" title="${imDef:imGetText('MSG.originalInvoice')}" sortable="false" style="text-align:left;" headerClass="tct"/-->
                <!--            Số HĐ-->
                <display:column escapeXml="true" property="invoiceNo" title="${imDef:imGetText('MSG.contractNo')}" sortable="false" style="text-align:left;" headerClass="tct"/>
                <!--            Ma TRA-->
                <%--<display:column escapeXml="true" property="esdCode" title="${imDef:imGetText('lb.tra.code')}" sortable="false" style="text-align:left;" headerClass="tct"/>--%>
                <!--            Tên khách hàng-->

                <!--            Số HĐ-->
                <display:column escapeXml="true" property="invoiceNo" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.144'))}" sortable="false" style="text-align:left;" headerClass="tct"/>
                <!--            Tên khách hàng-->
                <display:column escapeXml="true" property="custName" title="${fn:escapeXml(imDef:imGetText('MSG.cusName'))}" sortable="false" headerClass="tct" style="text-align:left;"/>
                <!--            Trước thuế-->
                <display:column escapeXml="true" property="amountNotTax" title="Before Tax" sortable="false" headerClass="tct" style="text-align:right;" format="{0,number,#,###.00}"/>
                <!--            Thuế-->
                <display:column escapeXml="true" property="tax" title="Tax" sortable="false" headerClass="tct" style="text-align:right" format="{0,number,#,###.00}"/>
                <!--            Khuyến mại-->
                <!--display:column escapeXml="true" property="promotion" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.080'))}" sortable="false" headerClass="tct" style="text-align:right" format="{0,number,#,###.00}"/-->
                <!--            Chiết khấu-->
                <display:column escapeXml="true" property="discount" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.081'))}" sortable="false" headerClass="tct" style="text-align:right" format="{0,number,#,###.00}"/>
                <!--            Tổng tiền-->

                <display:column escapeXml="true" property="amountTax" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.145'))}" sortable="false" headerClass="tct" style="text-align:right;" format="{0,number,#,###.00}"/>

                <display:column escapeXml="true" property="currency" title="Currency" sortable="false" headerClass="tct" style="text-align:center; width:75px;"/>


                <!--            Trạng thái-->
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SIK.002'))}" sortable="false" headerClass="tct" style="text-align:left;">
                    <s:if test = "#attr.invoiceUsed.invoiceStatus*1 == 4">
                        &nbsp; 
                        <s:property escapeJavaScript="true"  value ="getText('MSG.GOD.196')"/>
                        <!--                    Đã huỷ-->
                    </s:if>
                    <s:else>
                        &nbsp;
                        <s:property escapeJavaScript="true"  value ="getText('MSG.active')"/>
                        <!--                    Hiệu lực-->
                    </s:else>
                </display:column>

                <display:column escapeXml="false" title=" ${imDef:imGetText('MSG.printCopies1')}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
                    <!--                  In Lien 1 Windows-->
                    <div id="a<s:property value="#attr.invoiceUsed.invoiceUsedId"/>">
                        <s:if test = "#attr.invoiceUsed.checkPrint*1 == 1">
                            <a href="#" onclick="printInvoice('<s:property value="#attr.invoiceUsed.invoiceUsedId"/>', '1','1')">
                                <img src="${contextPath}/share/img/accept.png" title="${imDef:imGetText('MSG.printCopies1')}" alt="${imDef:imGetText('MSG.printCopies1')}"/>
                            </a>
                        </s:if>
                    </div>
                </display:column>
                <display:column escapeXml="false" title=" ${imDef:imGetText('MSG.printCopies2')}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
                    <!--                In Lien 2 Windows-->
                   <div id ="abcdef">
                    <s:url action="InvoiceManagementAction!printInvoice" id="URL32">
                        <s:param name="invoiceUsedId" value="%{#attr.invoiceUsed.invoiceUsedId}"/>
                        <!--tannh20180424 : sua theo YC phong tai chinh TraTV gioi han so lan in-->
                        <s:param name="invoiceType" value="2"/>
                        <s:param name="printType" value="2"/>
                    </s:url>
                         <!--tannh20180424 : sua theo YC phong tai chinh TraTV gioi han so lan in-->
                  <s:if test = "#attr.invoiceUsed.checkPrint2*1 == 0">    
                    <sx:a targets="InvoicePrinterArea" href="%{#URL32}" cssClass="cursorHand" executeScripts="true" parseContent="true" >
                        <img src="${contextPath}/share/img/accept.png" onclick="test()" id="imageabc" title="${imDef:imGetText('MSG.printCopies2')}" alt="${imDef:imGetText('MSG.printCopies2')}"/>
                    </sx:a>
                  </s:if>    
                   </div>
                </display:column>

                <display:column title="${imDef:imGetText('MSG.requestReprint')}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
                    <!--                  Yeu cau in lai-->
                    <div id="b<s:property value="#attr.invoiceUsed.invoiceUsedId"/>">
                        <s:if test = "#attr.invoiceUsed.checkRequestPrint*1 == 1">
                            <%--<s:if test = "#attr.invoiceUsed.checkRequestPrint*1 == 1">--%>
                            <a href="#" onclick="requestPrint('<s:property value="#attr.invoiceUsed.invoiceUsedId"/>')">
                                <img src="${contextPath}/share/img/accept.png" title="${imDef:imGetText('MSG.requestReprint')}" alt="${imDef:imGetText('MSG.requestReprint')}"/>
                            </a>
                        </s:if>
                        <s:elseif test = "#attr.invoiceUsed.checkRequestPrint*1 == 2">
                            <s:property escapeJavaScript="true"  value ="getText('MSG.waitApproval')"/>
                        </s:elseif>
                    </div>

                </display:column>

                <display:column title="${imDef:imGetText('MSG.approvalReprint')}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
                    <!--                  Duyet in lai-->
                    <div id="c<s:property value="#attr.invoiceUsed.invoiceUsedId"/>">
                        <s:if test = "#attr.invoiceUsed.checkApprovePrint*1 == 1">
                            <a href="#" onclick="approvePrint('<s:property value="#attr.invoiceUsed.invoiceUsedId"/>')">
                                <img src="${contextPath}/share/img/accept.png" title="${imDef:imGetText('MSG.approvalReprint')}" alt="${imDef:imGetText('MSG.approvalReprint')}"/>
                            </a>
                        </s:if>
                    </div>

                </display:column>

                <%--display:column title="${fn:escapeXml(imDef:imGetText('MSG.listTrans'))}" sortable="false" style="text-align:center; width:50px;" headerClass="tct">
                    <!--                MSG.listTrans
                                    Danh sách GD-->
                    <s:if test = "#attr.invoiceUsed.invoiceStatus*1 == 1">
                        <s:url action="InvoiceManagementAction!searchSaleTransList" id="URL1">
                            <s:param name="invoiceUsedId" value="#attr.invoiceUsed.invoiceUsedId"/>
                        </s:url>
                        <sx:a targets="invoiceSaleTransSearchlResultArea" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                            <img src="${contextPath}/share/img/accept.png" 
                                 title="<s:property escapeJavaScript="true"  value ="getText('MSG.listTrans')"/>"
                                 alt="<s:property escapeJavaScript="true"  value ="getText('MSG.listTrans')"/>"
                                 />
                            <!--                        Danh sách GD-->
                        </sx:a>
                    </s:if>
                    <s:else>-</s:else>
                </display:column>

            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.printNoteUsingWindows'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
                <!--                  In HĐ Windows-->
                <s:if test = "#attr.invoiceUsed.invoiceStatus*1 == 1">
                    <s:url action="InvoiceManagementAction!printInvoice" id="URL3">
                        <s:param name="invoiceUsedId" value="%{#attr.invoiceUsed.invoiceUsedId}"/>
                        <s:param name="invoiceType" value="1"/>
                    </s:url>
                    <sx:a targets="InvoicePrinterArea" href="%{#URL3}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/accept.png" title="In Hoá đơn" alt="In HĐ"/>
                    </sx:a>
                </s:if>
                <s:else>
                    -
                </s:else>
            </display:column>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.printNoteUsingUbuntu'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
                <!--                In HĐ Ubuntu-->
                <s:if test = "#attr.invoiceUsed.invoiceStatus*1 == 1">
                    <s:url action="InvoiceManagementAction!printInvoice" id="URL32">
                        <s:param name="invoiceUsedId" value="%{#attr.invoiceUsed.invoiceUsedId}"/>
                        <s:param name="invoiceType" value="2"/>
                    </s:url>
                    <sx:a targets="InvoicePrinterArea" href="%{#URL32}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/accept.png" title="In HĐ Ubuntu" alt="In HĐ Ubuntu"/>
                    </sx:a>
                </s:if>
                <s:else>
                    -                            
                </s:else>
            </display:column--%>

                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.detailContract'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
                    <!--                Chi tiết HĐ-->
                    <s:a href="" onclick="aOnclick('%{#attr.invoiceUsed.invoiceUsedId}')">
                        <img src="<%=StringEscapeUtils.escapeHtml(request.getContextPath())%>/share/img/edit_icon.jpg" title="Detail" alt="Detail" />
                    </s:a>
                </display:column>
                <display:footer> <tr> <td colspan="18" style="color:green">
                            <s:property escapeJavaScript="true"  value ="getText('MSG.totalRecord')"/>:<s:property escapeJavaScript="true"  value="%{#request.lstInvoice.size()}" /></td> <tr> </display:footer>

                </div>

        </inventoryDisplay:table>
    </s:if>
</div>
<script>
    selectCbAll = function(){
        selectAll("checkAllID", "form.invoiceUsedIdList", "checkBoxID");
    }
    checkSelectCbAll = function(){
        checkSelectAll("checkAllID", "form.invoiceUsedIdList", "checkBoxID");
    }
</script>


<script type="text/javascript" language="javascript">
       test = function(){
          document.getElementById("imageabc").style.display = 'none';
       }
       
       
    validateDelete = function(){
        if (!isCheckedBox()){
            $( 'returnMsgClient' ).innerHTML='Chưa chọn hoá đơn nào!';
            return false;
        }
        if ($('reasonId').value.trim() == ''){
            $( 'returnMsgClient' ).innerHTML='Chưa chọn lý do huỷ HĐ!';
            return false;
        }
        return true;
    }

    isCheckedBox = function(){
        var tbl = $('invoiceUsed');
        inputs = tbl.getElementsByTagName( "input" );
        for( i = 0; i < inputs.length; i++ ) {
            if( inputs[i].type == "checkbox" && inputs[i].checked == true ) {
                return true;
            }
        }
        return false;
    }

    aOnclick = function(invoiceUsedId) {
        openDialog('InvoiceManagementAction!getInvoiceInfo.do?invoiceUsedId=' + invoiceUsedId,1024,768,false);
    }

    printInvoice = function (invoiceUsedId, type, printType) {
        document.getElementById("a" + invoiceUsedId).style.display = 'none';
        document.getElementById("b" + invoiceUsedId).style.display = 'none';
        document.getElementById("c" + invoiceUsedId).style.display = 'none';
        gotoAction('InvoicePrinterArea', "${contextPath}/InvoiceManagementAction!printInvoice.do?invoiceUsedId=" + invoiceUsedId + "&invoiceType=" + type  + "&printType=" + printType + "&" + token.getTokenParamString());
    };

    requestPrint = function (invoiceUsedId) {
        var strConfirm = getUnicodeMsg('<s:property value="getText('MSG.requestPrint')"/>');
        if (confirm(strConfirm)) {
            document.getElementById("a" + invoiceUsedId).style.display = 'none';
            document.getElementById("b" + invoiceUsedId).style.display = 'none';
            document.getElementById("c" + invoiceUsedId).style.display = 'none';
            gotoAction('InvoicePrinterArea', "${contextPath}/InvoiceManagementAction!requestPrint.do?invoiceUsedId=" + invoiceUsedId + "&" + token.getTokenParamString());
        }
    };

    approvePrint = function (invoiceUsedId) {
        var strConfirm = getUnicodeMsg('<s:property value="getText('MSG.approvePrint')"/>');
        if (confirm(strConfirm)) {
            document.getElementById("a" + invoiceUsedId).style.display = 'none';
            document.getElementById("b" + invoiceUsedId).style.display = 'none';
            document.getElementById("c" + invoiceUsedId).style.display = 'none';
            gotoAction('InvoicePrinterArea', "${contextPath}/InvoiceManagementAction!approvePrint.do?invoiceUsedId=" + invoiceUsedId + "&" + token.getTokenParamString());
        }
    };

</script>
