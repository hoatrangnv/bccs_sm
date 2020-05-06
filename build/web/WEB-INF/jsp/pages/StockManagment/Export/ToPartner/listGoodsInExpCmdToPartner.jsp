<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listGoodsInExpCmdToPartner
    Created on : Sep 2, 2010, 10:41:36 AM
    Author     : Doan Thanh 8
    Desc       : danh sach hang hoa trong lenh xuat hang cho doi tac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="display" uri="VTdisplaytaglib" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@page import="com.guhesan.querycrypt.QueryCrypt" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
    String pageId = request.getParameter("pageId");
    request.setAttribute("listGoods", request.getSession().getAttribute("lstGoods" + pageId));
%>


<sx:div id="divListGoods">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="listMessage" id="listMessage" propertyValue="listMessageParam" type="key"/>
    </div>

    <!-- phan nhap thong tin hang hoa -->
    <table style="width:100%" cellspacing="0" cellpadding="0">
        <tr>
            <td style="width:40%; vertical-align:top">
                <fieldset class="imFieldset">
                    <legend>${fn:escapeXml(imDef:imGetText('MSG.stock.detail.goods'))}</legend>
                    <div style="width:100%; height:300px; overflow:auto;">
                        <s:form action="createExpCmdToPartnerAction" theme="simple" enctype="multipart/form-data"  method="POST" id="goodsForm">
<s:token/>

                            <s:hidden name="goodsForm.ownerType"/>
                            <s:hidden name="goodsForm.ownerId"/>
                            <s:hidden name="goodsForm.ownerCode"/>

                            <table class="inputTbl2Col">
                                <tr>
                                    <!-- mat hang -->
                                    <td><tags:label key="MSG.GOD.007" required="true"/></td>
                                    <td>
                                        <tags:imSearch codeVariable="goodsForm.stockModelCode" nameVariable="goodsForm.stockModelName"
                                                       codeLabel="MSG.GOD.007" nameLabel="MSG.GOD.008"
                                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                                       searchMethodName="getListStockModel"
                                                       getNameMethod="getStockModelName"/>
                                    </td>
                                </tr>
                                <tr>
                                    <!-- tinh trang hang -->
                                    <td><tags:label key="MSG.GOD.331" required="true"/></td>
                                    <td>
                                        <tags:imSelect name="goodsForm.stateId" id="stateId"
                                                       headerKey="1" headerValue="MSG.GOD.169"
                                                       list="3:MSG.GOD.170"
                                                       theme="simple"
                                                       cssClass="txtInputFull"/>
                                    </td>
                                </tr>
                                <tr>
                                    <!-- so luong -->
                                    <td><tags:label key="MSG.quanlity" required="true"/></td>
                                    <td>
                                        <s:textfield name="goodsForm.quantity" id="quantity" cssClass="txtInputFull" maxLength="12" theme="simple" onkeyup="textFieldNF(this);" />
                                    </td>
                                </tr>
                                <tr>
                                    <!-- ghi chu -->
                                    <td><tags:label key="MSG.comment"/></td>
                                    <td>
                                        <s:textfield maxlength="500" name="goodsForm.note" id="note" cssClass="txtInputFull"  theme="simple"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td>
                                        <tags:submit id="btnAddGoods"
                                                     formId="goodsForm"
                                                     showLoadingText="true"
                                                     cssStyle="width: 80px;"
                                                     targets="divListGoods"
                                                     value="MSG.GOD.019"
                                                     preAction="createExpCmdToPartnerAction!addGoods.do"
                                                     validateFunction="checkValidGoodsForm()"/>

                                        <s:url action="ViewStockDetailAction!prepareViewStockDetail" id="URLViewStock" encode="true" escapeAmp="false">
                                            <s:param name="ownerType" value="goodsForm.ownerType"/>
                                            <s:param name="ownerId" value="goodsForm.ownerId"/>
                                            <s:param name="stockModelId" value="goodsForm.stockModelId"/>
                                        </s:url>

                                        <input type="button"
                                               onclick="viewStockDetail('<s:property escapeJavaScript="true"  value="#attr.URLViewStock"/>')"
                                               value='<s:text name="MSG.GOD.171"/>'
                                               style="width: 80px;"/>

                                    </td>
                                </tr>
                            </table>
                        </s:form>
                    </div>
                </fieldset>
            </td>
            <td style="width: 5px;"></td>
            <td style="vertical-align:top">
                <fieldset class="imFieldset">
                    <legend>${fn:escapeXml(imDef:imGetText('MSG.detail.goods'))}</legend>
                    <div style="width:100%; height:300px; overflow:auto;">
                        <display:table id="tblListGoods" name="listGoods" class="dataTable" requestURI="javascript: void(0)" cellpadding="1" cellspacing="1">
                            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct" style="text-align:center;">
                                <s:property escapeJavaScript="true"  value="%{#attr.tblListGoods_rowNum}"/>
                            </display:column>
                            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.007'))}" property="stockModelCode" sortable="false" headerClass="tct"/>
                            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.008'))}" property="stockModelName" sortable="false" headerClass="tct"/>
                            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.331'))}" sortable="false" headerClass="tct">
                                <s:if test="#attr.tblListGoods.stateId ==1"><tags:label key="MSG.GOD.169"/></s:if>
                                <s:elseif test="#attr.tblListGoods.stateId ==3"><tags:label key="MSG.GOD.170"/></s:elseif>
                            </display:column>
                            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.179'))}" property="quantity" format="{0}"  style="text-align:right" sortable="false" headerClass="tct"/>
                            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.021'))}" sortable="false" headerClass="tct" style="text-align:center;">
                                <s:url action="createExpCmdToPartnerAction!delGoods" id="URL1" escapeAmp="false">
                                    <s:token/>
                                    <s:param name="struts.token.name" value="'struts.token'"/>
                                    <s:param name="struts.token" value="struts.token"/>
                                    <s:param name="stockModelId" value="#attr.tblListGoods.stockModelId"/>
                                    <s:param name="stateId" value="#attr.tblListGoods.stateId"/>
                                </s:url>
                                <a class="cursorHand" onclick="delGoods('divListGoods','<s:property escapeJavaScript="true"  value="#attr.URL1"/>')">
                                    <img src="${contextPath}/share/img/delete_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.021'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.GOD.021'))}"/>
                                </a>
                            </display:column>
                        </display:table>
                    </div>
                </fieldset>
            </td>
        </tr>
    </table>

