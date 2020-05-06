<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%-- 
    Document   : SelectBillDepartment
    Created on : Feb 18, 2009, 10:51:45 AM
    Author     : TungTV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>


<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>


        <%int iIndex = 0;%>
        <s:if test="#session.invoiceList != null && #session.invoiceList.size()>0">
            
                <sx:div id="displayTagFrame">
                    <%
        request.setAttribute("invoiceList", request.getSession().getAttribute("invoiceList"));
                    %>
                    <%--font color='red'>
                        <html:errors/>
                    </font--%>
                    <display:table name="invoiceList" targets="displayTagFrame" id="invoice" pagesize="10" class="dataTable" cellpadding="1" cellspacing="1" requestURI="splitBillAction!pageNavigator.do" >
                        <%--font color='red'>
                            <html:errors/>
                        </font--%>
                        <display:column title="STT" sortable="false" headerClass="tct">
                            <div align="center"><%=StringEscapeUtils.escapeHtml(++iIndex)%></div>
                        </display:column>
                        <display:column title="Tách dải" sortable="false" headerClass="tct">
                            
                            <%--bean:define name="invoice" property="invoiceListId" id="invoiceListId" type="java.lang.Long"/--%>
                            <s:url action="splitBillAction" id="URL1">
                                <s:param name="invoiceListId" value="#attr.invoice.invoiceListId"/>
                            </s:url>
                            <div align="center" style="vertical-align:middle; width:50px">
                                <a class="cursorHand" onclick="openPopup('<s:property escapeJavaScript="true"  value="URL1"/>',1050,550)">
                                    <img src="<%=StringEscapeUtils.escapeHtml(request.getContextPath())%>/share/img/edit_icon.jpg" title="Tách dải" alt="Tách dải"/>
                                </a>
                            </div>
                        </display:column>
                            <display:column property="serialNo" title="Ký hiệu hoá đơn" sortable="false" headerClass="sortable" escapeXml="true"/>
                        <display:column escapeXml="true"  property="blockName" title="Loại T/Q" sortable="false" headerClass="sortable"/>
                        <display:column property="blockNo" title="Số T/Q" sortable="false" headerClass="sortable" escapeXml="true"/>
                        <display:column property="fromToInvoice" title="Dải hóa đơn" sortable="false" headerClass="sortable" escapeXml="true"/>
                        <display:column escapeXml="true"  property="numberOfInvoice" title="Số hóa đơn" sortable="false" headerClass="sortable"/>
                        <display:column title="Trạng thái" sortable="false" headerClass="sortable">
                            <s:if test="#attr.invoice.status == '1'">
                                Trong kho - chưa giao
                            </s:if>
                            <s:if test="#attr.invoice.status == '3'">
                                Đang sử dụng
                            </s:if>
                        </display:column>
                    </display:table>
                </sx:div>
            
        </s:if>
        <s:else>
            <font color='red'>
                <tags:label key="MSG.not.found.records"/>
            </font>
        </s:else>
        
        <!--</fieldset>-->
        
    </body>
</html>
