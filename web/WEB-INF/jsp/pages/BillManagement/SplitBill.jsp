
<%-- 
    Document   : SelectBillDepartment
    Created on : Feb 18, 2009, 10:51:45 AM
    Author     : TungTV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>


<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="splitBillAction" method="POST" id="frm" theme="simple">
<s:token/>

    <fieldset style="width:95%; padding:10px 10px 10px 10px">
        <legend class="transparent">Thông tin hóa đơn </legend>

        <table style="width:100%" cellpadding="0" cellspacing="4" border="0">

            <tr>
                <td class="label8Col">
                    <tags:label key="MSG.bill.sign"/>
                </td>
                <td class="row8Col">
                    <s:textfield name="form.billSerial" id="billSerial" cssClass="txtInput"/>
                </td>
                <td class="label8Col">
                    <tags:label key="MSG.bin.book.type"/>
                </td>
                <td class="row8Col">
                    <s:select name="form.billCategory"
                              id="billCategory"
                              cssClass="txtInput"
                              headerKey="" headerValue="--Chọn loại T/Q--"
                              list="%{#session.listBookTypes != null?#session.listBookTypes:#{}}"
                              listKey="bookTypeId" listValue="blockName"
                              />
  
                </td>
            </tr>

            <tr>
                <td class="label8Col">
                    <tags:label key="MSG.generates.status"/>
                </td>
                <td class="row8Col">
                    <s:select name="form.billSituation" id="billSituation"
                              list="#{'1':'Chưa giao','3':'Đang sử dụng'}"
                              headerKey="" headerValue="--Chọn trạng thái hóa đơn--"/>

                </td>
            </tr>

        </table>

        <div style="width:100%" align="center">
            <tags:stylishButton ajax="false" type="button" onClick="transition('search');">Tìm kiếm</tags:stylishButton>
        </div>

    </fieldset>

    <fieldset style="width:95%; padding:10px 10px 10px 10px">
        <legend class="transparent"> <tags:label key="MSG.invoice.list"/></legend>
        <table width="100%" align="center">
            <tr>
                <td>
                    <jsp:include page="SplitBillSearchResult.jsp"/>
                </td>
            </tr>
        </table>
    </fieldset>



</s:form>
