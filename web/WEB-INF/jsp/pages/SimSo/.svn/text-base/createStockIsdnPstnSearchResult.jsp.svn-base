<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : createStockIsdnPstnSearchResult
    Created on : Jan 15, 2008, 2:54:01 PM
    Author     : Tuannv
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.guhesan.querycrypt.QueryCrypt" %>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            String pageId = request.getParameter("pageId");
            request.setAttribute("editAndDeletePstnToDLU", request.getSession().getAttribute("editAndDeletePstnToDLU" + pageId));
            //  request.setAttribute("importPstnList", request.getSession().getAttribute("importPstnList"));
%>

<!-- hien thi message, tamdt1 -->
<div>
    <tags:displayResult id="messageList" property="messageList" type="key"/>
</div>
<c:set var="editAndDeletePstnToDLU" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.editAndDeletePstnToDLU, request))}" />
<s:if test="#request.importPstnList != null && #request.importPstnList.size() != 0">
    <div style="height:300px;overflow:auto" >
        <display:table name="importPstnList" id="importPstn"
                       pagesize="500" class="dataTable"
                       cellpadding="1" cellspacing="1"
                       requestURI="${contextPath}/processPSTNAction!pageNavigatorForList.do"
                       targets="divListRange">
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="text-align:center; width:50px" headerClass="tct">
                ${fn:escapeXml(importPstn_rowNum)}
            </display:column>
            <display:column escapeXml="true"  property="isdn" title="${fn:escapeXml(imDef:imGetText('MSG.pstnNumber'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="dslamCode" title="${fn:escapeXml(imDef:imGetText('MSG.DLU'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="module" title="${fn:escapeXml(imDef:imGetText('MSG.module'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="rack" title="${fn:escapeXml(imDef:imGetText('MSG.Rack'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="shelf" title="${fn:escapeXml(imDef:imGetText('MSG.Shelf'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="slot" title="${fn:escapeXml(imDef:imGetText('MSG.slot'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="port" title="${fn:escapeXml(imDef:imGetText('MSG.port'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="das" title="${fn:escapeXml(imDef:imGetText('MSG.DAS'))}" sortable="false" headerClass="sortable"/>
            <!--display:column property="deviceType" title="Loại thiết bị" sortable="false" headerClass="sortable"/-->
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.device.type'))}" sortable="false" headerClass="sortable">
                <s:property escapeJavaScript="true"  value="#attr.importPstn.deviceType"/>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" sortable="false" headerClass="sortable">
                <s:if test = "#attr.importPstn.status == 1">
                    ${fn:escapeXml(imDef:imGetText('MSG.newIsdn'))}
                </s:if>
                <s:elseif test = "#attr.importPstn.status == 2">
                    ${fn:escapeXml(imDef:imGetText('MSG.nowIsdn'))}
                </s:elseif>
                <s:elseif test = "#attr.importPstn.status == 3">
                    ${fn:escapeXml(imDef:imGetText('MSG.stopIsdn'))}
                </s:elseif>
                <s:elseif test = "#attr.importPstn.status == 5">
                    ${fn:escapeXml(imDef:imGetText('LockISDN'))}
                </s:elseif>
            </display:column>


            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.isdn_type'))}" sortable="false" headerClass="sortable">
                <s:if test = "#attr.importPstn.isdnType == 1">
                    ${fn:escapeXml(imDef:imGetText('PrepaidISDN'))}
                </s:if>
                <s:elseif test = "#attr.importPstn.isdnType == 2">
                    ${fn:escapeXml(imDef:imGetText('PostPaidISDN'))}
                </s:elseif>
                <s:elseif test = "#attr.importPstn.isdnType == 3">
                    ${fn:escapeXml(imDef:imGetText('SpecialISDN'))}
                </s:elseif>
                <s:elseif test = "#attr.importPstn.isdnType == 4">
                    ${fn:escapeXml(imDef:imGetText('FakeISDN'))}
                </s:elseif>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.importDate'))}" property="createDate" format="{0,date,dd/MM/yyyy}" sortable="false" headerClass="sortable" style="text-align:center; width:100px; "/>

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" style="text-align:center; width:50px" headerClass="tct">
                <s:if test = "#attr.editAndDeletePstnToDLU">
                    <s:url action="processPSTNAction!gotoEditPstn" id="URL">
<!--                        CSRF Hieptd-->
                        <s:token/>
                        <s:param name="stockIsdnPstnId" value="#attr.importPstn.id"/>
                        <s:param name="struts.token.name" value="'struts.token'"/>
                        <s:param name="struts.token" value="struts.token"/>

                    </s:url>
                    <a class="cursorHand" onclick="openDialog('<s:property escapeJavaScript="true"  value="#URL"/>',800, 600, true)"/>
                    <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="MSG.generates.edit"/>" alt="<s:text name="MSG.generates.edit"/>"/>
                </s:if>
                <s:else>
                    <img src="${contextPath}/share/img/edit_icon_1.jpg" title="" alt="<s:text name="MSG.generates.edit"/>"/>
                </s:else>
            </display:column>
            <%-- <display:column title="Xóa" sortable="false" style="text-align:center; width:50px" headerClass="tct">
                 <s:if test = "#attr.editAndDeletePstnToDLU && (#attr.importPstn.status == 1 || #attr.importPstn.status == 3)">
                     <s:a onclick="delStockIsdnPstn('%{#attr.importPstn.id}')">
                         <img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa" alt="Xóa"/>
                     </s:a>
                 </s:if>
                 <s:else>
                     <img src="${contextPath}/share/img/delete_icon_1.jpg" title="" alt="Sửa"/>
                 </s:else>
             </display:column>--%>

            <display:column title="<input id = 'checkAllId' type='checkbox' onclick=\"selectCbAll()\">" sortable="false" headerClass="tct" style="width:50px; text-align:center">
                <s:if test = "#attr.editAndDeletePstnToDLU">
                    <s:checkbox  name="form.selectedItems" id="checkBoxId%{#attr.importPstn.id}"
                                 theme="simple" fieldValue="%{#attr.importPstn.id}"
                                 onclick="checkSelectCbAll();"/>
                </s:if>
            </display:column>
        </display:table>
    </div>
</s:if>
<s:else>
    <table class="dataTable">
        <tr>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.pstnNumber'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.DLU'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.module'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.Rack'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.Shelf'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.slot'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.port'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.DAS'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.device.type'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.status'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.isdn_type'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.importDate'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.delete'))}</th>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
    </table>
</s:else>


<script language="javascript">


    delStockIsdnPstn = function(stockIsdnPstnId) {
        //if(confirm("Bạn có chắc chắn muốn xoá DLU gán cho số PSTN này không?"))
        if(confirm("'<s:text name="MSG.ISN.043"/>'")) {
            gotoAction('divListRange', "${contextPath}/processPSTNAction!deletePstn.do?stockIsdnPstnId=" + stockIsdnPstnId+"&"+ token.getTokenParamString(),"ImportPSTNForm");
            $('messageList').innerHTML= '<s:text name="MSG.ISN.044"/>';
            //$("messageList").innerHTML="Xóa thành công";
        }
    }

    selectCbAll = function(){
        selectAll("checkAllId", "form.selectedItems", "checkBoxId");
    }

    checkSelectCbAll = function(){
        checkSelectAll("checkAllId", "form.selectedItems", "checkBoxId");
    }

    checkSelected =function() {
        var rowId = document.getElementsByName('form.selectedItems');
        var cbId="checkBoxId";
        var i=0;
        var count=0;

        for(i = 0; i < rowId.length; i++){
            if (document.getElementById(cbId+rowId[i].value).checked==true) {
                count++;
            }
        }
        //alert(rowId);
        if (count==0) {
            alert(getUnicodeMsg('<s:text name="MSG.ISN.060"/>'));
            //alert ("Chưa chọn bản ghi để xóa");
            return false;
        }
        return true;
    }

</script>


