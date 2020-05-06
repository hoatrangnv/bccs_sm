<%-- 
    Document   : ReportStockImpExpV2
    Created on : Feb 7, 2013, 2:16:38 PM
    Author     : trungdh3_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%request.setAttribute("contextPath", request.getContextPath());%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>

<s:form method="POST" id="reportStockImpExpForm" action="reportImportExportStock" theme="simple" enctype="multipart/form-data" method="post">
    <%--vt:ShortCutJSParams  shortcutKey="F9" functionCallback="searchShop()"  styleId="reportStockImpExpForm.shopCode"/--%>
    <s:token/>
    <tags:imPanel title="MSG.criterion.report.create">
        <table class="inputTbl6Col" border="0" style="width:100%">
            <tr>
                <td class="label"><tags:label key="MSG.store.code" required="true" /></td>
                <td class="text">
                    <tags:imSearch codeVariable="reportStockImpExpForm.shopCode"
                                   nameVariable="reportStockImpExpForm.shopName"
                                   codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                   
                                   searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                   searchMethodName="getListShop"
                                   getNameMethod="getShopName"
                                   elementNeedClearContent=""
                                   roleList=""/>
                </td>
                <td class="label"><tags:label key="MSG.fromDate" required="true" /></td>
                <td  class="text">
                    <tags:dateChooser  property="reportStockImpExpForm.fromDate" id="fromDate"/>
                </td>
                <td class="label"><tags:label key="MSG.generates.to_date" required="true" /></td>
                <td class="text">
                    <tags:dateChooser property="reportStockImpExpForm.toDate" id="toDate"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.stock.stock.type" /></td>
                <td  class="text" >
                    <tags:imSelect name="reportStockImpExpForm.stockTypeId"
                                   id="reportStockImpExpForm.stockTypeId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.RET.160"
                                   list="stockType"
                                   listKey="stockTypeId" listValue="name"
                                   onchange=""/>

                <td class="label"><tags:label key="L.100083" /></td>
                <td  class="text" >
                    <tags:imSelect name="reportStockImpExpForm.lever"
                                   theme="simple"
                                   id="status"
                                   cssClass="txtInputFull"
                                    headerKey="" headerValue="L.100084"
                                   list="2:L.100085,3:L.100086,4:L.100087,5:L.100088"
                                   />       
                <td class="label"><tags:label key="MSG.generates.goods.status" /></td>
                <td class="text" >
                    <tags:imSelect name="reportStockImpExpForm.status"
                                   theme="simple"
                                   id="status"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.FAC.AssignPrice.ChooseStatus"
                                   list="1:MSG.GOD.new,2:MSG.GOD.old,3:MSG.GOD.error"
                                   />

            </tr>

            <tr>
                <td colspan="8" align="center" width="100%">
                    <tags:submit id="report" confirm="false" formId="reportStockImpExpForm" showLoadingText="true"  validateFunction="check()"
                                 targets="bodyContent" value="MSG.report" preAction="reportImportExportStock!exportStockImpExpReport.do"/>
                </td>
            </tr>
            <tr>
                <td colspan="8" align="center"  width="100%">
                    <tags:displayResult id="resultStockImpExpRpt" property="resultStockImpExpRpt"/>
                </td>
            </tr>
            <tr>
                <td colspan="8" align="center" width="100%">
                    <s:if test="#request.filePath !=null && #request.filePath!=''">
                        <script>
                            window.open('${contextPath}/download.do?${fn:escapeXml(filePath)}', '', 'toolbar=yes,scrollbars=yes');
                            <%--window.open('${contextPath}<s:property value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');
                                window.open('<s:property value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');--%>
                        </script>
                        <div>
                            <a href="${contextPath}/download.do?${fn:escapeXml(filePath)}">
                                <tags:displayResult id="reportStockImportPendingMessage" property="reportStockImportPendingMessage" type="key"/>
                            </a>
                            <%--<a href='${contextPath}<s:property value="#request.filePath"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về.</a>
                            <a href='<s:property value="#request.filePath"/>' ><tags:label key="MSG.clickhere.to.download" /></a>--%>
                        </div>
                    </s:if>
                </td>
            </tr>
        </table>

    </tags:imPanel>
</s:form>
<script language="javascript">
    check=function(){
        if(trim($('reportStockImpExpForm.shopCode').value)==""){
            $('resultStockImpExpRpt').innerHTML = '<s:text name="ERR.ISN.028"/>';
            $('reportStockImpExpForm.shopCode').select();
            $('reportStockImpExpForm.shopCode').focus();
            return false;
        }
        var fromDate=dojo.widget.byId("fromDate");
        //alert(fromDate.domNode.childNodes[1].value );
        if(fromDate.domNode.childNodes[1].value == null || fromDate.domNode.childNodes[1].value == "" ){
            $( 'resultStockImpExpRpt' ).innerHTML='<s:text name="ERR.UTY.028"/>';
            fromDate.domNode.childNodes[1].focus();
            return false;
        }
                var toDate=dojo.widget.byId("toDate");
        //alert(toDate.domNode.childNodes[1].value );
        if(toDate.domNode.childNodes[1].value == null || toDate.domNode.childNodes[1].value == "" ){

            $( 'resultStockImpExpRpt' ).innerHTML='<s:text name="ERR.UTY.029"/>';
            toDate.domNode.childNodes[1].focus();
            return false;
        }
        return true;
    }
        
</script>
