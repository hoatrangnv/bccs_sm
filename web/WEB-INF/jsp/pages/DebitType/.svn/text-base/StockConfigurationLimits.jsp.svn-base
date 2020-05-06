<%-- 
    Document   : StockConfigurationLimits
    Created on : Apr 23, 2013, 3:14:14 PM
    Author     : linhnt28
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    if (request.getAttribute("lstListLimits") == null) {
        request.setAttribute("lstListLimits", request.getSession().getAttribute("lstListLimits"));
    }
    if (request.getAttribute("lstListLimitType") == null) {
        request.setAttribute("lstListLimitType", request.getSession().getAttribute("lstListLimitType"));
    }
%>


<s:form action="debitTypeAction" theme="simple" method="post" id="debitTypeForm">
    <tags:imPanel title="title.StockConfigurationLimits.page">
        <!-- hien thi message -->
        <div>
            <tags:displayResult id="message" property="message" propertyValue="nessage" type="key"/>
        </div>

        <table class="inputTbl4Col">
            <tr>
                <td class="label"><tags:label key="MSG.STOCK.CONFIGURATION.LIMITS.LIMIT" required="true"/></td>
                <td class="oddColumn">
                    <s:if test="#session.toEditDebitType == 1">
                        <s:hidden name="debitTypeForm.limit" id="limitHidden" theme="simple" 
                                  cssClass="txtInputFull" readonly="true"/>
                        <tags:imSelect name="debitTypeForm.limit"
                                       id="debitTypeForm.limit" theme="simple"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.STOCK.CONFIGURATION.LIMITS.SELECT.LIMIT"
                                       list="lstListLimits"
                                       listKey="code" listValue="name"
                                       />
                    </s:if>
                    <s:else>
                        <tags:imSelect name="debitTypeForm.limit"
                                       id="debitTypeForm.limit" theme="simple"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.STOCK.CONFIGURATION.LIMITS.SELECT.LIMIT"
                                       list="lstListLimits"
                                       listKey="code" listValue="name"
                                       />
                    </s:else>
                </td>
                <td class="label"><tags:label key="MSG.STOCK.CONFIGURATION.LIMITS.TYPE" required="true"/></td>
                <td class="oddColumn">
                    <s:if test="#session.toEditDebitType == 1">
                        <s:hidden name="debitTypeForm.type" id="typeHidden" theme="simple" 
                                  cssClass="txtInputFull" readonly="true"/>
                        <tags:imSelect name="debitTypeForm.type" id="debitTypeForm.type" theme="simple" 
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.STOCK.CONFIGURATION.LIMITS.SELECT.LIMIT"
                                       list="lstListLimitType"
                                       listKey="code" listValue="name"
                                       />
                    </s:if>
                    <s:else>
                        <tags:imSelect name="debitTypeForm.type"
                                       id="debitTypeForm.type" theme="simple"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.STOCK.CONFIGURATION.LIMITS.SELECT.TYPE"
                                       list="lstListLimitType"
                                       listKey="code" listValue="name"
                                       />
                    </s:else>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.STOCK.CONFIGURATION.LIMITS.VALUE" required="true"/></td>
                <td>
                    <s:textfield name="debitTypeForm.value" style="text-align:right" id="value" maxlength="18" cssClass="txtInputFull" />
                </td>
                <td><tags:label key="MSG.LIMITED.06" required="true"/></td>
                <td>
                    <tags:imSelect name="debitTypeForm.status"
                                   id="status"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.GOD.189"
                                   list="0:MSG.GOD.274, 1:MSG.GOD.297"/>
                </td>
                <s:hidden name="debitTypeForm.debitTypeId" id="debitTypeForm.debitTypeId" />
            </tr>

        </table>
        <div align="center" style="vertical-align:top; ">
            <s:if test="#session.toEditDebitType == 0">
                <tags:submit formId="debitTypeForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             id="btnAdd"
                             validateFunction="checkValidate()"
                             value="MSG.GOD.010"
                             targets="bodyContent"
                             preAction="debitTypeAction!addNewDebitType.do"/>
                <tags:submit targets="bodyContent"
                             formId="debitTypeForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.009"
                             showLoadingText="true"
                             preAction="debitTypeAction!searchDebitType.do"
                             />
            </s:if>
            <s:if test="#session.toEditDebitType == 1">
                <tags:submit targets="bodyContent" 
                             formId="debitTypeForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.020"
                             validateFunction="checkValidate();"
                             confirm="true" confirmText="confirm.updateDebitType"
                             showLoadingText="true"
                             preAction="debitTypeAction!editDebitType.do"
                             />
                <tags:submit targets="bodyContent" 
                             formId="debitTypeForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.018"
                             showLoadingText="true"
                             preAction="debitTypeAction!preparePage.do"
                             />
            </s:if>
            <br/>

        </div>

    </tags:imPanel>
    <br/>
    <tags:imPanel title="MSG.listSearResult">
        <div id="listDebitType">
            <jsp:include page="listDebitType.jsp"/>
        </div>
    </tags:imPanel>
