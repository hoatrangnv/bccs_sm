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

<tags:imPanel title="MES.CHL.038">
    <s:form action="borrowCashAction" theme="simple" method="post" id="form">
        <s:token/>

        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td class="label"><tags:label key="MSG.SAE.126"/></td>
                    <td class="text">
                        <tags:dateChooser property="form.fromDateSearch" id="fromDateSearch" styleClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.127"/></td>
                    <td class="text">
                        <tags:dateChooser property="form.toDateSearch" id="toDateSearch" styleClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
            <br/>
            <div style="width:100%" class="divHasBorder">
                <div align="center" style="width:100%">
                    <tags:submit targets="detailArea"
                                 formId="form" 
                                 value="MSG.GOD.200"
                                 preAction="borrowCashAction!getReportDebitByTrans.do"
                                 showLoadingText="true"
                                 cssStyle="width:120px;"/>
                </div>
            </div>
        </div>

        <%--Hien thi ket qua tim kiem--%>
        <sx:div id="detailArea">
            <jsp:include page="borrowCashReportDebitByUser.jsp"/>
        </sx:div>

    </s:form>

</tags:imPanel>
