<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ListRequestCardNotSale
    Created on : Dec 9, 2015, 10:38:28 AM
    Author     : mov_itbl_dinhdc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("listRequestUnLock", request.getSession().getAttribute("listRequestUnLock"));
            request.setAttribute("UnlockUserCardOfBranch", request.getSession().getAttribute("UnlockUserCardOfBranch"));
%>

<sx:div id="displayTagFrame">
    <display:table targets="displayTagFrame" name="listRequestUnLock"
                   id="listRequestUnLockId" pagesize="100" class="dataTable"
                   cellpadding="1" cellspacing="1"
                   requestURI="unLockUserAction!pageNavigator.do">
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center">
            ${fn:escapeXml(listRequestUnLockId_rowNum)}
        </display:column>
        <display:column property="staffCode" title="${fn:escapeXml(imDef:imGetText('MSG.StaffCode.Lock'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
        <display:column property="serial" title="${fn:escapeXml(imDef:imGetText('MSG.serial.number'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
        <display:column property="reqCode" title="${fn:escapeXml(imDef:imGetText('lbl.ma_yeu_cau'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
        <display:column property="staffCreateCode" title="${fn:escapeXml(imDef:imGetText('MSG.createStaff'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
        <display:column property="approveStaffCode" title="${fn:escapeXml(imDef:imGetText('lbl.approve.staff.code'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
        <display:column property="approveDate" title="${fn:escapeXml(imDef:imGetText('lbl.approve.date'))}" format="{0,date,dd/MM/yyyy}" style="text-align:center" sortable="false" headerClass="sortable"/>
        <%--
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.070'))}" sortable="false" headerClass="tct" style="width:70px; text-align:center; ">
            
            <s:a href="" cssClass="cursorHand" onclick="aOnclick('%{#attr.listRequestUnLockId.reqId}')">
                <img src="<%=StringEscapeUtils.escapeHtml(request.getContextPath())%>/share/img/accept_1.png"
                     title="<s:text name="lbl.xem_chi_tiet" />"
                     alt="<s:text name="lbl.xem_chi_tiet" />"/>
            </s:a>
         </display:column>
         --%> 
         <display:column title="${imDef:imGetText('lbl.duyet.yeu.cau')}" sortable="false" headerClass="tct" style="text-align:center;">
                <s:if test="#attr.listRequestUnLockId.status == 1">
                    <s:checkbox name="reqCardNotSaleFrom.selectedStockCardLostIds" theme="simple"
                                id="checkBoxID%{#attr.listRequestUnLockId.reqDetailId}"
                                fieldValue="%{#attr.listRequestUnLockId.reqDetailId}"
                                />
                </s:if>
                <s:else>
                    <s:checkbox name="selectedStockCardLostIds" theme="simple"
                                id="selectedStockCardLostIds"
                                disabled="true"
                                />
               </s:else>
        </display:column>
         
    </display:table>
    
    <table class="inputTbl4Col">
        
            <s:hidden name="reqCardNotSaleFrom.reqId" id="reqCardNotSaleFrom.reqId"/>
            <s:if test="#session.UnlockUserCardOfBranch == 1">
                <tr>
                    <td class="label"><tags:label key="MSG.GOD.240" required="true" /><%--Loại Ly Do--%></td>
                    <td colspan="2">
                        <div class="text">
                            <tags:imSelect name="reqCardNotSaleFrom.reasonId" id="reqCardNotSaleFrom.reasonId" headerKey="" 
                                           headerValue="label.option" 
                                           cssClass="txtInputFull"
                                           list="listReasonUnLockUser"
                                           listKey="reasonId" 
                                           listValue="reasonName"
                                           />
                        </div>
                    </td>

                </tr>
                
            </s:if>
            <s:else>
                <tr>
                    <td class="label"><tags:label key="MSG.GOD.240" required="true" /><%--Loại Ly Do--%></td>
                    <td colspan="2">
                        <div class="text">
                            <tags:imSelect name="reqCardNotSaleFrom.reasonId" id="reqCardNotSaleFrom.reasonId" headerKey="" 
                                           headerValue="label.option" 
                                           cssClass="txtInputFull"
                                           list="listReasonUnLockUser"
                                           listKey="reasonId" 
                                           listValue="reasonName"
                                           />
                        </div>
                    </td>
                </tr>
            </s:else>
                <tr>
                   <td class="label"><tags:label key="lbl.file_cong_van_dinh_kem" /><font color="red">(select file .pdf)</td>
                    <td colspan="2">
                        <tags:imFileUpload name="reqCardNotSaleFrom.clientFileName" id="reqCardNotSaleFrom.clientFileName" serverFileName="reqCardNotSaleFrom.serverFileName" extension="pdf" />
                    </td> 
                </tr>
    </table>
            
                <s:if test="#session.UnlockUserCardOfBranch == 1">
                    <div align="center" style="padding-bottom:4px; padding-top:4px;">
                        <tags:submit
                            formId="reqCardNotSaleFrom"
                            showLoadingText="true"
                            targets="bodyContent"
                            cssStyle="width:200px"
                            value="UNLOCK.USER.CARD.BRANCH"
                            preAction="unLockUserAction!UnlockUserCardOfBranch.do"
                            validateFunction="checkValidFields();"
                            />
                    </div>   
                </s:if>        
                
                <div align="center" style="padding-bottom:4px; padding-top:4px;">
                    <tags:submit
                        formId="reqCardNotSaleFrom"
                        showLoadingText="true"
                        targets="bodyContent"
                        cssStyle="width:200px"
                        value="UNLOCK.USER.CARD"
                        preAction="unLockUserAction!unLockUser.do"
                        validateFunction="checkBeforeUnlock();"
                        />
                </div>
                
         
    <br/>
