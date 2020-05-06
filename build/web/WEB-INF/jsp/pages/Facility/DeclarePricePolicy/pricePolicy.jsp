<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : pricePolicy
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

<tags:imPanel title="title.pricePolicy.page">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan hien thi thong tin can tra cuu -->
    <div class="divHasBorder">
        <s:form action="pricePolicyAction" theme="simple" method="post" id="pricePolicyForm">
            <s:token/>

            <s:hidden name="pricePolicyForm.pricePolicyId" id="pricePolicyForm.pricePolicyId"/>

            <table class="inputTbl6Col">
                <tr>
                    <td><tags:label key="MSG.LST.NPP" required="true"/></td>
                    <td class="oddColumn">
                        <s:textfield name="pricePolicyForm.pricePolicyName" id="pricePolicyForm.pricePolicyName" maxlength="80" cssClass="txtInputFull"/>
                    </td>
                    <td class="oddColumn">
                        <s:checkbox name="pricePolicyForm.isDefaultPricePolicy" id="pricePolicyForm.isDefaultPricePolicy" onclick="changeCheckBox()"/>
                        ${fn:escapeXml(imDef:imGetText('MSG.LST.IDPP'))}
                    </td>
                    <td><tags:label key="MSG.LST.DPP" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="pricePolicyForm.defaultPricePolicyId"
                                       id="pricePolicyForm.defaultPricePolicyId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.LST.SDPP"
                                       list="listDefaultPricePolicy"
                                       listKey="code" listValue="name"/>
                    </td>
                    <td><tags:label key="MSG.LST.806" required="true"/></td>
                    <td>
                        <tags:imSelect name="pricePolicyForm.pricePolicyStatus"
                                       id="pricePolicyForm.pricePolicyStatus"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.LST.807"
                                       list="1:MSG.LST.808, 0:MSG.LST.809"/>
                    </td>
                </tr>
            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <div align="center" style="padding-top:3px; padding-bottom: 3px;">
            <s:if test="#attr.pricePolicyForm.pricePolicyId != null && #attr.pricePolicyForm.pricePolicyId != ''">
                <tags:submit formId="pricePolicyForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.LST.814"
                             validateFunction="checkValidDataToAddOrEdit();"
                             preAction="pricePolicyAction!addOrEditPricePolicy.do"/>

                <tags:submit formId="pricePolicyForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.LST.815"
                             preAction="pricePolicyAction!preparePage.do"/>
            </s:if>
            <s:else>
                <tags:submit formId="pricePolicyForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.LST.811"
                             validateFunction="checkValidDataToAddOrEdit();"
                             preAction="pricePolicyAction!addOrEditPricePolicy.do"/>

                <tags:submit formId="pricePolicyForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.LST.810"
                             preAction="pricePolicyAction!searchPricePolicy.do"/>
            </s:else>
        </div>
    </div>

    <div>
        <jsp:include page="listPricePolicy.jsp"/>
    </div>

</tags:imPanel>


<script language="javascript">
    //
    $('pricePolicyForm.pricePolicyName').select();
    $('pricePolicyForm.pricePolicyName').focus();

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
        if(trim($('pricePolicyForm.pricePolicyName').value) == "") {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.PPNNE')"/>';
            $('pricePolicyForm.pricePolicyName').select();
            $('pricePolicyForm.pricePolicyName').focus();
            return false;
        }

        //neu ko la CSCK mac dinh thi p chon CSCK cha
        var isCheck = $('pricePolicyForm.isDefaultPricePolicy').checked;
        if(!isCheck && trim($('pricePolicyForm.defaultPricePolicyId').value) == "") {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.PPPNE')"/>';
            $('pricePolicyForm.defaultPricePolicyId').focus();
            return false;
        }


        if(trim($('pricePolicyForm.pricePolicyStatus').value) == "") {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.PPSNE')"/>';
            $('pricePolicyForm.pricePolicyStatus').focus();
            return false;
        }

        return true;
    }

    //
    checkValidFields = function() {
        //ten chiet khau khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('pricePolicyForm.pricePolicyName').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.LST.PPCH')"/>';
            $('pricePolicyForm.pricePolicyName').select();
            $('pricePolicyForm.pricePolicyName').focus();
            return false;
        }

        //trim cac truong can thiet
        $('pricePolicyForm.pricePolicyName').value = trim($('pricePolicyForm.pricePolicyName').value);

        return true;
    }

    changeCheckBox = function() {
        var isCheck = $('pricePolicyForm.isDefaultPricePolicy').checked;
        if(isCheck) {
            $('pricePolicyForm.defaultPricePolicyId').disabled = true;
        } else {
            $('pricePolicyForm.defaultPricePolicyId').disabled = false;
        }
    }

    changeCheckBox();

</script>
