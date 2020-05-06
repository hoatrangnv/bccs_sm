<%-- 
    Document   : searchDocDeposit
    Created on : Feb 18, 2009, 11:43:14 AM
    Author     : tuannv
--%>

<%--
    Note: tim kiem thong tin ve giấy nộp tiền
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
            if (request.getAttribute("listBankReceipt") == null) {
                request.setAttribute("listBankReceipt", request.getSession().getAttribute("listBankReceipt"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="commDocDepositAction" id="docDepositForm"  theme="simple" method="post" >
<s:token/>
    
    <tags:imPanel title="MSG.search.doc.deposit">
        <table class="inputTbl4Col">
            <tr>
                <td class="label"><tags:label key="MSG.object.type" required="false"/></td>
                <td class="text">
                    <tags:imSelect name="docDepositForm.submitTypeB" id="submitTypeB"
                                   list="2:MSG.GOD.262,1:MSG.GOD.261"
                                   cssClass="txtInputFull"
                                   />
<!--                    1:dv cap duoi; 2: nhan vien-->
                </td>            
                <td class="label"><tags:label key="MSG.object"/></td>
                <td class="text">
                    <tags:imSearch codeVariable="docDepositForm.shopCodeB" nameVariable="docDepositForm.shopNameB"
                                   codeLabel="MSG.SAE.067" nameLabel="MSG.SAE.070"
                                   searchClassName="com.viettel.im.database.DAO.DocDepositDAO"
                                   searchMethodName="getListShopOrStaff"
                                   otherParam="submitTypeB"
                                   getNameMethod="getNameShopOrStaff"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.telecom.service"/></td>
                <td class="text">
                    <tags:imSelect name="docDepositForm.telecomServiceIdB"
                                   id="telecomServiceIdB"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.SIK.263"
                                   list="listTelecomService"
                                   listKey="telecomServiceId" listValue="telServiceName"/>
                </td>
                <td class="label"> <tags:label key="MSG.account.number"/></td>
                <td class="text">
                    <tags:imSearch codeVariable="docDepositForm.accountNoB" nameVariable="docDepositForm.bankNameB"
                                   codeLabel="MSG.account.number" nameLabel="MSG.bank"
                                   searchClassName="com.viettel.im.database.DAO.DocDepositDAO"
                                   searchMethodName="getCodeBankAccount"                                   
                                   getNameMethod="getNameBankAccount"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.date.paid.from"/></td>
                <td class="text">
                    <tags:dateChooser  property="docDepositForm.fromDateB" id="fromDateB" readOnly="false" styleClass="txtInputFull"/>
                </td>
                <td class="label"> <tags:label key="MSG.date.paid.to"/></td>
                <td class="text">
                    <tags:dateChooser  property="docDepositForm.toDateB" id="toDateB" readOnly="false" styleClass="txtInputFull"/>
                </td>
            </tr>
            <%--tr>
                <td class="label"><tags:label key="MSG.telecom.service"/></td>
                <td class="text">
                    <tags:imSelect name="docDepositForm.telecomServiceIdB"
                                   id="telecomServiceIdB"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.SIK.263"
                                   list="listTelecomService"
                                   listKey="telecomServiceId" listValue="telServiceName"/>
                </td>
                <td class="label"><tags:label key="MSG.date.paid.from"/></td>
                <td class="text">
                    <tags:dateChooser  property="docDepositForm.fromDateB" id="fromDateB" readOnly="false" styleClass="txtInputFull"/>
                </td>
                <td class="text" colspan="2"></td>
            </tr>
            <tr>
                <td class="label"> <tags:label key="MSG.status"/></td>
                <td class="text">
                    <tags:imSelect name="docDepositForm.statusB" id="statusB"
                                   cssClass="txtInputFull" disabled="false"
                                   list="1:MSG.not.approved,2:MSG.approved,4:MSG.deny"
                                   headerKey="" headerValue="MSG.SIK.262"/>
                </td>
                <td class="label"> <tags:label key="MSG.date.paid.to"/></td>
                <td class="text">
                    <tags:dateChooser  property="docDepositForm.toDateB" id="toDateB" readOnly="false" styleClass="txtInputFull"/>
                </td>
                <td class="text" colspan="2"></td>
            </tr--%>
            <tr>
                <td colspan="4">
                    <tags:displayResult id="displayResultMsgClientB" property="returnMsgB" propertyValue="returnMsgValue" type="key" />
                </td>
            </tr>
        </table>
    </tags:imPanel>    
    <br/>
    <div align="center">
        <tags:submit targets="bodyContent" formId="docDepositForm" cssStyle="width:80px;"
                     value="MSG.find" preAction="commDocDepositAction!searchBankReceipt.do"
                     validateFunction="checkBeforeSearch();"
                     showLoadingText="true"/>
    </div>

    <%-- Danh mục giấy nộp tiền --%>
    <%--<sx:bind id="updateContent" indicator="overlay" events="onclick" listenTopics="updateContent" targets="bodyContent" separateScripts="true" executeScripts="true"/>--%>
    <s:hidden name="docDepositForm.bankReceiptId" id="docDepositForm.bankReceiptId"/>

    <tags:imPanel title="MSG.list.doc.deposit">
        <s:if test="#request.listBankReceipt != null && #request.listBankReceipt.size() > 0">
            <div id="listBankReceipt">
                <jsp:include page="listBankReceipt.jsp"/>
            </div>
        </s:if>
        <s:else>
            <tags:label key="MSG.list.null" required="false"/>
        </s:else>
    </tags:imPanel>
    <br>
    <tags:imPanel title="MSG.info.doc.deposit">
        <table class="inputTbl4Col">
            <tr>
                <td colspan="4">
                    <tags:displayResult id="displayResultMsgClient" property="returnMsg" type="key" />
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.object.type" required="true"/></td>
                <td class="text">
                    <tags:imSelect name="docDepositForm.submitType" id="submitType"
                                   cssClass="txtInputFull"
                                   list="2:MSG.GOD.262,1:MSG.GOD.261"
                                   />
<!--                    1: dv cap duoi; 2: nhan vien-->
                </td>                
            </tr>
            <tr>
                <td class="label">  <tags:label key="MSG.object" required="true"/></td>
                <td colspan="3" class="text">
                    <tags:imSearch codeVariable="docDepositForm.shopCode" nameVariable="docDepositForm.shopName"
                                   codeLabel="MSG.SAE.067" nameLabel="MSG.SAE.070"
                                   searchClassName="com.viettel.im.database.DAO.DocDepositDAO"
                                   searchMethodName="getListShopOrStaff"
                                   otherParam="submitType"
                                   getNameMethod="getNameShopOrStaff"/>
                </td>                
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.account.number" required="true"/></td>
                <td colspan="3" class="text">
                    <tags:imSearch codeVariable="docDepositForm.accountNo" nameVariable="docDepositForm.bankName"
                                   codeLabel="MSG.account.number" nameLabel="MSG.bank"
                                   searchClassName="com.viettel.im.database.DAO.DocDepositDAO"
                                   searchMethodName="getCodeBankAccount"
                                   getNameMethod="getNameBankAccount"/>
                </td>                
            </tr>
            <tr>
                <td class="label"> <tags:label key="MSG.user.receive"/></td>
                <td class="text">
                    <s:textfield name="docDepositForm.receiver" id="receiver" maxlength="100"  value="Tổng công ty Viettel" readonly="true" cssClass="txtInputFull"/>
                </td>
                <td class="label"> <tags:label key="MSG.address"/></td>
                <td class="text">
                    <s:textfield name="docDepositForm.receiverAddress" id="receiverAddress" maxlength="100" value="Số 1 Giang Văn Minh" readonly="true" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.money.paid" required="true"/></td>
                <td class="text">                    
                    <%--<s:textfield name="docDepositForm.amount" id="amount" onfocus="unFormatNumber(this)" onblur="formatNumber(this)" maxlength="19" cssClass="txtInput"/>--%>
                    <s:textfield name="docDepositForm.amount" id="amount" onkeyup="textFieldNF(this)" maxlength="15" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.date.paid" required="true"/></td>
                <td class="text">
                    <tags:dateChooser  property="docDepositForm.bankDate" id="bankDate" readOnly="false" styleClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td class="label"> <tags:label key="MSG.telecom.service" required="true"/></td>
                <td class="text">
                    <tags:imSelect name="docDepositForm.telecomServiceId"
                                   id="telecomServiceId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.SIK.263"
                                   list="listTelecomService"
                                   listKey="telecomServiceId" listValue="telServiceName"/>

                    <%--<s:select name="docDepositForm.telecomServiceId"
                              id="telecomServiceId"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="--Chọn dịch vụ viễn thông--"
                              list="%{#request.listTelecomService != null? #request.listTelecomService :#{}}"
                              listKey="telecomServiceId" listValue="telServiceName"/>--%>
                </td>
            </tr>
        </table>
    </tags:imPanel>
    <br>
    <div align="center">
        <s:if test="#session.toEditBankReceipt != 1">
            <tags:submit targets="bodyContent" formId="docDepositForm" cssStyle="width:80px;" showLoadingText="true"
                         validateFunction="checkBeforeInsert();"
                         confirm="true" confirmText="MSG.confirm.add.new.doc.deposit"
                         value="MSG.generates.addNew" preAction="commDocDepositAction!addNewBankReceipt.do"/>
        </s:if>
        <s:else>
            <tags:submit targets="bodyContent" formId="docDepositForm" cssStyle="width:80px;" showLoadingText="true"
                         validateFunction="checkBeforeInsert();"
                         confirm="true" confirmText="MSG.confirm.edit.doc.deposit"
                         value="MSG.generates.edit" preAction="commDocDepositAction!editBankReceipt.do"/>
            <tags:submit targets="bodyContent" formId="docDepositForm" cssStyle="width:80px;" showLoadingText="true"
                         value="MSG.SAE.034" preAction="commDocDepositAction!prepareDrawDocDeposit.do"/>
        </s:else>
    </div>
</s:form>

<script language="javascript">
    textFieldNF($('amount'));

    confirmDelete = function(userId, staffId){
        if(userId != staffId){
//            $('displayResultMsgClientB').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.DET.042"/>';
            $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.042"/>';
            //$( 'displayResultMsgClientB' ).innerHTML = "Bạn không phải là người nhập giấy nộp tiền này";
            event.cancel = true;
        }
//        var strAlert = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.DET.005"/>');
        var strAlert = getUnicodeMsg('<s:text name="MSG.DET.005"/>');
        //alert(strAlert);
        if(!confirm(strAlert)){     //Ductm
            event.cancel = true;
        }
    };

    checkAllowEdit = function(userId, staffId){
        if(userId != staffId){
//            $('displayResultMsgClientB').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.DET.042"/>';
            $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.042"/>';
            //$( 'displayResultMsgClientB' ).innerHTML = "Bạn không phải là người nhập giấy nộp tiền này";
            event.cancel = true;
        }
    };

    checkBeforeSearch = function(){
        var tmp = document.getElementById('docDepositForm.shopCodeB').value.trim();
        if(tmp != "" && isHtmlTagFormat(tmp)){
//            $('displayResultMsgClientB').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.DET.043"/>';
            $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.043"/>';
            //$( 'displayResultMsgClientB' ).innerHTML = "Không nên nhập thẻ html vào mã đối tượng!";
            document.getElementById('docDepositForm.shopCodeB').focus();
            return false;
        }
        
        tmp = document.getElementById('docDepositForm.accountNoB').value.trim();
        if(tmp != "" && isHtmlTagFormat(tmp)){
//            $('displayResultMsgClientB').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.DET.002"/>';
            $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.002"/>';
            //$( 'displayResultMsgClientB' ).innerHTML = "Không nên nhập thẻ html vào số tài khoản!";
            document.getElementById('docDepositForm.accountNoB').focus();
            return false;
        }

        var checkDate = true;
        var dateExported= dojo.widget.byId("fromDateB");
        if(dateExported.domNode.childNodes[1].value.trim() != "" &&
            !isDateFormat(dateExported.domNode.childNodes[1].value)){
//            $('displayResultMsgClientB').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.DET.004"/>';
            $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.004"/>';
            //$( 'displayResultMsgClientB' ).innerHTML='Ngày nộp từ không hợp lệ';
            dateExported.domNode.childNodes[1].focus();
            return false;
        }
        if (dateExported.domNode.childNodes[1].value.trim() == "")
            checkDate = false;

        var dateExported1 = dojo.widget.byId("toDateB");
        if(dateExported1.domNode.childNodes[1].value.trim() != "" &&
            !isDateFormat(dateExported1.domNode.childNodes[1].value)){
//            $('displayResultMsgClientB').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.DET.005"/>';
            $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.005"/>';
            //$( 'displayResultMsgClientB' ).innerHTML='Ngày nộp đến không hợp lệ';
            dateExported1.domNode.childNodes[1].focus();
            return false;
        }
        if (dateExported1.domNode.childNodes[1].value.trim() == "")
            checkDate = false;

        if (checkDate){
            if(!isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
//                $('displayResultMsgClientB').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.DET.006"/>';
                $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.006"/>';
                //$( 'displayResultMsgClientB' ).innerHTML='Ngày nộp từ phải nhỏ hơn Ngày nộp đến';
                dateExported.domNode.childNodes[1].focus();
                dateExported.domNode.childNodes[1].select();
                return false;
            }
        }
       

        return true;
    }

    checkBeforeInsert = function(){
        var tmp = document.getElementById('docDepositForm.shopCode').value.trim();
        if (tmp == "") {
//            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.DET.045"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.045"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa nhập mã đối tượng!";
            document.getElementById('docDepositForm.shopCode').focus();
            return false;
        }
        if(tmp != "" && isHtmlTagFormat(tmp)){
//            $('displayResultMsgClientB').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.DET.043"/>';
            $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.043"/>';
            //$( 'displayResultMsgClientB' ).innerHTML = "Không nên nhập thẻ html vào mã đối tượng!";
            document.getElementById('docDepositForm.shopCode').focus();
            return false;
        }

        tmp = document.getElementById('docDepositForm.accountNo').value.trim();
        if (tmp == "") {
//            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.DET.046"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.046"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa nhập số tài khoản!";
            document.getElementById('docDepositForm.accountNo').focus();
            return false;
        }
        if(tmp != "" && isHtmlTagFormat(tmp)){
//            $('displayResultMsgClientB').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.DET.002"/>';
            $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.002"/>';
            //$( 'displayResultMsgClientB' ).innerHTML = "Không nên nhập thẻ html vào số tài khoản!";
            document.getElementById('docDepositForm.accountNo').focus();
            return false;
        }

        if($( 'amount' ).value == ""){
//            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.DET.044"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.044"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa nhập số tiền nộp";
            $( 'amount' ).focus();
            return false;
        }

        //Ductm
        var tmp = $('amount').value.trim().replace(/\,/g,""); //loai bo dau , trong chuoi
        tmpAmount = tmp * 1;
        if(tmpAmount<=0){
//            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.DET.047"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.047"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Số tiền nộp không hợp lệ";
            $( 'amount' ).focus();
            $( 'amount' ).select();
            return false;
        }

        //End Ductm

        var status = isMoneyFormat($( 'amount' ).value.trim());
        if(!status){
//            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.DET.047"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.047"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Số tiền nộp không hợp lệ";
            $( 'amount' ).focus();
            $( 'amount' ).select();
            return false;
        }

        var dateExported= dojo.widget.byId("bankDate");
        if (dateExported.domNode.childNodes[1].value.trim() == ""){
//            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.DET.034"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.034"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa nhập ngày nộp!";
            dateExported.domNode.childNodes[1].focus();
            return false;
        }
        if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
//            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.DET.035"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.035"/>';
            //$( 'displayResultMsgClient' ).innerHTML="Ngày nộp không hợp lệ!";
            dateExported.domNode.childNodes[1].focus();
            return false;
        }

        if($( 'telecomServiceId' ).value == ""){
            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.DET.048"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.048"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa chọn dịch vụ viễn thông!";
            $( 'telecomServiceId' ).focus();
            return false;
        }

        //Ductm
        if(compareDates(GetDateSys(),dateExported.domNode.childNodes[1].value)){
//            $('displayResultMsg').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.RET.061"/>';
            $('displayResultMsg').innerHTML= '<s:text name="ERR.RET.061"/>';
            //$('displayResultMsgClient').innerHTML='Lỗi. Ngày nộp không được lớn hơn ngày hiện tại';
            dateExported.domNode.childNodes[1].focus();
            return false;
        }
        return true;
    }
    function GetDateSys() {
        var time = new Date();
        var dd=time.getDate();
        var mm=time.getMonth()+1;
        var yy=time.getFullYear() ;
        var temp = '';
        if (dd < 10) dd = '0' + dd;
        if (mm < 10) mm = '0' + mm;
        temp = dd + "/" + mm + "/" + yy ;
        return(temp);
    }

</script>

