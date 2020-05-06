<%-- 
    Document   : ExportNoteConfirmToMasterAgent
    Created on : Sep 16, 2016, 4:19:38 PM
    Author     : mov_itbl_dinhdc
--%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            String pageId = request.getParameter("pageId");

            if (request.getSession().getAttribute("lstGoods" + pageId) != null) {
                request.setAttribute("lstGoods", request.getSession().getAttribute("lstGoods" + pageId));
            }

            if (request.getAttribute("listChannelType") == null) {
                request.setAttribute("listChannelType", request.getSession().getAttribute("listChannelType"));
            }

%>
<s:if test="exportStockForm.cmdExportCode != null && exportStockForm.cmdExportCode !='' ">
    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
    </div>
    <tags:displayResultList property="lstError" type="key"/>
    <s:hidden name="exportStockForm.canPrint"/>
    <s:token/>
    <table style="width:100%">
        <tr>
            <td style="vertical-align:top; width:50%">
                <tags:imPanel title="MSG.info.export.note">
                    <div style="height:210px; overflow:auto;">
                        <s:hidden name="exportStockForm.status" id="exportStockForm.status"/>
                        <s:hidden name="exportStockForm.actionId" id="exportStockForm.actionId"/>
                        <table class="inputTbl4Col">
                            <tr>
                                <td class="label">
                                    <tags:label key="MSG.exportCmdId"/>
                                </td>
                                <td class="text">
                                    <s:textfield readonly="true" theme="simple" maxlength="20"  name="exportStockForm.cmdExportCode" id="exportStockForm.cmdExportCode" cssClass="txtInputFull" />
                                </td>
                                <td class="label">
                                    <tags:label key="MSG.stock.export.channel"/>
                                </td>
                                <td  class="text">
                                    <s:textfield theme="simple" name="exportStockForm.sender" id="exportStockForm.sender" cssClass="txtInputFull" readonly="true"  />
                                    <s:hidden theme="simple" name="exportStockForm.senderId"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="label">
                                    <tags:label key="MSG.fromStore"/>
                                </td>
                                <td  class="text" colspan="3">
                                    <s:hidden theme="simple"  name="exportStockForm.shopExportId" id="exportStockForm.shopExportId"/>
                                    <table width="100%">
                                        <tr>
                                            <td width="30%">
                                                <s:textfield theme="simple" name="exportStockForm.shopExportCode" id="exportStockForm.shopExportCode" cssClass="txtInputFull" readonly="true"/>
                                            </td>
                                            <td width="70%">
                                                <s:textfield theme="simple" name="exportStockForm.shopExportName" id="exportStockForm.shopExportName" cssClass="txtInputFull" readonly="true"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td class="label">
                                    <tags:label key="MSG.staff.receive"/>
                                </td>
                                <td  class="text" colspan="3">
                                    <s:hidden name="exportStockForm.shopImportedId"/>
                                    <tags:imSearch codeVariable="exportStockForm.shopImportedCode" nameVariable="exportStockForm.shopImportedName"
                                                   codeLabel="MSG.store.receive.code" nameLabel="MSG.store.receive.name"
                                                   searchClassName="com.viettel.im.database.DAO.StockCommonDAO"
                                                   searchMethodName="getListStaffF9"
                                                   getNameMethod="getListStaffF9"
                                                   otherParam="exportStockForm.shopExportCode" readOnly="true"/>

                                </td>
                            </tr>

                            <tr>

                                <td class="label">
                                    <tags:label key="MSG.reasonExport"/>
                                </td>
                                <td  class="text">
                                    <s:textfield theme="simple" name="exportStockForm.reasonName" id="exportStockForm.reasonName" cssClass="txtInputFull" readonly="true"/>
                                    <s:hidden theme="simple" name="exportStockForm.reasonId"/>

                                </td>
                                <td class="label">
                                    <tags:label key="MSG.exportDate"/>
                                </td>
                                <td  class="text">
                                    <s:textfield theme="simple"  name="exportStockForm.dateExported" id="exportStockForm.dateExported" cssClass="txtInputFull" readonly="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td  class="label">
                                    <tags:label key="MSG.comment"/>
                                </td>
                                <td colspan="3"  class="text">
                                    <s:textfield theme="simple"  readonly="true" name="exportStockForm.note" maxlength="500" id="exportStockForm.note" cssClass="txtInputFull"/>
                                </td>
                            </tr>

                            <tr>
                                <td  class="label">
                                    <tags:label key="Channel type"/>
                                    <!--                    Channel type            -->
                                </td>
                                <td colspan="3"  class="text">
                                    <tags:imSelect theme="simple"
                                                   name="exportStockForm.channelTypeId"
                                                   id="exportStockForm.channelTypeId"
                                                   cssClass="txtInputFull"
                                                   disabled="true"
                                                   headerKey="" headerValue="--Channel type--"
                                                   list="listChannelType"
                                                   listKey="channelTypeId" listValue="name"/>
                                    <%--<s:hidden theme="simple"  name="exportStockForm.channelTypeId" id="exportStockForm.channelTypeId2"/>--%>
                                </td>
                            </tr>

                        </table>
                    </div>
                </tags:imPanel>
            </td>
            <td style="vertical-align:top; width:auto;">
                <tags:imPanel title="MSG.detail.goods">
                    <div style="height:210px; overflow:auto;">
                        <tags:displayResult id="returnMsgGoodsClient" property="returnMsg"/>
                        <display:table id="good" name="lstGoods" class="dataTable" requestURI="javascript: void(0)" cellpadding="1" cellspacing="1">
                            <display:column  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct">
                                <s:property escapeJavaScript="true"  value="%{#attr.good_rowNum}"/>
                            </display:column>
                            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.176'))}" property="stockTypeName"/>
                            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.175'))}" property="stockModelName" sortable="false" headerClass="tct"/>
                            <display:column title="Trạng thái" sortable="false" headerClass="tct">
                                <s:if test="#attr.good.stateId ==1"><tags:label key="MSG.GOD.169"/></s:if>
                                <s:if test="#attr.good.stateId ==3"><tags:label key="MSG.GOD.170"/></s:if>
                            </display:column>
                            <display:column escapeXml="true" property="unit" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.177'))}" sortable="false" headerClass="tct"/>
                            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.179'))}" property="quantity" format="{0}"  style="text-align:right" sortable="false" headerClass="tct"/>
                            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.181'))}" sortable="false" headerClass="tct">
                                <!--Chi nhung mat hang quan ly theo serial moi cho phep nhap chi tiet serial-->
                                <s:if test="#attr.good.checkSerial==1">
                                    <s:if test="#attr.good.lstSerial != null && #attr.good.lstSerial.size()>0">
                                        <div align="center">
                                            <s:url action="InputSerialAction!prepareInputSerial" id="URLView" encode="true" escapeAmp="false">
                                                <s:param name="totalReq" value="#attr.good.quantity"/>
                                                <s:param name="stockTypeId" value="#attr.good.stockTypeId"/>
                                                <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                                                <s:param name="stockModelName" value="#attr.good.stockModelName"/>
                                                <s:param name="ownerType" value="#attr.good.fromOwnerType"/>
                                                <s:param name="ownerId" value="#attr.good.fromOwnerId"/>
                                                <s:param name="stateId" value="#attr.good.stateId"/>
                                                <s:param name="isView" value="true"/>
                                                <s:param name="stockTransId" value="#attr.good.stockTransId"/>
                                                <s:param name="impChannelTypeId" value="#attr.good.channelTypeId"/>
                                                <s:param name="impOwnerId" value="#attr.good.toOwnerId"/>
                                                <s:param name="impOwnerType" value="#attr.good.toOwnerType"/>
                                            </s:url>
                                            <a href="javascript: void(0);" onclick="openDialog('<s:property escapeJavaScript="true"  value="#URLView"/>',800,700)">
                                                <tags:label key="MSG.GOD.070"/>
                                                <!--                                                Chi tiết-->
                                            </a>

                                        </div>
                                    </s:if>
                                    <s:else>
                                        <s:if test="#attr.good.quantity != null ">
                                            <s:if test="#request.notInputSerial==null || #request.notInputSerial !='true'">
                                                <div align="center">
                                                    <s:url action="InputSerialAction!prepareInputSerial" id="URL" encode="true" escapeAmp="false">
                                                        <s:param name="totalReq" value="#attr.good.quantity"/>
                                                        <s:param name="stockTypeId" value="#attr.good.stockTypeId"/>
                                                        <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                                                        <s:param name="stockModelName" value="#attr.good.stockModelName"/>
                                                        <s:param name="ownerType" value="#attr.good.fromOwnerType"/>
                                                        <s:param name="ownerId" value="#attr.good.fromOwnerId"/>
                                                        <s:param name="stateId" value="#attr.good.stateId"/>
                                                        <s:param name="checkDial" value="#attr.good.checkDial"/>
                                                        <s:param name="editable" value="goodsForm.editable"/>
                                                        <s:param name="impChannelTypeId" value="#attr.good.channelTypeId"/>
                                                        <s:param name="impOwnerId" value="#attr.good.toOwnerId"/>
                                                        <s:param name="impOwnerType" value="#attr.good.toOwnerType"/>
                                                    </s:url>

                                                    <a href="javascript:void(0)" onclick="openDialog('<s:property escapeJavaScript="true"  value="#URL"/>',800,700)">
                                                        <tags:label key="MSG.GOD.173"/>
                                                        <!--                                                        Nhập serial-->
                                                    </a>
                                                    <!--/s:else-->
                                                </div>
                                            </s:if>
                                        </s:if>
                                    </s:else>
                                </s:if>
                            </display:column>
                        </display:table>
                    </div>
                </tags:imPanel>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <s:if test="exportStockForm.canPrint !=null && exportStockForm.canPrint ==1">
                    <input type="button" value='<s:text name="MSG.GOD.200"/>' disabled/>
                    <tags:submit id="exp" formId="exportStockForm" showLoadingText="true" targets="searchArea"
                                 value="MSG.print.export.note"
                                 preAction="StockTransMasterAgentAction!printExpNote.do?type=expToMasterAgent"/>
                    <tags:submit id="exp" formId="exportStockForm" showLoadingText="true" targets="searchArea"
                                 value="MSG.print.export"
                                 preAction="StockTransMasterAgentAction!printExpNote.do?type=expToMasterAgent&expDetail=1"/>
                </s:if>
                <s:else>
                    <tags:submit id="exp" confirm="true" confirmText="MSG.GOD.202"
                                 formId="exportStockForm" showLoadingText="true" targets="searchArea"
                                 value="MSG.expStock"
                                 preAction="StockTransMasterAgentAction!expStock.do"/>
                    <input type="button" value='<s:text name="MSG.GOD.201"/>' disabled/>
                </s:else>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <s:if test="exportStockForm.exportUrl !=null && exportStockForm.exportUrl!=''">
                    <script>
                        window.open('${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
                    </script>
                    <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>
                </s:if>
            </td>
        </tr>
    </table>
</s:if>

<script>
  
    updateSerial = function (){
        gotoAction("searchArea", "StockTransMasterAgentAction!refreshListGoods.do","exportStockForm");
    }
 
</script>

