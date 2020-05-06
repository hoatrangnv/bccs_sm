<%-- 
    Document   : payDepositAtShop
    Created on : Apr 8, 2009, 8:30:43 AM
    Author     : tamdt1
--%>

<%--
    Note    : chi tra tien dat coc tai cua hang
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
            
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="payDepositAtShopAction" id="payDepositAtShopForm" method="post" theme="simple">
<s:token/>

    <tags:imPanel title="MSG.search.trans.deposit">
        <table class="inputTbl6Col">
            <tr>
                <td style="width:10%; "><tags:label key="MSG.SAE.185"/></td>
                <td style="width:30%; " class="oddColumn" colspan="5">
                    <tags:imSearch codeVariable="payDepositAtShopForm.shopCodeSearch" nameVariable="payDepositAtShopForm.shopNameSearch"
                                   codeLabel="MSG.SAE.185" nameLabel="MSG.SAE.186"
                                   searchClassName="com.viettel.im.database.DAO.InvoiceManagementDAO"
                                   searchMethodName="getListShop"
                                   elementNeedClearContent="form.staffCodeSearch;form.staffNameSearch"/>
                </td>
            </tr>
            <tr>
                <td style="width:10%; "><tags:label key="MSG.SAE.111"/></td>
                <td style="width:30%; " class="oddColumn" colspan="5">
                    <tags:imSearch codeVariable="payDepositAtShopForm.staffCodeSearch" nameVariable="payDepositAtShopForm.staffNameSearch"
                                   codeLabel="MSG.SAE.111" nameLabel="MSG.SAE.112"
                                   searchClassName="com.viettel.im.database.DAO.InvoiceManagementDAO"
                                   searchMethodName="getListStaff"
                                   otherParam="payDepositAtShopForm.shopCodeSearch"/>
                </td>
            </tr>
            <tr>
                <td> <tags:label key="MSG.cusName"/></td>
                <td>
                    <s:textfield name="payDepositAtShopForm.custNameS" id="custNameS" maxlength="250" cssClass="txtInput"/>
                </td>
                <td><tags:label key="MSG.isdn"/></td>
                <td>
                    <s:textfield name="payDepositAtShopForm.isdnB" id="isdnB" maxlength="20" cssClass="txtInput"/>
                </td>
                <td><tags:label key="MSG.deposit.type"/></td>
                <td>
                    <tags:imSelect name="payDepositAtShopForm.depositTypeIdB"
                                  id="depositTypeIdB"
                                  cssClass="txtInput"
                                  headerKey="" headerValue="MSG.SIK.261"
                                  list="listDepositType"
                                  listKey="depositTypeId" listValue="name"/>

                    <%--<s:select name="payDepositAtShopForm.depositTypeIdB"
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
                    <tags:dateChooser  property="payDepositAtShopForm.fromDateB" id="fromDateB" readOnly="false" styleClass="txtInputFull"/>
                </td>
                <td><tags:label key="MSG.transaction.date.to"/></td>
                <td>
                    <tags:dateChooser  property="payDepositAtShopForm.toDateB" id="toDateB" readOnly="false" styleClass="txtInputFull"/>
                </td>
                <td><tags:label key="MSG.status"/></td>
                <td>
                    <%--<s:select name="payDepositAtShopForm.typeS"
                              id="typeS"
                              cssClass="txtInput"
                              headerKey="" headerValue="--Chọn trạng thái--"
                              list="#{'0':'Chưa lập phiếu','1':'Đã lập phiếu'}"/>--%>
                    <tags:imSelect name="payDepositAtShopForm.statusS" id="typeS"
                              cssClass="txtInput" disabled="false"
                              list="0:MSG.GOD.190, 1: MSG.GOD.191 "
                              headerKey="" headerValue="MSG.DET.007"/>

                    <%--<s:select name="payDepositAtShopForm.statusS"
                              id="typeS"
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
                       formId="payDepositAtShopForm" loadingText="Processing..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:85px;"
                       key="MSG.find"  beforeNotifyTopics="payDepositAtShopAction/searchPayDepositAtShop"/>

            <%--<tags:submit targets="bodyContent" formId="payDepositAtShopForm" validateFunction="validateData();"
                         cssStyle="width:85px;" value="MSG.find"
                         showLoadingText="true"
                         preAction="payDepositAtShopAction!searchPayDepositAtShop.do"
                         />--%>
        </div>
    </tags:imPanel>
    <s:if test="#session.listPayDepositAtShop != null && #session.listPayDepositAtShop.size() > 0">
        <div id="listPayDepositAtShop">
            <tags:imPanel title="MSG.list.trans.deposit">
                <jsp:include page="listPayDepositAtShop.jsp"/>
            </tags:imPanel>
        </div>
    </s:if>
    <div id="detailArea">
        <s:if test="payDepositAtShopForm.depositId != null">
            <s:if test="payDepositAtShopForm.depositId != 0 ">
                <jsp:include page="createReceiptExpenseAtShop.jsp"/>
            </s:if>
        </s:if>
    </div>
