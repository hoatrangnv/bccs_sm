<%-- 
    Document   : replaceGoodsManagement
    Created on : Nov 4, 2011, 11:31:01 AM
    Author     : haint
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>

<s:if test="#request.result == true ">
    <script type="text/javascript" language="javascript">
        window.close();
        //refreshPackageGoodsList();
    </script>
</s:if>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="L.100024">

    <s:form action="packageGoodsV2Action" theme="simple" enctype="multipart/form-data"  method="post" id="packageGoodsReplaceForm">
<s:token/>


        <s:hidden name="packageGoodsReplaceForm.packageGoodsDetailId" id="packageGoodsReplaceForm.packageGoodsDetailId"/>        
        <s:hidden name="packageGoodsReplaceForm.packageGoodsId" id="packageGoodsReplaceForm.packageGoodsId"/>        

        <div class="divHasBorder">
            <table class="inputTbl2Col" >
                <tr>
                    <td><tags:label key="L.100033" required="true"/></td>
                    <td>
                        <tags:imSearch codeVariable="packageGoodsReplaceForm.stockModelCode" nameVariable="packageGoodsReplaceForm.stockModelName"
                                       codeLabel="L.100033" nameLabel="L.100034"
                                       searchClassName="com.viettel.im.database.DAO.PackageGoodsV2DAO"
                                       searchMethodName="getListStockModelReplace"
                                       getNameMethod="getStockModelReplaceName"
                                       otherParam="packageGoodsReplaceForm.packageGoodsDetailId"
                                       readOnly="false"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="L.100026"/></td>
                    <td colspan="3">
                        <s:textfield name="packageGoodsReplaceForm.note" id="packageGoodsReplaceForm.note" cssClass="txtInputFull" maxlength="200"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="4" align="center">
                        <input type="button" style="width:80px;" onclick="savePackageGoodsReplace();"  value="<s:text name="L.100023"/>"/>
                        <input type="button" style="width:80px;" onclick="cancelPackageGoodsReplace();"  value="<s:text name="L.100028"/>"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="4" align="center">
                        <tags:displayResult id="returnMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
                    </td>
                </tr>
            </table>
        </div>

        <div style="width:100%;" id="divPackageGoodsReplaceList">
            <jsp:include page="packageGoodsReplaceList.jsp"/>
        </div>

    </s:form>


</tags:imPanel>

<script language="javascript">
    validateSavePackageGoodsReplace = function(){
        //check null
        if(($('packageGoodsReplaceForm.stockModelCode').value) == null || trim($('packageGoodsReplaceForm.stockModelCode').value) ==""){
            $('returnMsgClient').innerHTML= '<s:text name="ERR.PACKAGE.GOODS.006"/>';
            $('packageGoodsReplaceForm.stockModelCode').focus();
            return false;
        }        
        if(($('packageGoodsReplaceForm.stockModelName').value) == null || trim($('packageGoodsReplaceForm.stockModelName').value) ==""){
            $('returnMsgClient').innerHTML= '<s:text name="E.100012"/>';
            $('packageGoodsReplaceForm.stockModelName').focus();
            return false;
        }
        //check do dai
        if(trim($('packageGoodsReplaceForm.stockModelCode').value).length > 50){
            $('returnMsgClient').innerHTML= '<s:text name="E.100014"/>';
            $('packageGoodsReplaceForm.stockModelCode').focus();
            return false;
        }        
        if($('packageGoodsReplaceForm.note').value != null && trim($('packageGoodsReplaceForm.note').value).length > 200){
            $('returnMsgClient').innerHTML= '<s:text name="E.100016"/>';
            $('packageGoodsReplaceForm.note').focus();
            return false;
        }       
        
        //check ky tu dac biet
        if (isHtmlTagFormat(trim($('packageGoodsReplaceForm.stockModelCode').value))){
            $('returnMsgClient').innerHTML= '<s:text name="E.100017"/>';
            $('packageGoodsReplaceForm.stockModelCode').focus();
            return false;
        }               
        
        return true;
    }
    savePackageGoodsReplace = function (){
        if (!validateSavePackageGoodsReplace()) {
            return;
        }
        var strConfirm = strConfirm = getUnicodeMsg('<s:text name="C.100005"/>');
        if (!confirm(strConfirm)) {
            return;
        }        
        document.getElementById("packageGoodsReplaceForm").action="packageGoodsV2Action!savePackageGoodsReplace.do?" + token.getTokenParamString();
        document.getElementById("packageGoodsReplaceForm").submit();
    }    
    cancelPackageGoodsReplace = function (){
        window.close();
    }
    
    deletePackageGoodsReplace = function(packageGoodsReplaceId){       
        //$('returnMsgClient2').innerHTML= '';
        var strConfirm = strConfirm = getUnicodeMsg('<s:text name="C.100006"/>');
        if (!confirm(strConfirm)) {
            return;
        }
        document.getElementById("packageGoodsReplaceForm").action="packageGoodsV2Action!deletePackageGoodsReplace.do?packageGoodsReplaceId="+packageGoodsReplaceId + "&" + token.getTokenParamString();
        document.getElementById("packageGoodsReplaceForm").submit();
        
    }
</script>
