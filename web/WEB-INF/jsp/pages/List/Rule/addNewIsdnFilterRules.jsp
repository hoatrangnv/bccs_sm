<%--
    Document   : addNewGroupFilterRuler
    Created on : Mar 5, 2009, 3:28:30 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:if test="#request.isdnFilterRulesMode == 'prepareAddOrEdit'">
    <s:set var="readonly" value="0" scope="request"/>
</s:if>

<s:else>
    <s:if test="#request.isdnFilterRulesMode == 'copy'">
        <s:set var="readonly" value="1" scope="request"/>
    </s:if>
    <s:else>
        <s:set var="readonly" value="2" scope="request"/>

    </s:else>
</s:else>

<tags:imPanel title="title.addNewIsdnFilterRules.page">
    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
    </div>

    <div class="divHasBorder">
        <!-- phan thong tin luat -->
        <s:form action="isdnFilterRulesAction" theme="simple" id="isdnForm" method="post">
            <s:token/>

            <s:hidden name="isdnForm.rulesId" id="isdnForm.rulesId"/>
            <table class="inputTbl6Col">
                <tr>
                    <td class="label"><tags:label key="MSG.rules.name" required="true" /></td>
                    <td class="text">
                        <s:textfield name="isdnForm.name" id="name" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.rules.of.set" required="true" /> </td>
                    <td class="text">

                        <tags:imSelect name="isdnForm.groupFilterRuleCode"
                                       id="groupFilterRuleCode"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.RET.166"
                                       list="listGroupFilterRules"
                                       onchange="changeGroupFilterRule(this.value)"
                                       listKey="groupFilterRuleCode" listValue="name"/>
                    </td>
                    <td class="label"><tags:label key="MSG.filterTypeRule" required="true" /> </td>
                    <td class="text">

                        <tags:imSelect name="isdnForm.filterTypeId"
                                       id="filterTypeId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.LST.015"
                                       list="listFilterTypes"
                                       listKey="filterTypeId" listValue="name"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.price" required="true" /> </td>
                    <td class="text">
                        <s:textfield name="isdnForm.price" id="price" onkeyup="textFieldNF(this)" maxlength="15" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.masks" required="true" /> </td>
                    <td class="text">
                        <s:textfield name="isdnForm.maskMapping" id="maskMapping" maxlength="20" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.priority.level" required="true" /> </td>
                    <td class="text">
                        <s:textfield name="isdnForm.ruleOrder" id="ruleOrder" maxlength="10" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.conditionFilter" required="true" /> </td>
                    <td class="text" colspan="5">
                        <s:textarea name="isdnForm.condition" id="condition" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.discription" required="false" /></td>
                    <td class="text" colspan="5">
                        <s:textarea name="isdnForm.notes" id="notes" cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <div align="center" style="padding-bottom:5px;">
            <s:if test="#request.readonly == 0">

                <tags:submit targets="bodyContent" formId="isdnForm"
                             cssStyle="width:80px;" value="MSG.accept"
                             showLoadingText="true"
                             validateFunction="checkRaV();"
                             preAction="isdnFilterRulesAction!insertOrUpDateIsdnFilterRules.do"
                             />
                <tags:submit targets="bodyContent" formId="isdnForm"
                             cssStyle="width:80px;" value="MSG.cancel2"
                             showLoadingText="true"
                             preAction="isdnFilterRulesAction!preparePage.do"
                             />



            </s:if>
            <s:if test="#request.readonly == 2">

                <tags:submit targets="bodyContent" formId="isdnForm"
                             cssStyle="width:80px;" value="MSG.generates.addNew"
                             validateFunction="checkRaV();"
                             showLoadingText="true"
                             preAction="isdnFilterRulesAction!insertOrUpDateIsdnFilterRules.do"
                             />
                <tags:submit targets="bodyContent" formId="isdnForm"
                             cssStyle="width:80px;" value="MSG.find"
                             validateFunction="checkValidFields();"
                             showLoadingText="true"
                             preAction="isdnFilterRulesAction!searchIsdnFilterRules.do"
                             />


                <tags:submit formId="isdnForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.report"
                             preAction="isdnFilterRulesAction!exportReportIsdnFilterRule.do"/>
                <s:if test="isdnForm.pathExpFile != null && isdnForm.pathExpFile!=''">
                    <script type="" >
                        window.open('${contextPath}<s:property escapeJavaScript="true"  value="isdnForm.pathExpFile"/>','','toolbar=yes,scrollbars=yes');
                    </script>
                    <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="isdnForm.pathExpFile"/>' ><tags:label key="MSG.SAE.158" /></a></div>
                </s:if>
            </s:if>
            <s:if test="#request.readonly == 1">

                <tags:submit targets="bodyContent" formId="isdnForm"
                             cssStyle="width:80px;" value="MSG.copy"
                             showLoadingText="true"
                             validateFunction="checkRaV();"
                             preAction="isdnFilterRulesAction!insertOrUpDateIsdnFilterRules.do?iscopy=1"
                             />
                <tags:submit targets="bodyContent" formId="isdnForm"
                             cssStyle="width:80px;" value="MSG.cancel2"
                             showLoadingText="true"
                             preAction="isdnFilterRulesAction!preparePage.do"
                             />


            </s:if>
        </div>

    </div>

    <!-- danh sach cac luat -->
    <sx:div id="divListIsdnFilterRules">
        <jsp:include page="listIsdnFilterRules.jsp"/>
    </sx:div>

