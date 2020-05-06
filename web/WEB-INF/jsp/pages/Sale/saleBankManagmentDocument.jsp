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
<s:form action="orderManagementAction!searchSaleTrans" theme="simple"  enctype="multipart/form-data" method="post" id="saleManagmentForm">
    <s:token/>


    <tags:imPanel  title="Search Bank Document">
        <div >
            <table class="inputTbl8Col">
                <tr>
                    <td class="label"> Bank document no </td>
                    <td style="width:15%; " >
                        <s:textfield name="saleManagmentForm.sSaleTransCode" id="sSaleTransCode" cssClass="txtInputFull" maxlength="20"/>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.SAE.126"/></td>
                    <td>
                        <tags:dateChooser id="sTransFromDate" property="saleManagmentForm.sTransFromDate" />
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.127"/></td>
                    <td>
                        <tags:dateChooser id="sTransToDate" property="saleManagmentForm.sTransToDate" />
                    </td>
                </tr>
                
            </table>
        </div>
        <sx:div id="searchResultArea">
            <jsp:include page="saleBankManagmentResultDocument.jsp"/>
        </sx:div>
    </tags:imPanel>
</s:form>
<script type="text/javascript" language="javascript">
    dojo.event.topic.subscribe("SaleTrans/search", function(event, widget) {
        widget.href = 'orderManagementAction!searchSaleTrans.do';
    });

</script>
