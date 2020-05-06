<%--
    Document   : createInvoiceNoSale
    Created on : Aut 08, 2009, 10:33:01 AM
    Author     : TrongLV
--%>

<%--
    Note: lap hoa don khong tu giao dich
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="searchSaleTransAction" theme="simple" enctype="multipart/form-data"  method="post" id="saleForm">
<s:token/>

    <fieldset style="width:95%; padding:10px 0px 10px 10px">
        <legend>Thông tin hóa đơn</legend>
        <table class="inputTbl">
            <%-- Thong tin Hoa don --%>
            <tr>
                <td>
                    <fieldset style="width:100%">
                        <table class="inputTbl">
                            <tr>
                                <td style="width:15%; "></td>
                                <td style="width:25%; "></td>
                                <td style="width:10%; "></td>
                                <td style="width:20%; "></td>
                                <td style="width:15%; "></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td><tags:label key="MSG.unit.channel"/></td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.shopName" id="shopName" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
                                </td>
                                <td> <tags:label key="MSG.createStaff"/></td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.staffName"  id="staffName" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
                                </td>
                                <td> <tags:label key="MSG.actionDate"/></td>
                                <td>
                                    <s:textfield name="saleForm.startDate" id="startDate" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
                                </td>
                            </tr>
                            <tr>
                                <td><tags:label key="MSG.bill.range" required="true"/></td>
                                <td class="oddColumn">
                                    <s:select name="saleForm.billSerial"
                                              id="invoiceId"
                                              cssClass="txtInputFull"
                                              headerKey="" headerValue="--Chọn dải hóa đơn--"
                                              list="%{#session.invoiceSerialList != null?#session.invoiceSerialList:#{}}"
                                              listKey="serialNo" listValue="serialNo"
                                              onchange="transition('invoiceChange');"/>
                                </td>
                                <td> <tags:label key="MSG.fromBillNo"/></td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.strFromInvoice" id="fromInvoice" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
                                </td>
                                <td> <tags:label key="MSG.toBillNo"/></td>
                                <td>
                                    <s:textfield name="saleForm.strToInvoice" id="toInvoice" maxlength="1000" readonly="true" cssClass="txtInputFull" />
                                </td>
                            </tr>
                            <tr>
                                <td> <tags:label key="MSG.nowBillNo"/></td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.strCurrInvoice" id="currInvoiceNo" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
                                </td>
                                <td><tags:label key="MSG.bill.number"/></td>
                                <td colspan="3">
                                    <font color="red" size="4px"><s:label name="saleForm.InvoiceNo" id="InvoiceNo"></s:label></font>
                                    <s:hidden name="saleForm.InvoiceNo" id="hInvoiceNo"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </td>
            </tr>
            <%-- Thong tin Khach hang --%>
            <tr>
                <td>
                    <fieldset style="width:100%">
                        <table class="inputTbl">
                            <tr>
                                <td style="width:15%; "></td>
                                <td style="width:25%; "></td>
                                <td style="width:10%; "></td>
                                <td style="width:20%; "></td>
                                <td style="width:15%; "></td>
                                <td></td>
                            </tr>                           
                            <tr>
                                <td><tags:label key="MSG.customer.name" required="true"/></td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.custName" id="custName" maxlength="25" cssClass="txtInputFull"/>
                                </td>
                                <td> <tags:label key="MSG.company"/></td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.company" id="company" maxlength="100" cssClass="txtInputFull"/>
                                </td>
                                <td><tags:label key="MSG.tax.code"/></td>
                                <td>
                                    <s:textfield name="saleForm.tin"  id="tin" maxlength="20" cssClass="txtInputFull"/>
                                </td>
                            </tr>
                            <tr>
                                <td> <tags:label key="MSG.account"/></td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.account" id="account" maxlength="20" cssClass="txtInputFull"/>
                                </td>
                                <td><tags:label key="MSG.address"/></td>
                                <td colspan="3">
                                    <s:textfield name="saleForm.address" id="address" maxlength="100" cssClass="txtInputFull"/>
                                </td>
                            </tr>
                        </table
                    </fieldset>
                </td>
            </tr>
            <%-- Hinh thuc thanh toan --%>
            <tr>
                <td>
                    <fieldset style="width:100%">
                        <table class="inputTbl" >
                            <tr>
                                <td style="width:15%; "></td>
                                <td style="width:25%; "></td>
                                <td style="width:10%; "></td>
                                <td style="width:20%; "></td>
                                <td style="width:15%; "></td>
                                <td></td>
                            </tr>
                            <tr>                                
                                <td> <tags:label key="MSG.payMethod" required="true"/></td>
                                <td class="oddColumn">
                                    <s:select name="saleForm.payMethod"
                                              id="payMethod"
                                              cssClass="txtInputFull"
                                              headerKey="" headerValue="--Chọn HTTT--"
                                              list="%{#session.listPayMethod != null?#session.listPayMethod:#{}}"
                                              listKey="code" listValue="name"/>
                                </td>
                                <td><tags:label key="MSG.reason" required="true"/></td>
                                <td colspan="5">
                                    <s:select name="saleForm.reasonId"
                                              id="reasonId"
                                              cssClass="txtInputFull"
                                              headerKey="" headerValue="--Chọn lý do--"
                                              list="%{#session.reasonList != null?#session.reasonList:#{}}"
                                              listKey="reasonId" listValue="reasonName"/>
                                </td>                                
                            </tr>
                            <tr>                                
                                <td><tags:label key="MSG.comment"/></td>
                                <td colspan="6" rowspan="2"  style="vertical-align:top; height:100%;">
                                    <s:textarea name="saleForm.note" cssStyle="height:100%; "  id="note" cssClass="txtInputFull"/>
                                </td>
                            </tr>
                            <tr><td colspan="6"></td></tr>
                            <tr><td colspan="6"></td></tr>
                            <tr><td colspan="6"></td></tr>
                            <tr><td colspan="6"></td></tr>
                            <tr>
                                <td colspan="6">
                                    <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key" />
                                </td>
                            </tr>                           
                        </table>
                    </fieldset>
                </td>
            </tr>             
        </table>
    </fieldset>
    
