<%--
    Document   : saleTransManagment.jsp
    Quan ly giao dich ban hang
    Created on : 10/06/2009
    Author     : ThanhNC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@page  import="com.viettel.im.database.BO.UserToken" %>


<%
            UserToken userToken = (UserToken) request.getSession().getAttribute("userToken");
            String shopName = userToken.getShopName();
            Long shopId = userToken.getShopId();
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("shopName", shopName);
            request.setAttribute("lstPayMethod", request.getSession().getAttribute("lstPayMethod"));
            request.setAttribute("saleInvoiceTypeList", request.getSession().getAttribute("saleInvoiceTypeList"));

%>
<s:form action="saleManagmentAction!searchSaleTrans" theme="simple"  enctype="multipart/form-data" method="post" id="saleManagmentForm">
<s:token/>


    <tags:imPanel title="MSG.SAE.107">
        <div class="divHasBorder">
            <table class="inputTbl8Col">
                <tr>
                    <td><tags:label key="MSG.SAE.108"/></td>
                    <td colspan="3">
                        <tags:imSearch codeVariable="saleManagmentForm.shopCode" nameVariable="saleManagmentForm.shopName"
                                       codeLabel="MSG.SAE.109" nameLabel="MSG.SAE.110"
                                       searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                       searchMethodName="getListShop"
                                       elementNeedClearContent="saleManagmentForm.staffCode;saleManagmentForm.staffName"
                                       getNameMethod="getNameShop"
                                       roleList="saleTransManagementf9Shop"/>
                    </td>
                    <td><tags:label key="MSG.SAE.111"/></td>
                    <td colspan="3">
                        <tags:imSearch codeVariable="saleManagmentForm.staffCode" nameVariable="saleManagmentForm.staffName"
                                       codeLabel="MSG.SAE.111" nameLabel="MSG.SAE.112"
                                       searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                       searchMethodName="getListStaff"
                                       otherParam="saleManagmentForm.shopCode"
                                       getNameMethod="getNameStaff"
                                       roleList="saleTransManagementf9Staff"/>

                    </td>
                </tr>
                <tr>
                    <td style="width:10%; " ><tags:label key="MSG.SAE.075"/></td>
                    <td style="width:15%; " >
                        <tags:imSelect name="saleManagmentForm.sTelecomServiceId"
                                       id="sTelecomServiceId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SAE.022"
                                       list="lstTelecomService"
                                       listKey="telecomServiceId" listValue="telServiceName"/>
                    </td>
                    <td style="width:10%; " ><tags:label key="MSG.SAE.113"/></td>
                    <td style="width:15%; " >
                        <s:textfield name="saleManagmentForm.sSaleTransCode" id="sSaleTransCode" cssClass="txtInputFull" maxlength="20"/>
                    </td>
                    <td style="width:10%; " ><tags:label key="MSG.SAE.114"/></td>
                    <td style="width:15%; " >
                        <%--tags:imSelect name="saleManagmentForm.sSaleTransType"
                                       id="sSaleTransType"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SAE.115"
                                       list="1:MSG.SAE.116, 2:MSG.SAE.117 ,3:MSG.SAE.118, 4:MSG.SAE.119,
                                       5:MSG.SAE.120, 6:MSG.SAE.121, 7:MSG.SAE.122, 9:MSG.SALE.213"/--%>

                        <tags:imSelect name="saleManagmentForm.sSaleTransType"
                                       id="sSaleTransType"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SAE.115"
                                       list="saleInvoiceTypeList" listKey="saleTransType" listValue="name"/>
                        
                    </td>
                    <td style="width:10%; " ><tags:label key="MSG.SAE.123"/></td>
                    <td>
                        <s:textfield name="saleManagmentForm.sCustName" id="sCustName" cssClass="txtInputFull" maxlength="100"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.SAE.124"/></td>
                    <td>
                        <s:textfield name="saleManagmentForm.sIsdn" id="sIsdn" cssClass="txtInputFull" maxlength="100"/>
                    </td>
                    <td><tags:label key="MSG.SAE.125"/></td>
                    <td>
                        <s:textfield name="saleManagmentForm.sContractNo" id="sContractNo" cssClass="txtInputFull" maxlength="100"/>
                    </td>
                    <td ><tags:label key="MSG.SAE.126"/></td>
                    <td>
                        <tags:dateChooser id="sTransFromDate" property="saleManagmentForm.sTransFromDate" />
                    </td>
                    <td ><tags:label key="MSG.SAE.127"/></td>
                    <td>
                        <tags:dateChooser id="sTransToDate" property="saleManagmentForm.sTransToDate" />
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.SAE.128"/></td>
                    <td>
                        <tags:imSelect name="saleManagmentForm.sTransStatus"
                                       id="sTransStatus"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SAE.129"
                                       list="1:MSG.SAE.130, 2:MSG.SAE.131, 3:MSG.SAE.132, 4:MSG.SAE.133"/>
                    </td>
                    <td><tags:label key="MSG.SAE.134"/></td>
                    <td>
                        <tags:imSelect name="saleManagmentForm.sDeliverStatus"
                                       id="sDeliverStatus"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SAE.135"
                                       list="0:MSG.SAE.136, 1:MSG.SAE.137"/>
                    </td>

                    <td class="label"><tags:label key="MSG.SAE.010"/></td>
                    <td class="text">
                        <tags:imSelect name="saleManagmentForm.sPayMethod"
                                       id="sPayMethod" 
                                       theme="simple"
                                       cssClass="txtInputFull"
                                       headerKey = "" 
                                       headerValue = "MSG.SAE.014"
                                       list = "lstPayMethod"
                                       listKey = "code" 
                                       listValue = "name"
                                       />
                    </td>
                </tr>
                <%--tr>
                    <td colspan="8" align="center">
                        <tags:submit  id="searchButton"
                                      formId="saleManagmentForm"
                                      showLoadingText="true"
                                      targets="searchResultArea"
                                      value="MSG.SAE.138"
                                      preAction="saleManagmentAction!searchSaleTrans.do"/>
                    </td>
                </tr--%>
            </table>
        </div>
        <sx:div id="searchResultArea">
            <jsp:include page="saleTransManagmentResult.jsp"/>
        </sx:div>
    </tags:imPanel>
</s:form>
<script type="text/javascript" language="javascript">
    dojo.event.topic.subscribe("SaleTrans/search", function(event, widget){
        widget.href = 'saleManagmentAction!searchSaleTrans.do';
    });
    
</script>
