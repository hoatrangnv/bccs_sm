<%--
    Document   : ExportStockToSenior
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%--
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<s:form method="POST" id="exportStockForm" action="exportStockToSenior" theme="simple">
<s:token/>

    <s:hidden name="exportStockForm.shopExpType" value="2"/>

    <tags:SearchTrans form="exportStockForm" target="searchArea" type="exp_to_senior" action="ExportStockSeniorAction!searchExpTrans.do"/>
    <sx:div id="searchArea" theme="simple">
        <jsp:include page="ListSearchExpNote.jsp"/>
        <jsp:include page="ExpStock.jsp"/>
    </sx:div>

</s:form>

<%--
<s:form action="exportStockToSenior!searchDeliverNote" theme="simple" enctype="multipart/form-data"  method="POST" id="frm">
<s:token/>

    <fieldset style="width:95%; padding:10px 10px 10px 10px">
        <legend class="transparent">Tìm kiếm phiếu xuất</legend>
        <table class="inputTbl" style="width:100%" cellpadding="0" cellspacing="4">
            <tr>
                <td>
                    Mã phiếu xuất
                </td>
                <td>
                    <s:textfield name="exportForm.inputExpNoteCode" id="exportForm.inputExpNoteCode" cssClass="txtInput"/>
                </td>
                <td>
                    <sx:submit formId="frm" targets="bodyContent" value="Tìm kiếm"/>
                </td>
                <td style="width:40%"></td>
            </tr>
            <s:if test="ExportStockForm.status >= 3">
                <tr>
                    <td colspan="4" align="center" class="txtError">
                        Phiếu đã xuất kho
                    </td>
                </tr>
            </s:if>
            <s:if test="ExportStockForm.errorUrl != null">
                <tr>
                    <td colspan="4" align="center">
                        <a href="<s:property escapeJavaScript="true"  value="ExportStockForm.errorUrl"/>">
                            Có lỗi xảy ra khi xuất kho. Click vào đây để lấy danh sách serial lỗi.
                        </a>
                    </td>
                </tr>
            </s:if>
        </table>
    </fieldset>

    <s:hidden name="exportForm.status"/>
    <s:if test="exportForm.cmdExportCode != null">
        <table style="width:97%">
            <tr>
                <td style="vertical-align:top;">
                    <tags:ExportStockCmd readOnly="true" disabled="true" viewOnly="true" type="note" subForm="exportForm"/>
                </td>
                <td style="vertical-align:top;">
                    <fieldset style="width:95%; padding:10px 10px 10px 10px">
                        <legend class="transparent">Danh sách hàng hoá </legend>
                        <div id="listGoods" style="height:170px; overflow:auto;">
                            <s:if test="exportForm.status == 2">
                                <jsp:include page="../../Common/ListGoods.jsp">
                                    <jsp:param name="inputSerial" value="true"/>
                                </jsp:include>
                            </s:if>
                            <s:if test="exportForm.status >= 3">
                                <jsp:include page="../../Common/ListGoods.jsp"/>
                            </s:if>
                        </div>
                    </fieldset>
                </td>
            </tr>
            <s:if test="exportForm.status == 2">
                <tr>
                    <td colspan="2">
                        <div style="width:100%" align="center">
                            <sx:submit parseContent="true" executeScripts="true" formId="frm" loadingText="loading..." showLoadingText="true" targets="bodyContent"  value="Xác nhận xuất kho"  beforeNotifyTopics="ExportAction/expStock"/>
                        </div>
                    </td>
                </tr>
            </s:if>
        </table>
    </s:if>
</s:form>
--%>
<script>
    dojo.event.topic.subscribe("ExportAction/expStock", function(event, widget){
        widget.href = "exportStockToSenior!expStock.do";
        //event: set event.cancel = true, to cancel request
    });
    /*
    function updateSerial(stockModelId){
        //alert(goodsDefId + "    "+ resTypeId);
        document.getElementById("className").value="StockSeniorDAO";
        document.getElementById("methodName").value="addSerial";
        document.getElementById("inStockModelId").value=stockModelId;
        document.getElementById("frm").submit();
    }
    function expStock(){
        document.getElementById("className").value="StockSeniorDAO";
        document.getElementById("methodName").value="expStock";
        document.getElementById("frm").submit();
    }
    */

</script>