</sx:div>

<script>

    textFieldNF($('quantity'));
    
    viewStockDetail = function(path){
        openDialog(path, 900, 700,false);
    }

    delGoods = function(target, action){
        var strConfirm = getUnicodeMsg('<s:text name="MSG.STK.001"/>');
        if(confirm(strConfirm)){
            gotoAction(target, action,"goodsForm");
        }
    }

    checkValidGoodsForm = function() {
        //kiem tra ma mat hang ko duoc de trong
        if(trim($('goodsForm.stockModelCode').value) == "") {
            $('listMessage').innerHTML = '<s:text name="MSG.GOD.035"/>';
            $('goodsForm.stockModelCode').select();
            $('goodsForm.stockModelCode').focus();
            return false;
        }

        //truong ma mat hang khong duoc chua cac ky tu dac biet
        if(!isValidInput($('goodsForm.stockModelCode').value, false, false)) {
            $('listMessage' ).innerHTML = '<s:text name="MSG.GOD.038"/>';
            $('goodsForm.stockModelCode').select();
            $('goodsForm.stockModelCode').focus();
            return false;
        }

        //truong so luong chi duoc phep nhap vao so duong
        var quantity = $('quantity').value.replace(/\,/g,""); //loai bo dau , trong chuoi
        if(!isInteger(trim(quantity))) {
            $('listMessage' ).innerHTML = '<s:text name="ERR.STK.118"/>';
            $('quantity').select();
            $('quantity').focus();
            return false;
        }

        //truong ghi chu khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('note').value))) {
            $('listMessage').innerHTML = '<s:text name="ERR.GOD.006"/>';
            $('note').select();
            $('note').focus();
            return false;
        }

        //trim cac truong can thiet
        $('goodsForm.stockModelCode').value = trim($('goodsForm.stockModelCode').value);
        $('quantity').value = trim($('quantity').value);
        $('note').value = trim($('note').value);

        return true;
    }

</script>
