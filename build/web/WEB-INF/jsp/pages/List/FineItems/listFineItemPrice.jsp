<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listFineItemPrice
    Created on : Sep 14, 2009, 3:14:10 PM
    Author     : TheTM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listFineItemPrices", request.getSession().getAttribute("listFineItemPrices"));
%>

<div style="width:100%; height:420px; overflow:auto;">
    <sx:div id="divListFineItemPrices">
        <fieldset class="imFieldset">
            <legend>Danh sách phí lý do phạt</legend>
            <div style="height:360px; overflow:auto; ">
                <s:if test="#request.listFineItemPrices != null && #request.listFineItemPrices.size() > 0">
                    <display:table id="tblListFineItemPrices" name="listFineItemPrices"
                                   class="dataTable" cellpadding="1" cellspacing="1" >
                        <display:column title="STT" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListFineItemPrices_rowNum)}</display:column>
                        <display:column property="price" title="Phí" format="{0,number,#,###}" style="text-align:right" sortable="false" headerClass="tct"/>
                        <display:column property="vat" title="VAT" format="{0}%" style="text-align:right" sortable="false" headerClass="tct"/>
                        <display:column title="Mô tả" sortable="false" headerClass="tct">
                            <s:property escapeJavaScript="true"  value="#attr.tblListFineItemPrices.description"/>
                        </display:column>
                        <display:column property="staDate" title="Từ ngày" format="{0,date,dd/MM/yyyy}" sortable="false" style="text-align:center" headerClass="tct"/>
                        <display:column property="endDate" title="Đến ngày" format="{0,date,dd/MM/yyyy}" sortable="false" style="text-align:center" headerClass="tct"/>
                        <display:column title="Trạng thái" sortable="false" headerClass="tct">
                            <s:if test="#attr.tblListFineItemPrices.status == 1">
                                Hiệu lực
                            </s:if>
                            <s:elseif test="#attr.tblListFineItemPrices.status == 0">
                                Không Hiệu lực
                            </s:elseif>
                        </display:column>
                        <display:column escapeXml="true"  property="userName" title="Người tạo" sortable="false" headerClass="tct"/>
                        <display:column property="createDate" title="Ngày tạo" format="{0,date,dd/MM/yyyy}" sortable="false" style="text-align:center" headerClass="tct"/>
                        <display:column title="Sửa" sortable="false" style="text-align:center" headerClass="tct">
                            <sx:a onclick="prepareEditFineItemPrice('%{#attr.tblListFineItemPrices.fineItemPriceId}')">
                                <img src="${contextPath}/share/img/edit_icon.jpg" title="Sửa phí nộp phạt" alt="Sửa"/>
                            </sx:a>
                        </display:column>
                        <display:column title="Xóa" sortable="false" style="text-align:center" headerClass="tct">
                            <sx:a onclick="deleteFineItemPrice('%{#attr.tblListFineItemPrices.fineItemPriceId}')">
                                <img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa phí nộp phạt" alt="Xóa"/>
                            </sx:a>
                        </display:column>
                    </display:table>

                    <script language="javascript">
                        //bat popup them price moi
                        prepareEditFineItemPrice = function(fineItemPriceId) {
                        <%--                            openDialog("${contextPath}/finePriceAction!prepareEditFineItemPrice.do?selectedFineItemPriceId=" + fineItemPriceId, 750, 700);
                        --%>                                    gotoAction('sxdivFineItemPrice','finePriceAction!prepareEditFineItemPrice.do?selectedFineItemPriceId=' + fineItemPriceId+"&"+ token.getTokenParamString());

                            }


                            //xoa gia hien co
                            deleteFineItemPrice = function(fineItemPriceId) {
                                if (confirm('Bạn có chắc chắn muốn xóa phí này không?')) {
                                    gotoAction('sxdivFineItemPrice', "${contextPath}/finePriceAction!deleteFineItemPrice.do?selectedFineItemPriceId=" + fineItemPriceId+'&'+  token.getTokenParamString());
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
                            <th class="tct">Mô tả</th>
                            <th class="tct">Từ ngày</th>
                            <th class="tct">Đến ngày</th>
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
            </div>
        </fieldset>
    </div>
</sx:div>
