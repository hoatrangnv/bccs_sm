<%-- 
    Document   : DebitDayType
    Created on : Apr 26, 2013, 11:11:20 AM
    Author     : linhnt28
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%
    request.setAttribute("contextPath", request.getContextPath());
    if (request.getAttribute("lstListLimitType") == null) {
        request.setAttribute("lstListLimitType", request.getSession().getAttribute("lstListLimitType"));
    }
%>
<s:if test="#session.toEditDebitDayType == 0">
    <s:set var="readonly" value="0" scope="request"/>
</s:if>
<s:else>
    <s:if test="#session.toEditDebitDayType == 1">
        <s:set var="readonly" value="1" scope="request"/>
    </s:if>
</s:else>

<s:form action="debitDayTypeAction" theme="simple" method="post" id="debitDayTypeForm">
    <tags:imPanel title="TITLE.DEBIT.DAY.TYPE.001">
        <!-- hien thi message -->
        <div>
            <tags:displayResult id="message" property="message" propertyValue="nessage" type="key"/>
        </div>
        <table class="inputTbl4Col">
            <tr>
                <td class="label"><tags:label key="TITLE.DEBIT.DAY.TYPE.003" required="true"/></td>

                <td class="text">
                    <tags:imSelect name="debitDayTypeForm.debitDayType"
                                   id="debitDayTypeForm.debitDayType"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.STOCK.CONFIGURATION.LIMITS.SELECT.TYPE"
                                   disabled="false"
                                   list="lstListLimitType"
                                   listKey="code" listValue="name"
                                   />
                    <s:if test="#request.readonly == 1">
                        <s:hidden name="debitDayTypeForm.debitDayType" id="debitDayTypeHidden"/>
                    </s:if>

                </td>
                <td class="label"><tags:label key="TITLE.DEBIT.DAY.TYPE.006" required="true"/></td>
                <td class="text">
                    <tags:imSelect name="debitDayTypeForm.status"
                                   id="debitDayTypeForm.status"
                                   cssClass="txtInputFull"
                                   headerKey=""
                                   headerValue="MSG.GOD.189"
                                   list="1:MSG.active, 0:MSG.inactive"

                                   />
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="TITLE.DEBIT.DAY.TYPE.004" required="true"/></td>
                <td class="text">
                    <tags:dateChooser property="debitDayTypeForm.staDate"  id="debitDayTypeForm.staDate"  styleClass="txtInput"  insertFrame="false"
                                      />
                </td>

                <td class="label"><tags:label key="TITLE.DEBIT.DAY.TYPE.005" required="true"/></td>
                <td class="text">
                    <tags:dateChooser property="debitDayTypeForm.endDate"  id="debitDayTypeForm.endDate"  styleClass="txtInput"  insertFrame="false"
                                      />
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="lbl.chon_cong_van"/></td>
                <td>
                    <tags:imFileUpload id="fileUpload" name="debitDayTypeForm.fileName" 
                                       serverFileName="debitDayTypeForm.serverFileName" extension="pdf;jpg;png;jpeg"/>
                </td>
                <td class="label"><tags:label key="lbl_ten_dot_km" required="true"/></td>
                <td class="text">
                    <s:textfield name="debitDayTypeForm.ddtName" id="ddtName" cssClass="txtInputFull" maxlength="200" theme="simple" />
                </td>
            </tr>
        </table>
        <s:hidden name="debitDayTypeForm.debitDayTypeId" id="debitDayTypeForm.debitDayTypeId"/>

        <div align="center" style="vertical-align:top; ">
            <s:if test="#request.readonly == 0">
                <tags:submit formId="debitDayTypeForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             id="btnAdd"
                             validateFunction="checkValidate()"
                             value="MSG.GOD.010"
                             targets="bodyContent"
                             preAction="debitDayTypeAction!addNewDebitDayType.do"/>
                <tags:submit targets="bodyContent"
                             formId="debitDayTypeForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.009"
                             showLoadingText="true"
                             preAction="debitDayTypeAction!searchDebitDayType.do"
                             />
            </s:if>
            <s:if test="#request.readonly == 1">
                <tags:submit targets="bodyContent"
                             formId="debitDayTypeForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.020"
                             validateFunction="checkValidate();"
                             confirm="true" confirmText="confirm.updateDebitType"
                             showLoadingText="true"
                             preAction="debitDayTypeAction!editDebitDayType.do"
                             />
                <tags:submit targets="bodyContent"
                             formId="debitDayTypeForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.018"
                             showLoadingText="true"
                             preAction="debitDayTypeAction!preparePage.do"
                             />
            </s:if>
        </div>
    </tags:imPanel>
