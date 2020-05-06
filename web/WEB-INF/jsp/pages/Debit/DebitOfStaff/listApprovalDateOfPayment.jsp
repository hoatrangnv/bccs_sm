<%-- 
    Document   : listDebitRequest
    Created on : May 16, 2013, 11:49:41 AM
    Author     : thinhph2_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
            String pageId = request.getParameter("pageId");
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listDebitRequest", request.getSession().getAttribute("listDebitRequest"));
%>

<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('lbl.danh_sach_yeu_cau'))}</legend>
    <div id="goodList" style="width:100%; vertical-align:top;height:280px " >
        <div style="height:270px;overflow:auto">
            <tags:displayResult id="displayResultMsg" property="returnMsg" type="key" propertyValue="returnMsgParam"/>
            <display:table targets="bodyContent" id="requestDebit" name="listDebitRequest" class="dataTable" cellpadding="1" cellspacing="1" requestURI="approvalDateOfPaymentAction!pageNavigator.do" pagesize="20">
                <display:column escapeXml="false" title="STT" sortable="false" headerClass="tct">
                    <div align="center" style="vertical-align:middle">${requestDebit_rowNum}</div>
                </display:column>
                <display:column escapeXml="true" property="requestCode" title="${imDef:imGetText('lbl.ma_yeu_cau')}" sortable="false" style="text-align:left" headerClass="tct"/>
                <display:column property="createDate" title="${imDef:imGetText('lbl.ngay_yeu_cau')}" sortable="false" style="text-align:left" format="{0,date,dd/MM/yyyy}" headerClass="tct"/>
                <display:column escapeXml="true" property="createUser" title="${imDef:imGetText('MSG.SIK.069')}" sortable="false" style="text-align:left" headerClass="tct"/>
                <display:column escapeXml="fasle" title="${imDef:imGetText('MSG.LIMITED.06')}" sortable="false" style="width:100px;text-align:left" headerClass="tct">
                    <s:if test="#attr.requestDebit.status == 1"><tags:label key="lbl.chua_duyet" /></s:if>
                    <s:elseif test="#attr.requestDebit.status == 2"><tags:label key="lbl.da_duyet_tat_ca" /></s:elseif>
                    <s:elseif test="#attr.requestDebit.status == 3"><tags:label key="lbl.da_duyet" /></s:elseif>
                    <s:elseif test="#attr.requestDebit.status == 0"><tags:label key="lbl.da_xoa" /></s:elseif>
                </display:column>
                <display:column escapeXml="true" property="descripttion" title="${imDef:imGetText('L.100026')}" sortable="false" style="text-align:left" headerClass="tct"/>
                <display:column title="${imDef:imGetText('lbl.xem_phieu_yeu_cau')}" sortable="false" style="width:80px;text-align:right" headerClass="tct">
                    <s:if test ="#attr.requestDebit.clientFileName != null">
                        <div align="center">
                            <a style="cursor:pointer" onclick="downloadFile('<s:property value="#attr.requestDebit.requestId"/>')">
                                <img height="16px" width="16px" src="${contextPath}/share/img/download_icon.gif" title='<s:property value="getText('lbl.tai_file')"/>'/>
                            </a>
                        </div>
                    </s:if>
                </display:column>
                <display:column escapeXml="false" title="${imDef:imGetText('lbl.duyet.yeu.cau')}" sortable="false" style="text-align:center;width:80px" headerClass="tct">
                    <s:if test="#attr.requestDebit.status == 1 || #attr.requestDebit.status == 3">
                        <div align="center" style="vertical-align:middle">
                            <s:url action="approvalDateOfPaymentAction!viewDetail" id="URL">
                                <s:param name="requestId" value="#attr.requestDebit.requestId"/>
                            </s:url>
                            <sx:a targets="listDebitRequestDetailDiv" href="%{#URL}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                                <img src="${contextPath}/share/img/accept_1.png" title="Duyệt yêu cầu" alt="Duyệt yêu cầu"/>
                            </sx:a>
                        </div>
                    </s:if>
                </display:column>                
            </display:table>
        </div>
    </div>
</fieldset>
