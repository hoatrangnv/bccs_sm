<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ViewStockStaffDetail
    Created on : Feb 23, 2010, 2:31:31 PM
    Author     : NamNX
--%>

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
            request.setAttribute("maxView", ResourceBundleUtils.getResource("MAX_GOOD_VIEW_DETAIL"));
//request.setAttribute("lstStockGoods", request.getSession().getAttribute("lstStockGoods"));
%>


<tags:imPanel title="title.viewStockStaffDetail.page">
    <s:form action="ViewStockDetailAction!viewStockStaffDetail.do"  theme="simple" method="POST" id="goodsForm">
        <s:token/>

        <div class="divHasBorder">
            <!--s:hidden name="goodsForm.ownerId"/-->
            <s:hidden name="goodsForm.ownerType" id="ownerType"/>
            <s:hidden name="goodsForm.viewType"/>
            <table class="inputTbl6Col">
                <tr>
                    <td style="width:10%;"><tags:label key="MSG.store.code"/></td>
                    <td class="oddColumn" style="width:23%;">
                        <s:hidden theme="simple" name="goodsForm.ownerId" id="goodsForm.ownerId"/>
                        <tags:imSearch codeVariable="goodsForm.shopCode" nameVariable="goodsForm.shopName"
                                       codeLabel="MSG.store.code" nameLabel="MSG.storeName"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       elementNeedClearContent="goodsForm.ownerCode;goodsForm.ownerName"
                                       getNameMethod="getShopName"/>
                    </td>                
                    <td style="width:10%;"><tags:label key="MSG.stock.staff.code"/></td>
                    <td class="oddColumn" style="width:23%;">
                        <%--tags:imSearch codeVariable="goodsForm.ownerCode" nameVariable="goodsForm.ownerName"
                                       codeLabel="MSG.stock.staff.code" nameLabel="MSG.staff.name"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListCTVDB"
                                       otherParam="goodsForm.shopCode"
                                       getNameMethod="getCTVDBName"/--%>
                        <tags:imSearch codeVariable="goodsForm.ownerCode" nameVariable="goodsForm.ownerName"
                                       codeLabel="MSG.stock.staff.code" nameLabel="MSG.staff.name"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListStaff"
                                       otherParam="goodsForm.shopCode"
                                       getNameMethod="getStaffName"/>

                    </td>
                    <td style="width:10%;"><tags:label key="MSG.generates.goods"/></td>
                    <td>
                        <s:hidden name="goodsForm.stockModelId" id="goodForm.stockModelId"/>
                        <tags:imSearch codeVariable="goodsForm.stockModelCode" nameVariable="goodsForm.stockModelName"
                                       codeLabel="MSG.stockModelId" nameLabel="MSG.stockModelName"
                                       searchClassName="com.viettel.im.database.DAO.StockCommonDAO"
                                       searchMethodName="getListStockModel" otherParam="ownerType;goodsForm.ownerId"
                                       getNameMethod="getListStockModel"/>
                    </td>
                </tr>
            </table>

            <!-- phan nut tac vu -->
            <div align="center" style="padding-top:3px; padding-bottom: 3px;">
                <s:if test="goodsForm.viewType !=null && goodsForm.viewType=='normal'">
                    <tags:submit confirm="false"
                                 formId="goodsForm"
                                 id="btnSearch"
                                 showLoadingText="true"
                                 value="MSG.viewStock"
                                 targets="bodyContent"
                                 preAction="ViewStockDetailAction!viewStockDetail.do"
                                 cssStyle="width:85px;"/>
                    <tags:submit confirm="false"
                                 formId="goodsForm"
                                 showLoadingText="true"
                                 value="MSG.RET.079"
                                 targets="resultDownload"
                                 preAction="ViewStockDetailAction!exportStockDetail.do"
                                 cssStyle="width:85px;"/>
                </s:if>
                <s:else>
                    <input type ="submit" value="<s:text name ="MSG.viewStock"/>" style="width:120px;"/>
