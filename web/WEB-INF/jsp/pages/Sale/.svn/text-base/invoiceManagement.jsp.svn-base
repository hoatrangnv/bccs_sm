<%--
    Document   : InvoiceManagement
    Created on : 14/09/2009
    Author     : TrongLV
    Desc       : Quan ly hoa don ban hang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.SAE.184">
    <s:form action="InvoiceManagementAction" theme="simple" method="post" id="form">
<s:token/>

        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td class="label"> <tags:label key="MSG.SAE.185"/></td>
                    <td class="text" colspan="5">
                        <tags:imSearch codeVariable="form.shopCodeSearch" nameVariable="form.shopNameSearch"
                                       codeLabel="MSG.SAE.109" nameLabel="MSG.SAE.110"
                                       searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                       searchMethodName="getListShop"
                                       elementNeedClearContent="form.staffCodeSearch;form.staffNameSearch"
                                       getNameMethod="getNameShop"
                                       roleList="saleTransManagementf9Shop"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.SAE.111"/></td>
                    <td class="text" colspan="5">
                        <tags:imSearch codeVariable="form.staffCodeSearch" nameVariable="form.staffNameSearch"
                                       codeLabel="MSG.SAE.111" nameLabel="MSG.SAE.112"
                                       searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                       searchMethodName="getListStaff"
                                       otherParam="form.shopCodeSearch"
                                       getNameMethod="getNameStaff"
                                       roleList="saleTransManagementf9Staff"/>
                    </td>
                </tr>
                <tr>
                    <s:hidden name="form.staffId" id="staffId"/>
                    <s:hidden name="form.shopId" id="shopId"/>
                    <td class="label"><tags:label key="MSG.SAE.123"/></td>
                    <td class="text">
                        <s:textfield name="form.custNameSearch" id="custNameSearch" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.187"/></td>
                    <td class="text">
                        <s:textfield name="form.serialNoSearch" id="serialNoSearch" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.188"/></td>
                    <td class="text">
                        <s:textfield name="form.invoiceNoSearch" id="invoiceNoSearch" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.SAE.126"/></td>
                    <td class="text">
                        <tags:dateChooser property="form.fromDateSearch" id="fromDateSearch" styleClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.127"/></td>
                    <td class="text">
                        <tags:dateChooser property="form.toDateSearch" id="toDateSearch" styleClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.189"/></td>
                    <td class="text">
                        <tags:imSelect name="form.invoiceStatusSearch"
                                       id="invoiceStatusSearch"
                                       cssClass="txtInputFull"
                                       list="1:MSG.SAE.191, 4:MSG.SAE.192"
                                       headerKey="" headerValue="MSG.SAE.190"/>
                    </td>
                </tr>
            </table>
            <br/>
            <div style="width:100%" class="divHasBorder">
                <div align="center" style="width:100%">
                    <tags:submit targets="bodyContent"
                                 formId="form" 
                                 value="MSG.SAE.138"
                                 preAction="InvoiceManagementAction!searchInvoice.do"
                                 showLoadingText="true"
                                 cssStyle="width:120px;"/>
                    <tags:submit id="exportExcel" confirm="false" formId="form" showLoadingText="true" 
                                 targets="bodyContent" value="Excel..." preAction="InvoiceManagementAction!expInvoiceToExcel.do"
                                 cssStyle="width:120px;"/>
                </div>
                <div align="center" style="width:100%">
                    <s:if test="form.exportUrl !=null && form.exportUrl!=''">
                        <script>
                            window.open('${contextPath}<s:property escapeJavaScript="true"  value="form.exportUrl"/>','','toolbar=yes,scrollbars=yes');
                        </script>
                        <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="form.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>
                    </s:if>
                </div>
            </div>
        </div>

        <%--Hien thi ket qua tim kiem--%>
        <jsp:include page="invoiceSaleTransList.jsp"/>

    </s:form>

</tags:imPanel>
