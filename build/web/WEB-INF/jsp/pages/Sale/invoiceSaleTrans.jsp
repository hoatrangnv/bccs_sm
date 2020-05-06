<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : invoiceSaleTrans
    Created on : Sep 7, 2009, 1:53:27 PM
    Author     : MrSun
    Description: search saleTransList to create invoice
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
            request.setAttribute("saleInvoiceTypeList", request.getSession().getAttribute("saleInvoiceTypeList"));
            request.setAttribute("lstTelecom", request.getSession().getAttribute("lstTelecom"));
            request.setAttribute("lstPayMethod", request.getSession().getAttribute("lstPayMethod"));
            request.setAttribute("lstReason", request.getSession().getAttribute("lstReason"));
            request.setAttribute("lstSaleTransStatus", request.getSession().getAttribute("lstSaleTransStatus"));
            request.setAttribute("childShopList", request.getSession().getAttribute("childShopList"));
            request.setAttribute("channelTypeList", request.getSession().getAttribute("channelTypeList"));
            request.setAttribute("collStaffList", request.getSession().getAttribute("collStaffList"));

%>

<!--<_tags:imPanel title="MSG.SAE.160">-->
<tags:imPanel title="MSG.SAE.212">
    <s:form action="SaleTransInvoiceAction" theme="simple" method="post" id="form">
<s:token/>

        <!-- phan tim kiem thong tin ve giao dich de lap hoa don -->

        <div class="divHasBorder">
            <table class="inputTbl6Col" >
                <tr>
                    <td class="label"><tags:label key="MSG.SAE.161"/></td>
                    <td class="text">
                        <s:hidden name="form.saleGroup" id="saleGroup" />
                        <s:hidden name="form.shopId" id="shopId" />
                        <s:hidden name="form.staffId" id="staffId" />
                        <s:hidden name="form.saleTransStatusSearch" id="saleTransStatusSearch" value="2" />
                        <s:textfield name="form.custNameSearch" id="custName" maxlength="100" cssClass="txtInput" theme="simple"/>
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.126"/></td>
                    <td class="text">
                        <tags:dateChooser property="form.fromDateSearch" styleClass="txtInput" id="fromDate"/>
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.127"/></td>
                    <td class="text">
                        <tags:dateChooser property="form.toDateSearch" styleClass="txtInput" id="toDate"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.SAE.162"/></td>
                    <td class="text">                        
                        <tags:imSelect name="form.saleTransTypeSearch" id="saleTransType"
                                       cssClass="txtInput" theme="simple"
                                       list="saleInvoiceTypeList"
                                       listKey="saleTransType" listValue="reasonSaleGroupName"
                                       headerKey="" headerValue="MSG.SAE.163"
                                       onchange="changTransType();"/>
                    </td>
                    <td class="label"><div id='telecomServiceLabel'><tags:label key="MSG.SAE.016"/></div></td>
                    <td class="text">
                        <tags:imSelect name="form.telecomServiceIdSearch"
                                       id="telecomServiceId" theme="simple"
                                       cssClass="txtInput"
                                       headerKey="" headerValue="MSG.SAE.022"
                                       list="lstTelecom"
                                       listKey="telecomServiceId" listValue="telServiceName"/>
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.164"/></td>
                    <td class="text">
                        <tags:imSelect name="form.vatSearch"
                                       id="vatSearch" theme="simple"
                                       cssClass="txtInput"
                                       list="-1:NOT VAT, 0:VAT = 0%, 17:VAT = 17%"
                                       headerKey="" headerValue="--VAT--"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.SAE.010"/></td>
                    <td class="text">
                        <tags:imSelect name="form.payMethodIdSearch"
                                       id="payMethod" theme="simple"
                                       cssClass="txtInput"
                                       headerKey="" headerValue="MSG.SAE.170"
                                       list="lstPayMethod"
                                       listKey="code" listValue="name"/>

                        <%--tags:imSelect name="form.payMethodIdSearch"
                                       id="payMethod" theme="simple"
                                       cssClass="txtInput" theme="simple"
                                       headerKey="" headerValue="MSG.SAE.170"
                                       list="lstPayMethod"
                                       listKey="code" listValue="name"/--%>
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.009"/></td>
                    <td class="text">
                        <tags:imSelect name="form.reasonIdSearch"
                                       id="reasonId" theme="simple"
                                       cssClass="txtInput"
                                       headerKey="" headerValue="MSG.SAE.013"
                                       list="lstReason"
                                       listKey="objId" listValue="objName"/>
                    </td>

                    <s:if test="form.saleGroup == 1">
                        <td class="label"><tags:label key="MSG.SAE.174"/></td>
                        <td class="text">
                            <tags:imSelect name="form.agentIdSearch"
                                           id="agentIdSearch" theme="simple"
                                           cssClass="txtInput"
                                           headerKey="" headerValue="MSG.SAE.175"
                                           list="childShopList"
                                           listKey="shopId" listValue="name"
                                           onchange=""/>
                        </td>
                    </s:if>
                    <s:elseif test="form.saleGroup != 4">
                        <td class="label"><tags:label key="MSG.SAE.176"/></td>
                        <td class="text">
                            <tags:imSelect name="form.collaboratorIdSearch"
                                           id="collaboratorIdSearch" theme="simple"
                                           cssClass="txtInput"
                                           headerKey="" headerValue="MSG.SAE.177"
                                           list="collStaffList"
                                           listKey="staffId" listValue="name"
                                           disabled="true"/>
                        </td>
                    </s:elseif>
                </tr>

                <!--                ban cho dail ly-->
                <s:if test="form.saleGroup == 1">
                    <script>
                        var title ="${fn:escapeXml(imDef:imGetText('MSG.SAE.171'))}";
                        document.title=title.trim();
                        document.getElementById("myHeader").innerHTML=title;
                    </script>

                </s:if>
                <!--cong tac vien ban hang-->
                <s:elseif test="form.saleGroup == 4">
                    <script>
                        var title ="${fn:escapeXml(imDef:imGetText('MSG.SAE.172'))}";
                        document.title=title.trim();
                        document.getElementById("myHeader").innerHTML=title;
                    </script>
                    <tr>
                        <td class="label"><tags:label key="MSG.SAE.066"/></td>
                        <td class="text">
                            <tags:imSelect name="form.agentTypeIdSearch"
                                           id="agentTypeIdSearch"
                                           cssClass="txtInput" disabled="false" theme="simple"
                                           list="channelTypeList"
                                           listKey="channelTypeId" listValue="name"
                                           onchange="changeObjectType();"/>
                        </td>

                        <td class="label"><tags:label key="MSG.SAE.067"/></td>
                        <td colspan="3" class="oddColumn">
                            <tags:imSearch codeVariable="form.agentCodeSearch" nameVariable="form.agentNameSearch"
                                           codeLabel="MSG.SAE.066" nameLabel="MSG.SAE.070"
                                           searchClassName="com.viettel.im.database.DAO.SaleTransInvoiceDAO"
                                           searchMethodName="getListObjectByParentIdOrOwenerId"
                                           otherParam="agentTypeIdSearch"
                                           getNameMethod="getObjectName"/>
                        </td>
                    </tr>
                </s:elseif>
                <!--                    ban le + lam dich vu + ban cho ctv-->
                <s:else>
                    <script>
                        var title ="${fn:escapeXml(imDef:imGetText('MSG.SAE.173'))}";
                        document.title=title.trim();
                        document.getElementById("myHeader").innerHTML=title;
                    </script>

                </s:else>
                <tr>
                    <td colspan="6" align="center">
                        <s:if test="form.saleGroup == 4 && #session.createInvoiceShop == false && #session.createInvoiceStaff == false ">
                            <input type="button" disabled ="true" value="${fn:escapeXml(imDef:imGetText('MSG.SAE.138'))}" style="width: 85px;"/>
                        </s:if>
                        <s:else>
                            <tags:submit targets="resultArea"
                                         formId="form"
                                         cssStyle="width:80px;"
                                         validateFunction="checkValidateToSearch();"
                                         value="MSG.SAE.138"
                                         preAction="SaleTransInvoiceAction!searchSaleTrans.do"
                                         showLoadingText="true"/>
                        </s:else>
                    </td>
                </tr>
            </table>
        </div>

        <div style="width:100%; text-align:center;" id="btnGotoCreateInvoice">
            <tags:submit targets="bodyContent" 
                         formId="form"
                         cssStyle="width:85px;"
                         value="MSG.SAE.178"
                         preAction="SaleTransInvoiceAction!gotoCreateInvoice.do"
                         showLoadingText="true"/>
        </div>
        <script>
            $('btnGotoCreateInvoice').hide();
        </script>

        <jsp:include page="invoiceSaleTransList.jsp"/>
    </s:form>

