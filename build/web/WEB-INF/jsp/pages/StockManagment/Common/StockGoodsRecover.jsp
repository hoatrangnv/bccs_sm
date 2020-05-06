<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : StockGoodsRecover
    Created on : Sep 25, 2009, 8:51:20 AM
    Author     : Vunt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            boolean isEdit = false;
            boolean notEdit = true;
            String pageId = request.getParameter("pageId");
            if (request.getSession().getAttribute("isEdit" + pageId) != null) {
                isEdit = Boolean.parseBoolean(request.getSession().getAttribute("isEdit" + pageId).toString());
            }
            notEdit = !isEdit;
            String nonEdit = new Boolean(notEdit).toString();
            String edit = new Boolean(isEdit).toString();
            request.setAttribute("edit", edit);
            request.setAttribute("nonEdit", nonEdit);
            request.setAttribute("contextPath", request.getContextPath());
            String require = "&nbsp;(<font color='red'>*</font>)";
%>
<script>

    checkValidate = function(){        
        var stockModelCode = document.getElementById("goodsForm.stockModelCode");
        var stateId = document.getElementById("stateId");
        var quantity = document.getElementById("quantity");
        var shopImportedCode= document.getElementById("exportStockForm.shopCode");        
        if(shopImportedCode.value==null || shopImportedCode.value ==''){
            $('returnMsgGoodsClient').innerHTML= '<s:text name="ERR.STK.005"/>';
            //$('returnMsgGoodsClient').innerHTML="Chưa chọn kho nhận hàng"
            shopImportedCode.focus();
            return false;
        }
        
        if(stockModelCode.value==null || trim(stockModelCode.value) ==''){
            $('returnMsgGoodsClient').innerHTML= '<s:text name="ERR.STK.008"/>';
            //$('returnMsgGoodsClient').innerHTML="Chưa chọn mặt hàng"
            stockModelCode.focus();
            return false;
        }

        if(stateId.value==null || stateId.value ==''){
            $('returnMsgGoodsClient').innerHTML= '<s:text name="ERR.STK.009"/>';
            //$('returnMsgGoodsClient').innerHTML="Chưa chọn trạng thái hàng"
            stateId.focus();
            return false;
        }
        if(quantity.value==null || trim(quantity.value) ==''){
            $('returnMsgGoodsClient').innerHTML= '<s:text name="ERR.STK.010"/>';
            //$('returnMsgGoodsClient').innerHTML="Chưa nhập số lượng"
            quantity.focus();
            return false;
        }
        if(!isInteger(trim(quantity.value)) || quantity. value.trim()==0){
            $('returnMsgGoodsClient').innerHTML= '<s:text name="ERR.STK.011"/>';
            //$('returnMsgGoodsClient').innerHTML="Số lượng phải là số nguyên dương"
            quantity.focus();
            return false;
        }

        quantity.value=quantity.value.trim();
        $('note').value=$('note').value.trim();
        
        return true;
    }
    //Vunt
    dojo.event.topic.subscribe("exportStockToRecover/add", function(event, widget){
        if(!checkValidate()){
            event.cancel=true;
            return;
        }
        var shopImportedCode= document.getElementById("exportStockForm.shopCode");
        widget.href = "CommonStockAction!addGoodsRecover.do?shopImportedCode=" + shopImportedCode.value+"&"+token.getTokenParamString();
        
    });
    dojo.event.topic.subscribe("exportStockToRecover/edit", function(event, widget){
        if(!checkValidate()){
            event.cancel=true;
            return;
        }
        var shopImportedCode= document.getElementById("exportStockForm.shopCode");
        widget.href = "CommonStockAction!editGoodsRecover.do?shopImportedCode=" + shopImportedCode.value+"&"+token.getTokenParamString();
    });
    dojo.event.topic.subscribe("exportStockToRecover/cacelEdit", function(event, widget){
        $('quantity').value ='';
        widget.href = "CommonStockAction!cancelEditGoodsRecover.do";
    });

</script>

