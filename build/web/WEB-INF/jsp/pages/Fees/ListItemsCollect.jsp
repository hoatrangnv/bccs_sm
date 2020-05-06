<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<%-- 
    Document   : ListFeesCollect
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : Tuannv
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>




<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("lstItemFees", request.getSession().getAttribute("lstItemFees"));

%>


<div id="listItemFees" style="width:100%; height:300px; overflow:auto;">
    <sx:div id="displayTagFrame">
        <s:if test="#request.lstItemFees != null && #request.lstItemFees.size()>0">
            <display:table id="itemsCollect"
                           targets="displayTagFrame"
                           name="lstItemFees"
                           class="dataTable"  cellpadding="1" cellspacing="1">
                <!--display:column  title="STT" sortable="false" headerClass="tct">
                <!--s:property value="%{#attr.isdnFilter_rowNum}"/>
                <!--/display:column-->
                <!--display:column title="Mã khoản mục" property="itemId"/-->
                <display:column title="STT" headerClass="tct" sortable="false" style="width:50px; text-align:center" >
                    ${fn:escapeXml(itemsCollect_rowNum)}
                </display:column>
                <display:column title="<input id ='checkAllItemId' type='checkbox' onclick=\"selectCbItemAll()\">" sortable="false" headerClass="tct" style="width:50px; text-align:center">
                    <s:checkbox name="collectFeesForm.selectedItems" id="checkBoxItemId%{#attr.itemsCollect.itemId}"
                                theme="simple" fieldValue="%{#attr.itemsCollect.itemId}"
                                onclick="checkSelectCbAll();"/>
                </display:column>
                <%-- <display:column title="Chọn" sortable="false" style="text-align:center" headerClass="tct">
                     <s:checkbox name="collectFeesForm.selectedItems" theme="simple" id="selectedItems" fieldValue="%{#attr.itemsCollect.itemId + ':' + #attr.itemsCollect.functionName}" />
                 </display:column>--%>

                <display:column escapeXml="true"  title="Tên khoản mục" property="itemName"/>
                <display:column title="Hồ sơ"  headerClass="tct" style="text-align:left">
                    <s:if test="#attr.itemsCollect.checkedDoc == 0">Không yêu cầu hồ sơ</s:if>
                    <s:if test="#attr.itemsCollect.checkedDoc == 1">Yêu cầu hồ sơ</s:if>
                </display:column>

                <display:column title="Loại khoản mục"  headerClass="tct" style="text-align:left">
                    <s:if test="#attr.itemsCollect.reportType == 1">Phí bán hàng</s:if>
                    <s:if test="#attr.itemsCollect.reportType == 2">Khoản giảm trừ</s:if>
                </display:column>

                <display:column escapeXml="true"  title="Tên thủ tục" property="functionName"/>
                <!--display:column title="Mã bộ đếm" property="counterId"/-->
                <!--display:column title="Ngày bắt đầu" property="startDate" sortable="false" headerClass="tct" format="{0,date,dd/MM/yyyy}"/>
                <!--display:column title="Ngày hết hạn" property="endDate" sortable="false" headerClass="tct" format="{0,date,dd/MM/yyyy}"/-->

            </display:table>

        </s:if>
    </sx:div>
</div>
<script>
    selectCbItemAll = function(){
        selectAll("checkAllItemId", "collectFeesForm.selectedItems", "checkBoxItemId");
    }

    checkSelectCbItemAll = function(){
        checkSelectAll("checkAllItemId", "collectFeesForm.selectedItems", "checkBoxItemId");
    }

</script>

