<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : createImportBankReceipt
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

<tags:imPanel title="MSG.DET.133">
    <div align="center">
        <tags:displayResult id="returnMsg" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
    </div>
    <s:form method="POST" id="form" action="importBankReceiptAction" theme="simple">
<s:token/>

        <div align="center">
            <table style="width:100%">
                <tr>
                    <td  class="label" style="width:10%"><tags:label key="MSG.generates.file_data" required="true"/></td>
                    <td class="text" colspan="7" style="width:80%">
                        <!-- Edit by hieptd -->
                        <tags:imFileUpload 
                            name="form.clientFileName"
                            id="form.clientFileName"
                            serverFileName="form.serverFileName"
                            cssStyle="width:500px;"
                            impBankReceipt="true" extension="xls"/>
                    </td>
                    <td class="label" colspan="2" style="width:10%">
                        (<a onclick=downloadPatternFile()>download template</a>)
                    </td>
                </tr>
            </table>
        </div>
        <br/>
        <div align="center">
            <tags:submit formId="form"  cssStyle="width:80px;"
                         showLoadingText="Processing..." validateFunction="validateData();"
                         confirm="true"
                         confirmText="MSG.DET.132"
                         preAction= "importBankReceiptAction!importBankReceipt_MV.do"
                         value="MSG.DET.084"
                         targets="bodyContent"/>

            <input type="button" style="width:80px;" value="<s:text name="MSG.DET.085"/>" onclick="goBack();"/>
        </div>
        
        <div>
            <s:if test="#request.reportPath != null">
                <script>
                    window.open('${contextPath}/${fn:escapeXml(reportPath)}','','toolbar=yes,scrollbars=yes');
                </script>
                <a href="${contextPath}/${fn:escapeXml(reportAccountPath)}">
                    <tags:displayResult id="reportMessage" property="reportMessage" type="key"/>
                </a>
            </s:if>
        </div>

        
        
        <br>
        <div align="center">
            <jsp:include page="listImportBankReceipt.jsp"/>            
        </div>
        <br>
        <div align="center" id="viewBRDetailArea">
            <jsp:include page="importBankReceiptDetail.jsp"/>
        </div>
    </s:form>
</tags:imPanel>

<script>
    goBack = function(){
        gotoAction("bodyContent", "${contextPath}/importBRMAction!preparePageIBRM.do?"+ token.getTokenParamString(), "form")
    }

    downloadPatternFile = function() {
        window.open('${contextPath}/share/pattern/importBankReceipt_MV.xls','','toolbar=yes,scrollbars=yes');
    }

    validateData = function(){
        if ($('form.clientFileName') == null){
            $('returnMsg').innerHTML = '<s:text name="MSG.DET.134"/>';
            return false;
        }
        var object = $('form.clientFileName');
        if (trim(object .value) == ""){
            $('returnMsg').innerHTML = '<s:text name="MSG.DET.134"/>';
            object.select();
            object.focus();
            return false;
        }
        return true;
    }
</script>
