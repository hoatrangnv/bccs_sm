<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : discountPolicy
    Created on : Sep 29, 2010, 7:55:22 PM
    Author     : Doan Thanh 8
    Desc       : thong tin ve chinh sach CK
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">


<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="title.discountPolicy.page">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan hien thi thong tin can tra cuu -->
    <div class="divHasBorder">
        <s:form action="discountPolicyAction" theme="simple" method="post" id="discountPolicyForm">
<s:token/>

            <s:hidden name="discountPolicyForm.discountPolicyId" id="discountPolicyForm.discountPolicyId"/>

            <table class="inputTbl6Col">
                <tr>
                    <td><tags:label key="MSG.LST.801" required="true"/></td>
                    <td class="oddColumn">
                        <s:textfield name="discountPolicyForm.discountPolicyName" id="discountPolicyForm.discountPolicyName" maxlength="80" cssClass="txtInputFull"/>
                    </td>
                    <td class="oddColumn">
                        <s:checkbox name="discountPolicyForm.isDefaultDiscountPolicy" id="discountPolicyForm.isDefaultDiscountPolicy" onclick="changeCheckBox()"/>
                        ${fn:escapeXml(imDef:imGetText('MSG.LST.802'))}
                    </td>
                    <td><tags:label key="MSG.LST.803" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="discountPolicyForm.defaultDiscountPolicyId"
                                       id="discountPolicyForm.defaultDiscountPolicyId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.LST.804"
                                       list="listDefaultDiscountPolicy"
                                       listKey="code" listValue="name"/>
                    </td>
                    <td><tags:label key="MSG.LST.806" required="true"/></td>
                    <td>
                        <tags:imSelect name="discountPolicyForm.discountPolicyStatus"
                                       id="discountPolicyForm.discountPolicyStatus"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.LST.807"
                                       list="1:MSG.LST.808, 0:MSG.LST.809"/>
                    </td>
                </tr>
            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <div align="center" style="padding-top:3px; padding-bottom: 3px;">
            <s:if test="#attr.discountPolicyForm.discountPolicyId != null && #attr.discountPolicyForm.discountPolicyId != ''">
                <tags:submit formId="discountPolicyForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.LST.814"
                             validateFunction="checkValidDataToAddOrEdit();"
                             preAction="discountPolicyAction!addOrEditDiscountPolicy.do"/>

                <tags:submit formId="discountPolicyForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.LST.815"
                             preAction="discountPolicyAction!preparePage.do"/>
            </s:if>
            <s:else>
                <tags:submit formId="discountPolicyForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.LST.811"
                             validateFunction="checkValidDataToAddOrEdit();"
                             preAction="discountPolicyAction!addOrEditDiscountPolicy.do"/>

                <tags:submit formId="discountPolicyForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.LST.810"
                             preAction="discountPolicyAction!searchDiscountPolicy.do"/>
            </s:else>
        </div>
    </div>

    <div>
        <jsp:include page="listDiscountPolicy.jsp"/>
    </div>

</tags:imPanel>


<script language="javascript">
    //
    $('discountPolicyForm.discountPolicyName').select();
    $('discountPolicyForm.discountPolicyName').focus();

    //
    checkValidDataToAddOrEdit = function() {
        if(checkRequiredFields() && checkValidFields()) {
            return true;
        } else {
            return false;
        }
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        //truong ten CSCK khong duoc de trong
        if(trim($('discountPolicyForm.discountPolicyName').value) == "") {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.121')"/>';
            $('discountPolicyForm.discountPolicyName').select();
            $('discountPolicyForm.discountPolicyName').focus();
            return false;
        }

        //neu ko la CSCK mac dinh thi p chon CSCK cha
        var isCheck = $('discountPolicyForm.isDefaultDiscountPolicy').checked;
        if(!isCheck && trim($('discountPolicyForm.defaultDiscountPolicyId').value) == "") {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.122')"/>';
            $('discountPolicyForm.defaultDiscountPolicyId').focus();
            return false;
        }


        if(trim($('discountPolicyForm.discountPolicyStatus').value) == "") {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.123')"/>';
            $('discountPolicyForm.discountPolicyStatus').focus();
            return false;
        }

        return true;
    }

    //
    checkValidFields = function() {
        //ten chiet khau khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('discountPolicyForm.discountPolicyName').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.LST.124')"/>';
            $('discountPolicyForm.discountPolicyName').select();
            $('discountPolicyForm.discountPolicyName').focus();
            return false;
        }

        //trim cac truong can thiet
        $('discountPolicyForm.discountPolicyName').value = trim($('discountPolicyForm.discountPolicyName').value);

        return true;
    }

    changeCheckBox = function() {
        var isCheck = $('discountPolicyForm.isDefaultDiscountPolicy').checked;
        if(isCheck) {
            $('discountPolicyForm.defaultDiscountPolicyId').disabled = true;
        } else {
            $('discountPolicyForm.defaultDiscountPolicyId').disabled = false;
        }
    }

    changeCheckBox();

</script>
