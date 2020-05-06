<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ListResource
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("lstIsdnFilter", request.getSession().getAttribute("lstIsdnFilter"));
%>


<div>
    <tags:displayResult property="resultFilterIsdn" id="resultFilterIsdnClient"/>
</div>
<div>
    <s:if test="filterIsdnForm.pathOut!=null && filterIsdnForm.pathOut!=''">
        <script type="text/javascript" language="JavaScript">
            window.open('${contextPath}<s:property escapeJavaScript="true"  value="filterIsdnForm.pathOut"/>','','toolbar=yes,scrollbars=yes');
        </script>
            <a href='${contextPath}<s:property escapeJavaScript="true"  value="filterIsdnForm.pathOut"/>' ><tags:label key="MSG.GOD.272"/></a>
    </s:if>
    &nbsp;
</div>

<fieldset class="imFieldset">
    <legend>
        ${fn:escapeXml(imDef:imGetText('MSG.GOD.273'))}
    </legend>
    <div style="height:430px; overflow:auto; ">
        <display:table id="isdnFilter" name="lstIsdnFilter" class="dataTable" requestURI="javascript: void(0)" cellpadding="1" cellspacing="1">
            <display:column  title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center; ">
                <s:property escapeJavaScript="true"  value="%{#attr.isdnFilter_rowNum}"/>
            </display:column>
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.ruleType'))}" property="groupFilterRuleName"/>
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.filterTypeRule'))}" property="filterTypeName" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.ruleName'))}"  property="rulesName" sortable="false" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.quantity'))}" property="quantity" sortable="false" format="{0,number,#,###}" headerClass="tct" style="text-align:right; "/>
            <display:column title="<input id = 'checkAllId' type='checkbox' onclick=\"selectCbAll()\">" sortable="false" headerClass="tct" style="width:50px; text-align:center">
                <s:checkbox name="filterIsdnForm.selectedRules" id="checkBoxId%{#attr.isdnFilter.rulesId}"
                            theme="simple" fieldValue="%{#attr.isdnFilter.rulesId}"
                            onclick="checkSelectCbAll();"/>
            </display:column>
        </display:table>
    </div>
</fieldset>

<s:if test="#request.lstIsdnFilter != null && #request.lstIsdnFilter.size()>0">
    <div align="center" style="width:100%">
        <tags:submit formId="filterIsdnForm" showLoadingText="true" confirm="true" confirmText="MSG.confirm.save.filtered.isdn" cssStyle="width:80px; "
                     targets="ListIsdnFilter"  value="MSG.saveResult" preAction="filterIsdnAction!saveData.do" validateFunction="validateForm()"/>
        <tags:submit formId="filterIsdnForm" showLoadingText="true" targets="ListIsdnFilter"  value="Export file" cssStyle="width:80px; "
                     preAction="filterIsdnAction!exportFilterIsdnResult.do" validateFunction="validateForm1()"/>
    </div>
</s:if>
<s:else>
    <div align="center" style="width:100%">
        <tags:submit formId="filterIsdnForm" value="MSG.saveResult" cssStyle="width:80px; "
                     preAction="filterIsdnAction!exportFilterIsdnResult.do" disabled="true"/>
        <tags:submit formId="filterIsdnForm" value="Export file" cssStyle="width:80px; "
                     preAction="filterIsdnAction!exportFilterIsdnResult.do" disabled="true"/>

    </div>
</s:else>

<script>

    //tamdt1, 21/08/2009 - start
    //xu ly select all
    selectCbAll = function(){
        selectAll("checkAllId", "filterIsdnForm.selectedRules", "checkBoxId");
    }

    checkSelectCbAll = function(){
        checkSelectAll("checkAllId", "filterIsdnForm.selectedRules", "checkBoxId");
    }
    //tamdt1, 21/08/2009 - end


    validateForm = function(){

        /*
        var selectedRules =$('selectedRules').value;
        if(selectedRules.length <0)
        {
            $('resultFilterIsdnClient').innerHTML="Chưa chọn luật để lưu";
            return false;
        }
         */

        $('resultFilterIsdnClient').innerHTML="";
        var rowId = document.getElementsByName("filterIsdnForm.selectedRules");
        var i = 0;
        for(i = 0; i < rowId.length; i++){
            if(document.getElementById("checkBoxId" + rowId[i].value).checked) {
                return true;
            }
        }
        $('resultFilterIsdnClient').innerHTML= '<s:text name="ERR.ISN.023"/>';
        //$('resultFilterIsdnClient').innerHTML="!!!Lỗi. Chưa chọn luật để lưu";
        return false;
    }
    validateForm1 = function(){

        $('resultFilterIsdnClient').innerHTML="";
        var rowId = document.getElementsByName("filterIsdnForm.selectedRules");
        var i = 0;
        for(i = 0; i < rowId.length; i++){
            if(document.getElementById("checkBoxId" + rowId[i].value).checked) {
                return true;
            }
        }
        $('resultFilterIsdnClient').innerHTML= '<s:text name="ERR.ISN.024"/>';
        //$('resultFilterIsdnClient').innerHTML="!!!Lỗi. Chưa chọn luật để export file";
        return false;


    }
 

</script>
