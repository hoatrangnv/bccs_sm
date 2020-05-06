<%--
    Document   : ExportStockToStaff
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<s:form action="exportStockToStaffAction" theme="simple" enctype="multipart/form-data"  method="POST" id="exportStockForm">
<s:token/>
        
    <s:hidden name="exportStockForm.actionType"/>
    <s:hidden name="exportStockForm.shopExpType" value="1"/>
    <tags:imPanel title="MSG.search.trans">
        <table class="inputTbl4Col" >
            <tr>
                <td class="label"> <tags:label key="MSG.transaction.code"/></td>
                <td class="text">
                    <s:textfield name="exportStockForm.actionCode" id="actionCode" cssClass="txtInputFull"/>
                    <script>$('actionCode').focus();</script>
                </td>
                <td  class="label"><tags:label key="MSG.status"/></td>
                <td  class="text">
                    <tags:imSelect name="exportStockForm.transStatus"
                              id="transStatus"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="MSG.GOD.189"
                              list="1:MSG.GOD.190,2:MSG.GOD.191,3:MSG.GOD.194,4:MSG.GOD.195,5:MSG.GOD.196"/>
                </td>
            </tr>
            <tr>
                <td  class="label"> <tags:label key="MSG.fromStore"/></td>
                <td  class="text">
                    <s:textfield name="exportStockForm.fromOwnerName" id="fromOwnerName" readonly="true" cssClass="txtInputFull"/>
                    <s:hidden name="exportStockForm.fromOwnerCode" id="fromOwnerCode"/>
                    <s:hidden name="exportStockForm.fromOwnerId" id="fromOwnerId"/>
                    <s:hidden name="exportStockForm.fromOwnerType" id="fromOwnerType"/>
                </td>
                <td  class="label"><tags:label key="MSG.toStore"/></td>
                <td  class="text" >
                    <s:hidden name="exportStockForm.toOwnerType" id="toOwnerType"/>
                    <tags:imSearch codeVariable="exportStockForm.toOwnerCode" nameVariable="exportStockForm.toOwnerName"
                                   codeLabel="MSG.store.receive.code" nameLabel="MSG.store.receive.name"
                                   searchClassName="com.viettel.im.database.DAO.StockCommonDAO"
                                   searchMethodName="getListStaffF9"
                                   getNameMethod="getListStaffF9"
                                   otherParam="fromOwnerCode"/>
                </td>
            </tr>
            <tr>
                <td  class="label">
                     <tags:label key="MSG.fromDate"/>
                </td>
                <td  class="text">

                    <tags:dateChooser property="exportStockForm.fromDate" id="fromDate" styleClass="txtInputFull"/>
                </td>
                <td  class="label">
                     <tags:label key="MSG.consign.date.to"/>
                </td>
                <td  class="text">

                    <tags:dateChooser property="exportStockForm.toDate" id="toDate" styleClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td colspan="4" align="center" >

                    <tags:submit formId="exportStockForm" confirm="false"
                                 showLoadingText="true" targets="searchArea"
                                 value="MSG.find" preAction="exportStockToStaffAction!searchExpTrans.do"/>
                </td>
            </tr>
        </table>


    </tags:imPanel>
    <sx:div id="searchArea" theme="simple">
        <jsp:include page="ListSearchExpNote.jsp"/>
        <jsp:include page="ExpStock.jsp"/>
    </sx:div>

</s:form>
