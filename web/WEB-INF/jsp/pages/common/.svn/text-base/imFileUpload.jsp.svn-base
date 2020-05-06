<%-- 
    Document   : imSearchPopup
    Created on : Nov 17, 2009, 10:27:33 AM
    Author     : Doan Thanh 8
    Desc       : bat popup tim kiem thong tin
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="Upload file">
    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan tim kiem thong tin (loc danh sach) -->
    <div class="divHasBorder">
        <s:form enctype="multipart/form-data" method="post" action="ImFileUploadAction.do?serverFileName=" theme="simple" id="uploadForm">
            <input type="hidden"  name="pageId" id="pageId"/> <!--ThanhNC them de luu pageId tren popup-->
            <table class="inputTbl5Col">
                <tr>
                    <td style="width:10%; "><tags:label key="MSG.importByFile"/></td>
                    <td class="oddColumn" style="width:25%; ">
                        <s:file id = "uploadForm.fileUpload" name="uploadForm.fileUpload" size="70"/>
                    </td>
                    <td style="width:85px; text-align:right; ">
                        <input type="button" value="Upload" style="width:80px;" onclick="uploadFile()">
                    </td>
                </tr>
            </table>
            <s:hidden id="clientFileName" value="%{#request.clientFileName}"/>
            <s:hidden id="serverFileName" value="%{#request.serverFileName}"/>
            <s:hidden  id="clientFileNameId" name="uploadForm.clientFileNameId" />
            <s:hidden  id="serverFileNameId" name="uploadForm.serverFileNameId" />
            <s:token/>
            <s:hidden  id="uploadForm.impBankReceipt" name="uploadForm.impBankReceipt" />

        </s:form>
    </div>
    <s:if test="#request.Okie=='true'">
        <script type="text/javascript">
            var clientFileName = document.getElementById('clientFileName').value;	    	
            var clientFileNameId = document.getElementById('clientFileNameId').value;
            var serverFileName = document.getElementById('serverFileName').value;
            var serverFileNameId = document.getElementById('serverFileNameId').value;
            window.opener.refreshFilePath(clientFileNameId, clientFileName, serverFileNameId, serverFileName);
            window.close();
        </script>
    </s:if>
</tags:imPanel>

<script>

    uploadFile = function(){
        // dinh dang file 
        extension = getParam("extension");
        if (validateExtension(extension)) {
            document.getElementById("uploadForm").action="FileUploadAction!uploadFile.do?"+token.getTokenParamString();
            document.getElementById("uploadForm").submit();
        }
        else {
            $('message').innerHTML= '<s:text name="error.uploader.extension"/>' + extension; 
        }
    }
    // tutm1 : check extension cua file
    validateExtension = function(extension){
        // nguoi dung cho phep tat ca cac dinh dang file
        if (extension == null || extension.trim() == "")
            return true;
        extensions = extension.split(";");
        
        // lay duoi file tai len
        fileName = $('uploadForm.fileUpload').value;
        fileExt = fileName.split('.').pop();
        
        for (var i = 0; i < extensions.length; i++) {
            if (extensions[i].toLowerCase() == fileExt.toLowerCase())
                return true;
        }
        return false;
    }
    // lay param tu query string
    function getParam(name) {
        var regexS = "[\\?&]"+name+"=([^&#]*)";
        var regex = new RegExp( regexS );
        var tmpURL = window.location.href;
        var results = regex.exec( tmpURL );
        if( results == null )
            return "";
        else
            return results[1];
    }

</script>

