<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listCommFees
    Created on : Apr 1, 2009, 11:20:33 AM
    Author     : DatPV-TungTV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">


<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("listCommItemFeeChannel", request.getSession().getAttribute("listCommItemFeeChannel"));
%>

<!-- phan hien thi message -->
<div>
    <tags:displayResult property="messageList" id="messageList" type="key"/>
</div>


<s:if test="#request.listCommItemFeeChannel != null && #request.listCommItemFeeChannel.size() != 0">
    <display:table id="tblCommFeeItems" name="listCommItemFeeChannel"
                   class="dataTable" cellpadding="1" cellspacing="1">
        <display:column title="STT" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblCommFeeItems_rowNum)}</display:column>
        <display:column property="fee" title="Giá" format="{0,number,#,###}" style="text-align:right" sortable="false" headerClass="tct"/>
        <display:column property="vat" title="VAT" format="{0}%" style="text-align:right" sortable="false" headerClass="tct"/>
        <display:column property="staDate" title="Ngày bắt đầu" format="{0,date,dd/MM/yyyy}" sortable="false" headerClass="tct"/>
        <display:column property="endDate" title="Ngày kết thúc" format="{0,date,dd/MM/yyyy}" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  property="channelTypeName" title="Đối tượng áp phí" sortable="false" headerClass="tct"/>
        <display:column title="Trạng thái" sortable="false" headerClass="tct">
            <s:if test = "#attr.tblCommFeeItems.status == 1">
                Hiệu lực
            </s:if>
            <s:elseif test = "#attr.tblCommFeeItems.status == 2">
                Không hiệu lực
            </s:elseif>
        </display:column>
        <display:column escapeXml="true"  property="userCreate" title="Người tạo" sortable="false" headerClass="tct"/>
        <display:column property="createDate" title="Ngày tạo" format="{0,date,dd/MM/yyyy}" sortable="false" headerClass="tct"/>
        <display:column title="Sửa" sortable="false" style="text-align:center" headerClass="tct">
            <sx:a onclick="prepareEditCommFees('%{#attr.tblCommFeeItems.itemFeeChannelId}')">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="Sửa" alt="Sửa"/>
            </sx:a>
        </display:column>
        <display:column title="Xóa" sortable="false" style="text-align:center" headerClass="tct">
            <sx:a onclick="deleteCommFees('%{#attr.tblCommFeeItems.itemFeeChannelId}')">
                <img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa" alt="Xóa"/>
            </sx:a>
        </display:column>
    </display:table>

    <script language="javascript">
        //bat popup sua thong tin Fee
        prepareEditCommFees = function(itemFeeChannelId) {
            openDialog("${contextPath}/commItemsAction!prepareEditCommFees.do?selectedItemFeeChannelId=" + itemFeeChannelId, 750, 500, true);
        }

        //xoa gia hien co
        deleteCommFees = function(itemFeeChannelId) {
            if (confirm('Bạn có chắc chắn muốn xóa phí khoản mục này không?')) {
                gotoAction('divListCommFees', "${contextPath}/commItemsAction!deleteCommFees.do?selectedItemFeeChannelId=" + itemFeeChannelId+"&"+token.getTokenParamString());
            }
        }

    </script>

</s:if>
<s:else>
    <table class="dataTable">
        <tr>
            <th class="tct">STT</th>
            <th class="tct">Giá</th>
            <th class="tct">VAT</th>
            <th class="tct">Ngày bắt đầu</th>
            <th class="tct">Ngày kết thúc</th>
            <th class="tct">Đối tượng áp phí</th>
            <th class="tct">Trạng thái</th>
            <th class="tct">Người tạo</th>
            <th class="tct">Ngày tạo</th>
            <th class="tct">Sửa</th>
            <th class="tct">Xóa</th>
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
        </tr>
    </table>
</s:else>


