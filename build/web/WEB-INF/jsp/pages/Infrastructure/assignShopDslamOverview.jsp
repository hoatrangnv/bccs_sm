<%-- 
    Document   : assignShopDslamOverview
    Created on : May 14, 2010, 12:00:00 AM
    Author     : TrongLV
--%> 

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<table style="width:100%; background-color:#F5F5F5;">
    <tr>
        <td style="width:250px; vertical-align:top">
            <tags:imPanel title="MSG.shopid.list">

                <div align="left" style="width:250px; height:580px; overflow:scroll; margin-left:5px; margin-top:5px;">
                    <tags:treeAjax
                        actionParamName="nodeId"
                        getNodeAction="${contextPath}/assignShopDslamAction!getListShopTree.do"
                        rootId="0_"
                        rootTitle="MSG.unit.list"
                        containerId="container111"
                        containerTextId="holder111"
                        loadingText="Loading..."
                        treeId="tree111"
                        target="divDisplayInfo"
                        lazyLoad="false"/>
                </div>
            </tags:imPanel>
        </td>
        <td style="width:10px"></td>
        <td style="vertical-align:top">
            <sx:div id="divDisplayInfo">
                <jsp:include page="assignShopDslamListDslam.jsp"/>
            </sx:div>
        </td>
    </tr>
</table>



<script type="text/javascript" language="javascript">

    checkValidate = function(){
        if (trim($('addShopDslamForm.shopId').value) == ""){
//            $('returnMsgClient').innerHTML = '<s_:property value="getError('ERR.INF.032')" />';
            $('returnMsgClient').innerHTML = '<s:text name = "ERR.INF.032" />';
            return false;
        }
        if (trim($('addShopDslamForm.dslamCode').value) == ""){
//            $('returnMsgClient').innerHTML =  '<s_:property value="getError('ERR.INF.033')" />';
            $('returnMsgClient').innerHTML =  '<s:text name="ERR.INF.033" />';
            return false;
        }

        return true;
    }

    deleteShopDslam = function(dslamId) {
        var shopId = trim($('addShopDslamForm.shopId').value);
        if (shopId == ""){
            alert('<s:property escapeJavaScript="true"  value="getError('ERR.INF.032')" />');
            return false;
        }
        if(confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.00011')" />'))){
            gotoAction('divDisplayInfo','assignShopDslamAction!deleteShopDslam.do?shopId=' + shopId + '&' + dslamId + '&' + token.getTokenParamString());
        }
    }

</script>
