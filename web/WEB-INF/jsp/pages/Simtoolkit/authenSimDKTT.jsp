<%-- 
    Document   : authenSimDKTT
    Created on : Mar 11, 2016, 8:27:57 AM
    Author     : mov_itbl_dinhdc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="menu.simtk.management">
    <s:form action="simtkDKTTAction" theme="simple"  enctype="multipart/form-data" method="post" id="simtkManagementForm">
    <s:token/>

            <div>
                <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key" />
            </div>
            <table class="inputTbl2Col">
                 <tr>
                    <td style="width:10%;"><tags:label key="SHOP.BRANCH" required="true" /></td>
                    <td class="oddColumn" style="width:30%;">
                        <tags:imSelect name="simtkManagementForm.shopBranchId"
                                       id="simtkManagementForm.shopBranchId"
                                       cssClass="txtInputFull"
                                       list="listShopBranch"
                                       listKey="shopId" listValue="shopCode"
                                       headerKey="${keyShopBranch}" headerValue="${valueShopBranch}"
                                       onchange="selectBranch()"
                                       />
                    </td>

                    <td style="width:10%;"><tags:label key="SHOP.CENTER"/></td>
                    <td class="oddColumn" style="width:30%;">
                        <tags:imSelect name="simtkManagementForm.shopCenterId"
                                       id="simtkManagementForm.shopCenterId"
                                       cssClass="txtInputFull"
                                       list="listShopCenter"
                                       listKey="shopId" listValue="shopCode"
                                       headerKey="${keyShopCenter}" headerValue="${valueShopCenter}"
                                       onchange="selectCenter()"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:10%;"><tags:label key="SHOP.SHOWROOM"/></td>
                    <td class="oddColumn" style="width:30%;">
                        <tags:imSelect name="simtkManagementForm.shopShowroomId"
                                       id="simtkManagementForm.shopShowroomId"
                                       cssClass="txtInputFull"
                                       list="listShopShowroom"
                                       listKey="shopId" listValue="shopCode"
                                       headerKey="" headerValue="SHOP.SHOWROOM.SELECT"/>
                    </td>

                    <td class="label" ><tags:label key="MES.CHL.005" required="true" /><font color="red">(select file .xls)</font></td>
                    <td class="text" >
                        <tags:imFileUpload name="simtkManagementForm.clientFileName" id="simtkManagementForm.clientFileName" serverFileName="simtkManagementForm.serverFileName" extension="xls"/>
                    </td>
                </tr>
                <tr>
                    
                    <td class="label"><tags:label key="MSG.SIK.018"/></td>
                    <td colspan="2" class="text">
                        <s:textfield id="simtkManagementForm.isdnSearch" name="simtkManagementForm.isdnSearch" theme="simple" maxlength="20"/>
                    </td>
                    
                    <td colspan="1" align="center">
                        <a href="${contextPath}/share/pattern/importAuthenSimtkByFile.xls">${fn:escapeXml(imDef:imGetText('MSG.TT.01'))}</a>
                    </td>
                </tr>
            </table>

            <div align="center" style="padding-bottom:4px; padding-top:4px;">

                <tags:submit formId="simtkManagementForm" showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.find"
                             preAction="simtkDKTTAction!searchSimDKTT.do"
                             validateFunction="checkValidFieldsSearch();"/>

                <tags:submit formId="simtkManagementForm" showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="Button.Authen.By.File"
                             preAction="simtkDKTTAction!authenSimtkByFile.do"
                             validateFunction="checkBeforeAuthenSimByFile();"/>

                <tags:submit formId="simtkManagementForm" showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.report.export"
                             preAction="simtkDKTTAction!exportReport.do"
                             validateFunction="checkValidFieldsSearch();"/>

           </div>
        <div>
            <s:if test="#request.reportAccountPath != null">
                <script>
                window.open('${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}','','toolbar=yes,scrollbars=yes');
                </script>
                <a href="${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}">
                    <tags:displayResult id="reportAccountMessage" property="reportAccountMessage" type="key"/>
                </a>
            </s:if>
        </div>        
        <sx:div id="searchResultArea" theme="simple">
            <jsp:include page="listSimtkDKTT.jsp"/>
        </sx:div>
    </s:form>
</tags:imPanel>        

<script language="javascript">
    
    checkValidFieldsSearch = function() {
           
            var shopBranchId = document.getElementById("simtkManagementForm.shopBranchId");
            if (shopBranchId.value == ''){
                <%--"Bạn chưa nhap ma chi nhanh"--%>
                $('message').innerHTML = '<s:property value="getText('err.input.shopcode')"/>';
                shopBranchId.focus();
                return false;
            }
            
            return true;
     }
     
     checkBeforeAuthenSimByFile = function() {
           
            //ten file ko duoc de trong
            var clientFileName = document.getElementById("simtkManagementForm.clientFileName");
            if (trim(clientFileName.value).length ==0){
                <%--$('resultViewChangeStaffInShopClient').innerHTML="Bạn chưa chọn file cần tạo"--%>
                $('message').innerHTML = '<s:property value="getText('ERR.GOD.067')"/>';
                //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn file dữ liệu";
                clientFileName.focus();
                return false;
            }
            
            return true;
     }
    
    selectBranch = function() {
        shopBranchId = $('simtkManagementForm.shopBranchId');
        var shopId = shopBranchId.value;
        if (shopBranchId.value != ''){
            gotoAction("bodyContent",'simtkDKTTAction!getShopCenter.do?shopId=' 
                    + shopId + "&" + token.getTokenParamString());
        }
    }
    
    selectCenter = function() {
        shopCenterId = $('simtkManagementForm.shopCenterId');
        var shopId = shopCenterId.value;
        if (shopCenterId.value != ''){
            gotoAction("bodyContent",'simtkDKTTAction!getShopShowroom.do?shopCenterId=' 
                    + shopId + "&" + token.getTokenParamString());
        }
    }
    
</script>

