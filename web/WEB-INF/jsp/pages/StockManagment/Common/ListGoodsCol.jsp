<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
        String pageId=request.getParameter("pageId");
        request.setAttribute("lstGoods", request.getSession().getAttribute("lstGoods"+pageId));

        String inputSerial = (String) request.getParameter("inputSerial");
        if (request.getAttribute("inputSerial") != null) {
            inputSerial = request.getAttribute("inputSerial").toString();
        }
        request.setAttribute("inputSerial", inputSerial);
        request.setAttribute("notInputSerial", request.getParameter("notInputSerial"));
        String collaborator = (String) request.getAttribute("collaborator");
        request.setAttribute("collaborator", collaborator);

%>

<sx:div id="localListGoods">
    <tags:imPanel title="MSG.detail.goods">
        <div style="height:210px; overflow:auto;">
            <tags:displayResult id="returnMsgGoodsClient" property="returnMsg"/>
            <display:table id="good" name="lstGoods" class="dataTable" requestURI="javascript: void(0)" cellpadding="1" cellspacing="1">
                <display:column  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct">
                    <s:property escapeJavaScript="true"  value="%{#attr.good_rowNum}"/>
                </display:column>
                <s:if test="#attr.collaborator =='coll'">
                    <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.175'))}" property="stockModelName" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.178'))}" property="price" style="text-align:right" sortable="false" headerClass="tct"/>
                </s:if> <s:else>
                    <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.176'))}" property="stockTypeName"/>
                    <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.175'))}" property="stockModelName" sortable="false" headerClass="tct"/>
                </s:else>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.013'))}" sortable="false" headerClass="tct">
                    <s:if test="#attr.good.stateId ==1"><tags:label key="MSG.GOD.169"/></s:if>
                    <s:if test="#attr.good.stateId ==3"><tags:label key="MSG.GOD.170"/></s:if>
                </display:column>
                <display:column escapeXml="true" property="unit" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.177'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.179'))}" property="quantity" style="text-align:right" sortable="false" headerClass="tct"/>
                <!--Neu cho phep nhap serial chi tiet cho cac hang hoa -->
                <s:if test="#request.inputSerial != null && #request.inputSerial=='true'">
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.181'))}" sortable="false" headerClass="tct">
                        <!--Chi nhung mat hang quan ly theo serial moi cho phep nhap chi tiet serial-->
                        <s:if test="#attr.good.checkSerial==1">
                            <s:if test="#attr.good.lstSerial != null && #attr.good.lstSerial.size()>0">
                                <div align="center">
                                    <s:url action="InputSerialAction!prepareInputSerial" id="URLView" encode="true" escapeAmp="false">
                                        <s:param name="totalReq" value="#attr.good.quantity"/>
                                        <s:param name="stockTypeId" value="#attr.good.stockTypeId"/>
                                        <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                                        <s:param name="stockModelName" value="#attr.good.stockModelName"/>
                                        <s:param name="ownerType" value="#attr.good.fromOwnerType"/>
                                        <s:param name="ownerId" value="#attr.good.fromOwnerId"/>
                                        <s:param name="stateId" value="#attr.good.stateId"/>
                                        <s:param name="isView" value="true"/>
                                    </s:url>
                                    <a href="javascript: void(0);" onclick="openDialog('<s:property escapeJavaScript="true"  value="#URLView"/>',800,700)">
                                        <tags:label key="MSG.GOD.070"/>
<!--                                        Chi tiết serial-->
                                    </a>

                                </div>
                            </s:if>
                            <s:else>
                                <s:if test="#attr.good.quantity != null ">
                                    <s:if test="#request.notInputSerial==null || #request.notInputSerial !='true'">
                                        <div align="center">
                                            <s:url action="InputSerialAction!prepareInputSerial" id="URL" encode="true" escapeAmp="false">
                                                <s:param name="totalReq" value="#attr.good.quantity"/>
                                                <s:param name="stockTypeId" value="#attr.good.stockTypeId"/>
                                                <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                                                <s:param name="stockModelName" value="#attr.good.stockModelName"/>
                                                <s:param name="ownerType" value="#attr.good.fromOwnerType"/>
                                                <s:param name="ownerId" value="#attr.good.fromOwnerId"/>
                                                <s:param name="stateId" value="#attr.good.stateId"/>
                                                <s:param name="checkDial" value="#attr.good.checkDial"/>
                                                <s:param name="editable" value="goodsForm.editable"/>
                                            </s:url>
                                            <a href="javascript:void(0)" onclick="openDialog('<s:property escapeJavaScript="true"  value="#URL"/>',800,700)">
                                                <tags:label key="MSG.GOD.173"/>