</tags:imPanel>


<script type="text/javascript" language="javascript">

    //Kiem tra neu loai giao dich la 'ban cho CTV/DB' thi hien thi danh sach CTV/DB tren combo
    changTransType = function() {
        var transType = document.getElementById("saleTransType").value;
        if (transType == '3' || transType == '9') {
            var staff = document.getElementById("collaboratorIdSearch");
            //var staffLabel = document.getElementById("staffLabel");
            staff.disabled = false;
            //staffLabel.disabled = false;
        } else {
            //var staff = document.getElementById("collaboratorIdSearch");
            //var staffLabel = document.getElementById("staffLabel");
            staff.value = "";
            staff.disabled = true;
            //staffLabel.value = "";
            //staffLabel.disabled = true;
        }
    }

    changeObjectType = function(){
        var objectType = document.getElementById("agentTypeIdSearch").value;
        if (objectType == null || trim(objectType) == ""){
            
        }
    }

    checkValidateToSearch = function(){
        return true;
    }
    

    changeChannelType = function() {
        updateData();
    }

    //changTransType();

    selectCbAll = function(){

        selectAll("checkAllId", "form.saleTransIdList", "checkBoxId");
    }

    checkSelectCbAll = function(){
        checkSelectAll("checkAllId", "form.saleTransIdList", "checkBoxId");
    }
</script>
