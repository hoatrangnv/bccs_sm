<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ReportStockGTHH
    Created on : 11082017
    Author     : lamnt
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<tags:imPanel title="MSG.criterion.report.create">
    <!-- phan hien thi tieu chi bao cao -->
    <s:form name="reportStockGTHH" theme="simple" method="post" id="ReportStockGthh">
        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <table class="inputTbl8Col">
                <tr>
                    <td  class="label">
                        <tags:label key="MSG.GOD.184"/>
                        <!--                Kho xuất-->
                    </td>
                    <td  class="text">
                        <s:textfield readonly="true"
                                     cssClass="txtInput"
                                     value="Movitel"/>
                    </td>
                    <td  class="label">
                        <tags:label key="MSG.GOD.185"/>
                        <!--                Kho nhận-->
                    </td>
                    <td  class="text">
                        <s:textfield readonly="true" 
                                     cssClass="txtInput"
                                     value="Kho Giảm Trừ Hàng Hóa"/>
                    </td>
                </tr>

            </table>

        </div>
        <div>
            <table>
                <tr>
                    <td  class="label">
                        <tags:label key="MSG.GOD.117"/>
                        <!--                Từ ngày-->
                    </td>
                    <td class="text">
                        <tags:dateChooser property="%{#attr.fromDate}" id="ReportStockGTHH.fromDate" styleClass="txtInput"/>  
                    </td>
                    <td  class="label">
                        <tags:label key="MSG.GOD.118"/>
                        <!--                Đến ngày-->
                    </td>
                    <td  class="text">
                        <tags:dateChooser property="%{#attr.toDate}" id="ReportStockGTHH.toDate" styleClass="txtInput"/>
                    </td>
                </tr>
            </table>
        </div>
    </s:form>
    <tags:displayResult id="resultCreateExpCmdClient" property="toDate" propertyValue="resultCreateExpParams" type="key"/>
    <!-- phan nut tim kiem -->
    <div align="center" style="padding-top:5px; padding-bottom:5px;">
        <tags:submit id="ListSearch"
                     formId="exportStockForm" 
                     showLoadingText="true" 
                     targets="bodyContent"
                     value="MSG.GOD.009"
                     preAction="reportStockActionGTHH!preparePageReportStockGTHH.do"/>
    </div>
</tags:imPanel>
    <display:table cellpadding="1" class="dataTable" pagesize="15"  cellpadding="1" cellspacing="1">
        <display:column  title="Nguoi Xuat" property="%{#attr.toDate}"  sortable="false" />
    </display:table>
<display:table cellpadding="1" cellspacing="1" class="dataTable" pagesize="15" cellpadding="1" cellspacing="1">
    <display:column  title="STT" sortable="false" headerClass="tct" >
        <s:property escapeJavaScript="true"  value="%{#attr.trans_rowNum}"/>
    </display:column>
    <display:column escapeXml="true" title="Ma Kho" property="ma_kho" />
    <display:column escapeXml="true" title="Lenh/Phieu Xuat" property="lenhphieu_xuat"/>
    <display:column  title="Nguoi Xuat" property="nguoi_xuat"  sortable="false" headerClass="tct"/>
    <display:column escapeXml="true" title="Ma Mat Hang" property="ma_mat_hang" sortable="false" headerClass="tct"/>
    <display:column escapeXml="true" title="Ten Mat Hang" property="ten_mat_hang" sortable="false" headerClass="tct"/>
    <display:column escapeXml="true" title="Ly Do Xuat" property="reason" sortable="false" headerClass="tct"/>
    <display:column escapeXml="true" title="Ma To Trinh" property="ma_to_trinh" sortable="false" headerClass="tct">
    </display:column>
    <display:column title="Download File" sortable="false" headerClass="tct">
        <div align="center">
            <s:url action="ExportStockUnderlyingAction!prepareExpStockFromNote" id="URL" encode="true" escapeAmp="false">
                <s:param name="actionId" value="#attr.trans.id.actionId"/>
            </s:url>

            <sx:a targets="searchArea" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                ${fn:escapeXml(imDef:imGetText('MSG.GOD.200'))}
            </sx:a>
        </div>
    </display:column>
</display:table>




