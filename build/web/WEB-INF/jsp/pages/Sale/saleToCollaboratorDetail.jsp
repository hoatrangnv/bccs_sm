<%--
    Document   : saleToCollaboratorDetail    
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="inventoryDisplay"%>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <table style="width:100%; border-spacing:0; margin-top:3px;">
            <tr>
                <td style="width:40%; vertical-align:top;">
                    <tags:imPanel title="Nhập chi tiết">
                        <sx:div  id="inputGoods" cssStyle="width:100%; height:380px; vertical-align:top">
                            <jsp:include page="StockGoodsCollaborator.jsp"/>
                        </sx:div>
                    </tags:imPanel>
                </td>
                <td style="width:2px; "></td>
                <td style="vertical-align:top">
                    <tags:imPanel title="Danh sách hàng hoá">
                        <sx:div id="listGoods" cssStyle="width:100%; height:380px; vertical-align:top">
                            <jsp:include page="ListCollaboratorModelResult.jsp">
                                <jsp:param name="editable" value="true"/>
                                 <jsp:param name="payMethodId" value="2"/>
                            </jsp:include>
                        </sx:div>
                    </tags:imPanel>
                </td>
            </tr>
        </table>

        <div style="margin-top:3px;">
        <sx:submit parseContent="true" executeScripts="true" formId="saleToCollaboratorForm"
                   cssStyle="width:90px;"
                   loadingText="loading..."
                   targets="bodyContent" label="Tạo giao dịch" value="Tạo giao dịch"
                   beforeNotifyTopics="saleToCollaboratorAction/saveSaleToCollaborator"/>
        <s:property escapeJavaScript="true"  value="saleToCollaboratorForm.reSult"/>
    </div>
    
    </body>
</html>
