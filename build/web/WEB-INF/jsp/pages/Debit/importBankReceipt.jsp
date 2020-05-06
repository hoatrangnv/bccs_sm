<%-- 
    Document   : importBankReceipt
    Created on : Oct 28, 2010, 10:55:30 AM
    Author     : tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="">
    <div align="center">
        <tags:displayResult id="returnMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
    </div>
    <s:form method="POST" id="form" action="importBRMAction" theme="simple">
<s:token/>

        <tags:imPanel title="MSG.search.info">
            <div align="center">
                <table style="width:100%" class="inputTbl4Col">
                    <tr>
                        <td class="label"><tags:label key="MSG.SAE.126"/></td>
                        <td class="text">
                            <tags:dateChooser property="form.fromDateSearch" id="form.fromDateSearch" styleClass="txtInputFull"/>
                        </td>
                        <td class="label"><tags:label key="MSG.SAE.127"/></td>
                        <td class="text">
                            <tags:dateChooser property="form.toDateSearch" id="form.toDateSearch" styleClass="txtInputFull"/>
                        </td>
                    </tr>
                </table>
            </div>
            <br/>
            <div align="center">
                <tags:submit formId="form" cssStyle="width:80px;"
                             preAction= "importBRMAction!searchImportBankReceipt.do"
                             value="MSG.DET.082"
                             targets="bodyContent"/>
                <tags:submit formId="form" cssStyle="width:80px;"
                             preAction= "importBankReceiptAction!preparePage.do"
                             value="Import"
                             targets="bodyContent"/>
            </div>           
        </tags:imPanel>
        
        <br>
        <div align="center" id ="lstBRMArea">
            <jsp:include page="listImportBankReceipt.jsp"/>
        </div>
        <br>
        <div align="center" id="viewBRDetailArea">
            <jsp:include page="importBankReceiptDetail.jsp"/>
        </div>
    </s:form>
</tags:imPanel>
