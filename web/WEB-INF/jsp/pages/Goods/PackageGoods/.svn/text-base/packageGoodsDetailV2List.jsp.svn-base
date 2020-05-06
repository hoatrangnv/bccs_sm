<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : packageGoodsDetailV2List
    Created on : Aug 8, 2011, 4:44:53 PM
    Author     : kdvt_tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<tags:displayResult id="returnMsgClient2" property="returnMsg2" propertyValue="returnMsgValue2" type="key"/>

<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('L.100036'))}</legend>
    <display:table id="tblPackageGoodsDetailList" name="packageGoodsDetailList" class="dataTable"
                   targets="divPackageGoodsDetailV2List" pagesize="100"
                   requestURI="packageGoodsV2Action!pageNavigator.do"
                   cellpadding="1" cellspacing="1">
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('L.100031'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblPackageGoodsDetailList_rowNum)}</display:column>
        <display:column escapeXml="true" property="stockModelCode" title="${fn:escapeXml(imDef:imGetText('MES.package.goods.001'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center" />
        <display:column escapeXml="true" property="stockModelName" title="${fn:escapeXml(imDef:imGetText('MES.package.goods.002'))}" sortable="false" headerClass="tct" style="width:50px; text-align:left" />
        <display:column escapeXml="true" property="note" title=" ${fn:escapeXml(imDef:imGetText('MES.package.goods.003'))}" sortable="false" headerClass="tct" />
        <display:column title="${fn:escapeXml(imDef:imGetText('MES.package.goods.004'))}" sortable="false" headerClass="tct" style="width:75px; text-align:left">
            <s:if test = "#attr.tblPackageGoodsDetailList.checkRequired*1 == 1">
                <!--                Có bắt buộc-->
                <s:text name="L.100037"/>
            </s:if>
            <s:else>
                <!--                    Không bắt buộc-->
                <s:text name="L.100038"/>
            </s:else>
        </display:column>

        <display:column escapeXml="true" property="createdBy" title=" ${fn:escapeXml(imDef:imGetText('MES.package.goods.005'))}" sortable="false" headerClass="tct" style="text-align:left"/>
        <display:column property="createdDate" title=" ${fn:escapeXml(imDef:imGetText('MES.package.goods.006'))}" sortable="false" headerClass="tct" style="text-align:left" format="{0,date,dd/MM/yyyy}" />
        <display:column escapeXml="true" property="lastUpdatedBy" title=" ${fn:escapeXml(imDef:imGetText('MES.package.goods.007'))}" sortable="false" headerClass="tct" style="text-align:left"/>
        <display:column property="lastUpdatedDate" title=" ${fn:escapeXml(imDef:imGetText('MES.package.goods.008'))}" sortable="false" headerClass="tct" style="text-align:left" format="{0,date,dd/MM/yyyy}" />

        <!-- haint41 4/11/2011 : them cac cot thong tin : Cho phep thay the ? , danh sach hang hoa thay the, Quan ly hang hoa thay the -->
        <display:column title=" ${fn:escapeXml(imDef:imGetText('MES.package.goods.009'))}" sortable="false" headerClass="tct" style="text-align:left" format="{0,date,dd/MM/yyyy}" >
            <s:if test = "#attr.tblPackageGoodsDetailList.checkReplace*1 == 1">
                <s:text name="L.100041"/> <!--Cho phép thay thế-->
            </s:if>
            <s:else>
                <s:text name="L.100042"/> <!--Không cho phép thay thế-->
            </s:else>
        </display:column>
        <display:column escapeXml="true" property="listReplaceGoods" title=" ${fn:escapeXml(imDef:imGetText('MES.package.goods.010'))}" sortable="false" headerClass="tct" />
        <display:column title=" ${fn:escapeXml(imDef:imGetText('MES.package.goods.011'))}" sortable="false" style="width:75px;text-align:center"  headerClass="tct" >
            <s:if test = "#attr.tblPackageGoodsDetailList.checkReplace*1 == 1">
                <s:a href="" onclick="prepareReplaceGoodsManagement('%{#attr.tblPackageGoodsDetailList.packageGoodsDetailId}')" cssClass="cursorHand">
                    <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="L.100027" />" alt="<s:text name="L.100027" />" />
                </s:a>
            </s:if>
        </display:column>
        <!-- end haint41 -->       

        <display:column title=" ${fn:escapeXml(imDef:imGetText('MES.package.goods.012'))}" sortable="false" style="width:50px;text-align:center" headerClass="tct" >
            <s:a href="" onclick="prepareEditPackageGoodsDetail('%{#attr.tblPackageGoodsDetailList.packageGoodsDetailId}')" cssClass="cursorHand">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="L.100027" />" alt="<s:text name="L.100027" />" />
            </s:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MES.package.goods.013'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
            <s:a href="" onclick="deletePackageGoodsDetail('%{#attr.tblPackageGoodsDetailList.packageGoodsDetailId}')" cssClass="cursorHand">
                <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name="L.100029" />" alt="<s:text name="L.100029" />" />
            </s:a>
        </display:column>                
        <display:footer> <tr> <td colspan="14" style="color:green"><s:text name="L.100032" />: <s:property escapeJavaScript="true"  value="%{#request.packageGoodsDetailList.size()}" /></td> <tr> </display:footer>
        </display:table>
</fieldset>


<script language="javascript">
            
    prepareEditPackageGoodsDetail = function (packageGoodsDetailId){
        $('returnMsgClient2').innerHTML= '';
        openDialog("${contextPath}/packageGoodsV2Action!prepareEditPackageGoodsDetail.do?packageGoodsDetailId="+packageGoodsDetailId + "&" + token.getTokenParamString(), 800, 600,true);
    }
    
    deletePackageGoodsDetail = function (packageGoodsDetailId){
        $('returnMsgClient2').innerHTML= '';
        var strConfirm = strConfirm = getUnicodeMsg('<s:text name="C.100004"/>');        
        if (!confirm(strConfirm)) {
            return;
        }        
        gotoAction("divPackageGoodsDetailV2List","${contextPath}/packageGoodsV2Action!deletePackageGoodsDetail.do?packageGoodsDetailId="+packageGoodsDetailId + "&" + token.getTokenParamString());
    }
    
    prepareReplaceGoodsManagement = function(packageGoodsDetailId){
        $('returnMsgClient2').innerHTML= '';
        openDialog("${contextPath}/packageGoodsV2Action!prepareReplaceGoodsManagement.do?packageGoodsDetailId="+packageGoodsDetailId + "&" + token.getTokenParamString(), 800, 600,true);
    }    
</script>

