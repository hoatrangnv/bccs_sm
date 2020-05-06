<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ProcessStockGoods
    Created on : Feb 24, 2010, 9:36:16 AM
    Author     : TheTM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@ page import="com.viettel.im.common.util.ResourceBundleUtils"%>
<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%
            request.setAttribute("contextPath", request.getContextPath());
//request.setAttribute("lstStockGoods", request.getSession().getAttribute("lstStockGoods"));
%>

<s:form action="ViewStockDetailAction!viewStockDetailProcess.do"  theme="simple" method="POST" id="goodsForm">
<s:token/>

    <sx:div id = "processStockGood">
<!--MSG.store.infor-->
        <tags:imPanel title="MSG.store.infor">
            <!--s:hidden name="goodsForm.ownerId"/-->
            <table class="inputTbl4Col" style="width:100%" border="0" cellpadding="0" cellspacing="4">
                <tr>
                    <td class="label">
                        <tags:label key="MSG.store.code" required="true"/>
                    </td>
                    <td class="text" colspan="2">
                        
                        <tags:imSearch codeVariable="goodsForm.shopCode" nameVariable="goodsForm.shopName"
                                       codeLabel="MSG.store.code" nameLabel="MSG.storeName"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       elementNeedClearContent="goodsForm.stafffCode;goodsForm.staffName"
                                       getNameMethod="getShopName" 
                                       roleList="SelectShopViewStock"/>
                    </td>
                    <td class="label">&nbsp;</td>

                </tr>
                <tr>
                    <td class="label">
                        <tags:label key="MSG.stock.staff.code"/>
                    </td>
                    <td class="text" colspan="2">

                        <tags:imSearch codeVariable="goodsForm.staffCode" nameVariable="goodsForm.staffName"
                                       codeLabel="MSG.stock.staff.code" nameLabel="MSG.staffName"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListStaff"
                                       otherParam="goodsForm.shopCode"
                                       getNameMethod="getStaffName"/>

                    </td>
                    <td class="label">&nbsp;</td>

                </tr>


                <tr>

                    <td class="label">
                        <tags:label key="MSG.stockModelId"/>
                    </td>
                    <td class="text" colspan="2">                        
                        <tags:imSearch codeVariable="goodsForm.stockModelCode" nameVariable="goodsForm.stockModelName"
                                       codeLabel="MSG.stockModelId" nameLabel="MSG.stockModelName"
                                       searchClassName="com.viettel.im.database.DAO.StockCommonDAO"
                                       searchMethodName="getListStockModelProcessGoods"
                                       getNameMethod="getListStockModelProcessGoods"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">
                        <tags:label key="MSG.generates.GOD.status"/>
                    </td>
                    <td class="text">
                        
                        <tags:imSelect name="goodsForm.stateId" id="status"
                                  cssClass="txtInput" disabled="false"
                                  list="1:MSG.GOD.242,3:MSG.GOD.243"
                                  headerKey="" headerValue="--Chọn trạng thái--"/>
                    </td>

                    <td class="label">
                        
                            <tags:submit confirm="false" formId="goodsForm" id="btnSearch" showLoadingText="true" value="MSG.viewStock"
                                         targets="bodyContent" preAction="ViewStockDetailAction!viewStockDetailProcess.do" validateFunction="validateForm()"/>
                           
                     
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <tags:displayResult property="returnMsg" id="returnMsgClient" type="key" />
                    </td
                </tr>
                <tr>
                    <td  colspan="4" align="center">
                        <sx:div id ="resultDownload">
                            <jsp:include page="downloadDetailSerial.jsp"/>
                        </sx:div>
                    </td>
                </tr>
            </table>
        </tags:imPanel>


        <s:if test="#request.lstStockGoods != null && #request.lstStockGoods.size() > 0">
            <table width="100%"  id="OfferSubTree" cellpadding="1" cellspacing="1" class="dataTable">
                <thead>
                    <tr>
                        <th>${fn:escapeXml(imDef:imGetText('MSG.GOD.175'))}</th>
                        <th>${fn:escapeXml(imDef:imGetText('MSG.GOD.013'))}</th>
                        <th>${fn:escapeXml(imDef:imGetText('MSG.GOD.247'))}</th>
                        <th>${fn:escapeXml(imDef:imGetText('MSG.GOD.246'))}</th>
                        <th>${fn:escapeXml(imDef:imGetText('MSG.GOD.245'))}</th>
                        <th>${fn:escapeXml(imDef:imGetText('MSG.GOD.244'))}</th>
                        <!-- chi xem kho cua hang moi hien thi so luong phai boc tham va khong phai boc tham-->
                        
                        <th>${fn:escapeXml(imDef:imGetText('MSG.GOD.248'))}</th>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator id="stockType" value="#request.lstStockGoods">
                        <tr class="cursorHand">
                            <s:set id="stockTypeId" value="%{#attr.stockType.stockTypeId}"/>
                            <td onclick="topUpNode('<s:property escapeJavaScript="true"  value='#attr.stockTypeId'/>', '${contextPath}')">
                                <s:set id="expanImgId" value="%{'expan_' + #attr.stockTypeId}"/>
                                <img id="<s:property escapeJavaScript="true"  value='#attr.expanImgId'/>" src="${contextPath}/share/img/treeview/minusbottom.gif" alt="">
                                <s:set id="folderImgId" value="%{'folder_' + #attr.stockTypeId}"/>
                                <img id="<s:property escapeJavaScript="true"  value='#attr.folderImgId'/>" src="${contextPath}/share/img/treeview/folderopen.gif"  alt="">
                                <s:property escapeJavaScript="true"  value="#attr.stockType.name"/>
                            </td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <!-- chi xem kho cua hang moi hien thi so luong phai boc tham va khong phai boc tham-->
                            
                            <td>
                                &nbsp;
                                <script>
                                    topUpNode('<s:property escapeJavaScript="true"  value='#attr.stockTypeId'/>', '${contextPath}')
                                </script>
                            </td>
                        </tr>
                        <s:if test="#attr.stockType.listStockDetail != null && #attr.stockType.listStockDetail.size() > 0">
                            <s:iterator id="stockTotalFull" value="#attr.stockType.listStockDetail">
                                <tr id="<s:property escapeJavaScript="true"  value='#attr.stockType.stockTypeId'/>">
                                    <td>
                                        <img src="${contextPath}/share/img/treeview/line.gif" alt="">
                                        <img src="${contextPath}/share/img/treeview/joinbottom.gif"  alt="">
                                        <img src="${contextPath}/share/img/treeview/page.gif"  alt="">
                                        <s:property escapeJavaScript="true"  value="#attr.stockTotalFull.stockModelCode"/> - <s:property escapeJavaScript="true"  value="#attr.stockTotalFull.name"/>
                                    </td>
                                    <td>
                                        <s:if test="#attr.stockTotalFull.id.stateId==1">Hàng mới</s:if>
                                        <s:if test="#attr.stockTotalFull.id.stateId==2">Hàng cũ</s:if>
                                        <s:if test="#attr.stockTotalFull.id.stateId==3">Hàng hỏng</s:if>
                                    </td>
                                    <td>
                                        <s:property escapeJavaScript="true"  value="#attr.stockTotalFull.unit"/>
                                    </td>
                                    <td align="center">
                                        <s:property escapeJavaScript="true"  value="%{#attr.stockTotalFull.quantity - #attr.stockTotalFull.quantityIssue - #attr.stockTotalFull.quantityDial}"/>
                                    </td>
                                    <td align="center">
                                        <s:property escapeJavaScript="true"  value="%{#attr.stockTotalFull.quantityIssue +#attr.stockTotalFull.quantityDial}"/>
                                    </td>
                                    <td align="center">
                                        <s:property escapeJavaScript="true"  value="#attr.stockTotalFull.quantity"/>
                                    </td>

                                    <!-- chi xem kho cua hang moi hien thi so luong phai boc tham va khong phai boc tham-->
                                    
                                    <td align="center">
                                        <s:if test="#attr.stockTotalFull.checkSerial !=null && #attr.stockTotalFull.checkSerial==1">
                                            <%String maxView = ResourceBundleUtils.getResource("MAX_GOOD_VIEW_DETAIL");%>
                                            <s:set id="viewSerialParameter" value="%{#stockTotalFull.id.ownerType +',' + #stockTotalFull.id.ownerId + ','
                                                   + #stockTotalFull.id.stockModelId + ','
                                                   + #attr.stockTotalFull.id.stateId +','
                                                   + #attr.stockTotalFull.quantity +','
                                                   + #attr.stockTotalFull.quantityIssue}"/>
                                            <a onclick="viewSerial(<s:property escapeJavaScript="true"  value="#attr.viewSerialParameter"/>,<%=StringEscapeUtils.escapeHtml(maxView)%>)">
                                                ${fn:escapeXml(imDef:imGetText('MSG.GOD.070'))}
                                            </a>
                                        </s:if>
                                    </td>
                                </tr>
                            </s:iterator>
                        </s:if>
                    </s:iterator>
                </tbody>
            </table>
        </s:if>
    </sx:div>
</s:form>

<s:if test="#request.processMode == 'closePopup'">

    <script language="javascript">
        window.close();
    </script>

</s:if>

<script>
    viewSerial = function (ownerType, ownerId, stockModelId, stateId,quantity,quantityIssue,maxView) {
        $('returnMsgClient').innerHTML=""; 
        if(quantity*1>maxView*1){
            $('returnMsgClient').innerHTML='<s:text name="stock.viewDetailSerial.maxResult"/>';
            return false;
        }
        openDialog("${contextPath}/ViewStockDetailAction!viewDetailSerialProcess.do?ownerType=" + ownerType +
            "&ownerId=" + ownerId + "&stockModelId=" + stockModelId + "&stateId=" +
            stateId +"&total="+quantity+"&totalReq="+quantityIssue, 800, 700);
    }

    processStockGoods = function(){
        gotoAction("processStockGood","ViewStockDetailAction!prepareProcessStockGoods.do","goodsForm");
    }
    validateForm = function (){
        if($('goodsForm.shopCode') ==null || $('goodsForm.shopCode').value== ''){
            $('returnMsgClient').innerHTML= '<s:text name="ERR.STK.004"/>';
            //$('returnMsgClient').innerHTML= 'Chưa nhập mã kho'
        }
        return true;
    }



</script>
