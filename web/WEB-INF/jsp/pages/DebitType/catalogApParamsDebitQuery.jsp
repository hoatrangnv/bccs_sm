<%-- 
    Document   : catalogConfigPayDateQuery
    Created on : May 29, 2013, 8:42:08 AM
    Author     : ThinhPH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<s:form id="catalogApParamsDebitForm" method="POST" theme="simple">
    <s:hidden name="inputForm.code" id="hiddenCode"
              cssClass="txtInputFull"/>
    <table style="width:100%" class="inputTbl7Col">
        <tr>
            <td><tags:label key="lbl.loai_tham_so"/></td>
            <td>
                <tags:imSelect name="inputForm.type"
                               id="type"
                               cssClass="txtInputFull"
                               headerKey="" headerValue="lbl.chon_loai_tham_so"
                               disabled="false"
                               list="lstParamType"
                               listKey="code" listValue="name"
                               />
                <s:if test="#request.isEdit">
                    <s:hidden name="inputForm.type" id="hiddenType"
                              cssClass="txtInputFull"/>
                </s:if>
            </td>
            <td><tags:label key="lbl.ten_tham_so"/></td>
            <td>
                <s:textfield id="name" name="inputForm.name" maxlength="50" />
            </td>
            <td><tags:label key="MSG.LIMITED.06"/></td>
            <td>
                <tags:imSelect name="inputForm.status"
                               id="status"
                               cssClass="txtInputFull"
                               headerKey="" headerValue="MSG.GOD.189"
                               list="0:MSG.inactive, 1:MSG.active"/>
            </td>
        </tr>
    </table>
</s:form>
<center>
    <tags:submit id="searchId" formId="catalogApParamsDebitForm"
                 showLoadingText="true" targets="divListResultId" value="MSG.find"                                                                  
                 validateFunction="validateSearch()"
                 preAction="catalogApParamsDebit!search.do"
                 />
    <tags:submit id="insertId" formId="catalogApParamsDebitForm"
                 showLoadingText="true" targets="divListResultId" value="MSG.generates.addNew"                                                                  
                 validateFunction="validateInsert()"
                 preAction="catalogApParamsDebit!insert.do"
                 />
    <tags:submit id="updateId" formId="catalogApParamsDebitForm"
                 showLoadingText="true" targets="divListResultId" value="MSG.SIK.306"                                                                  
                 validateFunction="validateUpdate()"
                 preAction="catalogApParamsDebit!update.do" cssStyle="display:none"
                 />
    <button id="cancelUpdate" onclick="cancelUpdate()" style="display: none"><s:text name="lbl.bo_qua"/></button>
</center>
<script type="text/javascript">
    <s:if test="#request.isEdit">
        jQuery("#type").attr("disabled", "true");
    </s:if>
    <s:else>
        jQuery("#type").attr("disabled", "");
    </s:else>
    <s:if test="#request.isEdit">
        jQuery("#updateId").show();
        jQuery("#cancelUpdate").show();
        jQuery("#searchId").hide();
        jQuery("#insertId").hide();
    </s:if>
</script>