</s:form>
<br/>
<tags:imPanel title="MSG.listSearResult">
    <div id="listDebitDayType">
        <jsp:include page="listDebitDayType.jsp"/>
    </div>
</tags:imPanel>

<script type="text/javascript" language="javascript">
    <s:if test="#request.readonly == 1">
        jQuery("#debitDayTypeForm\\.debitDayType").attr("disabled","true");
    </s:if>
    <s:else>
        jQuery("#debitDayTypeForm\\.debitDayType").attr("disabled","");
    </s:else>
    checkValidate = function() {
        var dateStart = dojo.widget.byId("debitDayTypeForm.staDate");
        var dateEnd = dojo.widget.byId("debitDayTypeForm.endDate");
        $('message').innerHTML = "";

        if (trim($('debitDayTypeForm.debitDayType').value) == "") {
            $('message').innerHTML = "";
            $('message').innerHTML = '<s:property value="getText('ERR.DEBIT.DAY.TYPE.001')"/>';
            $('debitDayTypeForm.debitDayType').focus();
            return false;
        }
        if (trim($('debitDayTypeForm.status').value) == "") {
            $('message').innerHTML = "";
            $('message').innerHTML = '<s:property value="getText('ERR.DEBIT.DAY.TYPE.002')"/>';
            $('debitDayTypeForm.status').focus();
            return false;
        }
        if (dateStart.domNode.childNodes[1].value.trim() == "") {
            $('message').innerHTML = "";
            $('message').innerHTML = '<s:property value="getText('ERR.DEBIT.DAY.TYPE.003')"/>';
            $('debitDayTypeForm.staDate').focus();
            return false;
        }
        if (dateEnd.domNode.childNodes[1].value.trim() == "") {
            $('message').innerHTML = "";
            $('message').innerHTML = '<s:property value="getText('ERR.DEBIT.DAY.TYPE.004')"/>';
            $('debitDayTypeForm.endDate').focus();
            return false;
        }

        if (dateStart.domNode.childNodes[1].value.trim() != "" && !isDateFormat(dateStart.domNode.childNodes[1].value)) {
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày bắt đầu không hợp lệ';--%>
                $('message').innerHTML = '<s:property value="getText('ERR.SIK.025')"/>';
                $('debitDayTypeForm.staDate').focus();
                return false;
            }
            if (dateEnd.domNode.childNodes[1].value != "" && !isDateFormat(dateEnd.domNode.childNodes[1].value)) {
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày kết thúc không hợp lệ';--%>
                $('message').innerHTML = '<s:property value="getText('ERR.LST.046')"/>';
                $('debitDayTypeForm.endDate').focus();
                return false;
            }
            if (dateEnd.domNode.childNodes[1].value != "" && dateStart.domNode.childNodes[1].value.trim() != "" && !isCompareDate(dateStart.domNode.childNodes[1].value.trim(), dateEnd.domNode.childNodes[1].value.trim(), "VN")) {
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày bắt đầu phải nhỏ hơn ngày kết thúc';--%>
                $('message').innerHTML = '<s:property value="getText('ERR.SIK.027')"/>';
                $('debitDayTypeForm.staDate').focus();
                return false;
            }

            if (trim($('ddtName').value) == "") {
                $('message').innerHTML = "";
                $('message').innerHTML= '<s:property value="getText('err.chua_nhap_ten_dot_km')"/>';
                $('ddtName').focus();
                return false;
            }
            
            if(checkingSpecialCharacter($('ddtName').value)){
                $('message').innerHTML = "";
                $('message').innerHTML= '<s:property value="getText('err.ten_dot_km_khong_duoc_chua_ktdb')"/>';
                $('ddtName').focus();
                return false;
            }
            return true;
        }

        delDebitDayType = function(debitDayTypeId) {
            var strConfirm = getUnicodeMsg('<s:property value="getText('confirm.deleteDebitType')"/>');
            if (confirm(strConfirm)) {
                gotoAction('bodyContent', 'debitDayTypeAction!deleteDebitDayType.do?debitDayTypeId=' + debitDayTypeId + "&" + token.getTokenParamString());
            }
        }
        downloads = function(id) {
            window.open("${contextPath}/debitDayTypeAction!download.do?debitDayTypeForm.debitDayTypeId=" + id, '', 'toolbar=no,scrollbars=no');
        }
</script>
