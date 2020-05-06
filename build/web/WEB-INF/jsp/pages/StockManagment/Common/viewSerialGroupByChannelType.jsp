<%-- 
    Document   : viewSerialGroupByChannelType
    Created on : Sep 21, 2010, 8:19:48 AM
    Author     : Doan Thanh 8
    Desc       : xem chi tiet danh sach serial phan nhom theo loai kenh
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
            request.setAttribute("message", request.getSession().getAttribute("message"));
            request.getSession().removeAttribute("message");
            request.setAttribute("currentDiscountDetailId", request.getSession().getAttribute("currentDiscountDetailId"));
            request.setAttribute("listDiscountDetails", request.getSession().getAttribute("listDiscountDetails"));
//request.setAttribute("lstSerial", request.getSession().getAttribute("lstSerial"));
%>


<tags:imPanel title="title.viewSerialGroupByChannelType.page">

    <s:form action="ViewStockDetailAction" id="serialGoods" theme="simple">
<s:token/>

        <sx:div id="detailSerial">
            <input type="hidden"  name="pageId" id="pageId"/>
            <s:hidden name="serialGoods.stockModelId" id="stockModelId"/>
            <s:hidden name="serialGoods.stockModelName" id="stockModelName"/>
            <s:hidden name="serialGoods.stockTypeId" id="stockTypeId"/>
            <s:hidden name="serialGoods.ownerId" id="ownerId"/>
            <s:hidden name="serialGoods.ownerType" id="ownerType"/>
            <s:hidden name="serialGoods.ownerName" id="ownerName"/>
            <s:hidden name="serialGoods.stateId" id="stateId"/>
            <s:hidden name="serialGoods.actionId" id="actionId"/>

            <!-- phan hien thi danh sach cac dai serial-->
            <fieldset class="imFieldset">
                <legend>${fn:escapeXml(imDef:imGetText('MSG.STK.033'))}</legend>
                <%--<div>
                    
                <s:if test="#request.listChannelType != null && #request.listChannelType.size() > 0">
                    <table width="100%" id="OfferSubTree" class="dataTable">
                        <thead>
                            <tr>
                                <th style="width:35px; ">${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}</th> <!-- STT -->
                                <th>${fn:escapeXml(imDef:imGetText('MSG.GOD.011'))}</th> <!-- Loai kenh -->
                                <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.049'))}</th> <!-- Tu serial -->
                                <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.050'))}</th> <!-- Den serial -->
                                <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.051'))}</th> <!-- So luong -->
                            </tr>
                        </thead>
                        <tbody>
                            <!-- doi voi cac dai serial chua duoc gan kenh -->
                            <tr>
                                <s:set id="topUpNodeParameter" value="%{'-888, ' + #request.contextPath}"/>
                                <td style="text-align:center; ">
                                    1
                                </td>
                                <td class="cursorHand" onclick="topUpNode('-888', '${contextPath}')">
                                    <s:set id="expanImgId" value="%{'expan_' + '-888'}"/>
                                    <img id="<s:property escapeJavaScript="true"  value='#attr.expanImgId'/>" src="${contextPath}/share/img/treeview/minusbottom.gif" alt="">
                                    <s:set id="folderImgId" value="%{'folder_' + '-888'}"/>
                                    <img id="<s:property escapeJavaScript="true"  value='#attr.folderImgId'/>" src="${contextPath}/share/img/treeview/folderopen.gif"  alt="">
                                    ${fn:escapeXml(imDef:imGetText('MSG.STK.051'))}
                                </td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <s:if test="#request.lstSerial != null && #request.lstSerial.size() > 0">
                                <s:iterator id="stockTransSerial" value="#request.lstSerial" status="stockTransSerialStatus">
                                    <s:if test="#attr.stockTransSerial.channelTypeId == null">
                                        <tr id="-888">
                                            <td></td>
                                            <td>
                                                <img src="${contextPath}/share/img/treeview/line.gif" alt="">
                                                <img src="${contextPath}/share/img/treeview/joinbottom.gif"  alt="">
                                                <img src="${contextPath}/share/img/treeview/page.gif"  alt="">
                                                ${fn:escapeXml(imDef:imGetText('MSG.STK.052'))}
                                            </td>
                                            <td>
                                                <s:property escapeJavaScript="true"  value="#attr.stockTransSerial.fromSerial"/>
                                            </td>
                                            <td>
                                                <s:property escapeJavaScript="true"  value="#attr.stockTransSerial.toSerial"/>
                                            </td>
                                            <td style="text-align:right; ">
                                                <s:text name="format.money">
                                                    <s:param name="value" value="#attr.stockTransSerial.quantity"/>
                                                </s:text>
                                            </td>
                                        </tr>
                                    </s:if>
                                </s:iterator>
                            </s:if>

                        <script>
                            topUpNode('-888', '${contextPath}')
                        </script>

                        <!-- doi voi cac dai serial da duoc gan kenh -->
                        <s:iterator id="channelType" value="#request.listChannelType" status="channelTypeStatus">
                            <tr>
                                <s:set id="topUpNodeParameter" value="%{#attr.channelType.channelTypeId + ', ' + #request.contextPath}"/>
                                <td style="text-align:center; ">
                                    <s:property escapeJavaScript="true"  value="%{#channelTypeStatus.index + 2}" />
                                </td>
                                <td class="cursorHand" onclick="topUpNode('<s:property escapeJavaScript="true"  value='#attr.channelType.channelTypeId'/>', '${contextPath}')">
                                    <s:set id="expanImgId" value="%{'expan_' + #attr.channelType.channelTypeId}"/>
                                    <img id="<s:property escapeJavaScript="true"  value='#attr.expanImgId'/>" src="${contextPath}/share/img/treeview/minusbottom.gif" alt="">
                                    <s:set id="folderImgId" value="%{'folder_' + #attr.channelType.channelTypeId}"/>
                                    <img id="<s:property escapeJavaScript="true"  value='#attr.folderImgId'/>" src="${contextPath}/share/img/treeview/folderopen.gif"  alt="">
                                    <s:property escapeJavaScript="true"  value="#attr.channelType.name"/>
                                </td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <s:if test="#request.lstSerial != null && #request.lstSerial.size() > 0">
                                <s:iterator id="stockTransSerial" value="#request.lstSerial" status="stockTransSerialStatus">
                                    <s:if test="#attr.stockTransSerial.channelTypeId == #attr.channelType.channelTypeId">
                                        <tr id="<s:property escapeJavaScript="true"  value='#attr.channelType.channelTypeId'/>">
                                            <td></td>
                                            <td>
                                                <img src="${contextPath}/share/img/treeview/line.gif" alt="">
                                                <img src="${contextPath}/share/img/treeview/joinbottom.gif"  alt="">
                                                <img src="${contextPath}/share/img/treeview/page.gif"  alt="">
                                                ${fn:escapeXml(imDef:imGetText('MSG.STK.052'))}
                                            </td>
                                            <td>
                                                <s:property escapeJavaScript="true"  value="#attr.stockTransSerial.fromSerial"/>
                                            </td>
                                            <td>
                                                <s:property escapeJavaScript="true"  value="#attr.stockTransSerial.toSerial"/>
                                            </td>
                                            <td style="text-align:right; ">
                                                <s:text name="format.money">
                                                    <s:param name="value" value="#attr.stockTransSerial.quantity"/>
                                                </s:text>
                                            </td>
                                        </tr>
                                    </s:if>
                                </s:iterator>
                            </s:if>

                            <!-- doan javascript dong tung hang cua table (giao dien) -->
                            <script>
                                    topUpNode('<s:property escapeJavaScript="true"  value='#attr.channelType.channelTypeId'/>', '${contextPath}')
                            </script>

                        </s:iterator>
                        </tbody>
                    </table>
                </s:if>
                <s:else>
                    <table width="100%" id="OfferSubTree" class="dataTable">
                        <thead>
                            <tr>
                                <th style="width:35px; ">${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}</th> <!-- STT -->
                                <th style="width:35px; ">${fn:escapeXml(imDef:imGetText('MSG.GOD.011'))}</th> <!-- Loai kenh -->
                                <th style="width:35px; ">${fn:escapeXml(imDef:imGetText('MSG.SAE.049'))}</th> <!-- Tu serial -->
                                <th style="width:35px; ">${fn:escapeXml(imDef:imGetText('MSG.SAE.050'))}</th> <!-- Den serial -->
                                <th style="width:35px; ">${fn:escapeXml(imDef:imGetText('MSG.SAE.051'))}</th> <!-- So luong -->
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td colspan="5"></td>
                            </tr>
                        </tbody>
                    </table>
                </s:else>
                
                </div>--%>

                <display:table id="tblLstSerial" name="lstSerial"
                               class="dataTable" cellpadding="1" cellspacing="1"
                               targets="popupBody" pagesize="100"
                               requestURI="${contextPath}/ViewStockDetailAction!viewDetailSerialGroupByChannelType.do?ownerType=${fn:escapeXml(serialGoods.ownerType)}&ownerId=${fn:escapeXml(serialGoods.ownerId)}&stockModelId=${fn:escapeXml(serialGoods.stockModelId)}&stateId=${fn:escapeXml(serialGoods.stateId)}" >
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                    ${fn:escapeXml(tblLstSerial_rowNum)}
                    </display:column>
                    <display:column escapeXml="true" property="fromSerial" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.049'))}" sortable="false"  headerClass="tct"/>
                    <display:column escapeXml="true" property="toSerial" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.050'))}" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true" property="quantity" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.051'))}" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true" property="channelTypeName" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.011'))}" sortable="false" headerClass="tct"/>
                </display:table>

            </fieldset>

                <%--
            <div align="center" style="padding-top:3px; padding-bottom: 3px;">
                <input type="button" onclick="exportSerial();"  value="${fn:escapeXml(imDef:imGetText('MSG.SAE.055'))}"/>
                <br/>
                <s:url action="ViewStockDetailAction" method="viewDetailSerial" id="viewDetailUrl" escapeAmp="false">
                    <s:param name="ownerType" value="%{serialGoods.ownerType}"/>
                    <s:param name="ownerId" value="%{serialGoods.ownerId}"/>
                    <s:param name="stockModelId" value="%{serialGoods.stockModelId}"/>
                    <s:param name="stateId" value="%{serialGoods.stateId}"/>
                </s:url>

            </div>
            
            <div align="center" style="padding-top:3px; padding-bottom: 3px;">
                <s:if test="serialGoods.exportUrl!=null && serialGoods.exportUrl!=''">
                    <script>
                        window.open('${contextPath}<s:property escapeJavaScript="true"  value="serialGoods.exportUrl"/>','','toolbar=yes,scrollbars=yes');
                    </script>
                    <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="serialGoods.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>

                </s:if>
            </div>
                --%>

        </sx:div>
    </s:form>

</tags:imPanel>

<script>
    exportSerial= function(){
    <%--document.getElementById("serialGoods").action="ViewStockDetailAction!exportSerial.do?exportViewDetail=true";
    document.getElementById("serialGoods").submit();--%>
            setAction("serialGoods","ViewStockDetailAction!exportSerial.do?exportViewDetail=true");
        }
</script>
