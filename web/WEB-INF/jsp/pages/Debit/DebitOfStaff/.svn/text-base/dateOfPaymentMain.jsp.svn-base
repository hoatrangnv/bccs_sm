<%-- 
    Document   : createRequestDebitMain
    Created on : May 14, 2013, 2:02:04 PM
    Author     : ThinhPH2_S
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<tags:imPanel title="title.createRequest.page">
    <s:form id="createRequestDebitForm" action="dateOfPaymentAction" theme="simple" method="post">
        <fieldset class="imFieldset">
            <legend>${fn:escapeXml(imDef:imGetText('lbl.thong_tin_yeu_cau'))}</legend>
            <table class="inputTbl6Col">
                <tr>
                    <td class="label">
                        <tags:label key="L.100026"/>
                    </td>
                    <td class="text">
                        <s:textfield name="createRequestDebitForm.note" id="note" theme="simple" cssClass="txtInputFull" maxLength="200"/>
                    </td>
                    <td class="label">
                        <tags:label key="lbl.file.cong.van" required="true"/>
                    </td>
                    <td class="text">
                        <tags:imFileUpload id="fileUpload" name="createRequestDebitForm.clientFileName" serverFileName="createRequestDebitForm.serverFileName" extension="pdf;png;jpg;jpeg;bmp"/>
                    </td>
                </tr>                
            </table>
        </fieldset>

        <div id="inputDateOfPaymentDiv">
            <jsp:include page="dateOfPaymentForEmp.jsp"/>
        </div>

        <div align="center">
            <tags:submit targets="bodyContent"
                         formId="createRequestDebitForm"
                         cssStyle="width:100px;"
                         value="lbl.lap_yeu_cau"
                         showLoadingText="true"
                         preAction="dateOfPaymentAction!saveDebitRequest.do"
                         validateFunction="validateMain();"/>
        </div>
    </s:form>
</tags:imPanel>

<script type="text/javascript">
    validateMain = function(){
        if(jQuery("#fileUpload").val() == ""){
            jQuery("#displayResultMsg").html('<s:property value="getText('err.loi_chua_chon_file_dinh_kem')"/>');
            jQuery("#fileUpload").focus();
            return false;
        }
        return true;
    }
</script>