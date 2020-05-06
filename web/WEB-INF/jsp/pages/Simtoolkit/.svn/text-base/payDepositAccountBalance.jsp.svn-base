<%-- 
    Document   : payDepositAccountBalance
    Created on : Jan 20, 2012, 4:14:14 PM
    Author     : TrongLV
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<script language="javascript">
    if (request.getParameter("closeForm") != null && request.getParameter("closeForm") == true ){
        window.close();
    }
</script>

<s:property escapeJavaScript="true"  value="requeset.closeForm"/>

<s:form action="simtkManagmentAction" theme="simple" method="post" id="accountBalanceForm">
<s:token/>

    <tags:imPanel title="MSG.SIK.091">
        <s:hidden name="accountBalanceForm.accountId" id="accountBalanceForm.accountId"/>
        <s:hidden name="accountBalanceForm.balanceId" id="accountBalanceForm.balanceId"/>
        <s:hidden name="accountBalanceForm.receiptId" id="accountBalanceForm.receiptId"/>
        <div>
            <tags:displayResult id="messageShow" property="messageShow" propertyValue="paramMsg" type="key" />
        </div>
        <table class="inputTbl4Col" style="width:100%">
            <tr>
                <td class="label"><tags:label key="shop code"/></td>
                <td class="text">
                    <s:textfield name="accountBalanceForm.shopCode" id="accountBalanceForm.shopCode" 
                                 theme="simple" maxlength="10"  cssClass="txtInputFull" readonly = "true" />
                </td>
                <td class="label"><tags:label key="shop name"/></td>
                <td class="text">
                    <s:textfield name="accountBalanceForm.shopName" id="accountBalanceForm.shopName" 
                                 theme="simple" maxlength="10"  cssClass="txtInputFull" readonly = "true" />
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="channel code"/></td>
                <td class="text">
                    <s:textfield name="accountBalanceForm.ownerCode" id="accountBalanceForm.ownerCode" 
                                 theme="simple" maxlength="10"  cssClass="txtInputFull" readonly = "true" />
                </td>
                <td class="label"><tags:label key="channel name"/></td>
                <td class="text">
                    <s:textfield name="accountBalanceForm.ownerName" id="accountBalanceForm.ownerName" 
                                 theme="simple" maxlength="10"  cssClass="txtInputFull" readonly = "true" />
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.SIK.079"/></td>
                <td class="text">
                    <s:textfield name="accountBalanceForm.realBalanceDisplay" id="accountBalanceForm.realBalanceDisplay" cssStyle ="text-align:right" theme="simple" maxlength="10"  cssClass="txtInputFull"
                                 readonly = "true" />
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="L.200007"/></td>
                <td class="text">
                    <s:textfield name="accountBalanceForm.depositReceiptCode" id="accountBalanceForm.depositReceiptCode" 
                                 cssStyle ="text-align:left" theme="simple" maxlength="10"  cssClass="txtInputFull" readonly = "true" />
                </td>
                <td class="label"><tags:label key="MSG.SIK.072"/></td>
                <td class="text">
                    <tags:dateChooser property="accountBalanceForm.depositCreateDate"  id="accountBalanceForm.depositCreateDate" 
                                      styleClass="txtInputFull"  insertFrame="false" readOnly="true"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.reason" required="true"/></td>
                <td class="text">
                    <s:if test="accountBalanceForm.receiptId == null">
                        <tags:imSelect name="accountBalanceForm.depositReasonId"
                                       id="accountBalanceForm.depositReasonId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SIK.253"
                                       list="listReason"
                                       listKey="reasonId" listValue="reasonName"/>
                    </s:if>
                    <s:else>
                        <tags:imSelect name="accountBalanceForm.depositReasonId"
                                       id="accountBalanceForm.depositReasonId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SIK.253"
                                       list="listReason" disabled="true"
                                       listKey="reasonId" listValue="reasonName"/>
                    </s:else>
                </td>
                <td class="label"><tags:label key="MSG.SIK.080" required="true"/></td>
                <td class="text">
                    <s:if test="accountBalanceForm.receiptId == null">
                        <s:textfield name="accountBalanceForm.depositAmount" id="accountBalanceForm.depositAmount" 
                                     style="text-align:right" onkeyup="textFieldNF(this)" theme="simple" maxlength="10" cssClass="txtInputFull"/>
                    </s:if>
                    <s:else>
                        <s:textfield name="accountBalanceForm.depositAmount" id="accountBalanceForm.depositAmount" 
                                     style="text-align:right" onkeyup="textFieldNF(this)" theme="simple" maxlength="10" cssClass="txtInputFull" readonly="true"/>
                    </s:else>
                </td>
            </tr>
        </table>                
        <br/>
        <div>
            <s:if test="accountBalanceForm.receiptId == null ">
                <input type="button" onclick="createReceipt();"  value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.089')"/>" style="width: 120px;"/>
                <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.221')"/>" style="width: 120px;" disabled/>
            </s:if>
            <s:else>
                <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.088')"/>" style="width: 120px;" disabled/>
                <input type="button" onclick="printReceipt();"  value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.221')"/>" style="width: 120px;"/> 
            </s:else>

            <input type="button" onclick="resetForm();"  value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.102')"/>"style="width: 120px;"/>
            <input type="button" onclick="refreshParent();"  value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.103')"/>" style="width: 120px;"/>
        </div>
    </tags:imPanel>
