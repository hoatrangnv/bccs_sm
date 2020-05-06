<%-- 
    Document   : ListResource
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
            boolean isEdit = false;
            boolean notEdit = true;
            if (request.getSession().getAttribute("isEdit") != null) {
                isEdit = Boolean.parseBoolean(request.getSession().getAttribute("isEdit").toString());
            }
            notEdit = !isEdit;
            String nonEdit = new Boolean(notEdit).toString();
            String edit = new Boolean(isEdit).toString();
            request.setAttribute("edit", edit);
            request.setAttribute("nonEdit", nonEdit);
            request.setAttribute("contextPath", request.getContextPath());
            String require = "&nbsp;(<font color='red'>*</font>)";
            String collaborator = (String) request.getAttribute("collaborator");
            request.setAttribute("collaborator", collaborator);
            request.setAttribute("require", require);
%>
<script>

    checkValidate = function(){
        alert('hello you');
        var stockTypeId = document.getElementById("stockTypeId");
        var telecomserviceID = document.getElementById("telecomServiceId");
        var stockModelId = document.getElementById("stockModelId");
        var stateId = document.getElementById("stateId");
        var quantity = document.getElementById("quantity");
        var shopImportedId= document.getElementById("exportStockForm.shopImportedId");
        //alert(shopImportedId.value);
       
        if(shopImportedId.value==null || shopImportedId.value ==''){
            $('returnMsgGoodsClient').innerHTML= '<s:text name="ERR.STK.005"/>';
            //$('returnMsgGoodsClient').innerHTML="Chưa chọn kho nhận hàng"
            shopImportedId.focus();
            return false;
        }
        if(telecomserviceID.value==null || telecomserviceID.value ==''){
            $('returnMsgGoodsClient').innerHTML= '<s:text name="ERR.STK.006"/>';
            //$('returnMsgGoodsClient').innerHTML="Chưa chọn loại dịch vu"
            telecomserviceID.focus();
            return false;
        }
        if(stockTypeId.value==null || stockTypeId.value ==''){
            $('returnMsgGoodsClient').innerHTML= '<s:text name="ERR.STK.007"/>';
            //$('returnMsgGoodsClient').innerHTML="Chưa chọn loại mặt hàng"
            stockTypeId.focus();
            return false;
        }
        
        if(stockModelId.value==null || stockModelId.value ==''){
            $('returnMsgGoodsClient').innerHTML= '<s:text name="ERR.STK.008"/>';
            //$('returnMsgGoodsClient').innerHTML="Chưa chọn mặt hàng"
            stockModelId.focus();
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
        if(isHtmlTagFormat($('note').value)){
            $('returnMsgGoodsClient').innerHTML= '<s:text name="ERR.STK.012"/>';
            //$('returnMsgGoodsClient').innerHTML='Trường ghi chú không được nhập thẻ HTML';
            $('note').focus();
            return false;
        }
        $('note').value=$('note').value.trim();
        return true;
    }
    //đổi hướng action sang action khác
    dojo.event.topic.subscribe("ExportStockForm/add", function(event, widget){
        alert('CommonStockAction!addGoodsColl.do');
        if(!checkValidate()){
            event.cancel=true;
        }
        $('quantity').value=$('quantity').value.trim();
        widget.href = "CommonStockAction!addGoodsColl.do?"+token.getTokenParamString();
    });
            
    // DatTV -- Sửa hàng hóa vừa thêm
    dojo.event.topic.subscribe("ExportStockForm/edit", function(event, widget){
        if(!checkValidate()){
            event.cancel=true;
        }
        $('quantity').value=$('quantity').value.trim();
        widget.href = "CommonStockAction!editGoods.do?"+token.getTokenParamString();
    });
    dojo.event.topic.subscribe("ExportStockForm/cacelEdit", function(event, widget){
        $('quantity').value ='';
        widget.href = "CommonStockAction!cancelEditGoods.do";
    });
    //Vunt
    dojo.event.topic.subscribe("exportStockToCollaboratorAction/add", function(event, widget){
        if(!checkValidate()){
            event.cancel=true;
        }
        $('quantity').value=$('quantity').value.trim();
        var shopImportedId= document.getElementById("importStockForm.toOwnerId");
        alert("CommonStockAction!addGoodsColl.do?shopImportedId=" + shopImportedId.value);
        widget.href = "CommonStockAction!addGoodsColl.do?shopImportedId=" + shopImportedId.value+"&"+token.getTokenParamString();
    });
    dojo.event.topic.subscribe("exportStockToCollaboratorAction/edit", function(event, widget){
        if(!checkValidate()){
            event.cancel=true;
        }
        $('quantity').value=$('quantity').value.trim();
        var shopImportedId= document.getElementById("importStockForm.toOwnerId");
        widget.href = "CommonStockAction!editGoodsColl.do?shopImportedId=" + shopImportedId.value+"&"+token.getTokenParamString();
    });
    dojo.event.topic.subscribe("exportStockToCollaboratorAction/cacelEdit", function(event, widget){
        $('quantity').value ='';
        widget.href = "CommonStockAction!cancelEditGoodsColl.do";
    });

</script>


<tags:imPanel title="MSG.stock.detail.goods">
    <!--Su dung cho truong hop xuat nhap voi nhan vien cho phep nhap serial ngay o buoc tao phieu-->
    <s:hidden name="goodsForm.inputSerial" id="inputSerial"/>
    <s:hidden name="goodsForm.ownerType" id="ownerType" value="2"/>
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

        <table class="inputTbl4Col">
            <tr> 
                <td class="label">
                    <tags:label key="MSG.GOD.type"/>
                </td>
                <td class="text">

                    <tags:imSelect name="goodsForm.stockTypeId"
                              id="stockTypeId" theme="simple"
                              cssClass="txtInput"
                              disabled="${fn:escapeXml(attr.edit)}"
                              headerKey="" headerValue="MSG.GOD.168"
                              list="lstStockType"
                              listKey="stockTypeId" listValue="name"
                              onchange="selectStockType('stockTypeId','stockModelId','${request.contextPath}/CommonStockAction!getStockModel.do');"/>
                    <s:hidden name="goodsForm.stockTypeName" id="stockTypeName"/>
                </td>
                <td class="label">
                    <tags:label key="MSG.goods"/>
                </td>
                <td class="text">

                    <tags:imSelect name="goodsForm.stockModelId"
                              id="stockModelId" theme="simple"
                              cssClass="txtInput"
                              disabled="${fn:escapeXml(attr.edit)}"
                              headerKey="" headerValue="MSG.GOD.167"
                              list="lstStockModel"
                              listKey="stockModelId" listValue="name"
                              onchange="selectStockModel()"
                              />

                </td>  
            </tr>
            <s:if test="goodsForm.expType!=null && goodsForm.expType=='dial' && goodsForm.canDial!=null && goodsForm.canDial ==1">
                <tr>
                    <td colspan="4" class="text" ><tags:label key="MSG.GOD.dial"/> &nbsp;&nbsp;
                        <s:checkbox name="goodsForm.checkDial"  theme="simple"/>
                    </td>
                </tr>
            </s:if>
            <tr>
                <td class="label"><tags:label key="MSG.generates.GOD.status"/></td>
                <td class="text">
                    <tags:imSelect name="goodsForm.stateId" id="stateId"
                              list="1:MSG.GOD.169,3:MSG.GOD.170"
                              disabled="%{#attr.edit}" theme="simple"
                              cssClass="txtInput"
                              />
                    <!--headerKey="" headerValue="--Chọn trạng thái hàng--"-->
                    <s:if test="#attr.edit == 'true'">
                        <%--<s:hidden name="goodsForm.stockTypeId" id="stockTypeId"/>--%>
                        <s:hidden name="goodsForm.telecomServiceId" id="telecomServiceId"/>
                        <%--<s:hidden name="goodsForm.stockModelId" id="stockModelId"/>--%>
                        <%--<s:hidden name="goodsForm.stateId" id="stateId"/>--%>
                        <!--s:hidden name="goodsForm.checkDial" id="checkDial"/-->
                    </s:if>
                </td>
                <td class="label">
                    <tags:label key="MSG.quanlity" required="true"/>
                </td>
                <td class="text">
                    <s:textfield name="goodsForm.quantity" id="quantity" cssClass="txtInput" maxLength="10" theme="simple"/>
                </td>
            </tr>
            <tr>
                <td class="label">
                     <tags:label key="MSG.comment"/>
                </td>
                <td colspan="3" class="text">
                    <s:textfield maxlength="500" name="goodsForm.note" id="note" cssClass="txtInput2Col"  theme="simple"/>
                </td>
            </tr>
            <tr>
                <td colspan="4" align="center" class="text">

                    <tags:VTsx_submit parseContent="true" executeScripts="true"
                                      formId="goodsForm" loadingText="loading..." showLoadingText="true"
                                      targets="inputGoods"  value="Thêm"  beforeNotifyTopics="ExportStockForm/add"
                                      errorNotifyTopics="errorAction" disabled="${fn:escapeXml(edit)}"
                                      />

                    <tags:VTsx_submit parseContent="true" executeScripts="true" formId="goodsForm"
                                      loadingText="loading..." showLoadingText="true" targets="inputGoods"
                                      value="Sửa"  beforeNotifyTopics="ExportStockForm/edit"
                                      errorNotifyTopics="errorAction" disabled="${fn:escapeXml(nonEdit)}"/>
                    <tags:VTsx_submit parseContent="true" executeScripts="true" formId="goodsForm"
                                      loadingText="loading..." showLoadingText="true" targets="inputGoods"
                                      value="Bỏ qua"  beforeNotifyTopics="ExportStockForm/cacelEdit"
                                      errorNotifyTopics="errorAction" disabled="${fn:escapeXml(nonEdit)}"/>

                    <s:url action="ViewStockDetailAction!prepareViewStockDetail" id="URLViewStock" encode="true" escapeAmp="false">
                        <%--s:param name="ownerType" value="goodsForm.ownerType"/>
                        <%--s:param name="ownerId" value="goodsForm.ownerId"/>
                        <s:param name="stockModelId" value="goodsForm.stockModelId"/--%>
                    </s:url>

                    <a id="hrefViewStockDetail" href="javascript:viewStockDetail('<s:property escapeJavaScript="true"  value="#attr.URLViewStock"/>')"> Xem kho</a>

                </td>
            </tr>
        </table>
    </div>
</tags:imPanel>
<script>
    viewStockDetail=function(path){        
        var ownerId = document.getElementById('ownerId');
        var stockModelId = document.getElementById('stockModelId');
        var ownerType = document.getElementById('ownerType');
        
        if (ownerId == null || ownerId.value == '') {
            alert('Bạn phải chọn kho thu hồi để xem hàng tồn kho');
            ownerId.focus();
        } else {
            document.getElementById('ownerId').value = ownerId.value;            
            path = path + "?ownerType=" + ownerType.value;
            path = path + "&ownerId=" + ownerId.value;   
            if (stockModelId != null && stockModelId.value != '') {
                path = path + "&stockModelId=" + stockModelId.value;
            }
            
            //alert(path);
            openDialog(path, 900, 700,false);
        }
    }
    selectStockType= function (source, des, path){
        var param=getFormAsString("goodsForm");
        path=path +"?param=1"+param;
        //alert(path);
        updateCombo(source,des,path);
    }
    selectStockModel =function(){
        // var param =getFormAsString("goodsForm");
        //alert(param);
        gotoAction("stockGoods", "CommonStockAction!selectStockModel.do" ,"goodsForm");        

    }
    selectStockModelColl =function(){
        //var param =getFormAsString("goodsForm");
        //alert(param);
        gotoAction("stockGoods", "CommonStockAction!selectStockModelColl.do" ,"goodsForm");

    }

</script>
