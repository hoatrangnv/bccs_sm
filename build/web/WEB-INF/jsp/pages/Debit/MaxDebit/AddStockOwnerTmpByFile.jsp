<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : AddStockOwnerTmpByFile
    Created on : Aug 8, 2011, 8:31:54 AM
    Author     : kdvt_phuoctv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.*"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%
    request.setAttribute("contextPath", request.getContextPath());
%>
<tags:imPanel title="label.singleStaff.maxDebit">
    <s:form action="stockOwnerTmpAction" theme="simple" method="post" id="stockOwnerTmpForm">
<s:token/>

        <fieldset class="imFieldset">
            <table class="inputTbl4Col">
                <tr>
                    <td colspan="3" align="center">
                        <%--Download template gán hạn mức cho nhân viên theo File.--%>
                        <a href="${contextPath}/share/pattern/importStockOwnerTmpByFile.xls">${fn:escapeXml(imDef:imGetText('link.download.template.assignDebitToStaff'))}</a>
                    </td>
                </tr>
                <%--<tr>
                    <td class="label" style="width:10%"><tags:label key="MSG.shop.code" required="true" /></td>
                    <td>
                        <tags:imSearch codeVariable="stockOwnerTmpForm.shopCode" nameVariable="stockOwnerTmpForm.shopName"
                                       codeLabel="MSG.shop.code" nameLabel="MSG.shop.name"
                                       searchClassName="com.viettel.im.database.DAO.StockOwnerTmpDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName" elementNeedClearContent="stockOwnerTmpForm.staffCode;stockOwnerTmpForm.staffName"
                                       roleList=""/>
                    </td>
                    <td class="label">&nbsp;</td>
                </tr>--%>
                <tr id="trImpByFile">
                    <td class="label"><tags:label key="MES.CHL.005" required="true" /></td>
                    <td class="text">
                        <!-- Edit by Hieptd-->
                        <tags:imFileUpload name="stockOwnerTmpForm.clientFileName"
                                           id="stockOwnerTmpForm.clientFileName"
                                           serverFileName="stockOwnerTmpForm.serverFileName"
                                           extension="xls"/>
                    </td>
                    <td class="text">
                        <tags:submit formId="stockOwnerTmpForm"
                                     showLoadingText="true"
                                     cssStyle="width:80px;"
                                     targets="bodyContent"
                                     value="MES.CHL.006"
                                     validateFunction="checkValidFile()"
                                     preAction="stockOwnerTmpAction!viewFile.do"/>
                    </td>
                </tr>
            </table>
        </fieldset>
        <br>
        <fieldset class="imFieldset">
            <legend>
                <s:text name="MES.CHL.083"/>
            </legend>
            <sx:div id="divFileContent" theme="simple">
                <jsp:include page="ListStockOwnerTmpInFile.jsp"/>
            </sx:div>
        </fieldset>
        <div align="center" style="padding-top:5px; padding-bottom:5px;">
            <tags:submit formId="stockOwnerTmpForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MSG.update"
                         confirm="true"
                         confirmText="MSG.GOD.017"
                         validateFunction="checkValidFile()"
                         preAction="stockOwnerTmpAction!updateStockOwnerTmpByFile.do"/>
        </div>
        <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="paramMsg" type="key" />
        <div>
            <s:if test="#request.exportPath != null">
                <script>
                    window.open('${contextPath}/${fn:escapeXml(exportPath)}','','toolbar=yes,scrollbars=yes');
                </script>
                <a href="${contextPath}/${fn:escapeXml(exportPath)}">
                    <tags:displayResult id="exportMessage" property="exportMessage" type="key"/>
                </a>
            </s:if>
        </div>
    </s:form>
</tags:imPanel>
<script type="text/javascript" language="javascript">
    checkValidFile = function(){
        var clientFileName = document.getElementById("stockOwnerTmpForm.clientFileName");
        if (trim(clientFileName.value).length ==0){
            $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.001')"/>';
            clientFileName.focus();
            return false;
        }
        return true;
    }
</script>