</s:form>

<script language="javascript">
    
    createReceipt = function(){
        if (!checkValidate()){
            return false;
        }
        $('messageShow').innerHTML='';
        
        var strConfirm = getUnicodeMsg('<s:text name="C.200006"/>');
        if (!confirm(strConfirm)) {
            return false;
        }
        
        document.getElementById("accountBalanceForm").action="simtkManagmentAction!createPayDeposit.do";
        document.getElementById("accountBalanceForm").submit();
    }
    
    printReceipt  = function(){
        alert("He thong dang nang cap");
    }
    
    resetForm = function(){
        document.getElementById("accountBalanceForm").action="simtkManagmentAction!resetPayDeposit.do";
        document.getElementById("accountBalanceForm").submit();
    }
    
    refreshParent = function(){
        window.close();            
        window.opener.updateParent($('accountBalanceForm.accountId').value, $('accountBalanceForm.balanceId').value);
        
    }
    

    checkValidate = function(){
        if (trim($("accountBalanceForm.depositReceiptCode").value) == ''){
            $('accountBalanceForm.depositReceiptCode').select();
            $('accountBalanceForm.depositReceiptCode').focus();
            $('messageShow').innerHTML='<s:text name ="getText('E.200025')"/>';
            return false;
        }
        
        if (trim($("accountBalanceForm.depositReasonId").value) == ''){
            $('accountBalanceForm.depositReasonId').select();
            $('accountBalanceForm.depositReasonId').focus();
            $('messageShow').innerHTML='<s:text name ="getText('E.200026')"/>';
            return false;
        }
        
        if (trim($("accountBalanceForm.depositAmount").value) == '' || trim($("accountBalanceForm.depositAmount").value) == '0'){
            $('accountBalanceForm.depositAmount').select();
            $('accountBalanceForm.depositAmount').focus();
            $('messageShow').innerHTML='<s:text name ="getText('E.200027')"/>';
            return false;
        }
        
        var amount = trim($('accountBalanceForm.depositAmount').value).replace(/\,/g,""); //loai bo dau , trong chuoi
        if(!isDouble(trim(amount))) {
            $('accountBalanceForm.depositAmount').select();
            $('accountBalanceForm.depositAmount').focus();
            $('messageShow').innerHTML='<s:text name ="getText('E.200028')"/>';
            return false;
        }
        if (amount * 1 <= 0) {
            $('messageShow').innerHTML='<s:text name ="getText('E.200028')"/>';
            $('accountBalanceForm.depositAmount').select();
            $('accountBalanceForm.depositAmount').focus();
            return false;
        }
        return true;
    }   


</script>
