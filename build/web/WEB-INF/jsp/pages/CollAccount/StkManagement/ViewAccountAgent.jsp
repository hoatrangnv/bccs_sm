<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ViewAccountAgent
    Created on : Mar 17, 2010, 2:01:15 PM
    Author     : Vunt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%
            if (request.getAttribute("listAccountBook") == null) {
                request.setAttribute("listAccountBook", request.getSession().getAttribute("listAccountBook"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="exportStockToCollaboratorAction" theme="simple" enctype="multipart/form-data"  method="post" id="viewAccountAgentForm">
<s:token/>

    <s:hidden name="viewAccountAgentForm.accountType" id="viewAccountAgentForm.accountType"/>
    <div>
        <tags:displayResult id="displayResultMsgClient" property="displayResultMsgClient" type="key" />
    </div>
    <table class="inputTbl6Col">
        <sx:div id="displayParamDetail">
            <tr>
                <%--<td>Mã CTV/ĐB/ĐL</td>--%>
                <td><tags:label key="MSG.SIK.203"/></td>
                <td colspan="3">
                    <tags:imSearch codeVariable="viewAccountAgentForm.accountCode" nameVariable="viewAccountAgentForm.accountName"
                                   codeLabel="MSG.SIK.203" nameLabel="MSG.SIK.204"
                                   searchClassName="com.viettel.im.database.DAO.StockCollaboratorDAO"
                                   searchMethodName="getListStaff"
                                   otherParam="viewAccountAgentForm.accountType"
                                   getNameMethod="getNameStaff"
                                   roleList=""/>
                </td>
                <td>
                    <%--<input type="button" onclick="searchAccountAgent();"  value="Tìm kiếm"/>--%>
                    <input type="button" onclick="searchAccountAgent();"  value="${fn:escapeXml(imDef:imGetText('MSG.SIK.115'))}"/>
                </td>
            </tr>


        </sx:div>
    </table>

    <tags:imPanel title="MSG.SIK.205">
        <table class="inputTbl4Col">
            <tr>
                <%--<td class="label">Mã CTV/ĐB/ĐL </td>--%>
                <td><tags:label key="MSG.SIK.203"/></td>
                <td class="text">
                    <s:textfield name="viewAccountAgentForm.ownerCode" id="ownerCode" maxlength="100" cssClass="txtInputFull" readonly="true"/>
                </td>
                <%--<td class="label">Tên CTV/ĐB/ĐL </td>--%>
                <td><tags:label key="MSG.SIK.204"/></td>
                <td class="text">
                    <s:textfield name="viewAccountAgentForm.ownerName" id="ownerName" maxlength="100" cssClass="txtInputFull" readonly="true"/>
                </td>
            </tr>
            <tr>
                <%--<td class="label">Tổng tiền </td>--%>
                <td><tags:label key="MSG.SIK.206"/></td>
                <td class="text">
                    <s:textfield name="viewAccountAgentForm.amountText" id="amountText" maxlength="100" cssClass="txtInputFull" readonly="true"/>
                </td>
                <%--<td class="label">Tổng tiền tín dụng </td>--%>
                <td><tags:label key="MSG.SIK.207"/></td>
                <td class="text">
                    <s:textfield name="viewAccountAgentForm.amountBalanceText" id="amountBalanceText" maxlength="100" cssClass="txtInputFull" readonly="true"/>
                </td>
            </tr>    
        </table>
    </tags:imPanel>
</s:form>

<script>
    //textFieldNF($('amount'));
    //textFieldNF($('amountBalance'));
    searchAccountAgent = function() {
        $( 'displayResultMsgClient' ).innerHTML='';
        if (!validate()) {
            event.cancel = true;
        }
        //khac phuc loi struts voi kieu Double
        if(document.getElementById("amount").value=="0.0") {
            document.getElementById("amount").value = "";
        }
        if(document.getElementById("amountBalance").value=="0.0") {
            document.getElementById("amountBalance").value = "";
        }
        document.getElementById("viewAccountAgentForm").action="exportStockToCollaboratorAction!searchAccountAgent.do?"+ token.getTokenParamString();
        document.getElementById("viewAccountAgentForm").submit();
    }

    validate = function(){        
        return true;
    }

</script>
