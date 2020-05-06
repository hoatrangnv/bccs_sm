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
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:if test="#request.filterTypeMode == 0">
    <s:set var="readonly" value="0" scope="request"/>
</s:if>

<s:else>
    <s:if test="#request.filterTypeMode == 1">
        <s:set var="readonly" value="1" scope="request"/>
    </s:if>
    <s:else>
        <s:set var="readonly" value="2" scope="request"/>

    </s:else>
</s:else>

<tags:imPanel title="title.addNewFilterType.page">
    <!-- hien thi message -->
    <div>
        <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
    </div>

    <div class="divHasBorder">
        <!-- thong tin nhom luat -->
        <s:form action="filterTypeAction" id="filterTypeForm" theme="simple"  method="post">
<s:token/>

            <s:hidden name="filterTypeForm.filterTypeId"/>
            <table class="inputTbl6Col">
                <tr>
                    <td class="label"><tags:label key="MSG.rules.of.group.name" required="true" /></td>
                    <td class="text">
                        <s:textfield name="filterTypeForm.name" id="name" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.rules.of.set" required="true" /> </td>
                    <td class="text">
                        <%--<s:select name="filterTypeForm.groupFilterRuleCode"
                                  id="groupFilterRuleCode"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="--Chọn tập luật--"
                                  list="%{#request.listGroupFilterRule != null ? #request.listGroupFilterRule : #{}}"
                                  listKey="groupFilterRuleCode" listValue="name"/>--%>

                        <tags:imSelect name="filterTypeForm.groupFilterRuleCode"
                                       id="groupFilterRuleCode"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.RET.166"
                                       list="listGroupFilterRule"
                                       listKey="groupFilterRuleCode" listValue="name"/>

                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.discription" required="false" /></td>
                    <td class="text" colspan="3">
                        <s:textarea name="filterTypeForm.notes" id="notes" cssClass="txtInputFull" />
                    </td>
                </tr>

            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <div align="center" style="vertical-align:top; padding-top:5px; padding-bottom: 5px;">
            <s:if test="#request.readonly == 1">

                <tags:submit targets="bodyContent" formId="filterTypeForm"
                             cssStyle="width:80px;" value="MSG.accept"
                             showLoadingText="true"
                             validateFunction="checkRaV();"
                             preAction="filterTypeAction!editFilterType.do"
                             />
                <tags:submit targets="bodyContent" formId="filterTypeForm"
                             cssStyle="width:80px;" value="MSG.cancel2"
                             showLoadingText="true"
                             preAction="filterTypeAction!preparePage.do"
                             />
            </s:if>
            <s:if test="#request.readonly == 0">

                <tags:submit targets="bodyContent" formId="filterTypeForm"
                             cssStyle="width:80px;" value="MSG.generates.addNew"
                             validateFunction="checkRaV();"
                             showLoadingText="true"
                             preAction="filterTypeAction!editFilterType.do"
                             />
                <tags:submit targets="bodyContent" formId="filterTypeForm"
                             cssStyle="width:80px;" value="MSG.find"
                             showLoadingText="true"
                             validateFunction="checkValidFields();"
                             preAction="filterTypeAction!search.do"
                             />


            </s:if>
            <s:if test="#request.readonly == 2">

                <tags:submit targets="bodyContent" formId="filterTypeForm"
                             cssStyle="width:80px;" value="MSG.copy"
                             showLoadingText="true"
                             validateFunction="checkRaV();"
                             preAction="filterTypeAction!copyFilterType.do"
                             />
                <tags:submit targets="bodyContent" formId="filterTypeForm"
                             cssStyle="width:80px;" value="MSG.cancel2"
                             showLoadingText="true"
                             preAction="filterTypeAction!preparePage.do"
                             />



            </s:if>
        </div>
    </div>

    <!-- danh sach nhom luat -->
    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MSG.filterTypeRule.list'))}</legend>
        <sx:div id="divListFilterType" >
            <jsp:include page="listFilterType.jsp"/>
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
        if(trim($('name').value) == "") {
            <%--$('message').innerHTML = "!!!Lỗi. Trường tên nhóm luật không được để trống";--%>
//            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.022')"/>';
            $('message').innerHTML= '<s:text name="ERR.LST.022"/>';
            $('name').select();
            $('name').focus();
            return false;
        }
        if(trim($('groupFilterRuleCode').value) == "") {
            <%--$('message').innerHTML = "!!!Lỗi. Chọn tập luật";--%>
//            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.023')"/>';
            $('message').innerHTML= '<s:text name="ERR.LST.023"/>';
            $('groupFilterRuleCode').focus();
            return false;
        }

        return true;
    }

    //kiem tra ma khong duoc chua cac ky tu dac biet
    checkValidFields = function() {
        /*
        if(checkingSpecialCharacter(trim($('name').value))) {
            $('message').innerHTML = "!!!Lỗi. Trường tên nhóm luật không được chứa các ký tự đặc biệt";
            $('name').select();
            $('name').focus();
            return false;
        }
        if(checkingSpecialCharacter(trim($('notes').value))) {
            $('message').innerHTML = "!!!Lỗi. Trường mô tả nhóm luật không được chứa các ký tự đặc biệt";
            $('notes').select();
            $('notes').focus();
            return false;
        }*/
        var notes = trim($('notes').value);
        if(notes != "" && notes.length > 450) {
            <%--$('message').innerHTML = "!!!Lỗi. Trường mô tả nhóm luật quá dài";--%>
//            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.024')"/>';
            $('message').innerHTML= '<s:text name="ERR.LST.024"/>';
            $('notes').select();
            $('notes').focus();
            return false;
        }

        //trim cac truong can thiet
        $('name').value = trim($('name').value);
        $('notes').value = trim($('notes').value);

        return true;
    }



    //
    dojo.event.topic.subscribe("filterTypeAction/search", function(event, widget){
        widget.href = "filterTypeAction!search.do?" +   token.getTokenParamString();
    });

    //
    dojo.event.topic.subscribe("filterTypeAction/cancel", function(event, widget){
        widget.href = "filterTypeAction!preparePage.do?"+ token.getTokenParamString();
    });

    //
    dojo.event.topic.subscribe("filterTypeAction/save",  function(event, widget){
        if(checkRequiredFields() && checkValidFields()) {
            widget.href = "filterTypeAction!editFilterType.do?" +  token.getTokenParamString();
        } else {
            event.cancel = true;
        }
        
    });
    dojo.event.topic.subscribe("filterTypeAction/copy",  function(event, widget){
        if(checkRequiredFields() && checkValidFields()) {
            widget.href = "filterTypeAction!copyFilterType.do?"+  token.getTokenParamString();
        } else {
            event.cancel = true;
        }

    });

</script>
