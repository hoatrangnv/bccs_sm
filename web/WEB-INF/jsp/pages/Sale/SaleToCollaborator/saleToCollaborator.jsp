<%-- 
    Document   : saleToRetail
    Created on : Feb 24, 2011, 1:57:42 PM 
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
            request.setAttribute("contextPath", request.getContextPath());

%>

<tags:imPanel title="MSG.SAE.001">
    <s:form action="saleToRetailV2Action" theme="simple" method="post" id="saleTransForm">
<s:token/>

        <div align="center">
            <fieldset class="imFieldset">
                <legend><s:text name="MSG.SAE.002"/></legend>

                <table class="inputTbl6Col">
                    <tr>
                        <td class="label"><tags:label key="MSG.SAE.005" required="true"/></td>
                        <td class="text">
                            <s:textfield name="saleTransForm.custName" id="saleTransForm.custName" maxlength="100" cssClass="txtInputFull"/>
                        </td>
                        <td class="label"><tags:label key="MSG.SAE.006"/></td>
                        <td class="text">
                            <s:textfield name="saleTransForm.company" id="saleTransForm.company" maxlength="100" cssClass="txtInputFull"/>
                        </td>
                        <td class="label"><tags:label key="MSG.SAE.007"/></td>
                        <td class="text">
                            <s:textfield name="saleTransForm.tin" id="saleTransForm.tin" maxlength="100" cssClass="txtInputFull"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="label"><tags:label key="MSG.SAE.010" required="true"/></td>
                        <td class="text">
                            <tags:imSelect name="saleTransForm.payMethod"
                                           id="saleTransForm.payMethod"
                                           cssClass="txtInputFull"
                                           headerKey="" headerValue="MSG.SAE.014"
                                           list="listPayMethod"
                                           listKey="code" listValue="name"/>
                        </td>
                        <td class="label"><tags:label key="MSG.SAE.009" required="true"/></td>
                        <td class="text">
                            <tags:imSelect name="saleTransForm.reasonId"
                                           id="saleTransForm.reasonId"
                                           cssClass="txtInputFull"
                                           headerKey="" headerValue="MSG.SAE.013"
                                           list="listReason"
                                           listKey="reasonId" listValue="reasonName"
                                           onchange=""/>
                        </td>
                        <td class="label"><tags:label key="MSG.SAE.008" required="true"/></td>
                        <td class="text">
                            <tags:dateChooser property="saleTransForm.saleTransDate"  id="saleTransForm.saleTransDate" styleClass="txtInput"   readOnly="true" insertFrame="false"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>
        <div align="center" id="saleToRetailDetailDiv">            
            <jsp:include page="saleToRetailDetail.jsp"/>
        </div>
        <div align="center">
            <tags:submit confirm="true" confirmText="MSG.SAE.204"
                         formId="saleTransForm"
                         targets="bodyContent"
                         validateFunction="checkValidateSaleTrans()" showLoadingText="true"
                         value="MSG.SAE.064" preAction="saleToRetailV2Action!saveSaleTransToRetail.do"/>
        </div>
    </s:form>
</tags:imPanel>



<script type="text/javascript" language="javascript">
    checkValidateSaleTrans = function(){
        if (trim($('saleTransForm.custName').value) == ""){
            $('displayResultMsg').innerHTML = '<s:text name="saleRetail.warn.cust"/>';
            $('saleTransForm.custName').select();
            $('saleTransForm.custName').focus();
            return false;
        }

        if (trim($('saleTransForm.payMethod').value) == ""){
            $('displayResultMsg').innerHTML = '<s:text name="saleRetail.warn.pay"/>';
            $('saleTransForm.payMethod').select();
            $('saleTransForm.payMethod').focus();
            return false;
        }
        if (trim($('saleTransForm.reasonId').value) == ""){
            $('displayResultMsg').innerHTML = '<s:text name="saleRetail.warn.reason"/>';
            $('saleTransForm.reasonId').select();
            $('saleTransForm.reasonId').focus();
            return false;
        }

        return true;
    }

</script>