<!--                    <input type ="submit" value="$_{imDef_:imGetText('MSG.GOD.171')}"/>-->
                </s:else>
            </div>

            <div align="center">
                <tags:displayResult property="returnMsg" id="returnMsgClient" type="key" />
            </div>

            <div align="center">
                <sx:div id ="resultDownload">
                    <jsp:include page="downloadDetailSerial.jsp"/>
                </sx:div>
            </div>
        </div>

        <fieldset class="imFieldset">
            <legend>${fn:escapeXml(imDef:imGetText('MSG.list.goods'))}</legend>

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
                            <s:if test="goodsForm.ownerType==1">
                                <th>${fn:escapeXml(imDef:imGetText('MSG.GOD.249'))} </th>
                                <th>${fn:escapeXml(imDef:imGetText('MSG.GOD.250'))} </th>
                            </s:if>
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
                                <s:if test="goodsForm.ownerType==1">
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                </s:if>
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
                                            <s:if test="#attr.stockTotalFull.id.stateId==1"><tags:label key="MSG.GOD.169"/></s:if>
                                            <s:if test="#attr.stockTotalFull.id.stateId==3"><tags:label key="MSG.GOD.170"/></s:if>
                                        </td>
                                        <td>
                                            <s:property escapeJavaScript="true"  value="#attr.stockTotalFull.unit"/>
                                        </td>
                                        <td align="right">
                                            <s:text name="format.money">
                                                <s:param name="value" value="%{#attr.stockTotalFull.quantity - #attr.stockTotalFull.quantityIssue - #attr.stockTotalFull.quantityDial}"/>
                                            </s:text>
                                            <%--s:property value="%{#attr.stockTotalFull.quantity - #attr.stockTotalFull.quantityIssue - #attr.stockTotalFull.quantityDial}"/--%>
                                        </td>
                                        <td align="right">
                                            <s:text name="format.money">
                                                <s:param name="value" value="%{#attr.stockTotalFull.quantityIssue +#attr.stockTotalFull.quantityDial}"/>
                                            </s:text>
                                            <%--s:property value="%{#attr.stockTotalFull.quantityIssue +#attr.stockTotalFull.quantityDial}"/--%>
                                        </td>
                                        <td align="right">
                                            <s:text name="format.money">
                                                <s:param name="value" value="#attr.stockTotalFull.quantity"/>
                                            </s:text>
                                            <%--s:property value="#attr.stockTotalFull.quantity"/--%>
                                        </td>

                                        <!-- chi xem kho cua hang moi hien thi so luong phai boc tham va khong phai boc tham-->
                                        <s:if test="goodsForm.ownerType==1">
                                            <td align="right">
                                                <s:text name="format.money">
                                                    <s:param name="value" value="#attr.stockTotalFull.quantityIssue"/>
                                                </s:text>
                                                <%--s:property value="#attr.stockTotalFull.quantityIssue"/--%>
                                            </td>
                                            <td align="right">
                                                <s:text name="format.money">
                                                    <s:param name="value" value="#attr.stockTotalFull.quantityDial"/>
                                                </s:text>
                                                <%--s:property value="#attr.stockTotalFull.quantityDial"/--%>
                                            </td>
                                        </s:if>
                                        <td align="center">
                                            <s:if test="#attr.stockTotalFull.checkSerial !=null && #attr.stockTotalFull.checkSerial==1">
                                                <s:set id="viewSerialParameter" value="%{goodsForm.ownerType +',' + goodsForm.ownerId + ','
                                                       + #stockTotalFull.id.stockModelId + ','
                                                       + #attr.stockTotalFull.id.stateId +','
                                                       + #attr.stockTotalFull.quantity }"/>
                                                <a class="cursorHand" onclick="viewSerial(<s:property escapeJavaScript="true"  value="#attr.viewSerialParameter"/>,'${fn:escapeXml(requestScope.maxView)}')">
                                                    ${fn:escapeXml(imDef:imGetText('MSG.GOD.070'))}
                                                </a>
                                                <!-- tamdt1, 27/09/2010, bo sung them phan xem chi tiet serial theo kenh -->
                                                &nbsp;|&nbsp;
                                                <a class="cursorHand" onclick="viewDetailSerialGroupByChannelType(<s:property escapeJavaScript="true"  value="#attr.viewSerialParameter"/>,'${fn:escapeXml(requestScope.maxView)}')">
                                                    ${fn:escapeXml(imDef:imGetText('MSG.detailGroupByChannelType'))}
                                                </a>
                                                <!-- tamdt1, 27/09/2010, bo sung them phan xem chi tiet serial theo kenh -->
                                            </s:if>
                                        </td>
                                    </tr>
                                </s:iterator>
                            </s:if>
                        </s:iterator>
                    </tbody>
                </table>
            </s:if>


        </fieldset>

    </s:form>

</tags:imPanel>

<script>
    viewSerial = function (ownerType, ownerId, stockModelId, stateId,quantity,maxView) {
        $('returnMsgClient').innerHTML="";
        if(quantity*1>maxView*1){
            $('returnMsgClient').innerHTML='<s:text name="stock.viewDetailSerial.maxResult"/>';
            return false;
        }
        openDialog("${contextPath}/ViewStockDetailAction!viewDetailSerial.do?ownerType=" + ownerType + "&ownerId=" + ownerId + "&stockModelId=" + stockModelId + "&stateId=" + stateId, 800, 700);
    }

    viewDetailSerialGroupByChannelType = function (ownerType, ownerId, stockModelId, stateId,quantity,maxView) {
        $('returnMsgClient').innerHTML="";
        if(quantity*1>maxView*1){
            $('returnMsgClient').innerHTML='<s:text name="stock.viewDetailSerial.maxResult"/>';
            return false;
        }
        openDialog("${contextPath}/ViewStockDetailAction!viewDetailSerialGroupByChannelType.do?ownerType=" + ownerType + "&ownerId=" + ownerId + "&stockModelId=" + stockModelId + "&stateId=" + stateId, 800, 700);
    }


</script>
