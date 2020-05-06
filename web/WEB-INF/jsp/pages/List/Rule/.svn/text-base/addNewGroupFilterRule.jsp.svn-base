<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : addNewGroupFilterRule
    Created on : Mar 5, 2009, 3:28:30 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:if test="#request.groupFilterRuleMode == 0">
    <s:set var="readonly" value="0" scope="request"/>
</s:if>

<s:else>
    <s:if test="#request.groupFilterRuleMode == 1">
        <s:set var="readonly" value="1" scope="request"/>
    </s:if>
    <s:else>
        <s:set var="readonly" value="2" scope="request"/>

    </s:else>
</s:else>

<tags:imPanel title="title.addNewGroupFilterRule.page">

    <!-- hien thi message -->
    <div>
        <tags:displayResult id="message" property="returnMsg" propertyValue="returnMsgParam" type="key"/>
    </div>

    <div class="divHasBorder">
        <!-- phan thong tin tap luat -->
        <s:form action="groupFilterRuleAction" theme="simple" enctype="multipart/form-data" id="groupFilterForm" method="post">
<s:token/>

            <s:hidden name="groupFilterForm.groupFilterRuleId" id="groupFilterForm.groupFilterRuleId"/>
            <table class="inputTbl6Col">
                <tr>
                    <td class="label"><tags:label key="MSG.rules.of.set.code" required="true" /> </td>
                    <td class="text">
                        <s:textfield name="groupFilterForm.groupFilterRuleCode" id="groupFilterRuleCode" maxlength="10" cssClass="txtInputFull" readonly="%{#request.groupFilterRuleMode == 'prepareEdit'}"/>
                    </td>
                    <td class="label"><tags:label key="MSG.rules.of.set.name" required="true" /> </td>
                    <td class="text">
                        <s:textfield name="groupFilterForm.name" id="name" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.resource.type" required="true" /> </td>
                    <td class="text">
                        <tags:imSelect name="groupFilterForm.stockTypeId" id="stockTypeId"
                                       cssClass="txtInputFull" disabled="false"
                                       list="2: MSG.homephoneNumber, 1: MSG.mobileNumber, 3:MSG.pstnNumber"
                                       listKey="abc" listValue=""
                                       headerKey="" headerValue="MSG.LST.008"/>

                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.discription" required="false" /></td>
                    <td class="text" colspan="5" >
                        <s:textarea name="groupFilterForm.notes" id="notes" cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <div align="center" style="vertical-align:top; padding-top:5px; padding-bottom: 5px;">
            <s:if test="#request.readonly == 1">
                <tags:submit targets="bodyContent" formId="groupFilterForm"
                             cssStyle="width:80px;" value="MSG.accept"
                             showLoadingText="true"
                             validateFunction="checkRaV();"
                             preAction="groupFilterRuleAction!insertOrUpDateGroupFilterRule.do"
                             />
                <tags:submit targets="bodyContent" formId="groupFilterForm"
                             cssStyle="width:80px;" value="MSG.cancel2"
                             showLoadingText="true"
                             preAction="groupFilterRuleAction!preparePage.do"
                             />
            </s:if >
            <s:if test="#request.readonly == 0">
                <tags:submit targets="bodyContent" formId="groupFilterForm"
                             cssStyle="width:80px;" value="MSG.generates.addNew"
                             showLoadingText="true"
                             validateFunction="checkRaV();"
                             preAction="groupFilterRuleAction!insertOrUpDateGroupFilterRule.do"
                             />
                <tags:submit targets="bodyContent" formId="groupFilterForm"
                             cssStyle="width:80px;" value="MSG.find"
                             showLoadingText="true"
                             validateFunction="checkValidFields();"
                             preAction="groupFilterRuleAction!search.do"
                             />
            </s:if>
            <s:if test="#request.readonly == 2">
                <tags:submit targets="bodyContent" formId="groupFilterForm"
                             cssStyle="width:80px;" value="MSG.copy"
                             showLoadingText="true"
                             validateFunction="checkRaV();"
                             preAction="groupFilterRuleAction!copyGroupFilterRule.do"
                             />
                <tags:submit targets="bodyContent" formId="groupFilterForm"
                             cssStyle="width:80px;" value="MSG.cancel2"
                             showLoadingText="true"
                             preAction="groupFilterRuleAction!preparePage.do"
                             />
            </s:if>

        </div>
    </div>

    <!-- danh sach tap luat -->
    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MSG.rule.of.set.list'))}</legend>
        <sx:div id="divListGroupFilterRule">
            <jsp:include page="listGroupFilterRule.jsp"/>
        </sx:div>
    </fieldset>


</tags:imPanel>

<script type="text/javascript">

    //focus vao truong dau tien
    $('name').select();
    $('name').focus();

    checkRaV=function(){
        return (checkRequiredFields() && checkValidFields());
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        //truong ma tap luat
        if(trim($('groupFilterRuleCode').value) == "") {
            $('message').innerHTML = "!!!Lỗi. Trường mã tập luật không được để trống";
            $('groupFilterRuleCode').select();
            $('groupFilterRuleCode').focus();
            return false;
        }
        if(trim($('name').value) == "") {
            $('message').innerHTML = "!!!Lỗi. Trường tên tập luật không được để trống";
            $('name').select();
            $('name').focus();
            return false;
        }
        if(trim($('stockTypeId').value) == "") {
            $('message').innerHTML = "!!!Lỗi. Chọn loại tài nguyên";
            $('stockTypeId').focus();
            return false;
        }

        return true;
    }

    //kiem tra ma khong duoc chua cac ky tu dac biet
    checkValidFields = function() {
        if(trim($('groupFilterRuleCode').value)!="" && !isValidInput(trim($('groupFilterRuleCode').value), false, false)) {
            $('message').innerHTML = "!!!Lỗi. Trường mã tập luật chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
            $('groupFilterRuleCode').select();
            $('groupFilterRuleCode').focus();
            return false;
        }
        var notes = trim($('notes').value);
        if(notes != "" && notes.length > 450) {
            $('message').innerHTML = "!!!Lỗi. Trường mô tả tập luật quá dài";
            $('notes').select();
            $('notes').focus();
            return false;
        }

        //trim cac truong can thiet
        $('groupFilterRuleCode').value = trim($('groupFilterRuleCode').value);
        $('name').value = trim($('name').value);
        $('notes').value = trim($('notes').value);

        return true;
    }


    </script>
