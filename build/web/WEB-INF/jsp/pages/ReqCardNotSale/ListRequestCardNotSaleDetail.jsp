<%-- 
    Document   : ListRequestCardNotSaleDetail
    Created on : Dec 18, 2015, 2:25:51 PM
    Author     : mov_itbl_dinhdc
--%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        
        request.setAttribute("requestListDeatil", request.getSession().getAttribute("requestListDeatil"));
     
        request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="approveRequestLockUserAction!lookDetailRequest.do" method="POST" id="reqCardNotSaleFrom" theme="simple">
    <div>
            <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
    </div>
    <s:if test="#session.requestListDeatil != null && #session.requestListDeatil.size() != 0"></s:if>
        <sx:div id="displayTagFrame">
            <fieldset style="width:95%; padding:10px 10px 10px 10px">
                <legend class="transparent">${fn:escapeXml(imDef:imGetText('MSG.RequestCardNotSale.listDetail'))} </legend>
                <div style="overflow:auto; max-height:350px;">
                    <display:table targets="displayTagFrame" name="requestListDeatil"
                                   id="requestListDeatilId" class="dataTable"
                                   cellpadding="1" cellspacing="1">
                        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" headerClass="tct" style="width:40px; text-align:center" escapeXml="true">
                            ${fn:escapeXml(requestListDeatilId_rowNum)}
                        </display:column>
                        <display:column property="serial" title="${fn:escapeXml(imDef:imGetText('MSG.serial.number'))}" escapeXml="true"/>
                        <display:column property="storageCode" title="${fn:escapeXml(imDef:imGetText('MSG.store.infor'))}" escapeXml="true"/>
                        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.unit'))}" escapeXml="true">
                            <s:if test="#attr.requestListDeatilId.ownerType == 1">
                                ${fn:escapeXml(imDef:imGetText('MSG.RET.106'))}
                            </s:if>
                            <s:elseif test="#attr.requestListDeatilId.ownerType == 2">
                                ${fn:escapeXml(imDef:imGetText('MSG.RET.107'))}
                            </s:elseif>
                            <s:else>
                                undefined - <s:property escapeJavaScript="true"  value="#attr.requestListDeatilId.ownerType"/>
                            </s:else>
                        </display:column>
                        <display:column property="staffCode" title="${fn:escapeXml(imDef:imGetText('MSG.StaffCode.Lock'))}" escapeXml="true"/>
                        <display:column property="custName" title="${fn:escapeXml(imDef:imGetText('MSG.cusName'))}" escapeXml="true"/>
                        <display:column property="reqNo" title="${fn:escapeXml(imDef:imGetText('lbl.ma_yeu_cau'))}" escapeXml="true"/>
                        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="sortable">
                            <s:if test="#attr.requestListDeatilId.status == 0">
                                ${fn:escapeXml(imDef:imGetText('MSG.Not.Approved.Request'))}
                            </s:if>
                            <s:elseif test="#attr.requestListDeatilId.status == 2">
                                ${fn:escapeXml(imDef:imGetText('MSG.LOST.CARD'))}
                            </s:elseif>
                            <s:elseif test="#attr.requestListDeatilId.status == 1">
                                ${fn:escapeXml(imDef:imGetText('MSG.Approved.Request'))}
                            </s:elseif>
                            <s:else>
                                undefined - <s:property escapeJavaScript="true"  value="#attr.listRequestId.status"/>
                            </s:else>
                        </display:column>
                        <%--
                        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.delete'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
                            <s:url action="approveRequestLockUserAction!deleteDetailRequest.do?" id="URL1" var="confirmDeleteRequest">
                                <s:token/>
                                <s:param name="reqDetailId" value="#attr.requestListDeatilId.reqDetailId"/>
                                <s:param name="reqId" value="#attr.requestListDeatilId.reqId"/>
                                <s:param name="struts.token.name" value="'struts.token'"/>
                                <s:param name="struts.token" value="struts.token"/>

                            </s:url>
                            <sx:a targets="displayTagFrame" href="%{confirmDeleteRequest}"
                                          executeScripts="true" parseContent="true"
                                          beforeNotifyTopics="confirmDeleteRequest">
                                        <img src="${contextPath}/share/img/delete_icon.jpg"
                                             title="<s:text name="MSG.generates.delete" />" 
                                             alt="<s:text name="MSG.generates.delete" />"/>
                            </sx:a>
                        </display:column>
                        --%>
                    </display:table>
                </div>
            </fieldset>
        </sx:div >
        <s:token/>
</s:form>

<script>
    
    dojo.event.topic.subscribe("confirmDeleteRequest", function(event, widget){
        if(confirm(etUnicodeMsg('<s:property value="getText('cf.xoa_yeu_cau')"/>'))){
        }else{
            event.cancel = true;
        }
    });

    
</script>