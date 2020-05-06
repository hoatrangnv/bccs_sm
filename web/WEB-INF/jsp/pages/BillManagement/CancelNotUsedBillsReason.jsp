<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 
    Document   : CreateNewBills
    Created on : Feb 18, 2009, 10:51:45 AM
    Author     : TungTV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%
            String staffId = (String) request.getSession().getAttribute("staffid");
%>
<script type="text/javascript" language="javascript">
    function transition(data){
        if(data == "destroy") {
            document.getElementById("className").value="CancelNotUsedBillsDAO";
            document.getElementById("methodName").value="cancelNotUsedBillsComplete";
            document.CancelNotUsedBillsReasonForm.submit();
        }
    }

</script>
<s:form action="/cancelNotUsedBillsReasonAction!cancelNotUsedBillsComplete.do" method="POST" id="CancelNotUsedBillsReasonForm" theme="simple">
<s:token/>

    <fieldset style="width:95%; padding:10px 10px 10px 10px">
        <legend class="transparent"><tags:label key="MSG.info.destroy.invoice"></legend>
            <table class="inputTbl">
                <tr>
                    <td>
                        <tags:label key="MSG.bill.sign">
                        </td>
                        <td>
                            <s:textfield name= "#session.billManagerViewHelper.serialNo" id="billSerial" cssClass="txtInput" disabled="true"/>
                        </td>
                        <td >
                            <tags:label key="MSG.bill.range">
                            </td>
                            <td >
                                <s:textfield name="#session.billManagerViewHelper.fromToInvoiceNo" id="fromToInvoiceNo" cssClass="txtInput" disabled="true"/>
                            </td>
                        </tr>
                        <s:if test="#session.staffId != null">
                            <tr>
                                <td>
                                    <tags:label key="MSG.nowBillNo"/>
                                </td>
                                <td>
                                    <s:textfield name= "#session.billManagerViewHelper.currentInvoiceNo" id="currentInvoiceNo" maxlength="10" cssClass="txtInput" disabled="true"/>
                                </td>
                            </tr>
                        </s:if>
                        <s:else>
                            <tr>
                                <td>
                                    <tags:label key="MSG.destroy.from.bill.No"/>
                                </td>
                                <td>
                                    <s:textfield name="cancelForm.billCancelStartNumber" value= "%{#session.billManagerViewHelper.billCancelStartNumber}" maxlength="10" id="billStartNumber" cssClass="txtInput"/>
                                </td>
                                <td>
                                    <tags:label key="MSG.destroy.to.bill.No"/>
                                </td>
                                <td>
                                    <s:textfield name="cancelForm.billCancelEndNumber" value= "%{#session.billManagerViewHelper.billSplitEndNumber}" maxlength="10" id="billEndNumber" cssClass="txtInput"/>
                                </td>
                            </tr>
                        </s:else>
                        <tr>
                            <td>
                                <tags:label key="MSG.reason.cancel">
                                </td>
                                <td>
                                    <s:select name="cancelForm.cancelBillReason"
                                              id="cancelBillReason"
                                              cssClass="txtInput"
                                              headerKey="" headerValue="--Chọn lý do hủy--"
                                              list="%{#session.reasonList != null?#session.reasonList:#{}}"
                                              listKey="reasonId" listValue="reasonName"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4" align="center">
                                    <tags:displayResult id="displayResultMsgClient" property="returnMsg" type="key" />
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <br />
                    <div s tyle="width:100%" align="center">
                        <%--<s:submit value="Hủy hóa đơn" key="MSG.cancel" onclick="return isValidate();" />--%>
                        <s:submit key="MSG.cancel" onclick="return isValidate();" />

                    </div>

                    </s:form>
                    <script type="text/javascript" language="javascript">
                        var regExp1 = /^[0-9]+$/;
                        isValidate = function(){
                            var staffId = '${fn:escapeXml(staffId)}';
                            if(staffId == null || staffId.trim() == ""){
                                if($('billStartNumber').value.trim() == ""){
                                    //                                                $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.ISN.010')"/>';
                                    $('displayResultMsgClient').innerHTML= '<<s:text name="ERR.ISN.010"/>';
                                    //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa nhập số hóa đơn bắt đầu";
                                    $( 'billStartNumber' ).focus();
                                    return false;
                                }

                                var result1 = $( 'billStartNumber' ).value.trim().match(regExp1);
                                if (result1 == null) {
                                    //                                                $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.ISN.011')"/>';
                                    $('displayResultMsgClient').innerHTML= '<<s:text name="ERR.ISN.011"/>';
                                    //$( 'displayResultMsgClient' ).innerHTML = "Số hóa đơn bắt đầu không hợp lệ";
                                    $( 'billStartNumber' ).select();
                                    $( 'billStartNumber' ).focus();
                                    return false;
                                }
                                if($('billEndNumber').value.trim() == ""){
                                    //                                                $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.ISN.012')"/>';
                                    $('displayResultMsgClient').innerHTML= '<s:text name="ERR.ISN.012"/>';
                                    //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa nhập số hóa đơn kết thúc";
                                    $( 'billEndNumber' ).focus();
                                    return false;
                                }

                                var result2 = $( 'billEndNumber' ).value.trim().match(regExp1);
                                if (result2 == null) {
                                    //                                                $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.ISN.013')"/>';
                                    $('displayResultMsgClient').innerHTML= '<s:text name="ERR.ISN.013"/>';
                                    //$( 'displayResultMsgClient' ).innerHTML = "Số hóa đơn kết thúc không hợp lệ";
                                    $( 'billEndNumber' ).select();
                                    $( 'billEndNumber' ).focus();
                                    return false;
                                }
                                var intResult1 = parseInt(result1);
                                var intResult2 = parseInt(result2);
                                if(intResult1 > intResult2){
                                    //                                                $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.ISN.004')"/>';
                                    $('displayResultMsgClient').innerHTML= '<s:text name="ERR.ISN.004"/>';
                                    //$( 'displayResultMsgClient' ).innerHTML = "Số hóa đơn bắt đầu phải nhỏ hơn số hóa đơn kết thúc";
                                    $( 'billStartNumber' ).select();
                                    $( 'billStartNumber' ).focus();
                                    return false;
                                }
                            }
                            if ($('cancelBillReason').value == ""){
                                //                                            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.ISN.014')"/>';
                                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.ISN.014"/>';
                                //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa chọn lý do hủy";
                                return false;
                            }
                        }
                    </script>