</sx:div >

<script>
    
    checkSelectedList =function(checkboxIds) { 
        var listId = document.getElementsByName(checkboxIds);  
        for (var i = 0; i < listId.length; i++) {            
            if (listId[i].checked == true) {
                return true;
            }
        }        
        return false;
    }
    
    checkValidFields = function() {

            var reasonId = document.getElementById("reqCardNotSaleFrom.reasonId");
            if (reasonId.value == ''){
                <%--"Bạn chưa nhap ly do"--%>
                $('message').innerHTML = '<s:property value="getText('err.input.reason')"/>';
                reasonId.focus();
                return false;
            }
            
            if(!checkSelectedList('reqCardNotSaleFrom.selectedStockCardLostIds')){
            $('message').innerHTML = "<s:property value="getText('Choose.serial')"/>";
            return false;
            }
            
            confirmText = getUnicodeMsg("<s:property value="getText('cf.duyet_yeu_cau')"/>");
            if (!confirm(confirmText)){
                return false;
            }

            return true;
     }
      
    checkBeforeUnlock = function() {

            var reasonId = document.getElementById("reqCardNotSaleFrom.reasonId");
            if (reasonId.value == ''){
                <%--"Bạn chưa nhap ly do"--%>
                $('message').innerHTML = '<s:property value="getText('err.input.reason')"/>';
                reasonId.focus();
                return false;
            }  
            
            //ten file ko duoc de trong
            var clientFileName = document.getElementById("reqCardNotSaleFrom.clientFileName");
            if (trim(clientFileName.value).length ==0 && reasonId.value == 200807){
                <%--$('resultViewChangeStaffInShopClient').innerHTML="Bạn chưa chọn file cần tạo"--%>
                $('message').innerHTML = '<s:property value="getText('ERR.GOD.067')"/>';
                //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn file dữ liệu";
                clientFileName.focus();
                return false;
            }
            
            if(!checkSelectedList('reqCardNotSaleFrom.selectedStockCardLostIds')){
            $('message').innerHTML = "<s:property value="getText('Choose.serial')"/>";
            return false;
            }
            
            confirmText = getUnicodeMsg("<s:property value="getText('cf.duyet_yeu_cau')"/>");
            if (!confirm(confirmText)){
                return false;
            }

            return true;
      }  
      
    aOnclick = function(reqId) {
        openPopup('unLockUserAction!popupUnLockUser.do?reqId='+reqId +'&'+token.getTokenParamString(),1050,550);
    }
    
</script>
