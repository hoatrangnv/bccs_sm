<%-- 
    Document   : listDebitRequestDetail
    Created on : May 16, 2013, 11:49:55 AM
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

<%
            String pageId = request.getParameter("pageId");
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listDebitRequestDetail", request.getSession().getAttribute("listDebitRequestDetail"));
%>
<s:if test="#request.listDebitRequestDetail.size() > 0 && #request.listDebitRequestDetail != null">
    <fieldset class="imFieldset">
        <legend>${imDef:imGetText('lbl.danh.sach.han.muc')}</legend>
        <div id="debitRequestDetailsDiv" style="width:100%; vertical-align:top;height:280px " >
            <div style="height:270px;overflow:auto">
                <tags:displayResult id="displayResultHMMsg" property="returnHMMsg" type="key" propertyValue="returnHMMsgParam"/>
                <display:table targets="bodyContent" id="requestDebitDetail" name="listDebitRequestDetail" class="dataTable" cellpadding="1" cellspacing="1" requestURI="approvalDebitRequestForShopAction!pageNavigatorDetail.do"  pagesize="100">
                    <display:column escapeXml="false" title="STT" sortable="false" headerClass="tct">
                        <div align="center" style="vertical-align:middle">${requestDebitDetail_rowNum}</div>
                    </display:column>
                    <display:column escapeXml="true" property="shopCode" title="${imDef:imGetText('MSG.generates.unit_code')}" sortable="false" style="text-align:left" headerClass="tct"/>
                    <display:column escapeXml="true" property="shopName" title="${imDef:imGetText('MSG.SAE.186')}" sortable="false" style="text-align:left" headerClass="tct"/>
                    <display:column escapeXml="false" title="${imDef:imGetText('MSG.generates.status')}" sortable="false" style="width:100px;text-align:left" headerClass="tct">
                        <s:if test="#attr.requestDebitDetail.status == 1"><tags:label key="lbl.chua_duyet" /></s:if>
                        <s:elseif test="#attr.requestDebitDetail.status == 2"><tags:label key="lbl.da_duyet" /></s:elseif>
                        <s:elseif test="#attr.requestDebitDetail.status == 0"><tags:label key="lbl.da_tu_choi" /></s:elseif>
                    </display:column>
                    <display:column escapeXml="true" property="debitTypeOld" title="${imDef:imGetText('lbl.gia_tri_han_muc_cu')}" sortable="false" style="text-align:left" headerClass="tct"/>
                    <display:column escapeXml="true" property="debitType" title="${imDef:imGetText('lbl.gia_tri_han_muc_moi')}" sortable="false" style="text-align:left" headerClass="tct"/>
                    <display:column escapeXml="true" property="debitDayType" title="${imDef:imGetText('lbl.ngay.ap.dung')}" sortable="false" style="text-align:left" headerClass="tct"/>
                    <display:column escapeXml="true" property="stockTypeName" title="${imDef:imGetText('lbl.loai_mat_hang')}" sortable="false" style="text-align:left" headerClass="tct"/>
                    <display:column title="${imDef:imGetText('label.request.debit.type')}" sortable="false" style="text-align:left" headerClass="tct">
                        <s:if test="#attr.requestDebitDetail.requestDebitType == 1"><tags:label key="MSG.impByQuantity" /></s:if>
                        <s:elseif test="#attr.requestDebitDetail.requestDebitType == 2"><tags:label key="lbl.total" /></s:elseif>
                    </display:column>
                    <display:column title="${imDef:imGetText('label.request.debit.type.old')}" sortable="false" style="text-align:left" headerClass="tct">
                        <s:if test="#attr.requestDebitDetail.requestDebitTypeOld == 1"><tags:label key="MSG.impByQuantity" /></s:if>
                        <s:if test="#attr.requestDebitDetail.requestDebitTypeOld == 2"><tags:label key="lbl.total" /></s:if>
                        <s:if test="#attr.requestDebitDetail.requestDebitTypeOld == 3">--</s:if>
                    </display:column>
                    <display:column escapeXml="false" title="<input id ='allNote' type='textbox'>" sortable="false" style="text-align:left;width:100px" headerClass="tct">
                        <s:textfield id="description%{#attr.requestDebitDetail.requestDetailId}" name="searchDebitRequestForm.listDescription" value="%{#attr.requestDebitDetail.note}" theme="simple" maxlength="200"/>
                        <s:hidden id="description%{#attr.requestDebitDetail.requestDetailId}" name="searchDebitRequestForm.listRequestDetailId" value="%{#attr.requestDebitDetail.requestDetailId}"/>
                        <s:hidden id="requestId%{#attr.requestDebitDetail.requestDetailId}" name="searchDebitRequestForm.listRequestId" value="%{#attr.requestDebitDetail.requestId}"/>
                        <s:hidden id="status%{#attr.requestDebitDetail.requestDetailId}" name="searchDebitRequestForm.statusList" value="%{#attr.requestDebitDetail.status}"/>
                    </display:column>
                    <s:if test="#request.enableApproval == 1">
                        <display:column title="<input id ='checkAllIDInspectStatus'
                                        type='checkbox'  onclick=\"selectCbAllInspectStatus()\">"
                                        sortable="false" style="text-align:left;width:50px" headerClass="tct">
                            <s:if test="#attr.requestDebitDetail.status == 1">
                                <s:select onchange="checkSelectCbAllInspectStatus();" id="approval%{#attr.requestDebitDetail.requestDetailId}" list="#{'0':'Từ chối','2':'Duyệt'}" name="searchDebitRequestForm.listApprovalReject" theme="simple" value="%{#attr.requestDebitDetail.status}" cssStyle="width:90px"/>
                            </s:if>
                            <s:elseif test="#attr.requestDebitDetail.status != 1">
                                <s:hidden id="approvalHiden%{#attr.requestDebitDetail.requestDetailId}" name="searchDebitRequestForm.listApprovalReject" value="0"/>
                            </s:elseif>
                        </display:column>
                    </s:if>
                </display:table>
            </div>
        </div>
    </fieldset>
    <div align="center">
        <s:if test="#request.enableApproval == 1">
            <tags:submit targets="bodyContent"
                         formId="searchDebitRequestForm"
                         cssStyle="width:100px;"
                         value="lbl.duyet.yeu.cau"
                         showLoadingText="true"
                         preAction="approvalDebitRequestForShopAction!approvalRequest.do"
                         validateFunction="validateApproval()"
                         id="btnApproval"/>
        </s:if>
        <s:elseif test="#request.enableApproval == 2">
            <tags:submit targets="bodyContent"
                         formId="searchDebitRequestForm"
                         cssStyle="width:100px;"
                         value="lbl.duyet.yeu.cau"
                         showLoadingText="true"
                         preAction="approvalDebitRequestForShopAction!approvalRequest.do"
                         validateFunction="validateApproval()"
                         id="btnApproval"
                         disabled="true"/>
        </s:elseif>
    </div>    
</s:if>

