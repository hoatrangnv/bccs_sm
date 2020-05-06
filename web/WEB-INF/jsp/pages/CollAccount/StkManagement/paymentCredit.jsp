<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : addMoneyToAccountBalance
    Created on : Oct 22, 2009, 2:43:17 PM
    Author     : LamNV5
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
            request.setAttribute("contextPath", request.getContextPath());
            String pageId = request.getParameter("pageId");
            request.setAttribute("typeId", request.getSession().getAttribute("typeId" + pageId));
%>
<s:form action="CollAccountManagmentAction" theme="simple" method="post" id="AddMoneyToAccountBalanceForm">
<s:token/>

    <input type="hidden"  name="pageId" id="pageId"/>
    <tags:imPanel title="MSG.SIK.086">
        <s:hidden name="AddMoneyToAccountBalanceForm.accountId" id="AddMoneyToAccountBalanceForm.accountId"/>
        <s:hidden name="AddMoneyToAccountBalanceForm.collId" id="AddMoneyToAccountBalanceForm.collId"/>
        <!--s:hidden name="AddMoneyToAccountBalanceForm.chargingDebit" id="AddMoneyToAccountBalanceForm.chargingDebit"/-->
        <!--s:hidden name="AddMoneyToAccountBalanceForm.realDebit" id="AddMoneyToAccountBalanceForm.realDebit"/-->
        <s:hidden name="AddMoneyToAccountBalanceForm.maxDebit" id="AddMoneyToAccountBalanceForm.maxDebit"/>
        <s:hidden name="AddMoneyToAccountBalanceForm.minSum" id="AddMoneyToAccountBalanceForm.minSum"/>
        <!--s:hidden name="AddMoneyToAccountBalanceForm.staffId" id="AddMoneyToAccountBalanceForm.staffId"/-->


        <div>
            <tags:displayResult id="messageShow" property="messageShow" propertyValue="paramMsg" type="key" />
        </div>
        <table class="inputTbl8Col" style="width:100%">
            <tr>
                <s:if test="#request.typeId =='2'">
                    <%--<td class="label">Mã CTV</td>--%>
                    <td><tags:label key="MSG.SIK.092"/></td>
                </s:if>
                <s:else>
                    <%--<td class="label">Mã Đại lý</td>--%>
                    <td><tags:label key="MSG.SIK.093"/></td>
                </s:else>
                <td class="text">
                    <s:textfield name="AddMoneyToAccountBalanceForm.collCode" id="collCode" theme="simple" maxlength="250" cssClass="txtInputFull"
                                 readonly="true"/>
                </td>
                <s:if test="#request.typeId =='2'">
                    <%--<td class="label">Tên CTV</td>--%>
                    <td><tags:label key="MSG.SIK.094"/></td>
                </s:if>
                <s:else>
                    <%--<td class="label">Tên Đại lý</td>--%>
                    <td><tags:label key="MSG.SIK.095"/></td>
                </s:else>
                <td class="text">
                    <s:textfield name="AddMoneyToAccountBalanceForm.namestaff" id="namestaff" theme="simple" maxlength="250" cssClass="txtInputFull"
                                 readonly="true"/>
                </td>



                <%--<td class="label">Số tiền thanh toán tín dụng (<font color="red">*</font>)</td>--%>
                <td><tags:label key="MSG.SIK.223" required="true"/></td>
                <td class="text">
                    <s:textfield name="AddMoneyToAccountBalanceForm.chargingDebit" id="chargingDebit" style="text-align:right" onkeyup="textFieldNF(this)" theme="simple" maxlength="20" cssClass="txtInputFull"/>
                </td>

                <%--<td class="label">Số nợ tín dụng</td>--%>
                <td><tags:label key="MSG.SIK.224" /></td>
                <td class="text">
                    <s:textfield name="AddMoneyToAccountBalanceForm.realDebit" id="realDebit" style="text-align:right" onkeyup="textFieldNF(this)" theme="simple" maxlength="20" cssClass="txtInputFull" readonly="true"/>
                </td>
            </tr>

        </table>
    </tags:imPanel>

    <div style="height:10px">
    </div>


    <tags:imPanel title="MSG.SIK.219">
        <table class="inputTbl6Col" style="width:100%">
            <tr>
                <%--<td class="label">Mã phiếu thu (<font color="red">*</font>)</td>--%>
                <td><tags:label key="MSG.SIK.220" required="true"/></td>
                <td class="text">
                    <s:textfield name="AddMoneyToAccountBalanceForm.receiptCode" id="receiptCode" theme="simple" maxlength="10" cssClass="txtInputFull"/>
                </td>
                <%--<td class="label">Ngày tạo</td>--%>
                <td><tags:label key="MSG.SIK.143"/></td>
                <td class="text">
                    <tags:dateChooser property="AddMoneyToAccountBalanceForm.createDate"  id="createDate"  styleClass="txtInput" readOnly="true"  insertFrame="false"/>
                </td>
            </tr>
            <tr>
                <%--<td class="label">Hình thức thanh toán (<font color="red">*</font>)</td>--%>
                <td><tags:label key="MSG.SIK.225" required="true"/></td>
                <td class="text">
                    <%--<s:select name="AddMoneyToAccountBalanceForm.payMethod"
                              id="payMethod"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="--Chọn hình thức thanh toán--"
                              listKey="code" listValue="name"
                              list="%{#request.listPayMethod != null ? #request.listPayMethod : #{} }"/>--%>
                    <tags:imSelect name="AddMoneyToAccountBalanceForm.payMethod"
                                   id="payMethod"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.SIK.259"
                                   list="listPayMethod"
                                   listKey="code" listValue="name"/>
                </td>
                <%--<td class="label">Lý do (<font color="red">*</font>)</td>--%>
                <td><tags:label key="MSG.SIK.098" required="true"/></td>
                <td class="text">
                    <%--<s:select name="AddMoneyToAccountBalanceForm.reasonId"
                              id="reasonId"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="--Chọn lý do--"
                              listKey="reasonId" listValue="reasonName"
                              list="%{#request.listReason != null ? #request.listReason : #{} }"/>--%>
                    <tags:imSelect name="AddMoneyToAccountBalanceForm.reasonId"
                                   id="reasonId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.SIK.253"
                                   list="listReason"
                                   listKey="reasonId" listValue="reasonName"/>
                </td>
            </tr>
            <tr>
                <%--<td class="label">Cửa hàng</td>--%>
                <td><tags:label key="MSG.SIK.226" /></td>
                <td class="text">
                    <table table style="width:100%; border-spacing:0px;">
                        <tr>
                            <td width="30%">
                                <s:textfield name="AddMoneyToAccountBalanceForm.shopcode" id="shopcode" theme="simple" maxlength="100" cssClass="txtInputFull"
                                             readonly="true"/>
                            </td>
                            <td>
                                <s:textfield name="AddMoneyToAccountBalanceForm.shopName" id="shopName" theme="simple" maxlength="1000" cssClass="txtInputFull"
                                             readonly="true"/>
                            </td>
                        </tr>
                    </table>
                </td>
                <%--<td class="label">Nhân viên thực hiện</td>--%>
                <td><tags:label key="MSG.SIK.100" /></td>
                <td class="text">
                    <table table style="width:100%; border-spacing:0px;">
                        <tr>
                            <td width="30%">                    
                                <s:textfield name="AddMoneyToAccountBalanceForm.staffCode" id="staffCode" theme="simple" maxlength="250" cssClass="txtInputFull"
                                             readonly="true"/>
                            </td>
                            <td>
                                <s:textfield name="AddMoneyToAccountBalanceForm.staffName" id="staffName" theme="simple" maxlength="1000" cssClass="txtInputFull"
                                             readonly="true"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="8" align="center">
                    <s:if test="#request.filePath !=null && #request.filePath!=''">
                        <script>
                            window.open('${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');
                        </script>
                        <%--<div><a href='${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về.</a></div>--%>
                        <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>' ><tags:label key="MSG.clickhere.to.download" /></a></div>
                    </s:if>
                </td>
            </tr>
        </table>
    </tags:imPanel>



    <div style="height:10px">
    </div>
    <div align="center" style="padding-bottom:5px;">
        <s:if test="#session.print == 1">
            <%--<input type="button" onclick="addMoney();"  value="Thanh toán" disabled/>--%>
            <input type="button" onclick="addMoney();"  value="${fn:escapeXml(imDef:imGetText('MSG.SIK.227'))}" disabled/>
            <%--<input type="button" onclick="printReceipt();"  value="In phiếu thu" />--%>
            <input type="button" onclick="printReceipt();"  value="${fn:escapeXml(imDef:imGetText('MSG.SIK.221'))}" />
        </s:if>
        <s:else>
            <%--<input type="button" onclick="addMoney();"  value="Thanh toán"/>--%>
            <input type="button" onclick="addMoney();"  value="${fn:escapeXml(imDef:imGetText('MSG.SIK.227'))}"/>
            <%--<input type="button" onclick="printReceipt();"  value="In phiếu thu" disabled/>--%>
            <input type="button" onclick="printReceipt();"  value="${fn:escapeXml(imDef:imGetText('MSG.SIK.221'))}" disabled/>
        </s:else>
        <!--input type="button" onclick="resetForm();"  value="Reset"/-->
        <%--<input type="button" onclick="refreshParent();"  value="Thoát"/>--%>
        <input type="button" onclick="refreshParent();"  value="${fn:escapeXml(imDef:imGetText('MSG.SIK.103'))}"/>
    </div>

