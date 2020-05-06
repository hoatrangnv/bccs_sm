<%-- 
    Document   : ImportCompetitorData
    Created on : May 31, 2012, 9:42:44 AM
    Author     : Thaiph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@page  import="com.viettel.im.database.BO.UserToken" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    UserToken userToken = (UserToken) request.getSession().getAttribute("userToken");
    String shopName = userToken.getShopName();
    Long shopId = userToken.getShopId();
    request.setAttribute("contextPath", request.getContextPath());
    request.setAttribute("shopName", shopName);

%>
<s:form action="importCompetitorDataAction" theme="simple" method="post" id="channelForm">
    <s:token/>

    <tags:imPanel title="reportCompetitorInfoBusiness">
        <div>
            <table class="inputTbl4Col">
                <tr>
                <td class="label" ><tags:label key="MSG.SAE.109" required="true" /></td>
                <td>
                    <tags:imSearch codeVariable="channelForm.compShopCode" nameVariable="channelForm.compShopName"
                                   codeLabel="MSG.RET.066" nameLabel="MSG.RET.067"
                                   searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                   searchMethodName="getListShop"
                                   getNameMethod="getShopName"/>
                </td>
                <td class="label" ><tags:label key="MSG.SAE.111" required="false" /></td>
                <td>
                    <tags:imSearch codeVariable="channelForm.compStaffCode" nameVariable="channelForm.compStaffName"
                                   codeLabel="MSG.store.receive.code" nameLabel="MSG.store.receive.name"
                                   searchClassName="com.viettel.im.database.DAO.StockCommonDAO"
                                   searchMethodName="getListStaffF9"
                                   getNameMethod="getListStaffF9"
                                   otherParam="channelForm.compShopCode"/>

                </td>
            </tr>
            <tr>
                
                <td  class="label"><tags:label key="MSG.fromDate" required="true" /></td>
                <td  class="text">
                    <tags:dateChooser property="channelForm.fromDate" id="fromDate"/>
                </td>
                <td  class="label"><tags:label key="MSG.generates.to_date" required="true" /></td>
                <td  class="text">
                    <tags:dateChooser property="channelForm.toDate" id="toDate"/>
                </td>
                </tr>
            </table>
        </div>

        <!-- phan nut tac vu -->
        <div align="center" style="padding-top:5px; padding-bottom:5px;">        
            <tags:submit formId="channelForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MSG.RET.079"
                         validateFunction="checkValidFile()"
                         preAction="importCompetitorDataAction!exportReportCompetitor.do"/>
        </div>

        <tags:displayResult id="resultViewChangeStaffInShopClient" property="resultViewChangeStaffInShopClient" type="key"/>
        <%--
        <div>
            <s:if test="#request.reportAccountPath != null">
                <script>
                    window.open('${fn:escapeXml(reportAccountPath)}','','toolbar=yes,scrollbars=yes');
                </script>
                <a href="${fn:escapeXml(reportAccountPath)}">
                    <tags:displayResult id="reportAccountMessage" property="reportAccountMessage" type="key"/>
                </a>
            </s:if>
        </div>
        --%>
        <div>
            <s:if test="#request.reportAccountPath != null">
                <script>
                    window.open('${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}', '', 'toolbar=yes,scrollbars=yes');</script>

                <a href="${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}">
                    <tags:displayResult id="reportAccountMessage" property="reportAccountMessage" type="key"/>
                </a>
            </s:if>
        </div>
    </tags:imPanel>

</s:form>
<script type="text/javascript" language="javascript">    
    checkValidFile = function(){
        var compShopCode= document.getElementById("channelForm.compShopCode");
        var compStaffCode= document.getElementById("channelForm.compStaffCode");
        
        if(compShopCode.value ==null || trim(compShopCode.value)==''){
            //chua chon shopcode
            $( 'resultViewChangeStaffInShopClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('error.not.select.unit.export.competitor')"/>';
            compShopCode.focus();
            return false;
        }
//        if(compStaffCode.value ==null || trim(compStaffCode.value)==''){
//            //chua chon staffcode
//            $( 'resultViewChangeStaffInShopClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.125')"/>';
//            compStaffCode.focus();
//            return false;
//        }
        
        var fromDate = dojo.widget.byId("fromDate");
        if(fromDate.domNode.childNodes[1].value.trim() == ""){
            $('resultViewChangeStaffInShopClient').innerHTML='<s:text name="ERR.RET.002"/>';
            $('fromDate').focus();
            return false;
        }
        var toDate= dojo.widget.byId("toDate");
        if(toDate.domNode.childNodes[1].value.trim() == ""){
            $('resultViewChangeStaffInShopClient').innerHTML='<s:text name="ERR.RET.002"/>';
            $('toDate').focus();
            return false;
        }
        
        if(!isCompareDate(fromDate.domNode.childNodes[1].value.trim(),toDate.domNode.childNodes[1].value.trim(),"VN")){
            $('resultViewChangeStaffInShopClient').innerHTML='<s:text name="ERR.RET.006"/>';
            $('fromDate').focus();
            return false;
        }
        
        return true;
    }
</script>
