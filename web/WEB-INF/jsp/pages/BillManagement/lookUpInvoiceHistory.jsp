<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : lookUpInvoiceHistory
    Created on : Jul 8, 2009, 1:54:56 PM
    Author     : nhocrep
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        if (request.getAttribute("invoiceListHistory") == null) {
            request.setAttribute("invoiceListHistory", request.getSession().getAttribute("invoiceListHistory"));
        }
        request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="reportBillsAction!lookUpComplete.do" method="POST" id="form" theme="simple">
    <s:if test="#session.invoiceListHistory != null && #session.invoiceListHistory.size() != 0"></s:if>
        <sx:div id="displayTagFrame">
            <fieldset style="width:95%; padding:10px 10px 10px 10px">
                <legend class="transparent">${fn:escapeXml(imDef:imGetText('MSG.history.invoice'))} </legend>
                <div style="overflow:auto; max-height:350px;">
                    <display:table targets="displayTagFrame" name="invoiceListHistory"
                                   id="invoice" class="dataTable"
                                   cellpadding="1" cellspacing="1">
                        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" headerClass="tct" style="width:40px; text-align:center" escapeXml="true">
                        ${fn:escapeXml(invoice_rowNum)}
                        </display:column>
                        <display:column property="serialNo" title="${fn:escapeXml(imDef:imGetText('MSG.bill.code'))}" escapeXml="true"/>
                        <display:column property="blockNo" title="${fn:escapeXml(imDef:imGetText('MSG.bin.book.number'))}" escapeXml="true"/>
                        <display:column property="fromToInvoice" title="${fn:escapeXml(imDef:imGetText('MSG.bill.range'))}" escapeXml="true"/>
                        <display:column property="parentShopCode" title="${fn:escapeXml(imDef:imGetText('MSG.communication.unit'))}" escapeXml="true"/>
                        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.receive.unit'))}" >
                            <s:if test="#attr.invoice.staffCode != null && #attr.invoice.staffCode!=''">
                                <s:property escapeJavaScript="true"  value="#attr.invoice.staffCode"/>
                            </s:if>
                            <s:else>
                                <s:property escapeJavaScript="true"  value="#attr.invoice.childShopCode"/>
                            </s:else>
                        </display:column>
                        <display:column property="dateCreated" title="${fn:escapeXml(imDef:imGetText('MSG.impact.date'))}" format="{0,date,hh:mm - dd/MM/yyyy }" style="text-align:center"/>
                        <display:column escapeXml="true"  property="transferTypeName" title="${fn:escapeXml(imDef:imGetText('MSG.impact.type'))}" style="text-align:center"/>
                        <display:column property="userCreated" title="${fn:escapeXml(imDef:imGetText('MSG.impact.user'))}" style="text-align:center" escapeXml="true"/>
                    </display:table>
                </div>
            </fieldset>
        </sx:div >
        <s:token/>
</s:form>
