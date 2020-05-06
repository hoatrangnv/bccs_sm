<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

<sx:div id="divExpCmdToPartner">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <s:form action="createExpCmdToPartnerAction" theme="simple" enctype="multipart/form-data"  method="POST" id="exportStockForm">
<s:token/>

        <!-- phan hien thi thong tin lenh xuat kho -->
        <div>
            <table style="width:100%" cellspacing="0" cellpadding="0">
                <tr>
                    <td style="width:40%; vertical-align:top">
                        <fieldset class="imFieldset">
                            <legend>${fn:escapeXml(imDef:imGetText('MSG.info.export.cmd'))}</legend>
                            <div style="width:100%; height:300px; overflow:auto;">
                                <s:hidden name="exportStockForm.status" id=".status"/>
                                <s:hidden name="exportStockForm.actionId" id="exportStockForm.actionId"/>

                                <table class="inputTbl2Col">
                                    <tr>
                                        <!-- ma lenh xuat -->
                                        <td><tags:label key="MSG.GOD.150"/></td>
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
                                        <td><tags:label key="MSG.GOD.159"/></td>
                                        <td>
                                            <tags:dateChooser readOnly="true" property="exportStockForm.dateExported" id="exportStockForm.dateExported" styleClass="txtInputFull" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <!-- kho xuat hang -->
                                        <td><tags:label key="MSG.GOD.138"/></td>
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
                                        <!-- doi tac xuat hang -->
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
                                </display:table>
                            </div>
                        </fieldset>
                    </td>
                </tr>
            </table>
        </div>

        <!-- phan nhap thong tin phieu xuat kho -->
        <div>
            <fieldset class="imFieldset">
                <legend>${fn:escapeXml(imDef:imGetText('MSG.info.export.note'))}</legend>
                <table class="inputTbl3Col">
                    <tr>
                        <td><tags:label key="MSG.expNoteId" required="true"/></td>
                        <td>
                            <s:textfield theme="simple"  name="exportStockForm.noteExpCode" readonly="true" id="exportStockForm.noteExpCode" maxlength="15" cssClass="txtInputFull"/>
                        </td>
                        <td>
                            <s:if test="#request.isPrintMode == 'true'">
                                <input type="button" style="width: 100px;" value="${fn:escapeXml(imDef:imGetText('MSG.createExportNote'))}" disabled/>
                                <tags:submit id="btnPrintExpNote"
                                             formId="exportStockForm"
                                             cssStyle="width: 100px;"
                                             showLoadingText="true"
                                             targets="divCreateExpNoteToPartner"
                                             value="MSG.print.export.note"
                                             preAction="createExpNoteToPartnerAction!printExpNote.do"/>
                            </s:if>
                            <s:else>
                                <tags:submit id="exp"
                                             confirm="true"
                                             confirmText="MSG.confirm.create.export.note"
                                             formId="exportStockForm"
                                             cssStyle="width: 100px;"
                                             showLoadingText="true"
                                             targets="divCreateExpNoteToPartner"
                                             value="MSG.createExportNote"
                                             validateFunction="checkValidExportStockForm()"
                                             preAction="createExpNoteToPartnerAction!createExpNoteToPartner.do"/>
                                <input type="button" style="width: 100px;" value="${fn:escapeXml(imDef:imGetText('MSG.print.export.note'))}" disabled/>
                            </s:else>
                        </td>
                        <td style="width: 40%; "></td>
                    </tr>
                </table>
            </fieldset>
        </div>

        <!-- hien thi link download phieu xuat trong truong hop in phieu -->
        <div>
            <s:if test="exportStockForm.exportUrl !=null && exportStockForm.exportUrl!=''">
                <script>
                    window.open('${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
                </script>
                <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>' >${fn:escapeXml(imDef:imGetText('MSG.click.here.to.download'))}</a></div>
            </s:if>

        </div>

    </s:form>

</sx:div>


<script>
    checkValidExportStockForm =function(){
        //ma phieu xuat khong duoc de trong
        if(trim($('exportStockForm.noteExpCode').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.STK.093"/>';
            $('exportStockForm.noteExpCode').select();
            $('exportStockForm.noteExpCode').focus();
            return false;
        }

        //ma phieu xuat ko duoc chua cac ky tu dac biet
        if(!isValidInput($('exportStockForm.cmdExportCode').value, false, false)) {
            $('message' ).innerHTML = '<s:text name="ERR.STK.094"/>';
            $('exportStockForm.noteExpCode').select();
            $('exportStockForm.noteExpCode').focus();
            return false;
        }

        return true;

    }


</script>