</s:form>

<script type="text/javascript" language="javascript">
    textFieldNF($('chargingDebit'));
    textFieldNF($('realDebit'));
    validate = function(){
        //var moneyBalance = document.getElementById("moneyBalance");
        //var moneyDebit = document.getElementById("moneyDebit");
        var chargingDebit = $('chargingDebit').value.replace(/\,/g,""); //loai bo dau , trong chuoi
        var realDebit = $('realDebit').value.replace(/\,/g,""); //loai bo dau , trong chuoi
        var receiptCode = document.getElementById("receiptCode");
        var reasonId = document.getElementById("reasonId");
        var payMethod = document.getElementById("payMethod");

        if(chargingDebit == null || trim(chargingDebit).length <= 0 ){
    <%--$('messageShow').innerHTML="Bạn chưa nhập số tiền thanh toán tín dụng"--%>
                $('messageShow').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.063')"/>';
                $('chargingDebit').focus();
                return false;
            }

            if(!isInteger( trim(chargingDebit) ) ){
    <%--$('messageShow').innerHTML="Số tiền nạp thêm phải là số nguyên dương"--%>
                $('messageShow').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.058')"/>';
                $('chargingDebit').focus();
                return false;
            }

            if(trim(realDebit)*1 < 0){
    <%--$('messageShow').innerHTML="Số tiền thanh toán tín dụng lớn hơn số nợ"--%>
                $('messageShow').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.064')"/>';
                $('chargingDebit').focus();
                return false;
            }

            if(receiptCode.value==null || receiptCode.value ==""){
    <%--$('messageShow').innerHTML="Chưa nhập mã phiếu thu"--%>
                $('messageShow').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.060')"/>';
                receiptCode.focus();
                return false;
            }

            receiptCode.value = trim(receiptCode.value);
            if (isHtmlTagFormat(trim(receiptCode.value))){
    <%--$('messageShow').innerHTML="Mã phiếu thu không được nhập định dạng thẻ HTML"--%>
                $('messageShow').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.061')"/>';
                receiptCode.focus();
                return false;
            }
            if(!isFormalCharacter(trim(receiptCode.value))){
    <%--$('messageShow').innerHTML="Mã phiếu thu không được nhập ký tự đặc biệt"--%>
                $('messageShow').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.062')"/>';
                receiptCode.focus();
                return false;
            }

            if (payMethod.value==null || payMethod.value=="") {
    <%--$('messageShow').innerHTML="Chưa chọn hình thức thanh toán"--%>
                $('messageShow').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.065')"/>';
                payMethod.focus();
                return false;
            }
        
            if(reasonId.value==null || reasonId.value ==""){
    <%--$('messageShow').innerHTML="Chưa chọn lý do"--%>
                $('messageShow').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.055')"/>';
                reasonId.focus();
                return false;
            }

            chargingDebit = trim(chargingDebit);
            realDebit = trim(realDebit);

            $('chargingDebit').value = chargingDebit; //loai bo dau , trong chuoi
            $('realDebit').value = realDebit; //loai bo dau , trong chuoi
            return true;
        }
    
        addMoney = function() {
            if (!validate()) {
                event.cancel = true;
                return;
            }
            /*
        if (confirm("Are you sure you want to delete")) {
        document.location = delUrl;
        }
             */
    <%--if (!confirm("Bạn có chắc chắn muốn thanh toán tín dụng không?")){--%>
            if (!confirm("'<s:property escapeJavaScript="true"  value="getError('MSG.SIK.228')"/>'")){
                event.cancel = true;
                return;
            }
        
            document.getElementById("AddMoneyToAccountBalanceForm").action="CollAccountManagmentAction!doPaymentCredit.do?"+ token.getTokenParamString();
            document.getElementById("AddMoneyToAccountBalanceForm").submit();
        }
        resetForm = function(){
            document.getElementById("AddMoneyToAccountBalanceForm").action="CollAccountManagmentAction!reset.do?"+ token.getTokenParamString();
            document.getElementById("AddMoneyToAccountBalanceForm").submit();
        }
        printReceipt = function(){
            document.getElementById("AddMoneyToAccountBalanceForm").action="CollAccountManagmentAction!printRevenueReceiptCredit.do?printType=1"+"&"+ token.getTokenParamString();
            document.getElementById("AddMoneyToAccountBalanceForm").submit();
        }
        refreshParent = function(){
            window.close();
            window.opener.updateParent();
        }
    
</script>
