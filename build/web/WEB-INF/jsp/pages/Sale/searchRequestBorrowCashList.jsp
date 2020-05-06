<%--
    Document   : invoiceSaleTransList
    Created on : 22/04/2009, 16:43:14 PM
    Author     : tungtv
    Desc       : danh sach giao dich can lap HD

--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>


<sx:div id="resultArea" theme="simple">
    <%--Thong bao ket qua tim kiem--%>
    <sx:div id="returnMsgArea" theme="simple">
        <jsp:include page="returnMsg.jsp"/>
    </sx:div>
    <sx:div id="RequestPrinterArea">
        <jsp:include page="printRequestBorrowCashResult.jsp"/>
    </sx:div>
    <%--Danh sach hoa don--%>
    <sx:div id="requestBorrowCashListArea" theme="simple">
        <table>
            <tr>
                <td style="width:100%;vertical-align:center">
                    <s:if test="#attr.form.urlReport != null">
                        <s:url id="fileguide" namespace="/" action="form02Download" >
                            <s:param name="fileDownloadForm02" value="#attr.form.urlReport"/>
                        </s:url>
                        <s:a  href="%{fileguide}">Export Excel</s:a>  
                    </s:if>
                </td>
            </tr>
        </table>
        <s:if test="#request.listRequest != null && #request.listRequest.size() != 0">
            <jsp:include page="requestBorrowCashList.jsp"/>
        </s:if>
    </sx:div>



</sx:div>
