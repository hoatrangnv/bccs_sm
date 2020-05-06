<%--
    Document   : addBTSByFile
    Created on : Aug 8, 2013, 11:44:20 AM
    Author     : thuannx1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@page  import="com.viettel.im.database.BO.UserToken" %>
<%@taglib prefix="imDef" uri="imDef" %>


<tags:imPanel title="TARGET.IMPORTBYFILE">
    <div>
        <tags:displayResult id="returnMsg1" property="returnMsg1" propertyValue="returnMsgParam" type="key"/>
    </div>
    <div>
        <s:form action="manageBTSGroupAction" theme="simple" method="post" id="agentDistributeManagementForm">
            <s:token/>
            <table class="inputTbl6Col" align="right">
                <tr>

                    <td colspan="3" align="center">
                        <a href="${contextPath}/download.do?down=1&<s:property escapeJavaScript="true" value="fileTemplateAssginTarget"/>" >
                            ${fn:escapeXml(imDef:imGetText('TARGET.TEMPLATE'))}
                        </a>
                    </td>

                    <td class="label" ><tags:label key="BTS.ChooseFileUp" required="true" /></td>
                    <td  class="text" >
                        <tags:imFileUpload name="agentDistributeManagementForm.clientFileName" id="agentDistributeManagementForm.clientFileName" serverFileName="agentDistributeManagementForm.serverFileName"
                                           extension="xls;xlsx"/>
                    </td>

                    <td id="mustChooseType">
                        <tags:submit formId="agentDistributeManagementForm"
                                     showLoadingText="true"
                                     targets="addTargetByFile"
                                     value="TARGET.import"
                                     preAction="manageBTSGroupAction!addTargetByFile.do"
                                     validateFunction="checkValidFile()"/>
                    </td>
                </tr>
            </table>
        </s:form>        
        <div>
            <s:if test="#request.reportAccountPath != null">
                <script>
                    window.open('${contextPath}/download.do?<s:property escapeJavaScript="true" value="#request.reportAccountPath"/>','','toolbar=yes,scrollbars=yes');
                    <%-- window.open('${contextPath}/download.do?down=1&<s:property escapeJavaScript="true" value="fileTemplateUpdateChannel"/>','','toolbar=yes,scrollbars=yes'); --%>
                </script>
                <a href="${contextPath}/download.do?<s:property escapeJavaScript="true" value="#request.reportAccountPath"/>">
                    <tags:displayResult id="reportAccountMessage" property="reportAccountMessage" type="key"/>
                </a>
            </s:if>
        </div>

    </div>

</tags:imPanel>


<script type="text/javascript">
    
   
    checkValidFile = function(){
        var clientFileName = document.getElementById("agentDistributeManagementForm.clientFileName");
        if (trim(clientFileName.value).length ==0){    
            $( 'returnMsg1' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000036')"/>';
            clientFileName.focus();
            return false;
        }
        return true;
    }

    downloadAssginTargetFile = function() {
        window.open('${contextPath}/download.do?down=1&<s:property escapeJavaScript="true" value="fileTemplateAssginTarget"/>','','toolbar=yes,scrollbars=yes');
    }

        

</script>
