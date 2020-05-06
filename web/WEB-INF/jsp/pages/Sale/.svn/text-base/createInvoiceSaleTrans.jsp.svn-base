<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : invoiceToWholeSale
    Created on : Apr 7, 2009, 10:33:01 AM
    Author     : Doan Thanh 8
--%>

<%--
    Note: lap hoa don cho dai ly
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
                                <td>Đơn vị</td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.shopName" id="shopName" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
                                </td>
                                <td>Người lập</td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.staffName"  id="staffName" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
                                </td>
                                <td>Ngày lập</td>
                                <td>
                                    <s:textfield name="saleForm.startDate" id="startDate" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Dải hóa đơn(<font color="red">*</font>)</td>
                                <td class="oddColumn">
                                    <s:select name="saleForm.billSerial"
                                              id="invoiceId"
                                              cssClass="txtInputFull"
                                              headerKey="" headerValue="--Chọn dải hóa đơn--"
                                              list="%{#session.invoiceSerialList != null?#session.invoiceSerialList:#{}}"
                                              listKey="serialNo" listValue="serialNo"
                                              onchange="transition('invoiceChange');"/>
                                </td>
                                <td>Từ số hóa đơn</td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.strFromInvoice" id="fromInvoice" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
                                </td>
                                <td>Đến số hóa đơn</td>
                                <td>
                                    <s:textfield name="saleForm.strToInvoice" id="toInvoice" maxlength="1000" readonly="true" cssClass="txtInputFull" />
                                </td>
                            </tr>
                            <tr>
                                <td>Số HĐ hiện tại</td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.strCurrInvoice" id="currInvoiceNo" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
                                </td>
                                <td>Số hóa đơn</td>
                                <td colspan="3">
                                    <font color="red" size="4px"><s:label name="saleForm.InvoiceNo" id="InvoiceNo" ></s:label></font>
                                    <s:hidden name="saleForm.InvoiceNo"/>
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
                            <s:if test="#session.saleTransInvoice.interfaceType == 1">
                                <tr>
                                    <td>Đại lý</td>
                                    <td class="oddColumn">
                                        <s:textfield name="saleForm.agentName" id="agentName" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
                                    </td>
                                    <td>Mã số thuế</td>
                                    <td class="oddColumn">
                                        <s:textfield name="saleForm.tin" id="tin" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Tài khoản</td>
                                    <td class="oddColumn">
                                        <s:textfield name="saleForm.agentAccount" id="account" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
                                    </td>
                                    <td>Địa chỉ </td>
                                    <td  colspan="3">
                                        <s:textfield name="saleForm.address"  id="address" readonly="true"  maxlength="1000" cssClass="txtInputFull"/>
                                    </td>
                                </tr>
                            </s:if>
                            <s:else>
                                <tr>
                                    <td>Tên khách hàng</td>
                                    <td class="oddColumn">
                                        <s:textfield name="saleForm.custName" id="custName" maxlength="1000" cssClass="txtInputFull"/>
                                    </td>
                                    <td>Công ty</td>
                                    <td class="oddColumn">
                                        <s:textfield name="saleForm.company" id="company" maxlength="1000" cssClass="txtInputFull"/>
                                    </td>
                                    <td>Mã số thuế</td>
                                    <td>
                                        <s:textfield name="saleForm.tin"  id="tin" maxlength="1000" cssClass="txtInputFull"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Tài khoản</td>
                                    <td class="oddColumn">
                                        <s:textfield name="saleForm.account" id="account" maxlength="1000" cssClass="txtInputFull"/>
                                    </td>
                                    <td>Địa chỉ</td>
                                    <td colspan="3">
                                        <s:textfield name="saleForm.address" id="address" maxlength="1000" cssClass="txtInputFull"/>
                                    </td>
                                </tr>
                            </s:else>
                        </table>
                    </fieldset>
                </td>
            </tr>
            <%-- Thong tin Tong tien --%>
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
                                <td>Tiền chưa thuế</td>
                                <td class="oddColumn">
                                    <s:if test="#session.saleTransInvoice.interfaceType != 4">
                                        <s:textfield name="saleForm.amount" readonly="true" id="amount" maxlength="1000" cssClass="txtInputFull" cssStyle="text-align:right;"/>
                                    </s:if>
                                    <s:else>
                                        <s:textfield name="saleForm.amount" id="amount" onkeyup="textFieldNF(this)" maxlength="1000" cssClass="txtInputFull"  cssStyle="text-align:right;"/>
                                    </s:else>
                                </td>
                                <td>HTTT (<font color="red">*</font>)</td>
                                <td class="oddColumn">
                                    <s:select name="saleForm.payMethod"
                                              id="payMethod"
                                              cssClass="txtInputFull"
                                              headerKey="" headerValue="--Chọn HTTT--"
                                              list="%{#session.listPayMethod != null?#session.listPayMethod:#{}}"
                                              listKey="code" listValue="name"/>
                                </td>
                                <td>Lý do lập HĐ (<font color="red">*</font>)</td>
                                <td >
                                    <s:select name="saleForm.reasonId"
                                              id="reasonId"
                                              cssClass="txtInputFull"
                                              headerKey="" headerValue="--Chọn lý do--"
                                              list="%{#session.lstReasonInvoice != null?#session.lstReasonInvoice:#{}}"
                                              listKey="reasonId" listValue="reasonName"/>
                                </td>
                               
                            </tr>
                            <tr>
                                <td>Tiền thuế</td>
                                <td class="oddColumn">
                                    <s:if test="#session.saleTransInvoice.interfaceType != 4">
                                        <s:textfield name="saleForm.tax" readonly="true" id="tax" maxlength="1000" cssClass="txtInputFull" cssStyle="text-align:right;"/>
                                    </s:if>
                                    <s:else>
                                        <s:textfield name="saleForm.tax" id="tax" onkeyup="textFieldNF(this)" maxlength="1000" cssClass="txtInputFull" cssStyle="text-align:right;"/>
                                    </s:else>
                                </td>
                                <td>Ghi chú</td>
                                <td colspan="3" rowspan="4"  style="vertical-align:top; height:100%;">
                                    <s:textarea name="saleForm.note" cssStyle="height:100%; "  id="note" cssClass="txtInputFull"/>
                                </td>
                               
                            </tr>
                            <tr>
                                <td>Chiết khấu</td>
                                <td class="oddColumn">
                                    <s:if test="#session.saleTransInvoice.interfaceType != 4">
                                        <s:textfield name="saleForm.discount" readonly="true" id="discount" maxlength="1000" cssClass="txtInputFull"  cssStyle="text-align:right;"/>
                                    </s:if>
                                    <s:else>
                                        <s:textfield name="saleForm.discount" readonly="false" id="discount" onkeyup="textFieldNF(this)" maxlength="1000" cssClass="txtInputFull"  cssStyle="text-align:right;"/>
                                    </s:else>
                                </td>
                                <td></td>
                                
                            </tr>
                            <tr>
                                <td>Khuyến mãi</td>
                                <td class="oddColumn">
                                    <s:textfield  name="saleForm.promotion" readonly="true" id="promotion" maxlength="1000" cssClass="txtInputFull"  cssStyle="text-align:right;"/>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>Tổng tiền phải trả</td>
                                <td class="oddColumn">
                                    <s:textfield  name="saleForm.finalAmount" readonly="true" id="finalAmount" maxlength="1000" cssClass="txtInputFull"  cssStyle="text-align:right;"/>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td colspan="6">
                                    <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key" />
                                </td>
                            </tr>

                        </table>
                    </fieldset>
                </td>
            </tr>
            <%-- Button su kien --%>
            <tr>
                <td>
                    <div align="center" style="width:100%">
                        <s:if test="#session.invoiceUsedId==null">
                            <sx:submit parseContent="true" executeScripts="true"
                                       cssStyle="width:85px;" id = "btnCreateInvoice"
                                       formId="saleForm" targets="bodyContent"
                                       value="Lập hóa đơn"  beforeNotifyTopics="saleForm/createInvoice"/>
                            <input type="button" value="In hóa đơn" style="width:85px;" disabled/>
                        </s:if>
                        <s:else>
                            <input type="button" value="Lập hóa đơn" style="width:85px; " disabled/>
                            <sx:submit parseContent="true" executeScripts="true"
                                       cssStyle="width:85px;" id = "btnPrintInvoice"
                                       formId="saleForm" targets="bodyContent"
                                       value="In hóa đơn"  beforeNotifyTopics="saleForm/printInvoice"/>
                        </s:else>
                        <s:if test="#session.saleTransInvoice.interfaceType != 4">
                            <sx:submit parseContent="true" executeScripts="true"
                                       cssStyle="width:80px;"
                                       formId="saleForm" targets="bodyContent"
                                       value="Quay lại"  beforeNotifyTopics="saleForm/back"/>
                        </s:if>
                    </div>
                    <div style="width:100%;" align="center"> 
                        <s:if test="saleForm.exportUrl!=null && saleForm.exportUrl!=''">
                            <script>
                                window.open('${contextPath}<s:property escapeJavaScript="true"  value="saleForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
                            </script>
                            <a href='${contextPath}<s:property escapeJavaScript="true"  value="saleForm.exportUrl"/>' >Bấm vào đây để download bản in hóa đơn</a>
                        </s:if>

                    </div>
                </td>
            </tr>
        </table>
    </fieldset>
    <br/>
    <%
        request.setAttribute("saleTransSelectedList", request.getSession().getAttribute("saleTransSelectedList"));
    %>
    <s:if test="#session.saleTransInvoice.interfaceType != 4">
        <fieldset style="width:95%; padding:10px 10px 10px 10px">
            <legend class="transparent">Danh sách các giao dịch đã chọn</legend>
            <sx:div id="saleTransSelectedList">
                <s:if test="#session.saleTransSelectedList != null && #session.saleTransSelectedList.size() != 0">
                    <display:table id="saleTrans" name="saleTransSelectedList" pagesize="10" class="dataTable" cellpadding="1" cellspacing="1" >
                        <display:column escapeXml="true" title="STT" sortable="false" style="width:40px; text-align:center" headerClass="tct">
                            ${fn:escapeXml(saleTrans_rowNum)}
                        </display:column>
                        <display:column escapeXml="true" property="saleTransCode" title="Mã GD" sortable="false" style="text-align:left;" headerClass="tct"/>
                        <display:column escapeXml="true" property="custName" title="Tên KH" sortable="false" style="text-align:left;" headerClass="tct"/>
                        <display:column property="saleTransDate" title="Ngày GD" sortable="false" style="text-align:center;" headerClass="tct" format="{0,date,dd/MM/yyyy HH:mm:ss}" />
                        <display:column escapeXml="true" property="typeName" title="Hình thức bán" sortable="false" style="text-align:left;" headerClass="tct"></display:column>
                        <display:column escapeXml="true" property="ISDN" title="ISDN" sortable="false" style="text-align:left;" headerClass="tct"/>
                        <display:column escapeXml="true" property="payMethodName" title="HTTT" sortable="false" style="text-align:left;" headerClass="tct"/>
                        <display:column escapeXml="true" property="reasonName" title="Lý do bán" sortable="false" style="text-align:left;" headerClass="tct"/>
                        <display:column escapeXml="true" property="staffName" title="Người tạo GD" sortable="false" style="text-align:left;" headerClass="tct"/>
                        <s:if test="#session.saleTransInvoice.interfaceType == 1">
                            <display:column escapeXml="true" property="stockOwnerName" title="Đại lý" sortable="false" style="text-align:center;" headerClass="tct"/>
                        </s:if>
                        <s:elseif test="#session.saleTransInvoice.interfaceType != null && #session.saleTransInvoice.interfaceType == 2">
                            <display:column escapeXml="true" property="stockOwnerName" title="Cộng tác viên" sortable="false" style="text-align:right;" headerClass="tct"/>
                        </s:elseif>
                        <display:column escapeXml="true" property="transStatusName"  title="Trạng thái GD" sortable="false" style="text-align:left;" headerClass="tct"></display:column>
                        <display:column property="discount" title="Chiết khấu" sortable="false" style="text-align:right;" headerClass="tct" format="{0,number,#,###}"/>
                        <display:column property="amountTax" title="Tổng tiền" sortable="false" style="text-align:right;" headerClass="tct" format="{0,number,#,###}"/>
                        <display:column title="Chi tiết" sortable="false" style="text-align:center; " headerClass="tct">
                            <s:url action="searchSaleTransAction!gotoSaleTransDetail" id="URL">
                                <s:param name="saleTransDetailId" value="#attr.saleTrans.saleTransId"/>
                            </s:url>
                            <sx:a targets="invoiceSaleTransDetail" href="%{#URL}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                                <img src="${contextPath}/share/img/accept.png" title="Xem hàng hóa trong giao dịch" alt="Chi tiết"/>
                            </sx:a>
                        </display:column>                    
                    </display:table>                    
                </s:if>
            </sx:div>
            <s:else>
                <font color='red'>
                    Không có giao dịch nào được chọn
                </font>
            </s:else>
        </fieldset>
    </s:if>
    <br>
    <sx:div id = "invoiceSaleTransDetail" theme="simple">
        <s:if test="#session.saleTransDetailList != null && #session.saleTransDetailList.size() != 0">
            <jsp:include page="invoiceSaleTransDetail.jsp"/>
        </s:if>
    </sx:div>

</s:form>

    
<script type="text/javascript" language="javascript">
    textFieldNF($('finalAmount'));
    textFieldNF($('amount'));
    textFieldNF($('discount'));
    textFieldNF($('tax'));
    textFieldNF($('promotion'));
    dojo.event.topic.subscribe("saleForm/search", function(event, widget){
        widget.href = "searchSaleTransAction!searchSaleTrans.do";
    });
    dojo.event.topic.subscribe("saleForm/createInvoice", function(event, widget){
        if (!checkValidateCreateInvoice())
            event.cancel = true;
        widget.href = "searchSaleTransAction!createSaleTransInvoice.do";
    });
    dojo.event.topic.subscribe("saleForm/printInvoice", function(event, widget){
        widget.href = "searchSaleTransAction!printSaleTransInvoice.do";
    });

    checkValidateInput = function(){
        if($( 'amount' ).value.trim() != "" && !isMoneyFormat($( 'amount' ).value)){
            $( 'displayResultMsgClient' ).innerHTML = "Giá trị tiền chưa thuế không hợp lệ";
            $( 'amount' ).select();
            return false;
        }
        if($( 'tax' ).value.trim() != "" && !isMoneyFormat($( 'tax' ).value)){
            $( 'displayResultMsgClient' ).innerHTML = "Giá trị thuế không hợp lệ";
            $( 'tax' ).select();
            return false;
        }
        if($( 'discount' ) != null && $( 'discount' ).value.trim() != "" && !isMoneyFormat($( 'discount' ).value)){
            $( 'displayResultMsgClient' ).innerHTML = "Giá trị triết khấu không hợp lệ";
            $( 'discount' ).select();
            return false;
        }
        return true;
    }
    
    checkValidateCreateInvoice = function(){
        var interfaceType = <s:property escapeJavaScript="true"  value="#session.saleTransInvoice.interfaceType"/>;
        if( interfaceType == 4){

            if($( 'custName' ).value.trim() == "" ){
                $( 'displayResultMsgClient' ).innerHTML = "Bạn chưa nhập tên khách hàng";
                $( 'custName' ).select();
                return false;
            }
            if($( 'company' ).value.trim() == "" ){
                $( 'displayResultMsgClient' ).innerHTML = "Bạn chưa nhập tên công ty";
                $( 'company' ).select();
                return false;
            }
            if($( 'address' ).value.trim() == "" ){
                $( 'displayResultMsgClient' ).innerHTML = "Bạn chưa nhập tên địa chỉ";
                $( 'address' ).select();
                return false;
            }
            if($( 'tin' ).value.trim() == "" ){
                $( 'displayResultMsgClient' ).innerHTML = "Bạn chưa nhập mã số thuế";
                $( 'tin' ).select();
                return false;
            }

            if($( 'account' ).value.trim() == "" ){
                $( 'displayResultMsgClient' ).innerHTML = "Bạn chưa nhập tài khoản";
                $( 'account' ).select();
                return false;
            }
            if($( 'amount' ).value.trim() == "" ){
                $( 'displayResultMsgClient' ).innerHTML = "Bạn chưa nhập tiền chưa thuế";
                $( 'amount' ).select();
                return false;
            }
            if($( 'tax' ).value.trim() == "" ){
                $( 'displayResultMsgClient' ).innerHTML = "Bạn chưa nhập thuế";
                $( 'tax' ).select();
                return false;
            }
            if($( 'amount' ).value.trim() != "" && !isMoneyFormat($( 'amount' ).value)){
                $( 'displayResultMsgClient' ).innerHTML = "Giá trị tiền chưa thuế không hợp lệ";
                $( 'amount' ).select();
                return false;
            }
            if($( 'tax' ).value.trim() != "" && !isMoneyFormat($( 'tax' ).value)){
                $( 'displayResultMsgClient' ).innerHTML = "Giá trị thuế không hợp lệ";
                $( 'tax' ).select();
                return false;
            }
            if($( 'discount' ).value.trim() != "" && !isMoneyFormat($( 'discount' ).value)){
                $( 'displayResultMsgClient' ).innerHTML = "Giá trị triết khấu không hợp lệ";
                $( 'discount' ).select();
                return false;
            }

        }
        if($( 'invoiceId' ).value == "" ){
            $( 'displayResultMsgClient' ).innerHTML = "Bạn chưa chọn dải hóa đơn";
            $( 'invoiceId' ).focus();
            return false;
        }
        if($( 'payMethod' ).value.trim() == "" ){
            $( 'displayResultMsgClient' ).innerHTML = "Bạn chưa chọn hình thức thanh toán";
            $( 'payMethod' ).focus();
            return false;
        }
        if($( 'reasonId' ).value.trim() == "" ){
            $( 'displayResultMsgClient' ).innerHTML = "Bạn chưa chọn lí do";
            $( 'reasonId' ).focus();
            return false;
        }
        
        return true;
    }

    transition = function (data){
        if(!checkValidateInput()){
            return;
        }
        var interfaceType = <s:property escapeJavaScript="true"  value="#session.saleTransInvoice.interfaceType"/>;
        var temp1 = "&saleForm.payMethod=" + document.getElementById("payMethod").value;
        var temp2 = "&saleForm.reasonId=" + document.getElementById("reasonId").value;
        var temp3 = "&saleForm.amount=" + document.getElementById("amount").value;
        var temp4 = "&saleForm.tax=" + document.getElementById("tax").value;
        var temp5 = "&saleForm.note=" + document.getElementById("note").value;
        var temp16 = "&saleForm.shopName=" + document.getElementById("shopName").value;
        var temp17 = "&saleForm.staffName=" + document.getElementById("staffName").value;
        var temp18 = "&saleForm.startDate=" + document.getElementById("startDate").value;


        var temp = temp1 + temp2 + temp3 + temp4 + temp5 +  temp16 + temp17 + temp18;

        if (document.getElementById("finalAmount") != null) {
            var temp19 = "&saleForm.finalAmount=" + document.getElementById("finalAmount").value;
            temp = temp + temp19;
        }
        if (document.getElementById("custName") != null) {
            var temp6 = "&saleForm.custName=" + document.getElementById("custName").value;
            temp = temp + temp6;
        }
        if (document.getElementById("company") != null) {
            var temp7 = "&saleForm.company=" + document.getElementById("company").value;
            temp = temp + temp7;
        }
        if (document.getElementById("address") != null) {
            var temp8 = "&saleForm.address=" + document.getElementById("address").value;
            temp = temp + temp8;
        }
        if (document.getElementById("tin") != null) {
            var temp9 = "&saleForm.tin=" + document.getElementById("tin").value;
            temp = temp + temp9;
        }
        if (document.getElementById("account") != null) {
            var temp10 = "&saleForm.account=" + document.getElementById("account").value;
            temp = temp + temp10;
        }
        if (document.getElementById("discount") != null) {
            var temp11 = "&saleForm.discount=" + document.getElementById("discount").value;
            temp = temp + temp11;
        }
        if (document.getElementById("promotion") != null) {
            var temp12 = "&saleForm.promotion=" + document.getElementById("promotion").value;
            temp = temp + temp12;
        }
        if (document.getElementById("agentName") != null) {
            var temp16 = "&saleForm.agentName=" + document.getElementById("agentName").value;
            temp = temp + temp16;
        }

        if(interfaceType != null && (interfaceType == 1 || interfaceType == 4)){
            var temp13 = "&saleForm.agentAddress=" + document.getElementById("address").value;
            var temp14 = "&saleForm.agentTin=" + document.getElementById("tin").value;
            var temp15 = "&saleForm.agentAccount=" + document.getElementById("account").value;
            temp = temp + temp13 + temp14 + temp15;
        }

        if (data == "agentChange") {
            gotoAction("bodyContent", 'searchSaleTransAction!changeAgent.do?agentId=' + document.getElementById("agentId").value + temp);
        } else if (data == "invoiceChange") {
            if(document.getElementById("agentId") != null && document.getElementById("agentId").value != ""){
                temp = temp + "&saleForm.agentId=" + document.getElementById("agentId").value;
            }
            gotoAction("bodyContent", 'searchSaleTransAction!choseInvoice.do?invoiceSerial=' + document.getElementById("invoiceId").value + temp);
        } else if (data == "invoiceType") {
            gotoAction("bodyContent", 'searchSaleTransAction!changeInvoiceType.do?invoiceType=' + document.getElementById("invoiceType").value + temp);
        }
    }
    dojo.event.topic.subscribe("saleForm/back", function(event, widget){
        widget.href = "searchSaleTransAction!back.do";
    });

    detail = function (saleTransId){
        gotoAction("invoiceSaleTransDetail",'searchSaleTransAction!gotoSaleTransDetail.do?saleTransDetailId=' + saleTransId);
    }

</script>