</tags:imPanel>

<script>
    //focus vao truong dau tien
    $('name').select();
    $('name').focus();

    //dinh danh cho truong price
    textFieldNF($('price'));

    //xu ly su kien khi GroupFilterRuleCode change
    //Ham nay lay group filter
    changeGroupFilterRule = function(groupFilterRuleCode) {
    updateData("${contextPath}/isdnFilterRulesAction!updateListFilterType.do?target=filterTypeId&groupFilterRuleCode=" + groupFilterRuleCode+"&"+ token.getTokenParamString());
    }

    checkRaV=function(){
    return (checkRequiredFields() && checkValidFields());
    }
    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
    //
    if(trim($('name').value) == "") {
    <%--$('message').innerHTML = "!!!Lỗi. Trường tên luật không được để trống";--%>
    //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.025')"/>';
    $('message').innerHTML= '<s:text name="ERR.LST.025"/>';
    $('name').select();
    $('name').focus();
    return false;
    }
    if(trim($('groupFilterRuleCode').value) == "") {
    <%--$('message').innerHTML = "!!!Lỗi. Chọn tập luật";--%>
    //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.023')"/>';
    $('message').innerHTML= '<s:text name="ERR.LST.023"/>';
    $('groupFilterRuleCode').focus();
    return false;
    }
    if(trim($('filterTypeId').value) == "") {
    <%--$('message').innerHTML = "!!!Lỗi. Chọn nhóm luật";--%>
    //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.026')"/>';
    $('message').innerHTML= '<s:text name="ERR.LST.026"/>';
    $('filterTypeId').focus();
    return false;
    }

    if(trim($('price').value) == "") {
    <%--$('message').innerHTML = "!!!Lỗi. Trường giá không được để trống";--%>
    //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.027')"/>';
    $('message').innerHTML= '<s:text name="ERR.LST.027"/>';
    $('price').select();
    $('price').focus();
    return false;
    }
    if(trim($('maskMapping').value) == "") {
    <%--$('message').innerHTML = "!!!Lỗi. Trường mặt nạ không được để trống";--%>
    //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.028')"/>';
    $('message').innerHTML= '<s:text name="ERR.LST.028"/>';
    $('maskMapping').select();
    $('maskMapping').focus();
    return false;
    }
    if(trim($('ruleOrder').value) == "") {
    <%--$('message').innerHTML = "!!!Lỗi. Trường cấp ưu tiên không được để trống";--%>
    //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.029')"/>';
    $('message').innerHTML= '<s:text name="ERR.LST.029"/>';
    $('ruleOrder').select();
    $('ruleOrder').focus();
    return false;
    }
    if(trim($('condition').value) == "") {
    <%--$('message').innerHTML = "!!!Lỗi. Trường điều kiện lọc không được để trống";--%>
    //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.030')"/>';
    $('message').innerHTML= '<s:text name="ERR.LST.030"/>';
    $('condition').select();
    $('condition').focus();
    return false;
    }

    return true;
    }

    //kiem tra ma khong duoc chua cac ky tu dac biet
    checkValidFields = function() {
    /*
    var name = trim($('name').value);
    if(checkingSpecialCharacter(name)) {
    $('message').innerHTML = "!!!Lỗi. Trường tên luật không được chứa các ký tự đặc biệt";
    $('name').select();
    $('name').focus();
    return false;
    }*/

    var price = trim($('price').value.replace(/\,/g,""));
    if(price != "" && !isInteger(price)) {
    <%--$('message').innerHTML = "!!!Lỗi. Giá phải là số không âm";--%>
    //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.031')"/>';
    $('message').innerHTML= '<s:text name="ERR.LST.031"/>';
    $('price').select();
    $('price').focus();
    return false;
    }

    var maskMapping = trim($('maskMapping').value);
    if(maskMapping != ""  && !stringContainsOnlyLetters(maskMapping, false, false)) {
    <%--$('message').innerHTML = "!!!Lỗi. Trường mặt nạ chỉ được chứa các ký tự A-Z, a-z";--%>
    //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.032')"/>';
    $('message').innerHTML= '<s:text name="ERR.LST.032"/>';
    $('maskMapping').select();
    $('maskMapping').focus();
    return false;
    }

    var ruleOrder = trim($('ruleOrder').value);
    if(ruleOrder != "" && !isInteger(ruleOrder)) {
    <%--$('message').innerHTML = "!!!Lỗi. Cấp ưu tiên phải là số không âm";--%>
    //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.033')"/>';
    $('message').innerHTML= '<s:text name="ERR.LST.033"/>';
    $('ruleOrder').select();
    $('ruleOrder').focus();
    return false;
    }

    /*
    var notes = trim($('notes').value);
    if(checkingSpecialCharacter(notes)) {
    $('message').innerHTML = "!!!Lỗi. Trường mô tả luật không được chứa các ký tự đặc biệt";
    $('notes').select();
    $('notes').focus();
    return false;
    }*/

    var condition = trim($('condition').value);
    if(condition != "" && condition.length > 450) {
    <%--$('message').innerHTML = "!!!Lỗi. Trường điều kiện lọc quá dài";--%>
    //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.034')"/>';
    $('message').innerHTML= '<s:text name="ERR.LST.034"/>';
    $('condition').select();
    $('condition').focus();
    return false;
    }

    var notes = trim($('notes').value);
    if(notes != "" && notes.length > 450) {
    <%--$('message').innerHTML = "!!!Lỗi. Trường mô tả luật quá dài";--%>
    //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.035')"/>';
    $('message').innerHTML= '<s:text name="ERR.LST.035"/>';
    $('notes').select();
    $('notes').focus();
    return false;
    }

    var strMaskMapping = trim($('maskMapping').value);
    var strCondition = trim($('condition').value);
    var bFlag = false;

    if(strMaskMapping != "" && strCondition != "") {
    for (var i = 0; i < strMaskMapping.length; i++) {
    if (strCondition.indexOf(strMaskMapping.charAt(i)) != -1) {
    bFlag = true;
    break;
    }
    }

    if(!bFlag) {
    <%--$('message').innerHTML = "!!!Lỗi. Trường mặt nạ và điều kiện lọc không tương thích";--%>
    //                    $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.036')"/>';
    $('message').innerHTML= '<s:text name="ERR.LST.036"/>';
    $('maskMapping').select();
    $('maskMapping').focus();
    return false;
    }
    }

    //trim cac truong can thiet
    $('name').value = trim($('name').value);
    $('price').value = trim($('price').value);
    $('maskMapping').value = trim($('maskMapping').value);
    $('condition').value = trim($('condition').value);
    $('ruleOrder').value = trim($('ruleOrder').value);
    $('notes').value = trim($('notes').value);

    return true;
    }


</script>

