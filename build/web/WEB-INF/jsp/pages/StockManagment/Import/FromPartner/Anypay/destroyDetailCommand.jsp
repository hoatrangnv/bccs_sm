<%-- 
    Document   : expNoteToPartner
    Created on : Sep 2, 2010, 11:16:49 PM
    Author     : Doan Thanh 8
    Descq      : tao phieu xuat kho cho doi tac
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
    request.setAttribute("listReason", request.getSession().getAttribute("listReason" + pageId));
%>

    <s:if test="#request.showDetail == 1">

    <s:form action="anypayPartnerAction" theme="simple" enctype="multipart/form-data"  method="POST" id="exportStockForm">
        <!-- phan hien thi thong tin lenh xuat kho -->
        <div>
            <table style="width:100%" cellspacing="0" cellpadding="0">
                <tr>
                    <td style="width:40%; vertical-align:top">
                        <fieldset class="imFieldset">
                            <legend>${imDef:imGetText('MSG.info.export.note')}</legend>
                            <div style="width:100%; height:300px; overflow:auto;">
                                <s:hidden name="exportStockForm.status" id=".status"/>
                                <s:hidden name="exportStockForm.actionId" id="exportStockForm.actionId"/>

                                <table class="inputTbl2Col">
                                    <tr>
                                        <!-- ma lenh xuat -->
                                        <td><tags:label key="MSG.exportCmdId"/></td>
                                        <td>
                                            <s:textfield theme="simple" maxlength="20" name="exportStockForm.cmdExportCode" id="exportStockForm.cmdExportCode" cssClass="txtInputFull" readonly="true"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <!-- nguoi xuat -->
                                        <td><tags:label key="MSG.GOD.155"/></td>
                                        <td>
                                            <s:textfield theme="simple" name="exportStockForm.sender" id="exportStockForm.sender" cssClass="txtInputFull" readonly="true"  />
                                        </td>

                                    </tr>
                                    <tr>
                                        <!-- ngay xuat -->
                                        <td><tags:label key="MSG.exportDate"/></td>
                                        <td>
                                            <tags:dateChooser readOnly="true" property="exportStockForm.dateExported" id="exportStockForm.dateExported" styleClass="txtInputFull" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <!-- kho xuat hang -->
                                        <td><tags:label key="MSG.fromStore"/></td>
                                        <td>
                                            <tags:imSearch codeVariable="exportStockForm.shopExportCode" nameVariable="exportStockForm.shopExportName"
                                                           codeLabel="MSG.RET.066" nameLabel="MSG.RET.067"
                                                           searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                                           searchMethodName="getListShop"
                                                           getNameMethod="getNameShop"
                                                           readOnly="true"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <!-- doi tac nhap hang -->
                                        <td><tags:label key="MSG.partner"/></td>
                                        <td>
                                            <tags:imSearch codeVariable="exportStockForm.shopImportedCode" nameVariable="exportStockForm.shopImportedName"
                                                           codeLabel="MSG.partner.code" nameLabel="MSG.partner.name"
                                                           searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                                           searchMethodName="getListPartner"
                                                           getNameMethod="getPartnerName"
                                                           readOnly="true"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <!-- ly do xuat -->
                                        <td><tags:label key="MSG.GOD.158"/></td>
                                        <td>
                                            <tags:imSelect name="exportStockForm.reasonId"
                                                           id="exportStockForm.reasonId"
                                                           cssClass="txtInputFull"
                                                           theme="simple"
                                                           headerKey="" headerValue="MSG.GOD.147"
                                                           list="listReason"
                                                           listKey="reasonId" listValue="reasonName"
                                                           disabled="true"/>
                                            <%--<s:hidden name="exportStockForm.reasonId"/>--%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <!-- ghi chu -->
                                        <td><tags:label key="MSG.GOD.031"/></td>
                                        <td>
                                            <s:textfield theme="simple" name="exportStockForm.note" id="exportStockForm.note" maxlength="500" cssClass="txtInputFull" readonly="true"/>
                                        </td>
                                    </tr>
                                </table>

                            </div>
                        </fieldset>
                    </td>
                    <td style="width: 5px;"></td>
                    <td style="vertical-align:top">
                        <fieldset class="imFieldset">
                            <legend>${imDef:imGetText('MSG.detail.goods')}</legend>
                            <div style="width:100%; height:300px; overflow:auto;">
                                <display:table id="tblListGoods" name="listGoods" class="dataTable" requestURI="javascript: void(0)" cellpadding="1" cellspacing="1">
                                    <display:column title="${imDef:imGetText('MSG.GOD.073')}" sortable="false" headerClass="tct" style="text-align:center;">
                                        <s:property value="%{#attr.tblListGoods_rowNum}"/>
                                    </display:column>
                                    <display:column escapeXml="true" title="${imDef:imGetText('MSG.GOD.007')}" property="stockModelCode" sortable="false" headerClass="tct"/>
                                    <display:column escapeXml="true" title="${imDef:imGetText('MSG.GOD.008')}" property="stockModelName" sortable="false" headerClass="tct"/>
                                    <display:column title="${imDef:imGetText('MSG.GOD.331')}" sortable="false" headerClass="tct">
                                        <s:if test="#attr.tblListGoods.stateId ==1"><tags:label key="MSG.GOD.169"/></s:if>
                                        <s:elseif test="#attr.tblListGoods.stateId ==3"><tags:label key="MSG.GOD.170"/></s:elseif>
                                    </display:column>
                                    <display:column title="${imDef:imGetText('MSG.GOD.179')}" property="quantity" format="{0}"  style="text-align:right" sortable="false" headerClass="tct"/>
                                </display:table>
                            </div>
                        </fieldset>
                    </td>
                </tr>
            </table>
        </div>
    </s:form>
</s:if>


<script>
    checkValidImportStockForm =function(){
        //ma phieu xuat khong duoc de trong
        if(trim($('exportStockForm.noteImpCode').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.SIK.116"/>';
            $('exportStockForm.noteExpCode').select();
            $('exportStockForm.noteExpCode').focus();
            return false;
        }

        //ma phieu xuat ko duoc chua cac ky tu dac biet
        if(!isValidInput($('exportStockForm.cmdImportCode').value, false, false)) {
            $('message' ).innerHTML = '<s:text name="ERR.STK.094"/>';
            $('exportStockForm.noteExpCode').select();
            $('exportStockForm.noteExpCode').focus();
            return false;
        }

        return true;

    }


</script>

