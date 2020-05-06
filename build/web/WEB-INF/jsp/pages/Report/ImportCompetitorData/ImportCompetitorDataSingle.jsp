<%-- 
    Document   : ImportCompetitorDataSingle
    Created on : May 31, 2012, 9:42:44 AM
    Author     : Thaiph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@page  import="com.viettel.im.database.BO.UserToken" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<tags:imPanel title="label.title.import.single">
    <div>
        <table class="inputTbl4Col">

            <tr id="trImpByFile">
                <td class="label" ><tags:label key="MSG.SAE.109" required="true" /></td>
                <td>
                    <tags:imSearch codeVariable="channelForm.compShopCode" nameVariable="channelForm.compShopName"
                                   codeLabel="MSG.RET.066" nameLabel="MSG.RET.067"
                                   searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                   searchMethodName="getListShop"
                                   getNameMethod="getShopName"/>
                </td>

                <td class="label" ><tags:label key="MSG.SAE.111" required="true" /></td>
                <td>
                    <tags:imSearch codeVariable="channelForm.compStaffCode" nameVariable="channelForm.compStaffName"
                                   codeLabel="MSG.store.receive.code" nameLabel="MSG.store.receive.name"
                                   searchClassName="com.viettel.im.database.DAO.StockCommonDAO"
                                   searchMethodName="getListStaffF9"
                                   getNameMethod="getListStaffF9"
                                   otherParam="channelForm.compShopCode"/>

                </td>
                <td class="label" ><tags:label key="MSG.createDate" required="true" /></td>
                <td>
                    <tags:dateChooser property="channelForm.compCollectorDate" id="channelForm.compCollectorDate" styleClass="txtInputFull"/>
                </td>
                <s:hidden name="channelForm.todayDate" id="channelForm.todayDate" styleClass="txtInputFull"/>
            </tr>
            <tr id="trImpByFile">
                <%--<td  class="label" >File dữ liệu (<font color="red">*</font>)</td>--%>
                <td class="label" ><tags:label key="Kit.Movitel" required="true" /></td>
                <s:textfield name="channelForm.compKitMovitel" id="channelForm.compKitMovitel" maxlength="10" cssClass="txtInputFull" theme="simple"/>
                <td class="label" ><tags:label key="Kit.Mctel" required="true" /></td>
                <s:textfield name="channelForm.compKitMctel" id="channelForm.compKitMctel" maxlength="10" cssClass="txtInputFull" theme="simple"/>
                <td class="label" ><tags:label key="Kit.Vodacom" required="true" /></td>
                <s:textfield name="channelForm.compKitVodacom" id="channelForm.compKitVodacom" maxlength="10" cssClass="txtInputFull" theme="simple"/>
            </tr>
            <tr id="trImpByFile">
                <td class="label" ><tags:label key="Card.Movitel" required="true" /></td>
                <s:textfield name="channelForm.compCardMovitel" id="channelForm.compCardMovitel" maxlength="10" cssClass="txtInputFull" theme="simple"/>
                <td class="label" ><tags:label key="Card.Mctel" required="true" /></td>
                <s:textfield name="channelForm.compCardMctel" id="channelForm.compCardMctel" maxlength="10" cssClass="txtInputFull" theme="simple"/>
                <td class="label" ><tags:label key="Card.Vodacom" required="true" /></td>
                <s:textfield name="channelForm.compCardVodacom" id="channelForm.compCardVodacom" maxlength="10" cssClass="txtInputFull" theme="simple"/>
            </tr>
        </table>
    </div>

    <!-- phan nut tac vu -->
    <div align="center" style="padding-top:5px; padding-bottom:5px;">        
        <tags:submit formId="channelForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     value="MSG.import"
                     confirm="true"
                     confirmText="MSG.confirm.import.data.competitor"
                     validateFunction="checkValidWebForm()"
                     preAction="importCompetitorDataAction!addStaffByWebForm.do"/>
    </div>

    <tags:displayResult id="resultImportSingle" property="resultImportSingle" type="key"/>
</tags:imPanel>