<tags:imPanel title="MSG.stock.detail.goods">


    <!--Su dung cho truong hop xuat nhap voi nhan vien cho phep nhap serial ngay o buoc tao phieu-->
    <s:hidden name="goodsForm.inputSerial" id="inputSerial"/>
    <s:hidden name="goodsForm.ownerType" id="ownerType"/>
    <s:hidden name="goodsForm.ownerId" id="ownerId"/>
    <s:hidden name="goodsForm.expType" id="goodsForm.expType"/>
    <s:hidden name="goodsForm.canDial" id="goodsForm.canDial"/>
    <s:hidden name="goodsForm.editable" id="goodsForm.editable"/>

    <s:if test="#request.inputSerial !=null">
        <script>
            $('inputSerial').value= <s:property escapeJavaScript="true"  value="#request.inputSerial"/>;
        </script>
    </s:if>

    <div style="height:210px;">

        <table class="inputTbl4Col" >
            <tr>
                <td class="label"><tags:label key="messages.stock.code" required="true"/></td>
                <td class="text" colspan="3">
                    <tags:imSearch codeVariable="goodsForm.stockModelCode" nameVariable="goodsForm.stockModelName"
                                   codeLabel="MSG.GOD.007" nameLabel="MSG.GOD.008"
                                   searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                   searchMethodName="getListStockModel"
                                   getNameMethod="getStockModelName"
                                   postAction="selectStockModel()"
                                   readOnly="${fn:escapeXml(requestScope.edit)}"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.stateId" required="true"/></td>
                <td class="text">
                    <tags:imSelect name="goodsForm.stateId" id="stateId"
                                   list="1:MSG.GOD.169"
                                   disabled="${fn:escapeXml(requestScope.isEdit)}" theme="simple"
                                   cssClass="txtInputFull"
                                   />

                    <s:hidden name="goodsForm.stockModelId" id="stockModelId"/>

                    <%--s:if test="#attr.isEdit == true">
                        <s:hidden name="goodsForm.stockTypeId" id="stockTypeId"/>
                        <s:hidden name="goodsForm.telecomServiceId" id="telecomServiceId"/>

                        <s:hidden name="goodsForm.stateId" id="stateId"/>
                    </s:if--%>
                </td>
                <td class="label">
                    <tags:label key="MSG.quanlity" required="true"/>
                </td>
                <td class="text">
                    <s:textfield name="goodsForm.quantity" id="quantity" cssClass="txtInputFull" maxLength="10" theme="simple"/>
                </td>
            </tr>
            <tr>
                <td class="label" >
                    <tags:label key="MSG.comment" required="true"/>
                </td>
                <td class="label" colspan="3">
                    <s:textfield maxlength="500" name="goodsForm.note" id="note" cssClass="txtInputFull"  theme="simple"/>
                </td>
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <tags:VTsx_submit parseContent="true" executeScripts="true"
                                      formId="goodsForm" loadingText="loading..." showLoadingText="true"
                                      targets="inputGoods"  value="MSG.GOD.019"  beforeNotifyTopics="exportStockToRecover/add"
                                      errorNotifyTopics="errorAction" disabled="${fn:escapeXml(edit)}"
                                      />

                    <tags:VTsx_submit parseContent="true" executeScripts="true" formId="goodsForm"
                                      loadingText="loading..." showLoadingText="true" targets="inputGoods"
                                      value="MSG.GOD.020"  beforeNotifyTopics="exportStockToRecover/edit"
                                      errorNotifyTopics="errorAction" disabled="${fn:escapeXml(nonEdit)}"/>
                    <tags:VTsx_submit parseContent="true" executeScripts="true" formId="goodsForm"
                                      loadingText="loading..." showLoadingText="true" targets="inputGoods"
                                      value="MSG.GOD.018"  beforeNotifyTopics="exportStockToRecover/cacelEdit"
                                      errorNotifyTopics="errorAction" disabled="${fn:escapeXml(nonEdit)}"/>

                    <%--s:url action="ViewStockDetailAction!prepareViewStockDetailRecover" id="URLViewStock" encode="true" escapeAmp="false">
                        <s:param name="stockModelId" value="goodsForm.stockModelId"/>
                    </s:url>

                    <a id="hrefViewStockDetail" href="javascript:viewStockDetail('<s:property escapeJavaScript="true"  value="#attr.URLViewStock"/>')">${fn:escapeXml(imDef:imGetText('MSG.GOD.171'))}</a--%>

                </td>
            </tr>
        </table>
    </div>
</tags:imPanel>

<script>

    viewStockDetail=function(path){
        
        var ownerType="1";
        var shopImportedCode= document.getElementById("exportStockForm.shopCode");                        
        var stockModelId= document.getElementById("stockModelId");   
        //alert(stockModelId.value);
        if (shopImportedCode.value ==''){
            //alert('Bạn phải chọn kho thu hồi để xem hàng tồn kho');
            alert('<s:text name="MSG.STK.002"/>');
        }
        else{
            path=path+"&ownerType=1&ownerCode="+shopImportedCode.value +"&stockModelIdGet="+stockModelId.value ;            
            openDialog(path, 900, 700,false);
        }        
    }
    
    selectStockModel =function(){
        var param =getFormAsString("goodsForm");
        //        alert(param);
        var shopImportedCode= document.getElementById("exportStockForm.shopCode");        
        if (shopImportedCode == null || trim(shopImportedCode.value) == ''){
            $('returnMsgGoodsClient').innerHTML= '<s:text name="ERR.STK.013"/>';
            return;
        }
        //        gotoAction("stockGoods", "CommonStockAction!selectStockModel.do?revokeAgent=true&shopCode="+trim(shopImportedCode.value)+"&ajax=1" +param);
        gotoAction("inputGoods", "CommonStockAction!selectStockModel.do?revokeAgent=true&shopCode="+trim(shopImportedCode.value)+"&ajax=1" +param);
    }
  
</script>
