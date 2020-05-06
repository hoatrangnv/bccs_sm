<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : invoiceManagementList
    Created on : Aug 13, 2009, 8:49:24 AM
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="inventoryDisplay"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
    //request.setAttribute("lstInvoice", request.getSession().getAttribute("lstInvoice"));
%>

<div id="invoiceUsedList">
    <s:if test="#request.listRequest != null && #request.listRequest.size() != 0">

        <div style="width:100%; height:500px; overflow:auto;">
            <inventoryDisplay:table targets="invoiceUsedList" id="requestBorrowCash"
                                    name="listRequest" pagesize="500"
                                    class="dataTable" cellpadding="1" cellspacing="1"
                                    requestURI="borrowCashAction!pageNavigator.do" >


                <display:column escapeXml="true" property="requestId" title="${imDef:imGetText('MSG.SAE.048')}" sortable="false" style="text-align:left;" headerClass="tct"/>

                <display:column escapeXml="true" property="createDate" title="${imDef:imGetText('MSG.actionDate')}" sortable="false" style="text-align:left;" headerClass="tct"/>

                <display:column escapeXml="true" property="staffCode" title="${imDef:imGetText('MSG.staff.execute')}" sortable="false" style="text-align:left;" headerClass="tct"/>


                <display:column escapeXml="true" property="name" title="${fn:escapeXml(imDef:imGetText('MSG.name'))}" sortable="false" style="text-align:left;" headerClass="tct"/>

                <display:column escapeXml="true" property="amount" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.180'))}" sortable="false" headerClass="tct" style="text-align:left;"/>

                <display:column escapeXml="true" property="branch" title="${fn:escapeXml(imDef:imGetText('SHOP.BRANCH'))}" sortable="false" headerClass="tct" style="text-align:right;"/>

                <display:column escapeXml="true" property="payFor" title="${fn:escapeXml(imDef:imGetText('MSG.Pay.For'))}" sortable="false" headerClass="tct" style="text-align:right"/>

                <display:column escapeXml="true" property="actionOtp" title="${fn:escapeXml(imDef:imGetText('MSG.Action.Otp'))}" sortable="false" headerClass="tct" style="text-align:right;"/>

                <display:column escapeXml="true" property="approveOtp" title="${fn:escapeXml(imDef:imGetText('MSG.Approve.Otp'))}" sortable="false" headerClass="tct" style="text-align:right"/>

                <display:column escapeXml="true" property="financeApprove" title="${fn:escapeXml(imDef:imGetText('MSG.Finance.Approve'))}" sortable="false" headerClass="tct" style="text-align:right;"/>

                <display:column escapeXml="true" property="staffConfirm" title="${fn:escapeXml(imDef:imGetText('MSG.Staff.Confirm'))}" sortable="false" headerClass="tct" style="text-align:right"/>


                <display:column escapeXml="false" title=" ${imDef:imGetText('MSG.Create.Form')}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
                    <s:if test="#attr.requestBorrowCash.financeApprove != 'Rejected'">
                        <div id="a<s:property value="#attr.requestBorrowCash.requestId"/>">
                            <a href="#" onclick="printRequestBorrowCash('<s:property value="#attr.requestBorrowCash.requestId"/>')">
                                <img src="${contextPath}/share/img/accept.png" title="${imDef:imGetText('MSG.Create.Form')}" alt="${imDef:imGetText('MSG.Create.Form')}"/>
                            </a>
                        </div>
                    </s:if>
                    <s:else>
                        <div id="a<s:property value="#attr.requestBorrowCash.requestId"/>">
                            <a>
                                <img src="${contextPath}/share/img/delete_icon.jpg" title="${imDef:imGetText('MSG.Create.Form')}" alt="${imDef:imGetText('MSG.Create.Form')}"/>
                            </a>
                        </div>
                    </s:else>
                </display:column>



                <display:footer> <tr> <td colspan="18" style="color:green">
                            <s:property escapeJavaScript="true"  value ="getText('MSG.totalRecord')"/>:<s:property escapeJavaScript="true"  value="%{#request.lstInvoice.size()}" /></td> <tr> </display:footer>

                </div>

        </inventoryDisplay:table>
    </s:if>
</div>

<script type="text/javascript" language="javascript">
                                printRequestBorrowCash = function(requestId) {
                                    gotoAction('RequestPrinterArea', "${contextPath}/borrowCashAction!printRequestBorrowCash.do?requestId=" + requestId);
                                };

</script>
