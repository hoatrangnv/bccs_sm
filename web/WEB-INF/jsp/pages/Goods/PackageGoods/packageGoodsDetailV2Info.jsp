<%-- 
    Document   : packageGoodsDetailV2Info
    Created on : Aug 8, 2011, 4:58:43 PM
    Author     : kdvt_tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>

<s:if test="#request.result == true ">
    <script type="text/javascript" language="javascript">
        //alert('<s:property escapeJavaScript="true"  value="packageGoodsDetailV2Form.packageGoodsId"/>');
        window.opener.reloadPackageGoodsDetailList('<s:property escapeJavaScript="true"  value="packageGoodsDetailV2Form.packageGoodsId"/>');
        window.close();
        //refreshPackageGoodsList();
    </script>
</s:if>

<s:form action="packageGoodsV2Action" theme="simple" enctype="multipart/form-data"  method="post" id="packageGoodsDetailV2Form">
<s:token/>

    <tags:imPanel title="L.100024">
        <s:hidden name="packageGoodsDetailV2Form.packageGoodsId" id="packageGoodsDetailV2Form.packageGoodsId"/>
        <s:hidden name="packageGoodsDetailV2Form.packageGoodsDetailId" id="packageGoodsDetailV2Form.packageGoodsDetailId"/>
        <table class="inputTbl4Col" >            
            <tr>
                <td><tags:label key="L.100033" required="true"/></td>
                <td colspan="3">
                    <s:if test="#attr.packageGoodsDetailV2Form.packageGoodsDetailId == null ">
                        <tags:imSearch codeVariable="packageGoodsDetailV2Form.stockModelCode" nameVariable="packageGoodsDetailV2Form.stockModelName"
                                       codeLabel="L.100033" nameLabel="L.100034"
                                       searchClassName="com.viettel.im.database.DAO.PackageGoodsV2DAO"
                                       searchMethodName="getListStockModel"
                                       getNameMethod="getStockModelName"
                                       readOnly="false"/>
                    </s:if>
                    <s:else>   
                        <tags:imSearch codeVariable="packageGoodsDetailV2Form.stockModelCode" nameVariable="packageGoodsDetailV2Form.stockModelName"
                                       codeLabel="L.100033" nameLabel="L.100034"
                                       searchClassName="com.viettel.im.database.DAO.PackageGoodsV2DAO"
                                       searchMethodName="getListStockModel"
                                       getNameMethod="getStockModelName"
                                       readOnly="true"/>
                    </s:else>
                </td>
            </tr>
            <tr>
                <td><tags:label key="L.100026"/></td>
                <td colspan="3">
                    <s:textfield name="packageGoodsDetailV2Form.note" id="packageGoodsDetailV2Form.note" cssClass="txtInputFull" maxlength="200"/>
                </td>
            </tr>
            <tr>
                <td ><tags:label key="L.100035"/></td>
                <td colspan="1">
                    <s:checkbox name="packageGoodsDetailV2Form.checkRequired" id="packageGoodsDetailV2Form.checkRequired"/>
                </td>
            </tr>    
            <tr>
                <!-- haint41 4/11/2011 : them truong check replace -->
                <td><tags:label key="L.100040"/></td>
                <td colspan="1">
                    <s:checkbox name="packageGoodsDetailV2Form.checkReplaced" id="packageGoodsDetailV2Form.checkReplaced"/>
                </td>
                <!-- end haint41 -->
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <s:if test="packageGoodsDetailV2Form.packageGoodsDetailId == null">
                        <input type="button" style="width:80px;" onclick="savePackageGoodsDetail();"  value="<s:text name="L.100023"/>"/>
                    </s:if>
                    <s:else>
                        <input type="button" style="width:80px;" onclick="savePackageGoodsDetail();"  value="<s:text name="L.100027"/>"/>
                    </s:else>
                    <input type="button" style="width:80px;" onclick="cancelPackageGoodsDetail();"  value="<s:text name="L.100028"/>"/>
                </td>            
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <tags:displayResult id="returnMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
                </td>
        </tr>
    </table>
</tags:imPanel>
</s:form>

<script language="javascript">
    validateSavePackageGoodsDetail = function(){
        //check null
        if(($('packageGoodsDetailV2Form.stockModelCode').value) == null || trim($('packageGoodsDetailV2Form.stockModelCode').value) ==""){
            $('returnMsgClient').innerHTML= '<s:text name="E.100011"/>';
            $('packageGoodsDetailV2Form.stockModelCode').focus();
            return false;
        }        
        if(($('packageGoodsDetailV2Form.stockModelName').value) == null || trim($('packageGoodsDetailV2Form.stockModelName').value) ==""){
            $('returnMsgClient').innerHTML= '<s:text name="E.100012"/>';
            $('packageGoodsDetailV2Form.stockModelName').focus();
            return false;
        }
        //check do dai
        if(trim($('packageGoodsDetailV2Form.stockModelCode').value).length > 50){
            $('returnMsgClient').innerHTML= '<s:text name="E.100014"/>';
            $('packageGoodsDetailV2Form.stockModelCode').focus();
            return false;
        }        
        if($('packageGoodsDetailV2Form.note').value != null && trim($('packageGoodsDetailV2Form.note').value).length > 200){
            $('returnMsgClient').innerHTML= '<s:text name="E.100016"/>';
            $('packageGoodsDetailV2Form.note').focus();
            return false;
        }       
        
        //check ky tu dac biet
        if (isHtmlTagFormat(trim($('packageGoodsDetailV2Form.stockModelCode').value))){
            $('returnMsgClient').innerHTML= '<s:text name="E.100017"/>';
            $('packageGoodsDetailV2Form.stockModelCode').focus();
            return false;
        }        
        if ($('packageGoodsDetailV2Form.note').value != null && isHtmlTagFormat(trim($('packageGoodsDetailV2Form.note').value))){
            $('returnMsgClient').innerHTML= '<s:text name="E.100019"/>';
            $('packageGoodsDetailV2Form.note').focus();
            return false;
        }        
        
        return true;
    }
    savePackageGoodsDetail = function (){
        if (!validateSavePackageGoodsDetail()) {
            return;
        }
        var strConfirm = "";
        if(($('packageGoodsDetailV2Form.packageGoodsDetailId').value) == null || trim($('packageGoodsDetailV2Form.packageGoodsDetailId').value) ==""){
            strConfirm = getUnicodeMsg('<s:text name="C.100001"/>');
        }else{
            strConfirm = getUnicodeMsg('<s:text name="C.100002"/>');
        }
        if (!confirm(strConfirm)) {
            return;
        }
        document.getElementById("packageGoodsDetailV2Form").action="packageGoodsV2Action!savePackageGoodsDetail.do?" + token.getTokenParamString();
        document.getElementById("packageGoodsDetailV2Form").submit();
    }    
    cancelPackageGoodsDetail = function (){
        window.close();
        //document.getElementById("packageGoodsDetailV2Form").action="packageGoodsV2Action!cancelPackageGoods.do";
        //document.getElementById("packageGoodsDetailV2Form").submit();
    }    
</script>
