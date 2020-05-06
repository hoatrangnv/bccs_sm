<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : CreateExpNote.jsp
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
--%>

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
    <tags:displayResult id="resultCreateExpCmdClient" property="resultCreateExp" type="key"/><br/>
    <tags:displayResultList property="lstError" type="key"/>
    <s:hidden name="exportStockForm.canPrint"/>
    <table style="width:100%">
        <tr>
            <td style="vertical-align:top; width:50%">
                <tags:imPanel title="MSG.info.cmd.export">
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
                                    <tags:dateChooser property="exportStockForm.dateExported" id="exportStockForm.dateExported" styleClass="txtInputFull" readOnly="true"/>
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
                            <display:column  title="STT" sortable="false" headerClass="tct">
                                <s:property escapeJavaScript="true"  value="%{#attr.good_rowNum}"/>
                            </display:column>
                            <%--display:column title="Tên loại hàng hoá" property="stockTypeName"/--%>
                            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.176'))}" property="stockTypeName"/>
                            <%--display:column title="Tên hàng hoá" property="stockModelName" sortable="false" headerClass="tct"/--%>
                            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.175'))}" property="stockModelName" sortable="false" headerClass="tct"/>
                            <%--display:column title="Trạng thái" sortable="false" headerClass="tct"--%>
                            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.013'))}" sortable="false" headerClass="tct">
                                <s:if test="#attr.good.stateId ==1"><tags:label key="MSG.GOD.169"/></s:if>
                                <s:if test="#attr.good.stateId ==3"><tags:label key="MSG.GOD.170"/></s:if>
                            </display:column>
                            <%--display:column property="unit" title="Đơn vị tính" sortable="false" headerClass="tct"/--%>
                            <display:column escapeXml="true" property="unit" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.177'))}" sortable="false" headerClass="tct"/>
                            <%--display:column title="Số lượng" property="quantity" format="{0}"  style="text-align:right" sortable="false" headerClass="tct"/--%>
                            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.179'))}" property="quantity" format="{0}"  style="text-align:right" sortable="false" headerClass="tct"/>
                        </display:table>
                    </div>
                </tags:imPanel>
            </td>
        </tr>
    </table>
    <!--trang thai lenh ok thi cho phep lap phieu-->
    <s:if test="exportStockForm.returnMsg == 'done'">
        <tags:imPanel title="MSG.info.export.note">
            <table class="inputTbl" style="width:100%">
                <tr>
                    <td>
                        <tags:label key="MSG.expNoteId"/>
                    </td>
                    <td>
                        <s:textfield theme="simple"  name="exportStockForm.noteExpCode" readonly="true" id="noteExpCode" cssClass="txtInput"/>
                    </td>
                    <td>                        
                        <s:if test="exportStockForm.canPrint !=null && exportStockForm.canPrint ==1">
                            <input type="button" value="<s:text name="MSG.createExportNote"/>" disabled/>
                            <tags:submit id="exp" formId="exportStockForm" showLoadingText="true" targets="bodyContent"
                                         value="MSG.print.export.note"
                                         preAction="exportStockToStaffAction!printExpNote.do"/>
                        </s:if>
                        <s:else>
                            <tags:submit id="exp" confirm="true" confirmText="MSG.confirm.create.export.note"
                                         formId="exportStockForm" showLoadingText="true" targets="bodyContent"
                                         value="MSG.createExportNote" validateFunction="validateForm()"
                                         preAction="exportStockToStaffAction!createExpNote.do"/>
                            <input type="button" value="<s:text name="MSG.print.export.note"/>" disabled/>
                        </s:else>
                    </td>
                    <td style="width:20%">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="4" align="center">
                        <s:if test="exportStockForm.exportUrl !=null && exportStockForm.exportUrl!=''">
                            <script>
                                window.open('${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
                            </script>
                            <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>
                        </s:if>
                    </td>
                </tr>
            </table>

        </tags:imPanel>
        <%--/logic:equal--%>
    </s:if>
</s:if>

<script>
    validateForm=function(){
        var noteExpCode= document.getElementById("noteExpCode");
        if(noteExpCode.value ==null || trim(noteExpCode.value)==''){
            //            $('resultCreateExpCmdClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.STK.017')"/>';
            $('resultCreateExpCmdClient').innerHTML= '<s:text name="ERR.STK.017"/>';
            //$('resultCreateExpCmdClient').innerHTML='Chưa nhập mã phiếu xuất';
            noteExpCode.focus();
            return false;
        }
        noteExpCode.value=trim(noteExpCode.value);
        if(!isFormalCharacter(noteExpCode.value)){
            //            $('resultCreateExpCmdClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.STK.018')"/>';
            $('resultCreateExpCmdClient').innerHTML= '<s:text name="ERR.STK.018"/>';
            //$('resultCreateExpCmdClient').innerHTML='Mã phiếu xuất không được chứa các ký tự đặc biệt';
            noteExpCode.focus();
            return false;
        }
        return true;
    }
 
</script>
