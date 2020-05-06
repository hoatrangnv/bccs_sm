

<%--
    Document   : TransDetail
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
<s:if test="exportStockForm.cmdExportCode != null && exportStockForm.cmdExportCode!='' ">
    <s:hidden name="exportStockForm.actionId"/>
    <sx:div id="TransDetailResult">
        <table style="width:100%">
            <tr>
                <td style="vertical-align:top;">
                    <tags:imPanel title="MSG.info.detail.trans">
                        <div style="width:100%;height:210px">
                            <table class="inputTbl" style="width:100%">
                                <tr>
                                    <td>
                                        <tags:label key="MSG.transaction.code"/>
                                    </td>
                                    <td>
                                        <s:textfield theme="simple" maxlength="20"  name="exportStockForm.cmdExportCode" id="exportStockForm.cmdExportCode" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                    <td>
                                        <tags:label key="MSG.createStaff"/>
                                    </td>
                                    <td>
                                        <s:textfield theme="simple" name="exportStockForm.sender" id="exportStockForm.sender" cssClass="txtInputFull" readonly="true"  />
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <tags:label key="MSG.fromStore"/>
                                    </td>
                                    <td>
                                        <s:textfield theme="simple" name="exportStockForm.shopExportName" id="exportStockForm.shopExportName" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                    <td>
                                        <tags:label key="MSG.toStore"/>
                                    </td>
                                    <td>

                                        <s:textfield theme="simple" name="exportStockForm.shopImportedName" id="exportStockForm.shopImportedName" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <tags:label key="MSG.reason"/>
                                    </td>
                                    <td>

                                        <s:textfield theme="simple"  name="exportStockForm.reasonName" id="exportStockForm.reasonName" readonly="true" cssClass="txtInputFull" />
                                    </td>
                                    <td>
                                        <tags:label key="MSG.actionDate"/>
                                    </td>
                                    <td>

                                        <tags:dateChooser readOnly="true"  property="exportStockForm.dateExported" id="exportStockForm.dateExported"  styleClass="txtInputFull" />

                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <tags:label key="MSG.comment"/>
                                    </td>
                                    <td colspan="3">
                                        <s:textfield theme="simple"  name="exportStockForm.note" id="note" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </tags:imPanel>
                </td>
                <td style="vertical-align:top;">
                    <jsp:include page="ListGoods.jsp">
                        <jsp:param name="notInputSerial" value="true"/>
                    </jsp:include>
                    <%--<jsp:include page="TransDetailListGoods.jsp">
                        <jsp:param name="notInputSerial" value="true"/>
                    </jsp:include>--%>
                </td>
            </tr>
        </table>


        <div style="width:100%" align="center">
            <%--sx:submit parseContent="true" executeScripts="true" formId="exportStockForm"
                       loadingText="loading..." showLoadingText="true" targets="bodyContent" cssStyle="width:140px" value="In chi tiết giao dịch"
                       errorNotifyTopics="errorAction"
                       beforeNotifyTopics="ExportStockForm/printStockTrans"/--%>
            <tags:submit formId="exportStockForm" showLoadingText="true" targets="detailArea" cssStyle="width:140px" value="MSG.printDetailTrans"
                         preAction="StockTransManagmentAction!printStockTrans.do"/>
        </div>

        <div align="center" style="width:100%">
            <tags:displayResult id="resultCreateExpCmdClient" property="resultCreateExp" type="key"/>
            <s:if test="exportStockForm.exportUrl !=null && exportStockForm.exportUrl!=''">
                <script>
                    window.open('${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
                </script>
                <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>

            </s:if>
        </div>
    </sx:div>
</s:if>

<%--script>

dojo.event.topic.subscribe("ExportStockForm/printStockTrans", function(event, widget){
widget.href = "StockTransManagmentAction!printStockTrans.do";
});
</script--%>

