

<%--
    Document   : ExportStockToUnderlyingNote
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
<!--tags:displayResult id="msgExpStock" property="msgExpStock"  type="key"/-->
<s:if test="exportStockForm.cmdExportCode != null && exportStockForm.cmdExportCode !='' ">
    <table style="width:100%">
        <tr>
            <td style="vertical-align:top; width:50%">
                <tags:ExportStockCmd subForm="exportStockForm" type="note" readOnly="true" disabled="true" viewOnly="true" height="210px"/>
            </td>
            <td style="vertical-align:top; width:auto;">

                <jsp:include page="../../Common/ListGoods.jsp">
                    <jsp:param name="inputSerial" value="true"/>
                </jsp:include>

            </td>
        </tr>
        <!--Lamnt 05082017 ẩn/hiện chức năng khi tìm theo kho giảm trừ hàng hóa-->
        <s:if test = "exportStockForm.shopId == '1003583'">
            <s:form action="exportStockForm!fileUpload" theme="simple" method="post" id="exportStockForm">
                <table class="inputTbl8Col" style="width:100%" >
                    <tr id="trImpByFile">
                        <td  style="width:10%"><tags:label key="MSG.generates.file_data" required="true"/></td>
                        <td class="text" colspan="7" style="width:90%">
                            <tags:imFileUpload name="exportStockForm.fileUpload" 
                                               id="exportStockForm.fileUpload" 
                                               serverFileName="exportStockForm.fileUpload" 
                                               cssStyle="width:500px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td  class="label">
                            <tags:label key="Reason"/>
                        </td>
                        <td class="text" colspan="7" style="width:30%"> 
                        <select size="1" name="exportStockForm.reason" id="exportStockForm.reason" >
                            <option value ="1">Xuất giảm trừ do thanh lý</option>
                            <option value ="2">Xuất giẩm trừ do sai sót nghiệp vụ</option>
                            <option value ="3">Xuất giảm trừ do thất thoát nhưng đã đền bù</option>
                            <option value ="4">Xuất giảm trừ do tiêu hủy hàng hóa</option>
                            <option value ="5">Xuất giảm trừ do trao, biếu, tặng khách hàng</option>
                            <option value ="6">Xuất giảm trừ do bán cho đối tác</option>
                            <option value ="7">Lý do khác</option>
                        </select>
                        </td> 
                    </tr>
                </table>
            </s:form>
        </s:if>
        <!--lamnt end--> 
        <!--tannh 12292017 ẩn/hiện chức năng khi tìm theo kho CHO XU LY -->
        <s:elseif test = "exportStockForm.shopId == 1003600 ||
                  exportStockForm.shopId == 1003860 ||
                  exportStockForm.shopId == 1003861 ||
                  exportStockForm.shopId == 1003880 ||
                  exportStockForm.shopId == 1003881 ||
                  exportStockForm.shopId == 1003882 ||
                  exportStockForm.shopId == 1003863 ||
                  exportStockForm.shopId == 1003865 ||
                  exportStockForm.shopId == 1003866 ||
                  exportStockForm.shopId == 1003867 ||
                  exportStockForm.shopId == 1003864 ||
                  exportStockForm.shopId == 1003862 ||
                  exportStockForm.shopId == 1003883
                  ">
            <s:form action="exportStockForm!fileUpload" theme="simple" method="post" id="exportStockForm">
                <table class="inputTbl8Col" style="width:100%" >
                    <tr id="trImpByFile">
                        <td  style="width:10%"><tags:label key="MSG.generates.file_data" required="true"/></td>
                        <td class="text" colspan="7" style="width:90%">
                            <tags:imFileUpload name="exportStockForm.fileUpload" 
                                               id="exportStockForm.fileUpload" 
                                               serverFileName="exportStockForm.fileUpload" 
                                               cssStyle="width:500px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td  >
                            <tags:label key="Reason"/>
                        </td>
                        <td class="text" colspan="7" style="width:30%"> 
                        <select size="1" name="exportStockForm.reason" id="exportStockForm.reason" >
                            <option value ="11">Do Mất trộm, mất cắp,mất cướp</option>
                            <option value ="16">Do cháy, hỏa hoạn, lũ lụt</option>
                            <option value ="17">Do nhân viên ôm hàng bỏ trốn</option>
<!--                            bo 12 va 13-->
<!--                            <option value ="12">Chênh lệch chưa rõ lý do</option>
                            <option value ="13">Dữ liệu phần mềm sai</option>-->
 <!--                            bo 12 va 13-->
<!--                            <option value ="14">Nhập/xuất sai nghiệp vụ</option>
                            <option value ="15">Lý do khác</option>-->
                        </select>
                        </td> 
                    </tr>
                </table>
            </s:form>
        </s:elseif>
        
        <!--tannh end--> 
      
        <!--trang thai lenh ok thi cho phep lap phieu-->
        <s:if test="exportStockForm.returnMsg == 'done'">
            <tr>
                <td colspan="2" align="center">
                    <%--sx:submit parseContent="true" executeScripts="true"
                               formId="exportStockForm" loadingText="loading..." showLoadingText="true"
                               targets="bodyContent"  value="Xuất kho"
                               errorNotifyTopics="errorAction"
                               beforeNotifyTopics="ExportStockForm/expStock"/--%>
                    <s:hidden name="exportStockForm.canPrint"/>
                    <s:if test="exportStockForm.canPrint !=null && exportStockForm.canPrint==1">
                        <input type="button" value="<s:text name="MSG.expStock"/>" disabled ="false"/>
                        <tags:submit id="print" confirm="true" confirmText="MSG.confirm.print.note" formId="exportStockForm" showLoadingText="true" targets="bodyContent"
                                     value="MSG.print.votes"
                                     preAction="ExportStockUnderlyingAction!printExpAction.do"/>
                        <!--R499-->
                        <tags:submit id="printBBBG" confirm="true" confirmText="MSG.confirm.print.note" formId="exportStockForm" showLoadingText="true" targets="bodyContent"
                                     value="In BBBG Chi tiết"
                                     preAction="ExportStockUnderlyingAction!printExpAction.do?expDetail=1"/>
                    </s:if>
                    <s:else>
                        <tags:submit id="exp" confirm="true" confirmText="MSG.confirm.export.store" formId="exportStockForm" showLoadingText="true" targets="bodyContent"
                                     value="MSG.expStock"
                                     preAction="ExportStockUnderlyingAction!expStock.do"/>
                        <input type="button" value='<s:text name="MSG.printNote"/>' disabled="false"/>
                        <input type="button" value="In BBBG Chi tiết" disabled="false"/>

                        <!--                        "In phiếu" -->
                    </s:else>
                </td>
            </tr>
        </s:if>


    </table>
</s:if>

<br>
<s:if test="exportStockForm.exportUrl !=null && exportStockForm.exportUrl!=''">
    <script>
        window.open('${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
    </script>
    <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>
</s:if>



<%--
<script>

dojo.event.topic.subscribe("ExportStockForm/expStock", function(event, widget){
widget.href = "ExportStockUnderlyingAction!expStock.do";
//event: set event.cancel = true, to cancel request
});
</script>
--%>
