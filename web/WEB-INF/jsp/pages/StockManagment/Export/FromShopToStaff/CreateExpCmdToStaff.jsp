<%--
    Document   : ExportStockToStaff
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    request.setAttribute("contextPath", request.getContextPath());

    if (request.getAttribute("listChannelType") == null) {
        request.setAttribute("listChannelType", request.getSession().getAttribute("listChannelType"));
    }

%>


<s:form action="exportStockToStaffAction" theme="simple"  method="POST" id="exportStockForm">
<s:token/>
   
    <s:hidden name="exportStockForm.canPrint"/>
    <tags:imPanel title="MSG.stock.create.cmd.exp.staff">
        <div style="width: 100%">
            <s:hidden name="exportStockForm.status" id="exportStockForm.status"/>
            <s:hidden name="exportStockForm.actionId" id="exportStockForm.actionId"/>
            <table class="inputTbl4Col">
                <tr>
                    <td class="label">
                        <tags:label key="MSG.cmd.code" required="true"/>
                    </td>
                    <td class="text">
                        <s:textfield theme="simple" maxlength="20"  name="exportStockForm.cmdExportCode" readOnly="true" id="exportStockForm.cmdExportCode" cssClass="txtInputFull" />
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
                        <s:hidden theme="simple" name="exportStockForm.shopExportId" id="exportStockForm.shopExportId"/>
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
                        <tags:label key="MSG.staff.receive" required="true"/>
                    </td>
                    <td  class="text" colspan="3">
                        <tags:imSearch codeVariable="exportStockForm.shopImportedCode" nameVariable="exportStockForm.shopImportedName"
                                       codeLabel="MSG.store.receive.code" nameLabel="MSG.store.receive.name"
                                       searchClassName="com.viettel.im.database.DAO.StockCommonDAO"
                                       searchMethodName="getListStaffF9"
                                       getNameMethod="getListStaffF9"
                                       otherParam="exportStockForm.shopExportCode"/>

                    </td>
                </tr>


                <tr>

                    <td class="label">
                        <tags:label key="MSG.reasonExport"/>
                    </td>
                    <td  class="text">

                        <%--tags:imSelect name="%{#attr.form+'.toOwnerId'}"
                              id="toOwnerId"
                              cssClass="txtInput"
                              headerKey="" headerValue="MSG.GOD.186"
                              list="lstShopImport"
                              listKey="shopId" listValue="name"/--%>

                        <tags:imSelect theme="simple"  name="exportStockForm.reasonId"
                                       id="exportStockForm.reasonId"
                                       cssClass="txtInputFull"                                 
                                       headerKey="" headerValue="MSG.GOD.147"
                                       list="lstReasonExp"
                                       listKey="reasonId" listValue="reasonName"/>


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
                        <s:textfield theme="simple"  name="exportStockForm.note" maxlength="500" id="exportStockForm.note" cssClass="txtInputFull"/>
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
                                       disabled="${fn:escapeXml(pageScope.disabled)}"
                                       headerKey="" headerValue="--Channel type--"
                                       list="listChannelType"
                                       listKey="channelTypeId" listValue="name"/>

                        <%--<s:if test="#attr.disabled =='true'">
                            <s:hidden theme="simple"  name="exportStockForm.channelTypeId" id="exportStockForm.channelTypeId"/>
                        </s:if>--%>
                    </td>
                </tr>                
            </table>
        </div>
    </tags:imPanel>
</s:form>


<!--    TrongLV : xuat cho NV : check hang hoa trong kho cua hang-->
<s:hidden name="staff_export_type" id="staff_export_type" value=""/>

<div id="inputGoods">
    <jsp:include page="../../Common/Goods.jsp"/>
    <%--jsp:param name="inputSerial" value="false"/>
    </jsp:include--%>
</div>
<div style="width:100%" align="center">
    <s:if test="exportStockForm.canPrint==1">
<!--        <input type="button" value='<s:property escapeJavaScript="true"  value="getError('MSG.GOD.198')"/>' disabled style="width: 150px;"/>-->
        <input type="button" value ='<s:text name="MSG.GOD.198"/>' disabled style="width: 150px;"/>
        <tags:submit formId="exportStockForm" showLoadingText="true" targets="bodyContent" value="MSG.printExportCmd"
                     preAction="exportStockToStaffAction!printExpCmd.do?type=toStaff"  cssStyle="width: 150px;"/>
    </s:if>
    <s:else>
        <tags:submit confirm="true" confirmText="MSG.confirm.create.cmd.exp.store"
                     formId="exportStockForm" showLoadingText="true" targets="bodyContent" value="MSG.createExportCmd"
                     validateFunction="validateForm1()"
                     preAction="exportStockToStaffAction!createExpCmdToStaff.do" cssStyle = "width:150px;"/>
<!--        <input type="button" value='<s:property escapeJavaScript="true"  value="getError('MSG.GOD.199')"/>'  disabled style="width: 150px;" />-->
        <input type="button" value='<s:text name="MSG.GOD.199"/>'  disabled style="width: 150px;" />
    </s:else>




    <tags:submit formId="exportStockForm" showLoadingText="true" targets="bodyContent" value="MSG.reset"
                 preAction="exportStockToStaffAction!clearFormExpCmd.do?type=toStaff"  cssStyle = "width:100px;"/>
    <br/>
    <s:if test="exportStockForm.exportUrl !=null && exportStockForm.exportUrl!=''">
        <script>
            window.open('${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
        </script>
        <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>' ><s:text name="MSG.download2.file.here"/></a></div>
    </s:if>
    <tags:displayResult id="resultCreateExpCmdClient" property="resultCreateExp" type="key"/>
</div>


<script>
    validateForm1 =function(){
        return validateForm("resultCreateExpCmdClient");
    }

    validateForm =function(target){
        var cmdExportCode= document.getElementById("exportStockForm.cmdExportCode");
        var shopImportedCode= document.getElementById("exportStockForm.shopImportedCode");
        var reasonId= document.getElementById("exportStockForm.reasonId");

        if(cmdExportCode.value ==null || trim(cmdExportCode.value)==''){
            $(target).innerHTML='<s:text name="ERR.STK.014"/>';
            //$(target).innerHTML='Chưa nhập mã lệnh/phiếu xuất';
            cmdExportCode.focus();
            return false;
        }

        if(!isFormalCharacter(cmdExportCode.value)){
            $(target).innerHTML='<s:text name="ERR.STK.018"/>';
            //$(target).innerHTML="Mã lệnh không được chứa các ký tự đặc biệt";
            cmdExportCode.focus();
            return false;
        }
        cmdExportCode.value=trim(cmdExportCode.value);
        if(shopImportedCode.value ==null || trim(shopImportedCode.value)==''){
            $(target).innerHTML='<s:text name="ERR.STK.005"/>';
            //$(target).innerHTML='Chưa chọn kho nhận hàng';
            shopImportedCode.focus();
            return false;
        }
        if(reasonId.value ==null || trim(reasonId.value)==''){
            $(target).innerHTML='<s:text name="ERR.STK.015"/>';
            //$(target).innerHTML='Chưa chọn lý do xuất';
            reasonId.focus();
            return false;
        }

        var note= document.getElementById("exportStockForm.note");

        if(isHtmlTagFormat(note.value)){
//            $(target).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.STK.016')"/>';
            $(target).innerHTML='<s:text name="ERR.STK.016"/>';
            //$(target).innerHTML='Trường ghi chú không được thẻ HTML';
            note.focus();
            return false;
        }
        note.value=note.value.trim();
        return true;
    }

</script>
