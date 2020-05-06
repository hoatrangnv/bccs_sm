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
                    <td style="width:10%; "><tags:label key="MSG.shop.code" required="true"/></td>
                    <td style="width:30%; " class="oddColumn">
                        <tags:imSearch codeVariable="form.shopCode"
                                       nameVariable="form.shopName"
                                       codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"/>
                    </td>
                    <td style="width:10%; "><tags:label key="MSG.fromDate" required="true" /></td>
                    <td style="width:20%; " class="oddColumn">
                        <tags:dateChooser property="form.fromDateSearch"/>
                    </td>
                    <td style="width:10%; "><tags:label key="MSG.generates.to_date" required="true" /></td>
                    <td class="oddColumn">
                        <tags:dateChooser property="form.toDateSearch"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:10%; "><tags:label key="MSG.SIK.002"/></td>
                    <td style="width:30%; " class="oddColumn">
                        <tags:imSelect name="form.approveStatusSearch"
                                       id="approveStatusSearch"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SAE.129"
                                       list="1:MSG.Status.Fn.Approved, 2:MSG.Status.Fn.NotApproved, 5: MSG.Status.Fn.Rejected, 3:MSG.Status.St.Confirmed ,4:MSG.Status.St.NotConfirmed"/>
                    </td>
                </tr>
            </table>
            <br/>
            <div style="width:100%" class="divHasBorder">
                <div align="center" style="width:100%">
                    <tags:submit targets="bodyContent"
                                 formId="form" 
                                 value="MSG.SAE.138"
                                 preAction="borrowCashAction!searchRequestBorrowCash.do"
                                 showLoadingText="true"
                                 cssStyle="width:120px;"/>
                </div>
            </div>
        </div>

        <%--Hien thi ket qua tim kiem--%>
        <jsp:include page="searchRequestBorrowCashList.jsp"/>

    </s:form>

</tags:imPanel>