<!--                                                Nhập serial-->
                                            </a>
                                            <!--/s:else-->
                                        </div>
                                    </s:if>
                                </s:if>
                            </s:else>
                        </s:if>
                    </display:column>
                </s:if>
                <!--Neu cho phep sua, xoa hang hoa trong danh sach -->
                <s:if test="goodsForm.editable != null && goodsForm.editable =='true'">
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.182'))}" sortable="false" headerClass="tct">
                        <%--
            <s:set var="stockModelId" value="#attr.good.stockModelId"  scope="request"/>
            <s:set var="stockTypeId" value="#attr.good.stockTypeId" scope="request"/>
            --%>
                        <s:if test="#attr.collaborator =='coll'">
                            <s:url action="CommonStockAction!prepareEditGoodsColl" id="URL2">
                                <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                                <s:param name="stockTypeId" value="#attr.good.stockTypeId"/>
                                <s:param name="stateId" value="#attr.good.stateId"/>
                            </s:url>
                            <s:url action="CommonStockAction!delGoodsColl" id="URL3" escapeAmp="false">
                                <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                                <s:param name="stockTypeId" value="#attr.good.stockTypeId"/>
                                <s:param name="stateId" value="#attr.good.stateId"/>
                            </s:url>
                            <div align="center">
                                <!-- chi cho phep sua nhung hang hoa chua nhap chi tiet serial-->
                                <s:if test="#attr.good.lstSerial == null || #attr.good.lstSerial.size()==0">

                                    <sx:a targets="inputGoods" href="%{#URL2}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                                        <img src="${contextPath}/share/img/edit_icon.jpg" title="'<s:text name="MSG.edit.goods"/>" alt="'<s:text name="MSG.edit.goods"/>"/>
                                    </sx:a>
                                </s:if>
                                <a onclick="deleteGoods('listGoods','<s:property escapeJavaScript="true"  value="#attr.URL3"/>')">
                                    <img src="${contextPath}/share/img/delete_icon.jpg" title="'<s:text name="MSG.delete.goods"/>" alt="'<s:text name="MSG.delete.goods"/>"/>
                                </a>
                                <%--sx:a targets="listGoods" href="%{#URL3}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                            <img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa hàng hoá" alt="Xóa hàng hoá"/>
                                </sx:a--%>
                            </div>
                        </s:if>
                        <s:else>
                            <s:url action="CommonStockAction!prepareEditGoods" id="URL2">
                                <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                                <s:param name="stockTypeId" value="#attr.good.stockTypeId"/>
                                <s:param name="stateId" value="#attr.good.stateId"/>
                            </s:url>
                            <s:url action="CommonStockAction!delGoods" id="URL3" escapeAmp="false">
                                <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                                <s:param name="stockTypeId" value="#attr.good.stockTypeId"/>
                                <s:param name="stateId" value="#attr.good.stateId"/>
                            </s:url>
                            <div align="center">
                                <!-- chi cho phep sua nhung hang hoa chua nhap chi tiet serial-->
                                <s:if test="#attr.good.lstSerial == null || #attr.good.lstSerial.size()==0">

                                    <sx:a targets="inputGoods" href="%{#URL2}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                                        <img src="${contextPath}/share/img/edit_icon.jpg" title="'<s:text name="MSG.edit.goods"/>" alt="'<s:text name="MSG.edit.goods"/>"/>
                                    </sx:a>
                                </s:if>
                                <a onclick="deleteGoods('listGoods','<s:property escapeJavaScript="true"  value="#attr.URL3"/>')">
                                    <img src="${contextPath}/share/img/delete_icon.jpg" title="'<s:text name="MSG.delete.goods"/>" alt="'<s:text name="MSG.delete.goods"/>"/>
                                </a>
                            </div>
                        </s:else>
                    </display:column>
                </s:if>
            </display:table>

            <table class="inputTbl">
                <tr>
                    <td><tags:label key="MSG.sum.money.deposit"/></td>
                    <td>
                        <s:textfield name="#session.amount" value = "%{#session.amount}"  theme="simple" cssStyle="text-align:right" id="session.amount" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
        </div>
    </tags:imPanel>
</sx:div>
<script>

    deleteGoods=function(target, action){
        //if(confirm("Bạn có thực sự muốn xoá hàng hoá khỏi danh sách?"))
        if(confirm("'<s:text name="MSG.STK.001"/>'")){
            gotoAction(target, action,"goodsForm");
        }
    }

    updateSerial=function(){
        <s:if test="#session.amount!=null">
                //var actionCode = document.getElementById("exportStockForm.cmdExportCode").value;
                //gotoAction("localListGoods", "InputSerialAction!refreshListGoodsColl.do?actionCode="+actionCode);
                gotoAction("localListGoods", "InputSerialAction!refreshListGoodsColl.do?" + token.getTokenParamString());
         </s:if>
         <s:else>
                 //var actionCode = document.getElementById("exportStockForm.cmdExportCode").value;
                 //gotoAction("localListGoods", "InputSerialAction!refreshListGoods.do?actionCode="+actionCode);
                 gotoAction("localListGoods", "InputSerialAction!refreshListGoods.do?" + token.getTokenParamString());
        </s:else>
            }
            function checkAndOpenPopup(path, w, h){
                if(document.getElementById("shopExportId").value ==0)
                {
                    alert("Chưa chọn nhân viên trả hàng");
                    document.getElementById("shopExportId").focus();
                    return false;
                }
                else
                    openDialog(path,w,h,true);
            }
</script>

