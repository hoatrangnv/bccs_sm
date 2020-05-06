<%-- 
    Document   : assignDslamAreaOverview
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

<%--<tags:imPanel title="Loại dịch vụ">
    <div align="center">
        <s:select name="chassicForm.status" id="status"
                              cssClass="txtInputFull" disabled="false"
                              list="#{'1':'Dịch vụ ADSL',
                                      '6':'Dịch vụ PSTN'}" theme="simple"
                              />
    </div>
</tags:imPanel>--%>




<table style="width:100%; background-color:#F5F5F5;">    
    <tr>
        <td style="width:250px; vertical-align:top">
            <tags:imPanel title="MSG.dslam.list">

                <div align="left" style="width:250px; height:580px; overflow:scroll; margin-left:5px; margin-top:5px;">
                    <tags:treeAjax
                        actionParamName="nodeId"
                        getNodeAction="${contextPath}/assignDslamAreaAction!getListAreaTree.do"
                        rootId="0_"
                        rootTitle="MSG.province.list"
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
                <jsp:include page="assignDslamAreaListArea.jsp"/>
            </sx:div>
        </td>
    </tr>    
</table>



<script type="text/javascript" language="javascript">

    checkValidate = function(){
        if (trim($('addDslamAreaForm.dslamId').value) == ""){
//            $('returnMsgClient').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.028')"/>';
            $('returnMsgClient').innerHTML = '<s:text name="ERR.INF.028"/>';
            return false;
        }
        if (trim($('addDslamAreaForm.provinceCode').value) == ""){
//            $('returnMsgClient').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.029')"/>';
            $('returnMsgClient').innerHTML =  '<s:text name="ERR.INF.029"/>';
            return false;
        }
        if (trim($('addDslamAreaForm.districtCode').value) == ""){
//            $('returnMsgClient').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.030')"/>';
            $('returnMsgClient').innerHTML =  '<s:text name="ERR.INF.030"/>';
            return false;
        }
        if (trim($('addDslamAreaForm.precinctCode').value) == ""){
//            $('returnMsgClient').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.031')"/>';
            $('returnMsgClient').innerHTML =  '<s:text name="ERR.INF.031"/>';
            return false;
        }
        
        return true;
    }

    deleteDslamArea = function(areaCode) {
        var dslamId = trim($('addDslamAreaForm.dslamId').value);
        if (dslamId == ""){
//            alert('<s:property escapeJavaScript="true"  value="getError('ERR.INF.028')"/>');
            alert('<s:text name="ERR.INF.028"/>');
            return false;
        }        
//        if(confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.00010')"/>'))){
        if(confirm(getUnicodeMsg('<s:text name="MSG.INF.00010"/>'))){
            gotoAction('divDisplayInfo','assignDslamAreaAction!deleteDslamArea.do?dslamId=' + dslamId + "&"+ areaCode + '&' + token.getTokenParamString());
        }
    }
    
</script>
