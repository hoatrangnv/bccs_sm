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
        <!-- hien thi message -->
        <div>
            <tags:displayResult id="message1" property="returnMsg1" propertyValue="returnMsgParam1" type="key"/>
        </div>
        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td class="label"><tags:label key="MSG.SAE.185"/></td>
                    <td class="text">
                        <tags:imSearch codeVariable="form.shopCodeSearch" nameVariable="form.shopNameSearch"
                                       codeLabel="MSG.SAE.185" nameLabel="MSG.SAE.186"
                                       searchClassName="com.viettel.im.database.DAO.InvoiceManagementDAO"
                                       searchMethodName="getListShop"
                                       elementNeedClearContent="form.staffCodeSearch;form.staffNameSearch"
                                       roleList="reportRevenueByInvoicef9Shop"
                                       />
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.111"/></td>
                    <td class="text">
                        <tags:imSearch codeVariable="form.staffCodeSearch" nameVariable="form.staffNameSearch"
                                       codeLabel="MSG.SAE.111" nameLabel="MSG.SAE.112"
                                       searchClassName="com.viettel.im.database.DAO.InvoiceManagementDAO"
                                       searchMethodName="getListStaff"
                                       otherParam="form.shopCodeSearch"
                                       roleList="reportRevenueByInvoicef9Staff"
                                       />
                    </td>
                    <td class="label">
                        <tags:label key="MSG.invoice.type" required="true"/>
                    </td>
                    <td class="text">
                        <tags:imSelect name="form.invoiceType"
                                       id="form.invoiceType"
                                       cssClass="txtInputFull"
                                       list="1:sale.invoice"
                                       />
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
                        <tags:dateChooser property="form.toDateSearch" id="toDateSearch" styleClass="txtInputFull" />
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.189"/></td>
                    <td class="text">
                        <tags:imSelect name="form.invoiceStatusSearch"
                                       id="invoiceStatusSearch"
                                       cssClass="txtInputFull"
                                       list="1:MSG.SAE.191"
                                       headerKey="" headerValue="MSG.SAE.190"/>
                    </td>
                </tr>
            </table>

            <div style="width:100%" align="center">
                <tags:submit targets="bodyContent"
                             formId="form"
                             value="MSG.SAE.138"
                             preAction="InvoiceCreditManagementAction!searchInvoice.do"
                             validateFunction="checkValidate()"
                             showLoadingText="true"/>
            </div>
        </div>

        <%--Hien thi ket qua tim kiem--%>
        <jsp:include page="invoiceCreditSaleTransList.jsp"/>

    </s:form>

</tags:imPanel>
<script type="text/javascript">
    checkValidate = function () {
        if (trim($('form.invoiceType').value) == "") {
            $('message1').innerHTML = '<s:property value="getText('error.not.choose.invoice.type')" />'
            $('form.invoiceType').select();
            $('form.invoiceType').focus();
            return false;
        }
        $('message1').innerHTML = '';
        return true;
    }
</script>