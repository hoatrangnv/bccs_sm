<%-- 
    Document   : catalogConfigPayDateMain
    Created on : May 29, 2013, 8:41:39 AM
    Author     : ThinhPH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<script type="text/javascript">
    validateSearch = function(){
        if(jQuery("#type").val()==""){
            alert('<s:text name="msg.693.17"/>');
            jQuery("#type").focus();
            return false;
        }
        return true;
    }
    preUpdate = function(type, code){
        ajaxAction("divQueryId","catalogApParamsDebit!preUpdate.do?inputForm.type="+type+"&inputForm.code="+code);
    }
    deletes = function(type, code){
        ajaxAction("divListResultId","catalogApParamsDebit!delete.do?"+token.getTokenParamString()
            +"&inputForm.type="+type+"&inputForm.code="+code);
    }
    cancelUpdate = function(){
        resetForm();
    }
    validateInsert = function(){
        if(jQuery("#type").val()==""){
            alert('<s:text name="msg.693.17"/>');
            jQuery("#type").focus();
            return false;
        }
        if(jQuery("#name").val()==""){
            alert('<s:text name="msg.693.18"/>');
            jQuery("#name").focus();
            return false;
        }
        return true;
    }
    validateUpdate = function(){
        if(jQuery("#status").val()==""){
            alert('<s:text name="msg.693.19"/>');
            jQuery("#status").focus();
            return false;
        }
        return validateInsert();
    }
    resetForm = function(){
        jQuery("#type").attr("disabled", false);
        jQuery("#name").val("");
        jQuery("#hiddenCode").val("");
        jQuery("#updateId").hide();
        jQuery("#cancelUpdate").hide();
        jQuery("#searchId").show();
        jQuery("#insertId").show();
        jQuery("#hiddenType").remove();
    }
</script>
<tags:imPanel title="title.catalogApParamsDebitMain.page">
    <fieldset class="imFieldset">
        <legend>${imDef:imGetText('lbl.cau_hinh_tham_so')}</legend>
        <div id="divQueryId">
            <jsp:include page="catalogApParamsDebitQuery.jsp"/>
        </div>
    </fieldset>
    <fieldset class="imFieldset">
        <legend>${imDef:imGetText('lbl.danh_sach_cau_hinh')}</legend>
        <div id="divListResultId">
            <jsp:include page="catalogApParamsDebitListResult.jsp"/>
        </div>
    </fieldset>
</tags:imPanel>
