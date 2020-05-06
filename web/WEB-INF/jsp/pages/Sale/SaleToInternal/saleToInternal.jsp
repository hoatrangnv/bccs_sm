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

<tags:imPanel title="MENU.200007">
    <s:form action="saleToInternalV2Action" theme="simple" method="post" id="saleTransForm">
<s:token/>


        <s:hidden name="staff_export_channel" id="staff_export_channel" value="staff_export_channel"/>
        
        <div align="center">
            <fieldset class="imFieldset">
                <legend><s:text name="MSG.SAE.002"/></legend>

                <table class="inputTbl6Col">
                    <!--TruongNQ5 20140820 R6534-->
                    <tr>
                        <td class="label"><tags:label key="MSG.SAE.066" required="true"/></td>
                        <td class="text">

                            <tags:imSelect name="saleTransForm.agentTypeIdSearch"
                                           id="agentTypeIdSearch"
                                           cssClass="txtInputFull"
                                           headerKey="" headerValue="Shop Channel"
                                           list="listChannelType"
                                           listKey="channelTypeId" listValue="name"
                                           onchange="changeAgentTypeId()"/>

                            <!--                        <tags_:imSelect name="saleToCollaboratorForm.agentTypeIdSearch"
                                                                   id="agentTypeIdSearch"
                                                                   cssClass="txtInputFull" disabled="false" theme="simple"
                                                                   list="80043:MSG.SAE.068"
                                                                   headerKey="10" headerValue="MSG.SAE.069"/>-->
                        </td>
                        <td class="label"><tags:label key="MSG.SAE.067" required="true"/></td>
                        <td colspan="5" class="text">
                            <tags:imSearch codeVariable="saleTransForm.agentCodeSearch" nameVariable="saleTransForm.agentNameSearch"
                                           codeLabel="MSG.SAE.067" nameLabel="MSG.SAE.070"
                                           searchClassName="com.viettel.im.database.DAO.SaleToInternalV2DAO"
                                           searchMethodName="getListObject"
                                           otherParam="agentTypeIdSearch"
                                           getNameMethod="getObjectName"/>
                        </td>
                    </tr>
                    <!--End TruongNQ5 20140820 R6534-->
                    <tr>
                        <td class="label"><tags:label key="MSG.SAE.005" required="false"/></td>
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
                                           list="1:MSG.SAE.071" value="1"/>
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
            <jsp:include page="saleToInternalDetail.jsp"/>
        </div>
        <div align="center">
            <tags:submit confirm="true" confirmText="MSG.SAE.204"
                         formId="saleTransForm"
                         targets="bodyContent"
                         validateFunction="checkValidateSaleTrans()" showLoadingText="true"
                         value="MSG.SAE.064" preAction="saleToInternalV2Action!save.do"/>
        </div>
    </s:form>
</tags:imPanel>



<script type="text/javascript" language="javascript">
    checkValidateSaleTrans = function(){
//        if (trim($('saleTransForm.custName').value) == ""){
//            $('displayResultMsg').innerHTML = '<s_:text name="saleRetail.warn.cust"/>';
//            $('saleTransForm.custName').select();
//            $('saleTransForm.custName').focus();
//            return false;
//        }
        var channelTypeId = document.getElementById("agentTypeIdSearch").value;
        if (trim($('saleTransForm.payMethod').value) == "") {
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
        if (channelTypeId != null && channelTypeId != '') {
            if (trim($('saleTransForm.agentCodeSearch').value) == "") {
                $('displayResultMsg').innerHTML = '<s:text name="Err.R6534.001"/>';
                $('saleTransForm.agentCodeSearch').select();
                $('saleTransForm.agentCodeSearch').focus();
                return false;
            }
        } else {
            if (trim($('saleTransForm.custName').value) == "") {
                $('displayResultMsg').innerHTML = '<s:text name="ERR.SAE.001"/>';
                $('saleTransForm.custName').select();
                $('saleTransForm.custName').focus();
                return false;
            }
        }

        return true;
    }
    changeAgentTypeId = function() {
        var channelTypeId = document.getElementById("agentTypeIdSearch").value;
        if (channelTypeId == null || channelTypeId == '') {
            $('saleTransForm.custName').disabled = false;
            $('saleTransForm.company').disabled = false;
            $('saleTransForm.tin').disabled = false;
        } else {
            $('saleTransForm.custName').disabled = true;
            $('saleTransForm.company').disabled = true;
            $('saleTransForm.tin').disabled = true;

        }
    }

</script>
