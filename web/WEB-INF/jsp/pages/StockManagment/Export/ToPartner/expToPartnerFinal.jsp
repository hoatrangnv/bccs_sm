<%-- 
    Document   : expToPartnerFinal
    Created on : Sep 3, 2010, 3:37:19 AM
    Author     : Doan Thanh 8
    Desc       : nhap serial va xuat kho cho doi tac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="display" uri="VTdisplaytaglib" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
    String pageId = request.getParameter("pageId");
    request.setAttribute("listGoods", request.getSession().getAttribute("lstGoods" + pageId));
    request.setAttribute("listReason", request.getSession().getAttribute("listReason" + pageId));
%>

<sx:div id="divExpCmdToPartner">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
        <tags:displayResultList property="lstError" type="key"/>
    </div>

    <s:form action="expToPartnerAction" theme="simple" enctype="multipart/form-data"  method="POST" id="exportStockForm">
<s:token/>

        <!-- phan hien thi thong tin lenh xuat kho -->
        <div>
            <table style="width:100%" cellspacing="0" cellpadding="0">
                <tr>
                    <td style="width:40%; vertical-align:top">
                        <fieldset class="imFieldset">
                            <legend>${fn:escapeXml(imDef:imGetText('MSG.info.export.note'))}</legend>
                            <div style="width:100%; height:300px; overflow:auto;">
                                <s:hidden name="exportStockForm.status" id=".status"/>
                                <s:hidden name="exportStockForm.actionId" id="exportStockForm.actionId"/>
                                <s:hidden name="exportStockForm.shopExportId" id="exportStockForm.shopExportId"/>

                                <table class="inputTbl2Col">
                                    <tr>
                                        <!-- ma phieu xuat -->
                                        <td><tags:label key="MSG.expNoteId"/></td>
                                        <td>
                                            <s:textfield theme="simple" maxlength="20" name="exportStockForm.cmdExportCode" id="exportStockForm.cmdExportCode" cssClass="txtInputFull" readonly="true"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <!-- nguoi xuat -->
                                        <td><tags:label key="MSG.GOD.155"/></td>
                                        <td>
                                            <s:textfield theme="simple" name="exportStockForm.sender" id="exportStockForm.sender" cssClass="txtInputFull" readonly="true"  />
                                        </td>

                                    </tr>
                                    <tr>
                                        <!-- ngay xuat -->
                                        <td><tags:label key="MSG.GOD.159"/></td>
                                        <td>
                                            <tags:dateChooser readOnly="true" property="exportStockForm.dateExported" id="exportStockForm.dateExported" styleClass="txtInputFull" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <!-- kho xuat hang -->
                                        <td><tags:label key="MSG.GOD.138"/></td>
                                        <td>
                                            <tags:imSearch codeVariable="exportStockForm.shopExportCode" nameVariable="exportStockForm.shopExportName"
                                                           codeLabel="MSG.RET.066" nameLabel="MSG.RET.067"
                                                           searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                                           searchMethodName="getListShop"
                                                           getNameMethod="getNameShop"
                                                           readOnly="true"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <!-- doi tac xuat hang -->
                                        <td><tags:label key="MSG.partner"/></td>
                                        <td>
                                            <tags:imSearch codeVariable="exportStockForm.shopImportedCode" nameVariable="exportStockForm.shopImportedName"
                                                           codeLabel="MSG.partner.code" nameLabel="MSG.partner.name"
                                                           searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                                           searchMethodName="getListPartner"
                                                           getNameMethod="getPartnerName"
                                                           readOnly="true"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <!-- ly do xuat -->
                                        <td><tags:label key="MSG.GOD.158"/></td>
                                        <td>
                                            <tags:imSelect name="exportStockForm.reasonId"
                                                           id="exportStockForm.reasonId"
                                                           cssClass="txtInputFull"
                                                           theme="simple"
                                                           headerKey="" headerValue="MSG.GOD.147"
                                                           list="listReason"
                                                           listKey="reasonId" listValue="reasonName"
                                                           disabled="true"/>
                                            <%--<s:hidden name="exportStockForm.reasonId"/>--%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <!-- ghi chu -->
                                        <td><tags:label key="MSG.GOD.031"/></td>
                                        <td>
                                            <s:textfield theme="simple" name="exportStockForm.note" id="exportStockForm.note" maxlength="500" cssClass="txtInputFull" readonly="true"/>
                                        </td>
                                    </tr>
                                </table>

                            </div>
                        </fieldset>
                    </td>
                    <td style="width: 5px;"></td>
                    <td style="vertical-align:top">
                        <fieldset class="imFieldset">
                            <legend>${fn:escapeXml(imDef:imGetText('MSG.detail.goods'))}</legend>
                            <div style="width:100%; height:300px; overflow:auto;">
                                <display:table id="tblListGoods" name="listGoods" class="dataTable" requestURI="javascript: void(0)" cellpadding="1" cellspacing="1">
                                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct" style="text-align:center;">
                                        <s:property escapeJavaScript="true"  value="%{#attr.tblListGoods_rowNum}"/>
                                    </display:column>
                                    <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.007'))}" property="stockModelCode" sortable="false" headerClass="tct"/>
                                    <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.008'))}" property="stockModelName" sortable="false" headerClass="tct"/>
                                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.331'))}" sortable="false" headerClass="tct">
                                        <s:if test="#attr.tblListGoods.stateId ==1"><tags:label key="MSG.GOD.169"/></s:if>
                                        <s:elseif test="#attr.tblListGoods.stateId ==3"><tags:label key="MSG.GOD.170"/></s:elseif>
                                    </display:column>
                                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.179'))}" property="quantity" format="{0}"  style="text-align:right" sortable="false" headerClass="tct"/>
                                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.181'))}" sortable="false" headerClass="tct">
                                        <!--Chi nhung mat hang quan ly theo serial moi cho phep nhap chi tiet serial-->
                                        <s:if test="#attr.tblListGoods.checkSerial==1">
                                            <s:if test="#attr.tblListGoods.lstSerial != null && #attr.tblListGoods.lstSerial.size()>0">
                                                <div align="center">
                                                    <s:url action="InputSerialAction!prepareInputSerial" id="URLView" encode="true" escapeAmp="false">
                                                        <s:param name="totalReq" value="#attr.tblListGoods.quantity"/>
                                                        <s:param name="stockTypeId" value="#attr.tblListGoods.stockTypeId"/>
                                                        <s:param name="stockModelId" value="#attr.tblListGoods.stockModelId"/>
                                                        <s:param name="stockModelName" value="#attr.tblListGoods.stockModelName"/>
                                                        <s:param name="ownerType" value="#attr.tblListGoods.fromOwnerType"/>
                                                        <s:param name="ownerId" value="#attr.tblListGoods.fromOwnerId"/>
                                                        <s:param name="stateId" value="#attr.tblListGoods.stateId"/>
                                                        <s:param name="isView" value="true"/>
                                                        <s:param name="stockTransId" value="#attr.good.stockTransId"/>
                                                        <%--s:param name="impChannelTypeId" value="-5"/--%>
                                                        <s:param name="purpose" value="2"/>
                                                    </s:url>
                                                    <a href="javascript: void(0);" onclick="openDialog('<s:property escapeJavaScript="true"  value="#URLView"/>',800,700)">
                                                        <tags:label key="MSG.GOD.070"/>
                                                        <!--                                        Chi tiết-->
                                                    </a>

                                                </div>
                                            </s:if>
                                            <s:else>
                                                <s:if test="#attr.tblListGoods.quantity != null ">
                                                    <s:if test="#request.notInputSerial==null || #request.notInputSerial !='true'">
                                                        <div align="center">
                                                            <s:url action="InputSerialAction!prepareInputSerial" id="URL" encode="true" escapeAmp="false">
                                                                <s:param name="totalReq" value="#attr.tblListGoods.quantity"/>
                                                                <s:param name="stockTypeId" value="#attr.tblListGoods.stockTypeId"/>
                                                                <s:param name="stockModelId" value="#attr.tblListGoods.stockModelId"/>
                                                                <s:param name="stockModelName" value="#attr.tblListGoods.stockModelName"/>
                                                                <s:param name="ownerType" value="#attr.tblListGoods.fromOwnerType"/>
                                                                <s:param name="ownerId" value="#attr.tblListGoods.fromOwnerId"/>
                                                                <s:param name="stateId" value="#attr.tblListGoods.stateId"/>
                                                                <s:param name="checkDial" value="#attr.tblListGoods.checkDial"/>
                                                                <s:param name="editable" value="tblListGoods.editable"/>
                                                                <%--s:param name="impChannelTypeId" value="-5"/--%>
                                                                <s:param name="purpose" value="2"/>
                                                            </s:url>
                                                            <a href="javascript:void(0)" onclick="openDialog('<s:property escapeJavaScript="true"  value="#URL"/>',800,700)">
                                                                <tags:label key="MSG.GOD.173"/>
                                                                <!--                                                Nhập serial-->
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
                        </fieldset>
                    </td>
                </tr>
            </table>
        </div>

        <!-- nhap kho -->
        <div style="width:100%; margin-top: 5px; " align="center">
            <s:if test="#request.isPrintMode == 'true'">
                <input type="button" style="width: 100px;" value="${fn:escapeXml(imDef:imGetText('MSG.expStock'))}" disabled/>
            </s:if>
            <s:else>
                <tags:submit id="exp"
                             confirm="true"
                             confirmText="MSG.confirm.export.store"
                             formId="exportStockForm"
                             showLoadingText="true"
                             cssStyle="width:80px; "
                             targets="divExpNoteToPartner"
                             value="MSG.expStock"
                             preAction="expToPartnerAction!expToPartner.do"/>
            </s:else>

        </div>


    </s:form>

</sx:div>


<script>
    updateSerial=function() {
        gotoAction("divExpNoteToPartner", "expToPartnerAction!refreshListGoods.do?actionId=${fn:escapeXml(exportStockForm.actionId)}");
    }

</script>
