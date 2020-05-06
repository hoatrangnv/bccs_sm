<%-- 
    Document   : imFileUpload
    Created on : Sep 12, 2009, 1:49:50 PM
    Author     : Doan Thanh 8
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="name" required="true" rtexprvalue="true"%>
<%@attribute name="id" required="true" rtexprvalue="true"%>
<%@attribute name="serverFileName" required="true" rtexprvalue="true"%>
<%@attribute name="cssStyle" required="false" rtexprvalue="true"%>

<%@attribute name="impBankReceipt" required="false" rtexprvalue="true"%>

<!--tutm1 21/10/2011 dinh dang file, vi du : xls;txt;pdf; -->
<%@attribute name="extension" required="false" rtexprvalue="true"%> 

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<%-- any content can be specified here e.g.: --%>
<table style="${pageScope.cssStyle}; border-spacing:0px; ">
    <tr>
        <td width="85%">
            <s:textfield name="%{#attr.name}" id="%{#attr.id}" readonly="true" cssStyle="width:100%; "/>
        </td>        
        <td style="width:15%; text-align:right; ">
            <s:if test="#attr.impBankReceipt != null && #attr.impBankReceipt == 'true' ">
                <input type="button" value="${imDef:imGetText('MSG.importByFile')}" id="${pageScope.id}_1" style="width:80px;" onclick="btnSelectFileIBRClick('${pageScope.id}', '${pageScope.serverFileName}_1', '${pageScope.extension}')"/>
            </s:if>
            <s:else>
                <input type="button" value="${imDef:imGetText('MSG.importByFile')}" id="${pageScope.id}_1" style="width:80px;" onclick="btnSelectFileClick('${pageScope.id}', '${pageScope.serverFileName}_1', '${pageScope.extension}')"/>
                <%--<input type="button" value="${imDef:imGetText('MSG.importByFile')}" id="${pageScope.id}_1" style="width:80px;" onclick="btnSelectFileClick('${pageScope.id}', '${pageScope.serverFileName}_1')"/>--%>
            </s:else>
        </td>
    </tr>
</table>

<s:hidden name="%{#attr.serverFileName}" id="%{#attr.serverFileName}_1"/> <%--doi tuong an, ten server file sau khi upload--%>

<script language="javascript">

    //Import GNT
    btnSelectFileIBRClick = function(clientFileNameId, serverFileName, extension) {
        openDialog("${contextPath}/FileUploadAction!prepareUploadFile.do?clientFileNameId=" + clientFileNameId + "&serverFileNameId=" + serverFileName + "&impBankReceipt=true" + "&extension=" + extension, 700, 450, true);
    }
    
    // tutm1 21/10/2011 : bo sung phan check extension cua file
    btnSelectFileClick = function(clientFileNameId, serverFileName, extension) {
        //openDialog("${contextPath}/ImFileUploadPopupAction.do?clientFileNameId=" + clientFileNameId + "&serverFileNameId=" + serverFileName, 700, 450, true);
        openDialog("${contextPath}/FileUploadAction!prepareUploadFile.do?clientFileNameId=" 
            + clientFileNameId + "&serverFileNameId=" + serverFileName + "&extension=" + extension, 700, 450, true);
    }
    //
    /*
    refreshFilePath = function(clientFileName, serverFileName) {
        $('${pageScope.id}').value = clientFileName;
        $('${pageScope.serverFileName}_1').value = serverFileName;
    }
     */
    //
    refreshFilePath = function(clientFileNameId, clientFileName, serverFileNameId, serverFileName) {
        document.getElementById(clientFileNameId).value = clientFileName;
        document.getElementById(serverFileNameId).value = serverFileName;
    }
    //
    disableImFileUpload = function (componentId, bDisable) {
        var imFileUploadComponent = document.getElementById(componentId + '_1');
        if(imFileUploadComponent != null) {
            imFileUploadComponent.disabled = bDisable;
        }
    }

</script>