<script type="text/javascript" language="javascript">    
    checkValidWebForm = function(){
        var compKitMovitel= document.getElementById("channelForm.compKitMovitel");
        var compKitMctel= document.getElementById("channelForm.compKitMctel");
        var compKitVodacom= document.getElementById("channelForm.compKitVodacom");
        var compCardMovitel= document.getElementById("channelForm.compCardMovitel");
        var compCardMctel= document.getElementById("channelForm.compCardMctel");
        var compCardVodacom= document.getElementById("channelForm.compCardVodacom");
        //        var compCollectorDate= document.getElementById("channelForm.compCollectorDate"); 
        var compStaffCode= document.getElementById("channelForm.compStaffCode");
        var compShopCode= document.getElementById("channelForm.compShopCode");
        
        
        if(compShopCode.value ==null || trim(compShopCode.value)==''){
            //chua chon shopcode
            $( 'resultImportSingle' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.ISN.028')"/>';
            compShopCode.focus();
            return false;
        }
        
        if(compStaffCode.value ==null || trim(compStaffCode.value)==''){
            //chua chon staffcode
            $( 'resultImportSingle' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.125')"/>';
            compStaffCode.focus();
            return false;
        }
        
        var compCollectorDate= dojo.widget.byId("channelForm.compCollectorDate");
        if(compCollectorDate.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo từ ngày';--%>
                        $('resultImportSingle').innerHTML='<s:text name="ERR.INF.100"/>';
                        $('channelForm.compCollectorDate').focus();
                        return false;
                    }
            
//                    var dateExported1 = dojo.widget.byId("channelForm.todayDate");
//                    if(!isCompareDate(compCollectorDate.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
//    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ ngày phải nhỏ hơn ngày báo cáo đến ngày';--%>
//                        $('resultImportSingle').innerHTML='<s:text name="ERR.RET.006"/>';
//                        $('channelForm.compCollectorDate').focus();
//                        return false;
//                    }
            

        
                    if(compKitMovitel.value==""){
                        //chua chon KitMovitel
                        $( 'resultImportSingle' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('error.stock.noRequirevalue')"/>';
                        compKitMovitel.focus();
                        return false;
                    }
                    if(!isInteger(trim(compKitMovitel.value))){
                        //KitMovitel is not integer
                        $( 'resultImportSingle' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('MSG.SAE.030')"/>';
                        compKitMovitel.focus();
                        return false;
                    }
        
                    if(compKitMctel.value ==null || trim(compKitMctel.value)==''){
                        //chua chon KitMctel
                        $( 'resultImportSingle' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('error.stock.noRequirevalue')"/>';
                        compKitMctel.focus();
                        return false;
                    }
                    if(!isInteger(trim(compKitMctel.value))){
                        //compKitMctel is not integer
                        $( 'resultImportSingle' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('MSG.SAE.030')"/>';
                        compKitMctel.focus();
                        return false;
                    }
        
                    if(compKitVodacom.value ==null || trim(compKitVodacom.value)==''){
                        //chua chon KitVodacom
                        $( 'resultImportSingle' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('error.stock.noRequirevalue')"/>';
                        compKitVodacom.focus();
                        return false;
                    }
                    if(!isInteger(trim(compKitVodacom.value))){
                        //compKitVodacom is not integer
                        $( 'resultImportSingle' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('MSG.SAE.030')"/>';
                        compKitVodacom.focus();
                        return false;
                    }
        
                    if(compCardMovitel.value ==null || trim(compCardMovitel.value)==''){
                        //chua chon compCardMovitel
                        $( 'resultImportSingle' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('error.stock.noRequirevalue')"/>';
                        compCardMovitel.focus();
                        return false;
                    }
                    if(!isInteger(trim(compCardMovitel.value))){
                        //compCardMovitel is not integer
                        $( 'resultImportSingle' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.TRANS.ANYPAY.003')"/>';
                        compCardMovitel.focus();
                        return false;
                    }
        
                    if(compCardMctel.value ==null || trim(compCardMctel.value)==''){
                        //chua chon compCardMctel
                        $( 'resultImportSingle' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('error.stock.noRequirevalue')"/>';
                        compCardMctel.focus();
                        return false;
                    }
                    if(!isInteger(trim(compCardMctel.value))){
                        //compCardMctel is not integer
                        $( 'resultImportSingle' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.TRANS.ANYPAY.003')"/>';
                        compCardMctel.focus();
                        return false;
                    }
        
                    if(compCardVodacom.value ==null || trim(compCardVodacom.value)==''){
                        //chua chon compCardVodacom
                        $( 'resultImportSingle' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('error.stock.noRequirevalue')"/>';
                        compCardVodacom.focus();
                        return false;
                    }
                    if(!isInteger(trim(compCardVodacom.value))){
                        //compCardVodacom is not integer
                        $( 'resultImportSingle' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.TRANS.ANYPAY.003')"/>';
                        compCardVodacom.focus();
                        return false;
                    }
        
        
                    return true;
        
            
                }
</script>
