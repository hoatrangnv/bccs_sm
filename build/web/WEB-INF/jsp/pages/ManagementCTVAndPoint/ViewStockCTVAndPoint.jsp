<%-- 
    Document   : ViewStockCTVAndPoint
    Created on : Jul 28, 2016, 11:50:12 AM
    Author     : mov_itbl_dinhdc
--%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page import="com.viettel.im.common.util.ResourceBundleUtils"%>
<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="title.ViewStockCTVAndPoint.page">
    <s:form action="ViewStockCTVAndPointAction"  theme="simple" method="POST" id="managementCTVAndPointForm">
        <s:token/>
        
        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td style="width:10%;"><tags:label key="MSG.store.code"/></td>
                    <td class="oddColumn" style="width:23%;">
                        <tags:imSearch codeVariable="managementCTVAndPointForm.shopCode" nameVariable="managementCTVAndPointForm.shopName"
                                       codeLabel="MSG.store.code" nameLabel="MSG.storeName"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"/>
                    </td>
                    <td  class="label"><tags:label key="MSG.STK.042" required="false" /></td>
                    <td class="text">
                        <tags:imSelect name="managementCTVAndPointForm.channelTypeId"
                                       id="managementCTVAndPointForm.channelTypeId"
                                       cssClass="txtInputFull"
                                       theme="simple"
                                       headerKey="" headerValue="MSG.RET.162"
                                       list="listChannelType"  listKey="channelTypeId" listValue="name"/>

                    </td>
                </tr>
                    
                <tr>
                    <td ><tags:label key="MES.CHL.064"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="managementCTVAndPointForm.staffManageCode" nameVariable="managementCTVAndPointForm.staffManageName"
                                       codeLabel="MES.CHL.064" nameLabel="MES.CHL.065"
                                       searchClassName="com.viettel.im.database.DAO.ViewStockCTVAndPointDAO"
                                       searchMethodName="getListStaff"
                                       getNameMethod="getStaffName"
                                       otherParam="managementCTVAndPointForm.shopCode"
                                       />
                    </td>
                    <td ><tags:label key="MSG.DET.008"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="managementCTVAndPointForm.ownerCode" nameVariable="managementCTVAndPointForm.ownerName"
                                       codeLabel="MSG.DET.008" nameLabel="MSG.DET.009"
                                       searchClassName="com.viettel.im.database.DAO.ViewStockCTVAndPointDAO"
                                       searchMethodName="getListOwner"
                                       getNameMethod="getOwnerName"
                                       otherParam="reportRevenueForm.shopCode;reportRevenueForm.staffManageCode"
                                       />
                    </td>
                </tr>
                
                <tr>
                    <td  class="label"><tags:label key="MSG.fromDate" required="true" /> </td>
                    <td  class="text">
                        <tags:dateChooser property="managementActionOfUserForm.fromDate" id="managementActionOfUserForm.fromDate"/>
                    </td>
                    <td  class="label"><tags:label key="MSG.generates.to_date" required="true" /> </td>
                    <td  class="text">
                        <tags:dateChooser property="managementActionOfUserForm.toDate" id="managementActionOfUserForm.toDate"/>
                    </td>
                </tr>
            </table>
            <!-- phan nut tac vu -->
            <div align="center" style="padding-top:3px; padding-bottom: 3px;">
                    <tags:submit confirm="false"
                                 formId="managementActionOfUserForm"
                                 id="btnSearch"
                                 showLoadingText="true"
                                 value="MSG.viewStock"
                                 targets="bodyContent"
                                 preAction="ViewStockCTVAndPointAction!viewStockCTVAndPoint.do"
                                 cssStyle="width:85px;"/>
            </div>
            <!-- phan thong bao -->
            <div align="center">
                <tags:displayResult property="returnMsg" id="returnMsgClient" type="key" />
            </div>
        </div>
        
       <fieldset class="imFieldset">
            <legend>${fn:escapeXml(imDef:imGetText('MSG.list.goods'))}</legend>    
       </fieldset>    
    </s:form>
</tags:imPanel>

<script>
    
</script>
