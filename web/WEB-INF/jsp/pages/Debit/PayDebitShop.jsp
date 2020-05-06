<%-- 
    Document   : PayDebit
    Created on : Feb 18, 2009, 11:43:14 AM
    Author     : tuannv
--%> 

<%--
    Note: thu tiền đặt cọc tại cửa hàng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
            if (request.getAttribute("listDepositType") == null) {
                request.setAttribute("listDepositType", request.getSession().getAttribute("listDepositType"));
            }
            if (request.getAttribute("listPayDebitShop") == null) {
                request.setAttribute("listPayDebitShop", request.getSession().getAttribute("listPayDebitShop"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="PayDebitShopAction.do" id="payDebitShopForm" method="post" theme="simple">
<s:token/>

    <tags:imPanel title="MSG.find.trans.debit">
        <table class="inputTbl6Col">

            <tr>
                <td style="width:10%; "><tags:label key="MSG.SAE.185"/></td>
                <td style="width:30%; " class="oddColumn" colspan="5">
                    <tags:imSearch codeVariable="payDebitShopForm.shopCodeSearch" nameVariable="payDebitShopForm.shopNameSearch"
                                   codeLabel="MSG.SAE.185" nameLabel="MSG.SAE.186"
                                   searchClassName="com.viettel.im.database.DAO.InvoiceManagementDAO"
                                   searchMethodName="getListShop"
                                   elementNeedClearContent="form.staffCodeSearch;form.staffNameSearch"/>
                </td>
            </tr>
            <tr>
                <td style="width:10%; "><tags:label key="MSG.SAE.111"/></td>
                <td style="width:30%; " class="oddColumn" colspan="5">
                    <tags:imSearch codeVariable="payDebitShopForm.staffCodeSearch" nameVariable="payDebitShopForm.staffNameSearch"
                                   codeLabel="MSG.SAE.111" nameLabel="MSG.SAE.112"
                                   searchClassName="com.viettel.im.database.DAO.InvoiceManagementDAO"
                                   searchMethodName="getListStaff"
                                   otherParam="payDebitShopForm.shopCodeSearch"/>
                </td>
            </tr>

            <tr>
                <td> <tags:label key="MSG.cusName"/></td>
                <td>
                    <s:textfield name="payDebitShopForm.custNameS" id="custNameS" maxlength="250" cssClass="txtInput"/>
                </td>
                <td><tags:label key="MSG.isdn"/></td>
                <td>
                    <s:textfield name="payDebitShopForm.isdnB" id="isdnB" maxlength="20" cssClass="txtInput"/>
                </td>
                <td><tags:label key="MSG.deposit.type"/></td>
                <td>
                    <tags:imSelect name="payDebitShopForm.depositTypeIdB"
                                   id="depositTypeIdB"
                                   cssClass="txtInput"
                                   headerKey="" headerValue="MSG.SIK.261"
                                   list="listDepositType"
                                   listKey="depositTypeId" listValue="name"/>

                    <%--<s:select name="payDebitShopForm.depositTypeIdB"
                              id="depositTypeIdB"
                              cssClass="txtInput"
                              headerKey="" headerValue="--Loại đặt cọc--"
                              list="%{#session.listDepositType != null? #session.listDepositType :#{}}"
                              listKey="depositTypeId" listValue="name"/>--%>
                </td>
            </tr>
            <tr>
                <td><tags:label key="MSG.transaction.date.from"/></td>
                <td>
                    <tags:dateChooser  property="payDebitShopForm.fromDateB" id="fromDateB" readOnly="false" styleClass="txtInputFull"/>
                </td>
                <td><tags:label key="MSG.transaction.date.to"/></td>
                <td>
                    <tags:dateChooser  property="payDebitShopForm.toDateB" id="toDateB" readOnly="false" styleClass="txtInputFull"/>
                </td>
                <td><tags:label key="MSG.status"/></td>
                <td>
                    <tags:imSelect name="payDebitShopForm.statusS" id="status"
                                   cssClass="txtInput" disabled="false"
                                   list="0:MSG.GOD.190, 1: MSG.GOD.191 "
                                   listKey="abc" listValue=""
                                   headerKey="" headerValue="MSG.DET.007"/>

                    <%--<s:select name="payDebitShopForm.statusS"
                              id="status"
                              cssClass="txtInput"
                              headerKey="" headerValue="--Chọn trạng thái--"
                              list="#{'0':'Chưa lập phiếu','1':'Đã lập phiếu'}"/>--%>
                </td>
            </tr>
            <tr>
                <td colspan="6">
                    <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key" />
                </td>
            </tr>
        </table>
        <div align="center">
            <sx:submit parseContent="true" executeScripts="true"
                       formId="payDebitShopForm" loadingText="Processing..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:85px;"
                       key="MSG.find"  beforeNotifyTopics="PayDebitShopAction/searchPayDebitShop"/>

            <%--<tags:submit targets="bodyContent" formId="payDebitShopForm" validateFunction="validateData();"
                         cssStyle="width:85px;" value="MSG.find"
                         showLoadingText="true"
                         preAction="partnerAction!addNewPartner.do"
                         />--%>

        </div>
    </tags:imPanel>
    <s:if test="#session.listPayDebitShop != null && #session.listPayDebitShop.size() > 0">
        <div id="listPayDebitShop">
            <tags:imPanel title="MSG.list.trans.debit">
                <jsp:include page="listPayDebitShop.jsp"/>
            </tags:imPanel>
        </div>
    </s:if>
    <div id="detailArea">
        <s:if test="payDebitShopForm.depositId != null">
            <jsp:include page="createReceiptExpense.jsp"/>
        </s:if>
    </div>        
</s:form>

<script type="text/javascript">
    var _myWidget2 = $('custNameS');
    if (_myWidget2 != null)
        _myWidget2.focus();

    //lang nghe, xu ly su kien khi click nut "Tim kiem"
    dojo.event.topic.subscribe("PayDebitShopAction/searchPayDebitShop", function(event, widget){
    <%--if($( 'isdnB' ).value.trim() == "" ){
        $( 'displayResultMsgClient' ).innerHTML='Bạn chưa nhập số thuê bao';
        event.cancel = true;
        return;
    }--%>
    <%--var status = isMoneyFormat($( 'isdnB' ).value.trim());
     if(!status){
         $( 'displayResultMsgClient' ).innerHTML = "Số thuê bao không hợp lệ";
         $( 'isdnB' ).select();
         event.cancel = true;
         return;
     }--%>

             var tmp = $('isdnB');
             if (tmp != null){
                 if (tmp.value != null && tmp.value.trim().length >0){
                     if (!isNumberFormat(tmp.value.trim())){
                         $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.014"/>';
                         //$( 'displayResultMsgClient' ).innerHTML='!!! Số ISDN không đúng định dạng';
                         tmp.focus();
                         event.cancel = true;
                         return;
                     }
                 }
             }

             var checkDate = true;
             var dateExported= dojo.widget.byId("fromDateB");
             if(dateExported.domNode.childNodes[1].value.trim() != "" &&
                 !isDateFormat(dateExported.domNode.childNodes[1].value)){
                 $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.004"/>';
                 //$( 'displayResultMsgClient' ).innerHTML='Ngày nộp từ không hợp lệ';
                 dateExported.domNode.childNodes[1].focus();
                 event.cancel = true;
                 return;
             }
             if (dateExported.domNode.childNodes[1].value.trim() == "")
                 checkDate = false;
             var dateExported1 = dojo.widget.byId("toDateB");
             if(dateExported1.domNode.childNodes[1].value.trim() != "" &&
                 !isDateFormat(dateExported1.domNode.childNodes[1].value)){
                 $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.005"/>';
                 //$( 'displayResultMsgClient' ).innerHTML='Ngày nộp đến không hợp lệ';
                 dateExported1.domNode.childNodes[1].focus();
                 event.cancel = true;
                 return;
             }
             if (dateExported1.domNode.childNodes[1].value.trim() == "")
                 checkDate = false;

             if (checkDate)
                 if(!isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
                     $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.015"/>';
                     //$( 'displayResultMsgClient' ).innerHTML='Ngày GD từ phải nhỏ hơn Ngày GD đến';
                     dateExported.domNode.childNodes[1].focus();
                     dateExported.domNode.childNodes[1].select();
                     event.cancel = true;
                     return;
                 }
             // TuTM1 04/03/2012 Fix ATTT CSRF
             widget.href = "PayDebitShopAction!searchPayDebitShop.do?" + token.getTokenParamString();
             //event: set event.cancel = true, to cancel request
         });

         //lang nghe, xu ly su kien khi click nut "Bỏ qua"
         dojo.event.topic.subscribe("PayDebitShopAction/resetPayDebitShop", function(event, widget){
             document.getElementById('isdnB').value="";
             document.getElementById('depositTypeIdB').value="";
             document.getElementById('fromDateB').value="";
             document.getElementById('toDateB').value="";
             event.cancel = true;
             //event: set event.cancel = true, to cancel request
         });



         //lang nghe, xu ly su kien khi click nut "Lập phiếu thu"
         dojo.event.topic.subscribe("PayDebitShopAction/addDocDepositShop", function(event, widget){
             if(document.getElementById('depositId').value == ""){
                 $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.016"/>';
                 //$( 'displayResultMsgClientB' ).innerHTML = "Bạn chưa chọn giao dịch đặt cọc";
                 event.cancel = true;
                 return;
             }
             if(document.getElementById('receiptNo').value.trim() == ""){
                 $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.016"/>';
                 //$( 'displayResultMsgClientB' ).innerHTML = "Bạn chưa điền mã phiếu thu";
                 event.cancel = true;
                 return;
             }
             if(isHtmlTagFormat($( 'receiptNo' ).value.trim())){
                 $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.017"/>';
                 //$( 'displayResultMsgClientB' ).innerHTML = "Không nên nhập thẻ html vào mã phiếu thu";
                 $( 'receiptNo' ).select();
                 event.cancel = true;
                 return;
             }
             if(document.getElementById('payMethod').value == ""){
                 $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.018"/>';
                 //$( 'displayResultMsgClientB' ).innerHTML = "Bạn chưa chọn phương thức thanh toán";
                 event.cancel = true;
                 return;
             }
             // TuTM1 04/03/2012 Fix ATTT CSRF
             widget.href = "PayDebitShopAction!addDocDepositShop.do?" + token.getTokenParamString();
             //event: set event.cancel = true, to cancel request
         });

         validateData = function(){
             if(document.getElementById('depositId').value == ""){
                 $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.016"/>';
                 //$( 'displayResultMsgClientB' ).innerHTML = "Bạn chưa chọn giao dịch đặt cọc";
                 return false;
             }
             if(document.getElementById('receiptNo').value == ""){
                 $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.019"/>';
                 //$( 'displayResultMsgClientB' ).innerHTML = "Bạn chưa điền mã phiếu thu";
                 return false;
             }
             if(isHtmlTagFormat($( 'receiptNo' ).value.trim())){
                 $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.017"/>';
                 //$( 'displayResultMsgClientB' ).innerHTML = "Không nên nhập thẻ html vào mã phiếu thu";
                 $( 'receiptNo' ).select();
                 return false;
             }
             if(document.getElementById('payMethod').value == ""){
                 $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.018"/>';
                 //$( 'displayResultMsgClientB' ).innerHTML = "Bạn chưa chọn phương thức thanh toán";
                 return false;
             }
             return true;
         }


         dojo.event.topic.subscribe("PayDebitShopAction/printDocDepositShop", function(event, widget){
            // TuTM1 04/03/2012 Fix ATTT CSRF
             widget.href = "PayDebitShopAction!printDocDepositShop.do?" + token.getTokenParamString();
         });

         resetValue = function(){
             totalSum = 0;
             arrName=new Array();
         };

         //ham phuc vu viec phan trang
         pageNavigator = function (ajaxTagId, pageNavigator, pageNumber){
             // TuTM1 04/03/2012 Fix ATTT CSRF
             dojo.widget.byId("updateContent").href = 'PayDebitShopAction!pageNavigator.do?' + pageNavigator + "=" 
                 + pageNumber + "&" + token.getTokenParamString();
             dojo.event.topic.publish('updateContent');
         }

         //lang nghe, xu ly su kien khi click nut "Hủy"
         dojo.event.topic.subscribe("PayDebitShopAction/resetPayDebitShop", function(event, widget){
             document.getElementById('isdnB').value="";
             document.getElementById('depositTypeIdB').value="";
             dojo.widget.byId("fromDateB").domNode.childNodes[1].value = "";
             dojo.widget.byId("toDateB").domNode.childNodes[1].value = "";
             event.cancel = true;
             //event: set event.cancel = true, to cancel request
         });


         isCheckBoxChecked = function()
         {
             if(document.getElementById('tblListPayDebitShop') == null)
             {
                 return false;
             }
             var i = 0;
             var tbl = $( 'tblListPayDebitShop' );
             var inputs = [];
             //var chkNum = 0;
             inputs = tbl.getElementsByTagName( "input" );
             for( i = 0; i < inputs.length; i++ ) {
                 //if( inputs[i].type == "checkbox" ) chkNum++;
                 if( inputs[i].type == "checkbox" && inputs[i].checked == true ) {
                     return true;
                 }
             }
             return false;
         }

         var totalSum = 0;
         var arrName=new Array();

         controlTextbox = function(chk, amount, name)
         {
             var val = parseInt(amount);
             var i = 0;
             if( chk.checked == true ) {
                 totalSum += val;
                 arrName.push(name);
             } else {
                 for(i=0;i<arrName.length;i++ )
                 {
                     if(arrName[i]==name)
                     {
                         arrName.splice(i,1);
                         break;
                     }
                 }
                 if( totalSum - val >= 0 ) {
                     totalSum -= val;
                 }
             }
             document.getElementById('amount').value = totalSum;
             document.getElementById('name').value = arrName;
         }

         if(document.getElementById('errorType').value == '0'){
             document.getElementById('isdnB').focus();
         }
         if(document.getElementById('errorType').value == '1'){
             document.getElementById('receiptNo').focus();
         }

</script>
