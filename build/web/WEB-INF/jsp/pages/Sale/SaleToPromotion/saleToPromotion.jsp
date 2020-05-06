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

<tags:imPanel title="MENU.200006">
    <s:form action="saleToPromotionV2Action" theme="simple" method="post" id="form">
<s:token/>


        <s:hidden name="staff_export_channel" id="staff_export_channel" value="staff_export_channel"/>

        <div align="center">
            <fieldset class="imFieldset">
                <legend><s:text name="MSG.SAE.002"/></legend>

                <table class="inputTbl6Col">
                    <tr>
                        <td class="label"><tags:label key="MSG.SAE.005" required="true"/></td>
                        <td class="text">
                            <s:textfield name="form.custName" id="form.custName" maxlength="100" cssClass="txtInputFull"/>
                        </td>
                        <td class="label"><tags:label key="MSG.SAE.006"/></td>
                        <td class="text">
                            <s:textfield name="form.company" id="form.company" maxlength="100" cssClass="txtInputFull"/>
                        </td>
                        <td class="label"><tags:label key="MSG.SAE.007"/></td>
                        <td class="text">
                            <s:textfield name="form.tin" id="form.tin" maxlength="100" cssClass="txtInputFull"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="label"><tags:label key="MSG.SAE.010" required="true"/></td>
                        <td class="text">
                            <tags:imSelect name="form.payMethod"
                                           id="form.payMethod"
                                           cssClass="txtInputFull"
                                           headerKey="" headerValue="MSG.SAE.014"
                                           list="1:MSG.SAE.071" value="1"/>
                        </td>
                        <td class="label"><tags:label key="MSG.SAE.009" required="true"/></td>
                        <td class="text">
                            <tags:imSelect name="form.reasonId"
                                           id="form.reasonId"
                                           cssClass="txtInputFull"
                                           headerKey="" headerValue="MSG.SAE.013"
                                           list="lstReason"
                                           listKey="reasonId" listValue="reasonName"
                                           onchange=""/>
                        </td>
                        <td class="label"><tags:label key="MSG.SAE.008" required="true"/></td>
                        <td class="text">
                            <tags:dateChooser property="form.saleTransDate"  id="form.saleTransDate" styleClass="txtInput"   readOnly="true" insertFrame="false"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>
        <div align="center" id="detailDiv">
            <jsp:include page="saleToPromotionDetail.jsp"/>
        </div>
        <div align="center">
            <s:if test="form.saleTransId == null">
                <tags:submit confirm="true" confirmText="MSG.SAE.204"
                             formId="form" cssStyle="width: 120px;"
                             targets="bodyContent"
                             validateFunction="checkValidateSaleTrans()" showLoadingText="true"
                             value="MSG.SAE.064" preAction="saleToPromotionV2Action!save.do"/>
            </s:if>
            <s:else>
                <input type="button" disabled value="<s:text name = "MSG.SAE.064"/>" style="width:120px;">
            </s:else>
            <tags:submit 
                formId="form" cssStyle="width: 120px;"
                targets="bodyContent"
                showLoadingText="true"
                value="MSG.reset" preAction="saleToPromotionV2Action!prepareSaleToPromotion.do"/>
        </div>
    </s:form>
</tags:imPanel>



<script type="text/javascript" language="javascript">
    checkValidateSaleTrans = function(){
        if (trim($('form.custName').value) == ""){
            $('displayResultMsg').innerHTML = '<s:text name="saleRetail.warn.cust"/>';
            $('form.custName').select();
            $('form.custName').focus();
            return false;
        }

        if (trim($('form.payMethod').value) == ""){
            $('displayResultMsg').innerHTML = '<s:text name="saleRetail.warn.pay"/>';
            $('form.payMethod').select();
            $('form.payMethod').focus();
            return false;
        }
        if (trim($('form.reasonId').value) == ""){
            $('displayResultMsg').innerHTML = '<s:text name="saleRetail.warn.reason"/>';
            $('form.reasonId').select();
            $('form.reasonId').focus();
            return false;
        }

        return true;
    }

</script>