<div id ="invoiceDetail">
        <jsp:include page="createInvoiceNoSaleDetail.jsp"/>
</div>

<table>
    <%-- Button su kien --%>
    <tr>
        <td>
            <div align="center" style="width:100%">
                <s:if test="#session.invoiceUsedId==null">
                    <sx:submit parseContent="true" executeScripts="true"
                               cssStyle="width:85px;" id = "btnCreateInvoice"
                               formId="saleForm" targets="bodyContent"
                               key="MSG.createBills"  beforeNotifyTopics="createInvoiceNoSaleAction/createInvoice"/>
                    <input type="button" value="In hóa đơn" style="width:85px;" disabled/>
                </s:if>
                <s:else>
                    <input type="button" value="Lập hóa đơn" style="width:85px; " disabled/>
                    <sx:submit parseContent="true" executeScripts="true"
                               cssStyle="width:85px;" id = "btnPrintInvoice"
                               formId="saleForm" targets="bodyContent"
                               key="MSG.printBills" beforeNotifyTopics="createInvoiceNoSaleAction/printInvoice"/>
                </s:else>                
            </div>
            <div style="width:100%;" align="center">
                <s:if test="saleForm.exportUrl!=null && saleForm.exportUrl!=''">
                    <script>
                        window.open('${contextPath}<s:property escapeJavaScript="true"  value="saleForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
                    </script>
                    <a href='${contextPath}<s:property escapeJavaScript="true"  value="saleForm.exportUrl"/>' ><tags:label key="MSG.click.download.invoice"/></a>
                    <%--br/>
                    <a href='${contextPath}<s:property escapeJavaScript="true"  value="saleForm.exprotUrlRac"/>' >Bấm vào đây để download bản xem hóa đơn</a--%>
                </s:if>

            </div>
        </td>
    </tr>
</table>
                        
</s:form>



<script type="text/javascript" language="javascript">
    textFieldNF($('invoiceTotal'));

    dojo.event.topic.subscribe("searchSaleTransAction/addItem", function(event, widget){        
        if (!checkValidateInputItem())
            event.cancel = true;
        widget.href = "searchSaleTransAction!addItem.do";
    });
    dojo.event.topic.subscribe("createInvoiceNoSaleAction/updateItem", function(event, widget){
        if (!checkValidateInputItem()){
            event.cancel = true;
            return;
        }
        widget.href = "searchSaleTransAction!addItem.do";
    });
    dojo.event.topic.subscribe("createInvoiceNoSaleAction/cancelItem", function(event, widget){
        widget.href = "searchSaleTransAction!cancelItem.do";
    });
    dojo.event.topic.subscribe("createInvoiceNoSaleAction/createInvoice", function(event, widget){        
        if (!checkValidateCreateInvoice()){
            event.cancel = true;
            return;
        }
        widget.href = "searchSaleTransAction!createInvoiceNoSale.do";
    });
    dojo.event.topic.subscribe("createInvoiceNoSaleAction/printInvoice", function(event, widget){
        widget.href = "searchSaleTransAction!printInvoiceNoSale.do";
    });
    
    checkValidateInputItem = function(){
        $( 'displayResultMsgClient' ).innerHTML = "";
        if($( 'itemName' ).value.trim() == ""){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.SAE.048"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Chưa nhập tên mặt hàng";
            $( 'itemName' ).select();
            return false;
        }
        if($( 'itemPriceString' ).value.trim() == ""){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.SAE.049"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Chưa nhập đơn giá";
            $( 'itemPriceString' ).select();
            return false;
        }
        if($( 'itemPriceString' ).value.trim() != "" && !isMoneyFormat($( 'itemPriceString' ).value)){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.SAE.050"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Đơn giá không hợp lệ";
            $( 'itemPriceString' ).select();
            return false;
        }
        if($( 'itemQtyString' ).value.trim() == ""){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.SAE.051"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Chưa nhập số lượng";
            $( 'itemQtyString' ).select();
            return false;
        }
        if($( 'itemQtyString' ).value.trim() != "" && !isMoneyFormat($( 'itemQtyString' ).value)){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.SAE.052"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Số lượng không hợp lệ";
            $( 'itemQtyString' ).select();
            return false;
        }        
        return true;
    }

    checkValidateCreateInvoice = function(){
        if($( 'invoiceId' ).value == "" ){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.SAE.053"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa chọn dải hóa đơn";
            $( 'invoiceId' ).focus();
            return false;
        }
        if($( 'custName' ).value.trim() == "" ){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.SAE.040"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa nhập tên khách hàng";
            $( 'custName' ).select();
            return false;
        }                        
        if($( 'payMethod' ).value.trim() == "" ){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.SAE.054"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa chọn hình thức thanh toán";
            $( 'payMethod' ).focus();
            return false;
        }
        if($( 'reasonId' ).value.trim() == "" ){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.SAE.055"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa chọn lí do";
            $( 'reasonId' ).focus();
            return false;
        }

        return true;
    }
    
    transition = function (data){
        var temp = "";
        temp = document.getElementById("invoiceId").value;
        if (temp == null || temp.trim().length<=0){
            document.getElementById("fromInvoice").value = "";
            document.getElementById("toInvoice").value = "";
            document.getElementById("currInvoiceNo").value = "";
            $( 'InvoiceNo' ).innerHTML = "";
            $( 'hInvoiceNo' ).innerHTML = "";
            return false;
        }
        
    temp = "";
    if (document.getElementById("shopName") != null)
        temp += "&shopName=" + document.getElementById("shopName").value;
    if (document.getElementById("staffName") != null)
        temp += "&staffName=" + document.getElementById("staffName").value;
    if (document.getElementById("startDate") != null)
        temp += "&startDate=" + document.getElementById("startDate").value;

    if (document.getElementById("custName") != null)
        temp += "&custName=" + document.getElementById("custName").value;
    if (document.getElementById("company") != null)
        temp += "&company=" + document.getElementById("company").value;
    if (document.getElementById("address") != null)
        temp += "&address=" + document.getElementById("address").value;
    if (document.getElementById("tin") != null)
        temp += "&tin=" + document.getElementById("tin").value;
    if (document.getElementById("account") != null)
        temp += "&account=" + document.getElementById("account").value;

    if (document.getElementById("payMethod") != null)
        temp += "&payMethod=" + document.getElementById("payMethod").value;
    if (document.getElementById("reasonId") != null)
        temp += "&reasonId=" + document.getElementById("reasonId").value;
    if (document.getElementById("note") != null)
        temp += "&note=" + document.getElementById("note").value;

    if (document.getElementById("itemId") != null)
        temp += "&itemId=" + document.getElementById("itemId").value;
    if (document.getElementById("itemCode") != null)
        temp += "&itemCode=" + document.getElementById("itemCode").value;
    if (document.getElementById("itemName") != null)
        temp += "&itemName=" + document.getElementById("itemName").value;
    if (document.getElementById("itemUnit") != null)
        temp += "&itemUnit=" + document.getElementById("itemUnit").value;
    if (document.getElementById("itemPriceString") != null)
        temp += "&itemPriceString=" + document.getElementById("itemPriceString").value;
    if (document.getElementById("itemQtyString") != null)
        temp += "&itemQtyString=" + document.getElementById("itemQtyString").value;
    if (document.getElementById("itemNote") != null)
        temp += "&itemNote=" + document.getElementById("itemNote").value;

    if (document.getElementById("invoiceTotal") != null)
        temp += "&invoiceTotal=" + document.getElementById("invoiceTotal").value;

        <%--if (data == "agentChange")
            gotoAction("bodyContent", 'searchSaleTransAction!changeAgent.do?agentId=' + document.getElementById("agentId").value + temp);+ temp14 + temp15;--%>
        
        gotoAction("bodyContent", 'searchSaleTransAction!getInvoiceNumber.do?invoiceSerial=' + document.getElementById("invoiceId").value + temp);

    }
</script>
