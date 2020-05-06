<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

<tags:imPanel title="MSG.channel.info">
    <s:form action="saleChannelTypeAction" method="POST" id="saleChannelTypeForm" theme="simple">
        <s:token/>
        
        <!-- phan hien thi message -->
        <div>
            <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="paramMsg" type="key"/>
        </div>
        <s:hidden name="saleChannelTypeForm.channelTypeId" id="saleChannelTypeForm.channelTypeId"/>
        <table class="inputTbl6Col">
            <tr>
                <td class="label"><tags:label key="MSG.chanel.type.name" required="true" /> </td>
                <td class="text">
                    <s:textfield name="saleChannelTypeForm.name" id="saleChannelTypeForm.name" maxlength="80" readonly="false" cssClass="txtInputFull" maxLength="50"/>
                </td>
                <td class="label"><tags:label key="MSG.generates.status" required="true" /> </td>
                <td class="text">
                    
                    <tags:imSelect name="saleChannelTypeForm.status" id="saleChannelTypeForm.status"
                                   cssClass="txtInputFull" disabled="false"
                                   list="1:MES.CHL.129,0:MES.CHL.130"
                                   headerKey="" headerValue="COM.CHL.012"/>
                </td>
                <td class="label"><tags:label key="MSG.chanel.group" required="false" /></td>
                <td class="text">
                    
                    <tags:imSelect name="saleChannelTypeForm.objectType" id="saleChannelTypeForm.objectType"
                                   cssClass="txtInputFull" disabled="false"
                                   list="1:Shop,2:Staff"
                                   headerKey="" headerValue="COM.CHL.013"/>
                </td>
            </tr>
            <tr>
                 <td class="label"><tags:label key="MSG.unit" required="false" /></td>
                <td class="text">
                    
                    <tags:imSelect name="saleChannelTypeForm.isVtUnit" id="saleChannelTypeForm.isVtUnit"
                                   cssClass="txtInputFull" disabled="false"
                                   list="1:MES.CHL.146,2:MES.CHL.147"
                                   headerKey="" headerValue="COM.CHL.014"/>
                </td>
                <td class="label"><tags:label key="MSG.bonus" required="false" /></td>
                <td class="text">
                    
                    <tags:imSelect name="saleChannelTypeForm.checkComm" id="saleChannelTypeForm.checkComm"
                                   cssClass="txtInputFull" disabled="false"
                                   list="1:MES.CHL.148,2:MES.CHL.149"
                                   headerKey="" headerValue="COM.CHL.015"/>
                </td>

                <td class="label"><tags:label key="MSG.export.bill.templace" required="false" /> </td>

                <td class="text">
                    <s:textfield name="saleChannelTypeForm.stockReportTemplate" id="saleChannelTypeForm.stockReportTemplate" maxlength="50" readonly="false" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <s:if test="#request.toEditChannelType == 1">
                    <td class="label"><tags:label key="MSG.SAE.067" required="false" /> </td>
                    <td class="text">
                        <s:textfield name="saleChannelTypeForm.code" id="saleChannelTypeForm.code" maxlength="10" readonly="true" cssClass="txtInputFull"/>
                    </td>
                </s:if>
                <s:else>
                    <td class="label"><tags:label key="MSG.SAE.067" required="true" /> </td>
                    <td class="text">
                        <s:textfield name="saleChannelTypeForm.code" id="saleChannelTypeForm.code" maxlength="10" readonly="false" cssClass="txtInputFull"/>
                    </td>
                </s:else>
                <td class="label">
                    <tags:label key="MSG.FAC.policy.price" />
                </td>
                <td class="text">
                    <tags:imSelect name="saleChannelTypeForm.pricePolicyDefault"
                                   id="psaleChannelTypeForm.ricePolicyDefault"
                                   cssClass="txtInputFull"
                                   headerKey=""
                                   headerValue="MSG.FAC.AssignPrice.ChoosePP"
                                   list="listPricePolicy"
                                   listKey="code" listValue="name"/>
                </td>
                <td class="label">
                    <tags:label key="MSG.FAC.policy.discount" />
                </td>
                <td class="text">
                    <tags:imSelect name="saleChannelTypeForm.discountPolicyDefault"
                                   id="saleChannelTypeForm.discountPolicyDefault"
                                   cssClass="txtInputFull"
                                   headerKey=""
                                   headerValue="MSG.FAC.AssignPrice.ChooseCKPolicy"
                                   list="listDiscountPolicy"
                                   listKey="code" listValue="name"/>
                </td>
            </tr>
        </table>

        <br />
        <div style="width:100%" align="center">
            <s:if test="#request.toEditChannelType == 0">
                <tags:submit targets="bodyContent" formId="saleChannelTypeForm"
                             cssStyle="width:80px;"
                             value="MSG.generates.addNew"
                             validateFunction="validateChannelTypeForm()"
                             preAction="saleChannelTypeAction!addNewChannelType.do"/>
                <tags:submit targets="bodyContent" formId="saleChannelTypeForm"
                         value="MSG.find"
                         cssStyle="width:80px;"
                         preAction="saleChannelTypeAction!searchChannelType.do"
                         showLoadingText="true"
                         />
            </s:if>
            <s:if test="#request.toEditChannelType == 2">

                <tags:submit targets="bodyContent" formId="saleChannelTypeForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             value="MSG.copy"
                             validateFunction="validateChannelTypeForm()"
                             preAction="saleChannelTypeAction!copyChannelType.do"/>
                <tags:submit targets="bodyContent" formId="saleChannelTypeForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             value="MES.CHL.073"
                             preAction="saleChannelTypeAction!preparePage.do"/>


            </s:if>
            <s:if test="#request.toEditChannelType == 1">

                <tags:submit targets="bodyContent" formId="saleChannelTypeForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             value="MSG.accept"
                             validateFunction="validateChannelTypeForm()"
                             preAction="saleChannelTypeAction!editChannelType.do"/>
                <tags:submit targets="bodyContent" formId="saleChannelTypeForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             value="MES.CHL.073"
                             preAction="saleChannelTypeAction!preparePage.do"/>

            </s:if>
        </div>
         
        <div>
            <fieldset class="imFieldset">
                <sx:div id="listChannelType">
                    <jsp:include page="searchChannelTypeResult.jsp"/>
                </sx:div>
        </div>
        
        <br>
    </s:form>
</tags:imPanel>
        
        
<script type="text/javascript" language="javascript">

    validateChannelTypeForm = function(){
        return true;
    }
    
</script>


