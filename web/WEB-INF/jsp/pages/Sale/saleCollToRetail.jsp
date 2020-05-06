<%-- 
    Document   : saleCollToRetail
    Created on : Aug 31, 2010, 11:46:36 AM
    Author     : tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("staffList", request.getSession().getAttribute("staffList"));
            request.setAttribute("reasonList", request.getSession().getAttribute("listReaSonSaleExpCollaborator"));
            request.setAttribute("listPayMethod", request.getSession().getAttribute("listPayMethod"));
            request.setAttribute("lstChannelTypeCol", request.getSession().getAttribute("lstChannelTypeCol"));


%>

<tags:imPanel title="title.saleCollToRetail.page">

    <s:form action="saleCollToRetailAction" theme="simple" method="post" id="saleToCollaboratorForm">
<s:token/>

        <fieldset class="imFieldset">
            <legend><s:text name="MSG.SAE.002"/></legend>
            <table class="inputTbl6Col">
                <tr>
                    <td class="label"><tags:label key="MSG.SAE.066" required="true"/></td>
                    <td class="text">

                        
                        <tags:imSelect name="saleToCollaboratorForm.agentTypeIdSearch" id="agentTypeIdSearch" headerKey="" 
                                       headerValue="label.option" cssClass="txtInputFull"
                                       list="lstChannelTypeCol" listKey="channelTypeId" listValue="name"/>
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.067" required="true"/></td>
                    <td colspan="3" class="text">
                        <tags:imSearch codeVariable="saleToCollaboratorForm.agentCodeSearch" nameVariable="saleToCollaboratorForm.agentNameSearch"
                                       codeLabel="MSG.SAE.067" nameLabel="MSG.SAE.070"
                                       searchClassName="com.viettel.im.database.DAO.SaleTransInvoiceDAO"
                                       searchMethodName="getListObjectByParentIdOrOwenerId"
                                       otherParam="agentTypeIdSearch"
                                       getNameMethod="getObjectName"/>
                    </td>
                </tr>

                <tr>
                    <td class="label"><tags:label key="MSG.SAE.005" required="true"/></td>
                    <td class="text">
                        <s:textfield name="saleToCollaboratorForm.custName" id="custName" maxlength="200" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.006"/></td>
                    <td class="text">
                        <s:textfield name="saleToCollaboratorForm.company" id="company" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.099"/></td>
                    <td class="text">
                        <s:textfield name="saleToCollaboratorForm.address" id="address" maxlength="200" cssClass="txtInputFull"/>
                    </td>
                </tr>

                <tr>
                    <td class="label"><tags:label key="MSG.SAE.009" required="true"/></td>
                    <td  class="text">
                        <tags:imSelect name="saleToCollaboratorForm.reasonId"
                                       id="reasonId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SAE.013"
                                       list="reasonList"
                                       listKey="reasonId" listValue="reasonName"/>
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.010" required="true"/></td>
                    <td class="text">
                        <tags:imSelect name="saleToCollaboratorForm.payMethodId"
                                       id="payMethodId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SAE.014"
                                       list="1:MSG.SAE.071" value="1"/>
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.008" required="true"/></td>
                    <td class="text">
                        <tags:dateChooser property="saleToCollaboratorForm.saleDate" styleClass="txtInput" readOnly="true"/>
                    </td>
                </tr>

            </table>
        </fieldset>

        <div id="saleToRetailDetailArea">
            <jsp:include page="saleCollToRetailDetail.jsp"/>
        </div>

        <div align="center" style="padding-top: 5px;">
            <tags:submit confirm="true" confirmText="MSG.SAE.204" cssStyle="width:100px;"
                         formId="saleToCollaboratorForm"
                         targets="bodyContent"
                         validateFunction="validate()" showLoadingText="true"
                         value="MSG.SAE.064" preAction="saleCollToRetailAction!saveSaleCollToRetail.do"/>

            <tags:submit confirm="true" confirmText="Do you want to reset?" cssStyle="width:100px;"
                         formId="saleToCollaboratorForm"
                         targets="bodyContent"                         
                         value="MSG.SIK.102" preAction="saleCollToRetailAction!prepareSaleCollToRetail.do"/>

        </div>

    </s:form>
</tags:imPanel>

<script type="text/javascript" language="javascript">

    validate = function(){

        if ($('agentTypeIdSearch').value == null || $('agentTypeIdSearch').value.trim() == "")
        {
            $('displayResultMsg').innerHTML='<s:text name="Error. You must select channel type!"/>';
            $('agentTypeIdSearch').focus();
            return ;
        }
        if ($('saleToCollaboratorForm.agentCodeSearch').value == null || $('saleToCollaboratorForm.agentCodeSearch').value.trim() == "")
        {
            $('displayResultMsg').innerHTML='<s:text name="Error. You must select channel information!"/>';
            $('saleToCollaboratorForm.agentCodeSearch').focus();
            return ;
        }
        
        var custName = document.getElementById("custName");
        var company = document.getElementById("company");
        var address = document.getElementById("address");

        if (trim(custName.value).length ==0){
            $('displayResultMsg').innerHTML='<s:text name="MSG.SAE.056"/>';
            custName.focus();
            return false;
        }

        if (trim(custName.value).length >100){
            $('displayResultMsg').innerHTML='<s:text name="MSG.SAE.057"/>';
            custName.focus();
            return false;
        }

        if(trim(company.value).length >100){
            $('displayResultMsg').innerHTML='<s:text name="MSG.SAE.058"/>';
            company.focus();
            return false;
        }

        if(trim(address.value).length >100){
            $('displayResultMsg').innerHTML='<s:text name="Error. You must input address of customer!"/>';
            address.focus();
            return false;
        }

        var reasonId = document.getElementById("reasonId");
        var payMethodId = document.getElementById("payMethodId");        
        if(trim(reasonId.value).length == 0){
            $('displayResultMsg').innerHTML='<s:text name="MSG.SAE.060"/>';
            reasonId.focus();
            return false;
        }
        if(trim(payMethodId.value).length == 0){
            $('displayResultMsg').innerHTML='<s:text name="MSG.SAE.061"/>';
            payMethodId.focus();
            return false;
        }        
        return true;
    }

</script>

