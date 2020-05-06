<%-- 
    Document   : packageGoodsV2Info
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
        window.opener.reloadSearchPage();
        window.close();
        //refreshPackageGoodsList();
    </script>
</s:if>

<s:form action="packageGoodsV2Action" theme="simple" enctype="multipart/form-data"  method="post" id="packageGoodsV2Form">
<s:token/>

    <tags:imPanel title="L.100024">
        <s:hidden name="packageGoodsV2Form.packageGoodsId" id="packageGoodsV2Form.packageGoodsId"/>
        <table class="inputTbl4Col" >            
            <tr>
                <td><tags:label key="L.100017" required="true"/></td>
                <td>
                    <s:if test="#attr.packageGoodsV2Form.packageGoodsId == null">
                        <s:textfield name="packageGoodsV2Form.code" id="packageGoodsV2Form.code" cssClass="txtInputFull" maxlength="50"/>
                    </s:if>
                    <s:else>
                        <s:textfield name="packageGoodsV2Form.code" id="packageGoodsV2Form.code" cssClass="txtInputFull" maxlength="50" readonly="true"/>
                    </s:else>
                </td>
                <td><tags:label key="L.100018" required="true"/></td>
                <td>
                    <s:textfield name="packageGoodsV2Form.name" id="packageGoodsV2Form.name" cssClass="txtInputFull" maxlength="200"/>
                </td>                    
            </tr>
            <tr>
                <td ><tags:label key="L.100019" required="true"/></td>
                <td>
                    <tags:imSelect name="packageGoodsV2Form.status"
                                   id="packageGoodsV2Form.status"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="L.100025"
                                   list="0:L.100020, 1:L.100021"/>
                </td>
                <td><tags:label key="L.100026"/></td>
                <td>
                    <s:textfield name="packageGoodsV2Form.note" id="packageGoodsV2Form.note" cssClass="txtInputFull" maxlength="200"/>
                </td>                    
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <s:if test="packageGoodsV2Form.packageGoodsId == null">
                        <input type="button" style="width:80px;" onclick="savePackageGoods();"  value="<s:text name="L.100023"/>"/>
                    </s:if>
                    <s:else>
                        <input type="button" style="width:80px;" onclick="savePackageGoods();"  value="<s:text name="L.100027"/>"/>
                    </s:else>
                    <input type="button" style="width:80px;" onclick="cancelPackageGoods();"  value="<s:text name="L.100028"/>"/>
                </td>            
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <tags:displayResult id="returnMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
                <td
        </tr>
    </table>
</tags:imPanel>
</s:form>

<script language="javascript">
    validate = function(){
        if(trim($('packageGoodsV2Form.code').value) == null || trim($('packageGoodsV2Form.code').value) ==""){
            $('returnMsgClient').innerHTML= '<s:text name="E.100011"/>';
            $('packageGoodsV2Form.code').focus();
            return false;
        }
        if(trim($('packageGoodsV2Form.name').value) == null || trim($('packageGoodsV2Form.name').value) ==""){
            $('returnMsgClient').innerHTML= '<s:text name="E.100012"/>';
            $('packageGoodsV2Form.name').focus();
            return false;
        }
        if(trim($('packageGoodsV2Form.code').value) == null || trim($('packageGoodsV2Form.code').value).length > 50){
            $('returnMsgClient').innerHTML= '<s:text name="E.100014"/>';
            $('packageGoodsV2Form.code').focus();
            return false;
        }
        if(trim($('packageGoodsV2Form.name').value) == null || trim($('packageGoodsV2Form.name').value).length > 200){
            $('returnMsgClient').innerHTML= '<s:text name="E.100015"/>';
            $('packageGoodsV2Form.name').focus();
            return false;
        }
        if(trim($('packageGoodsV2Form.note').value) == null || trim($('packageGoodsV2Form.note').value).length > 200){
            $('returnMsgClient').innerHTML= '<s:text name="E.100016"/>';
            $('packageGoodsV2Form.note').focus();
            return false;
        }
        
        if(trim($('packageGoodsV2Form.code').value).length > 50){
            $('returnMsgClient').innerHTML= '<s:text name="E.100014"/>';
            $('packageGoodsV2Form.code').focus();
            return false;
        }
        if(trim($('packageGoodsV2Form.name').value).length > 200){
            $('returnMsgClient').innerHTML= '<s:text name="E.100015"/>';
            $('packageGoodsV2Form.name').focus();
            return false;
        }
        if(trim($('packageGoodsV2Form.note').value).length > 200){
            $('returnMsgClient').innerHTML= '<s:text name="E.100016"/>';
            $('packageGoodsV2Form.note').focus();
            return false;
        }
        
        if (isHtmlTagFormat(trim($('packageGoodsV2Form.code').value))){
            $('returnMsgClient').innerHTML= '<s:text name="E.100017"/>';
            $('packageGoodsV2Form.code').focus();
            return false;
        }
        if (isHtmlTagFormat(trim($('packageGoodsV2Form.name').value))){
            $('returnMsgClient').innerHTML= '<s:text name="E.100018"/>';
            $('packageGoodsV2Form.name').focus();
            return false;
        }
        if (isHtmlTagFormat(trim($('packageGoodsV2Form.note').value))){
            $('returnMsgClient').innerHTML= '<s:text name="E.100019"/>';
            $('packageGoodsV2Form.note').focus();
            return false;
        }
        if(trim($('packageGoodsV2Form.status').value) == null || trim($('packageGoodsV2Form.status').value) ==""){
            $('returnMsgClient').innerHTML= '<s:text name="E.100013"/>';
            $('returnMsgClient').focus();
            return false;
        }
        
        return true;
    }
    savePackageGoods = function (){
        if (!validate()) {
            return;
        }
        var strConfirm = "";
        if(trim($('packageGoodsV2Form.packageGoodsId').value) == null || trim($('packageGoodsV2Form.packageGoodsId').value) ==""){
            strConfirm = getUnicodeMsg('<s:text name="C.100001"/>');
        }else{
            strConfirm = getUnicodeMsg('<s:text name="C.100002"/>');
        }
        if (!confirm(strConfirm)) {
            return;
        }
        
        document.getElementById("packageGoodsV2Form").action="packageGoodsV2Action!savePackageGoods.do?" + token.getTokenParamString();
        document.getElementById("packageGoodsV2Form").submit();
    }    
    cancelPackageGoods = function (){
        window.opener.reloadSearchPage();
        window.close();
        //document.getElementById("packageGoodsV2Form").action="packageGoodsV2Action!cancelPackageGoods.do";
        //document.getElementById("packageGoodsV2Form").submit();
    }    
</script>