</s:form>

<script type="text/javascript">
    var _myWidget2 = $('custNameS');
    if (_myWidget2 != null)
        _myWidget2.focus();
</script>

<script type="text/javascript">

    validateData = function(){
        if(document.getElementById('depositId').value == ""){
            $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.016"/>';
            //$( 'displayResultMsgClientB' ).innerHTML = "Bạn chưa chọn giao dịch đặt cọc";
            return false;
        }
        if(document.getElementById('receiptNo').value.trim() == ""){
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

    dojo.event.topic.subscribe("payDepositAtShopAction/searchPayDepositAtShop", function(event, widget){

        $('displayResultMsgClient').innerHTML = "";
        var tmp = $('isdnB');
        
        if (tmp.value.trim().length <=0 ){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.RET.036"/>';
            tmp.focus();
            event.cancel = true;
            return;
        }
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
        widget.href = "payDepositAtShopAction!searchPayDepositAtShop.do" + "?" + token.getTokenParamString();
    });

    dojo.event.topic.subscribe("payDepositAtShopAction/addPayDepositAtShop", function(event, widget){
        if(isCheckBoxChecked() == false){
            $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.016"/>';
            //$( 'displayResultMsgClientB' ).innerHTML = "Bạn chưa chọn giao dịch đặt cọc";
            event.cancel = true;
            return
        }
        if(document.getElementById('receiptNo').value == ""){
            $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.031"/>';
            //$( 'displayResultMsgClientB' ).innerHTML = "Bạn chưa nhập mã phiếu chi";
            event.cancel = true;
            return;
        }
        if(isHtmlTagFormat($( 'receiptNo' ).value.trim())){
            $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.041"/>';
            //$( 'displayResultMsgClientB' ).innerHTML = "Không nên nhập thẻ html vào mã phiếu chi";
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
        widget.href = "payDepositAtShopAction!addPayDepositAtShop.do?" + token.getTokenParamString();
    });

    pageNavigator = function (ajaxTagId, pageNavigator, pageNumber){
        // TuTM1 04/03/2012 Fix ATTT CSRF
        dojo.widget.byId("updateContent").href = 'payDepositAtShopAction!pageNavigator.do?' + pageNavigator + "=" 
            + pageNumber + "&"+token.getTokenParamString();
        dojo.event.topic.publish('updateContent');
    }

    dojo.event.topic.subscribe("payDepositAtShopAction/resetPayDepositAtShop", function(event, widget){
        document.getElementById('isdnB').value="";
        document.getElementById('depositTypeIdB').value="";
        dojo.widget.byId("fromDateB").domNode.childNodes[1].value = "";
        dojo.widget.byId("toDateB").domNode.childNodes[1].value = "";
        event.cancel = true;
        //event: set event.cancel = true, to cancel request
    });

    isCheckBoxChecked = function(){
        if(document.getElementById('tblListPayDepositAtShop') == null){
            return false;
        }
        var i = 0;
        var tbl = $( 'tblListPayDepositAtShop' );
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

    resetValue = function(){
        totalSum = 0;
        arrName=new Array();
    };

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

</script>