</s:form>
<script type="text/javascript" language="javascript">
    <s:if test="#session.toEditDebitType == 1">
        jQuery("#debitTypeForm\\.type").attr("disabled","true");
        jQuery("#debitTypeForm\\.limit").attr("disabled","true");
    </s:if>
    <s:else>
        jQuery("#debitTypeForm\\.type").attr("disabled","");
        jQuery("#debitTypeForm\\.limit").attr("disabled","");
    </s:else>
    checkValidate = function() {
        $('message').innerHTML = "";
        if (trim($('debitTypeForm.limit').value) == "") {
            $('message').innerHTML = "";
            $('message').innerHTML = '<s:property value="getText('ERR.CONFIGURATION.LIMITS.001')"/>';
            //                    $('message').innerHTML = "!!!Lỗi. Chọn mức hạn mức";
            $('debitTypeForm.limit').focus();
            return false;
        }
        if (trim($('debitTypeForm.type').value) == "") {
            $('message').innerHTML = "";
            $('message').innerHTML = '<s:property value="getText('ERR.CONFIGURATION.LIMITS.002')"/>';
            //                    $('message').innerHTML = "!!!Lỗi. Chọn loại hạn mức";
            $('debitTypeForm.type').focus();
            return false;
        }
        if (trim($('value').value) == "") {
            $('message').innerHTML = "";
            $('message').innerHTML = '<s:property value="getText('ERR.CONFIGURATION.LIMITS.003')"/>';
            //                    $('message').innerHTML = "!!!Lỗi. Nhập giá trị bắt buộc";
            $('value').focus();
            return false;
        }
        var a = trim($('value').value).length;
        if (a > 18 || a == 0) {
            $('message').innerHTML = "";
            $('message').innerHTML = '<s:property value="getText('ERR.CONFIGURATION.LIMITS.004')"/>';
            //                    $('message').innerHTML = "!!!Lỗi. Giá trị phải là số trong khoảng từ 1 đến 20";
            $('value').select();
            $('value').focus();
            return false;
        }
        if (!isMoneyFormat(trim($('value').value))) {
            $('message').innerHTML = "";
            $('message').innerHTML = '<s:property value="getText('ERR.CONFIGURATION.LIMITS.005')"/>';
            //                    $('message').innerHTML = "!!!Lỗi. Giá trị phải là số không âm";
            $('value').select();
            $('value').focus();
            return false;
        }
        if (trim($('status').value) == "") {
            $('message').innerHTML = "";
            $('message').innerHTML = '<s:property value="getText('ERR.DET.087')"/>';
            //                    $('message').innerHTML = "!!!Lỗi. Nhập giá trị bắt buộc";
            $('status').focus();
            return false;
        }
        
        if(!isNumberFormat($('value').value)){
            $('message').innerHTML = "";
            $('message').innerHTML = '<s:property value="getText('err.debit_type_is_must_number')"/>';
            $('value').select();
            $('value').focus();
            return false;
        }
        return true;
    }
    delDebitType = function(debitTypeId) {
        var strConfirm = getUnicodeMsg('<s:property value="getText('confirm.deleteDebitType')"/>');
        if (confirm(strConfirm)) {
            gotoAction('bodyContent', 'debitTypeAction!deleteDebitType.do?debitTypeId=' + debitTypeId + "&" + token.getTokenParamString());
        }
    }


</script>

