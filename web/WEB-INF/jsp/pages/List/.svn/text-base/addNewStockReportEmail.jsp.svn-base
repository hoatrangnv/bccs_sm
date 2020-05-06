<%-- 
    Document   : addNewStockReportEmail
    Created on : Jun 5, 2013, 10:16:34 AM
    Author     : LeVt1_S
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            if (request.getAttribute("listStockReportEmail") == null) {
                request.setAttribute("listStockReportEmail", request.getSession().getAttribute("listStockReportEmail"));
            }
%>

<tags:imPanel title="MSG.LST.StockReportEmail">

    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="displayResultMsgClient" property="returnMsg"  propertyValue="paramMsg" type="key" />
    </div>

    <!-- phan hien thi thong tin ve vu viec tai chinh -->
    <div>
        <s:form action="stockReportEmailAction" theme="simple" enctype="multipart/form-data"  method="post" id="stockReportEmailForm">
            <s:hidden name="stockReportEmailForm.id" id="stockReportEmailForm.id"/>
            <table class="inputTbl6Col">
                <tr>
                    <td class="label"><tags:label key="MES.CHL.128" required="true" /> </td>
                    <td class="text">
                        <s:textfield name="stockReportEmailForm.email" id="stockReportEmailForm.email" maxlength="50" cssClass="txtInputFull"/>(@viettel.com.vn)
                    </td>
                    <td class="label"><tags:label key="MSG.report.type" required="true" /></td>
                    <td class="text">
                        <tags:imSelect name="stockReportEmailForm.reportType" id="stockReportEmailForm.reportType"
                                       cssClass="txtInputFull" disabled="false"
                                       headerKey="" headerValue="L.200045"
                                       list="1:MSG.Serial.Stock"/>
                    </td>
                    <td class="label"><tags:label key="MSG.generates.status" required="true" /></td>
                    <td class="text">
                        <tags:imSelect name="stockReportEmailForm.status" id="stockReportEmailForm.status"
                                       cssClass="txtInputFull" disabled="false"
                                       headerKey="" headerValue="MSG.GOD.189"
                                       list="1:MSG.GOD.002, 0:MSG.GOD.003"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.discription" /></td>
                    <td class="text" colspan="5" >
                        <s:textarea name="stockReportEmailForm.description" id="stockReportEmailForm.description" cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
            <s:token/>
        </s:form>
    </div>
    <br />
    <!-- phan nut tac vu -->
    <div align="center" style="padding-bottom:5px; ">
        <s:if test="#session.toEditStockReportEmail == 0">
            <tags:submit targets="bodyContent" formId="stockReportEmailForm"
                         cssStyle="width:80px;" value="MSG.GOD.010"
                         validateFunction="validateForm()"
                         confirm="true" confirmText="MSG.confirm.add"
                         showLoadingText="true"
                         preAction="stockReportEmailAction!addNewStockReportEmail.do"
                         />
            <tags:submit targets="bodyContent" formId="stockReportEmailForm"
                         cssStyle="width:80px;" value="MSG.GOD.009"
                         showLoadingText="true"
                         preAction="stockReportEmailAction!searchStockReportEmail.do"
                         />
        </s:if>
        <s:if test="#session.toEditStockReportEmail == 2">
            <tags:submit targets="bodyContent" formId="stockReportEmailForm"
                         cssStyle="width:80px;" value="MSG.copy"
                         validateFunction="validateForm()"
                         confirm="true" confirmText="Confirm.Copy"
                         showLoadingText="true"
                         preAction="stockReportEmailAction!copyStockReportEmail.do"
                         />
            <tags:submit targets="bodyContent" formId="stockReportEmailForm"
                         cssStyle="width:80px;" value="MSG.cancel2"
                         showLoadingText="true"
                         preAction="stockReportEmailAction!prepareStockReportEmailPage.do"
                         />
        </s:if>
        <s:if test="#session.toEditStockReportEmail == 1">
            <tags:submit targets="bodyContent" formId="stockReportEmailForm"
                         cssStyle="width:80px;" value="MSG.SIK.157"
                         validateFunction="validateForm()"
                         confirm="true" confirmText="C.100007"
                         showLoadingText="true"
                         preAction="stockReportEmailAction!editStockReportEmail.do"
                         />

            <tags:submit targets="bodyContent" formId="stockReportEmailForm"
                         cssStyle="width:80px;" value="MSG.cancel2"
                         showLoadingText="true"
                         preAction="stockReportEmailAction!prepareStockReportEmailPage.do"
                         />
        </s:if>
    </div>

</tags:imPanel>

<br />

<tags:imPanel title="MSG.stockReportEmail.list">
    <div id="listStockReportEmail">
        <jsp:include page="addNewStockReportEmailResult.jsp"/>
    </div>
</tags:imPanel>

<script>

    //focus vao truong dau tien
    $('stockReportEmailForm.email').select();
    $('stockReportEmailForm.email').focus();

    //kiem tra tinh hop le cua form truoc khi submit
    validateForm=function(){
        $('displayResultMsgClient' ).innerHTML = "";
        if($('stockReportEmailForm.email').value ==null || $('stockReportEmailForm.email').value.trim() == ""){
            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.Email.01')"/>';
            $('stockReportEmailForm.email').select();
            $('stockReportEmailForm.email').focus();

            return false;
        }
        if($('stockReportEmailForm.reportType').value ==null || $('stockReportEmailForm.reportType').value.trim() == ""){
            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.RET.027')"/>';
            $('stockReportEmailForm.reportType').select();
            $('stockReportEmailForm.reportType').focus();

            return false;
        }
        if($('stockReportEmailForm.status').value ==null || $('stockReportEmailForm.status').value.trim() == ""){
            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.LST.042')"/>';
            $('stockReportEmailForm.status').focus();

            return false;
        }

        if(trim($('stockReportEmailForm.description').value).length > 200) {
            $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.020')"/>';
            $('stockReportEmailForm.description').select();
            $('stockReportEmailForm.description').focus();

            return false;
        }

        //trim cac truong can thiet
        $('stockReportEmailForm.email').value = trim($('stockReportEmailForm.email').value);

        return true;

    }

    //
    delStockReportEmail = function(id) {
        var strConfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getText('MSG.DET.005')"/>');
        if (confirm(strConfirm)) {
            gotoAction('bodyContent','stockReportEmailAction!deleteStockReportEmail.do?id=' + encodeURIComponent(id)+"&"+token.getTokenParamString());
        }
    }
    copyStockReportEmail = function(id) {
        gotoAction('bodyContent', "${contextPath}/stockReportEmailAction!prepareCopyStockReportEmail.do?id=" + id+"&"+token.getTokenParamString());
    }

    editStockReportEmail = function(id) {
        gotoAction('bodyContent', "${contextPath}/stockReportEmailAction!prepareEditStockReportEmail.do?id=" + id+"&"+token.getTokenParamString());
    }
    

</script>
