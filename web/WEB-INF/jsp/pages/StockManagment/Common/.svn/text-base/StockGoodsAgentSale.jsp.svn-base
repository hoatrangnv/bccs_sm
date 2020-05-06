<%-- 
    Document   : Mat hang ban dut
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



    com.viettel.database.DAO.BaseDAOAction baseDAOAction = new com.viettel.database.DAO.BaseDAOAction();

    boolean isEdit = false;
    boolean notEdit = true;
    boolean revoke = false;

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
    String collaborator = (String) request.getAttribute("collaborator");
    request.setAttribute("collaborator", collaborator);
    request.setAttribute("require", require);

    String combo_header_telecom_service = baseDAOAction.getText("MSG.GOD.166");
    request.setAttribute("combo_header_telecom_service", combo_header_telecom_service);

    String combo_header_stock_type = baseDAOAction.getText("MSG.GOD.168");
    request.setAttribute("combo_header_stock_type", combo_header_stock_type);

    String combo_header_stock_model = baseDAOAction.getText("MSG.GOD.167");
    request.setAttribute("combo_header_stock_model", combo_header_stock_model);

%>
<script>    
    
    checkValidate = function(){
        
        var stockTypeId = document.getElementById("stockTypeId");
        var telecomserviceID = document.getElementById("telecomServiceId");
        //        var stockModelId = document.getElementById("stockModelId");
        var stateId = document.getElementById("stateId");
        var quantity = document.getElementById("quantity");
        quantity.value = quantity.value.replace(/\,/g,""); //loai bo dau , trong chuoi
        var shopImportedId= document.getElementById("exportStockForm.shopImportedId");
        var shopImportedCode= document.getElementById("exportStockForm.shopImportedCode");

        //kiem tra ma mat hang ko duoc de trong
        if(trim($('goodsForm.stockModelCode').value) == "") {
            $('returnMsgGoodsClient').innerHTML = '<s:text name="MSG.GOD.035"/>';
            $('goodsForm.stockModelCode').select();
            $('goodsForm.stockModelCode').focus();
            return false;
        }

        //truong ma mat hang khong duoc chua cac ky tu dac biet
        if(!isValidInput($('goodsForm.stockModelCode').value, false, false)) {
            $('returnMsgGoodsClient' ).innerHTML = '<s:text name="MSG.GOD.038"/>';
            $('goodsForm.stockModelCode').select();
            $('goodsForm.stockModelCode').focus();
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
        if(!checkValidate()){
            event.cancel=true;
        }
        //alert('123');
        $('quantity').value=$('quantity').value.trim();    
        widget.href = "CommonStockAction!addGoodsAgentSale.do?"+token.getTokenParamString();
    });
    
    // DatTV -- Sửa hàng hóa vừa thêm
    dojo.event.topic.subscribe("ExportStockForm/edit", function(event, widget){
        if(!checkValidate()){
            event.cancel=true;
        }
        $('quantity').value=$('quantity').value.trim();    
        widget.href = "CommonStockAction!editGoodsAgentSale.do?"+token.getTokenParamString();
    });
    
    dojo.event.topic.subscribe("ExportStockForm/cacelEdit", function(event, widget){
        $('quantity').value ='';    
        widget.href = "CommonStockAction!cancelEditGoodsAgentSale.do";
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

        <table class="inputTbl4Col">
            <tr>
                <td class="label">
                    <tags:label key="MSG.goods"/>
                    <%--s:hidden name="goodsForm.telecomServiceId" id="telecomServiceId"/>
                    <s:hidden name="goodsForm.stockTypeName" id="stockTypeName"/--%>
                </td>
                <td class="text" colspan="3">
                    <!--chi chon hang hoa trong kho dai ly-->
                    <!--staff_export_type = staff_export_channel-->
                    <tags:imSearch codeVariable="goodsForm.stockModelCode" nameVariable="goodsForm.stockModelName"
                                   codeLabel="MSG.GOD.007" nameLabel="MSG.GOD.008"
                                   searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                   searchMethodName="getListStockModel"
                                   getNameMethod="getStockModelName"
                                   readOnly="${fn:escapeXml(requestScope.edit)}"/>
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
                <td class="label"><tags:label key="MSG.stateId"/> </td>
                <td class="text">
                    <tags:imSelect name="goodsForm.stateId" id="stateId"
                                   headerKey="1" headerValue="MSG.GOD.169"
                                   list="3:MSG.GOD.170"
                                   disabled="${fn:escapeXml(edit)}" theme="simple"
                                   cssClass="txtInput"/>
                    <s:if test="#attr.edit == 'true'">
                        <s:hidden name="goodsForm.stockTypeId" id="stockTypeId"/>
                        <s:hidden name="goodsForm.telecomServiceId" id="telecomServiceId"/>
                        <s:hidden name="goodsForm.stockModelId" id="stockModelId"/>
                        <%--<s:hidden name="goodsForm.stateId" id="stateId"/>--%>
                        <!--s:hidden name="goodsForm.checkDial" id="checkDial"/-->
                    </s:if>
                </td>
                <td class="label">
                    <tags:label key="MSG.quanlity"/>
                </td>
                <td class="text">
                    <s:textfield name="goodsForm.quantity" id="quantity" cssClass="txtInputFull" maxLength="15" theme="simple" onkeyup="textFieldNF(this);" />
                </td>
            </tr>
            <tr>
                <td class="label">
                    <tags:label key="MSG.comment"/>
                </td>
                <td colspan="3" class="text">
                    <s:textfield maxlength="500" name="goodsForm.note" id="note" cssClass="txtInputFull"  theme="simple"/>
                </td>
            </tr>
            <tr>
                <td colspan="4" align="center" class="text">
                    <tags:VTsx_submit parseContent="true" executeScripts="true"
                                      formId="goodsForm" loadingText="loading..." showLoadingText="true"
                                      targets="inputGoods"  value="MSG.GOD.019"  beforeNotifyTopics="ExportStockForm/add"
                                      errorNotifyTopics="errorAction" disabled="${fn:escapeXml(edit)}"
                                      />
                    <tags:VTsx_submit parseContent="true" executeScripts="true" formId="goodsForm"
                                      loadingText="loading..." showLoadingText="true" targets="inputGoods"
                                      value="MSG.GOD.020"  beforeNotifyTopics="ExportStockForm/edit"
                                      errorNotifyTopics="errorAction" disabled="${fn:escapeXml(nonEdit)}"/>
                    <tags:VTsx_submit parseContent="true" executeScripts="true" formId="goodsForm"
                                      loadingText="loading..." showLoadingText="true" targets="inputGoods"
                                      value="MSG.GOD.018"  beforeNotifyTopics="ExportStockForm/cacelEdit"
                                      errorNotifyTopics="errorAction" disabled="${fn:escapeXml(nonEdit)}"/>


                    <s:url action="ViewStockDetailAction!prepareViewStockDetail" id="URLViewStock" encode="true" escapeAmp="false">
                        <s:param name="ownerType" value="goodsForm.ownerType"/>
                        <s:param name="ownerId" value="goodsForm.ownerId"/>
                        <s:param name="stockModelId" value="goodsForm.stockModelId"/>
                    </s:url>
                    <input type="button" onclick="viewStockDetail('<s:property escapeJavaScript="true"  value="#attr.URLViewStock"/>')" value='<s:property escapeJavaScript="true"  value="getText('MSG.GOD.171')"/>'/>
                    <%--a id="hrefViewStockDetail" href="javascript:viewStockDetail('<s:property escapeJavaScript="true"  value="#attr.URLViewStock"/>')"> Xem kho</a--%>
                    <s:if test="#attr.collaborator =='coll'">
                        <s:url action="exportStockToCollaboratorAction!prepareViewAccountAgent" id="URLViewAccountAgent" encode="true" escapeAmp="false">
                        </s:url>
                        <input type="button" onclick="viewAccountAgentDetail('<s:property escapeJavaScript="true"  value="#attr.URLViewAccountAgent"/>')" value='<s:property escapeJavaScript="true"  value="getText('MSG.GOD.172')"/>'/>
                    </s:if>
                    <s:else>
                        <s:if test="#request.revokeColl !='true'">
                            <s:url action="exportStockToCollaboratorAction!prepareViewAccountAgent" id="URLViewAccountShop" encode="true" escapeAmp="false">
                            </s:url>
                            <input type="button" onclick="viewAccountAgentShop('<s:property escapeJavaScript="true"  value="#attr.URLViewAccountShop"/>')" value='<s:property escapeJavaScript="true"  value="getText('MSG.GOD.172')"/>' />
                        </s:if>
                    </s:else>
                </td>
            </tr>
        </table>
    </div>
</tags:imPanel>
<script>

    textFieldNF($('quantity'));

    if($('goodsForm.stockModelCode').value == null || $('goodsForm.stockModelCode').value == "") {
        $('goodsForm.stockModelCode').select();
        $('goodsForm.stockModelCode').focus();
    } else {
        $('goodsForm.stockModelName').focus();
    }

    viewStockDetail=function(path){
        openDialog(path, 900, 700,false);
    }
    viewAccountAgentDetail=function(path){        
        var shopImportedCode= document.getElementById("exportStockForm.shopImportedCode");        
        //path=path+"&accountType=2&accountCode="+shopImportedCode.value
        path=path+"?accountType=2&accountCode="+shopImportedCode.value;
        openDialog(path, 900, 700,false);
    }
    viewAccountAgentShop =function(path){        
        var shopImportedCode= document.getElementById("exportStockForm.shopImportedCode");
        //path=path+"&accountType=2&accountCode="+shopImportedCode.value
        path=path+"?accountType=1&accountCode="+shopImportedCode.value;        
        openDialog(path, 900, 700,false);        
    }
    selectStockType= function (source, des, path){
        var param=getFormAsString("goodsForm");
        path=path +"?param=1"+param;
        // alert(path);
        updateCombo(source,des,path);
    }
    
    
    selectStockModel =function(){
    <s:if test="#request.revokeColl =='true'">
            gotoAction("stockGoods", "CommonStockAction!selectStockModel.do?revokeColl=true" ,"goodsForm");
    </s:if>
    <s:else>
            gotoAction("stockGoods", "CommonStockAction!selectStockModel.do" ,"goodsForm");
    </s:else>
        }
        
        
        selectStockModelColl =function(){
            //var param =getFormAsString("goodsForm");
            //alert(param);
            gotoAction("stockGoods", "CommonStockAction!selectStockModelColl.do" ,"goodsForm");

        }

</script>
