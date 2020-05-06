<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listDiscountGroups
    Created on : Apr 18, 2009, 2:17:37 PM
    Author     : tamdt1
--%>

<%--
    Notes   : danh sach cac nhom chiet khau
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
            String pageId = request.getParameter("pageId");
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listDiscountGroups", request.getSession().getAttribute("listDiscountGroups" + pageId));
%>

<tags:imPanel title="Tìm kiếm thông tin chiết khấu">
    <div>
        <tags:displayResult id="lstDiscountGroupMessage" property="lstDiscountGroupMessage" propertyValue="lstDiscountGroupMessageParam" type="key"/>
    </div>
    <div class="divHasBorder">
        <s:form action="discountGroupAction" theme="simple" enctype="multipart/form-data"  method="post" id="discountGroupForm">
<s:token/>

            <s:hidden name="discountGroupForm.discountGroupId" id="discountGroupForm.discountGroupId"/>

            <table class="inputTbl4Col">
                <tr>
                    <td style="width:30%; ">Tên nhóm chiết khấu</td>
                    <td class="oddColumn" style="width:60%; ">
                        <s:textfield name="discountGroupForm.name" id="name" maxlength="50" cssClass="txtInputFull"/>
                    </td>

                    <td style="width:85px; ">
                        <div align="right">
                            <%--<sx:submit parseContent="true" executeScripts="true"
                                       formId="discountGroupForm" loadingText="Đang thực hiện..."
                                       cssStyle="width:80px;"
                                       showLoadingText="true" targets="divDisplayInfo"
                                       value="Tìm kiếm"  beforeNotifyTopics="discountGroupAction/searchDiscountGroup"/>--%>
                            <tags:submit id="btnSearch"
                                         formId="discountGroupForm"
                                         showLoadingText="true"
                                         cssStyle="width:80px;"
                                         targets="divDisplayInfo"
                                         value="Tìm kiếm"
                                         preAction="discountGroupAction!searchDiscountGroup.do"/>

                        </div>
                    </td>
                    <td style="width:85px; ">
                        <div align="right">
                            <tags:submit id="btnAdd"
                                         formId="discountGroupForm"
                                         showLoadingText="true"
                                         cssStyle="width:80px;"
                                         targets="divDisplayInfo"
                                         value="Thêm mới"
                                         preAction="discountGroupAction!prepareAddDiscountGroup.do"/>
                            <%--<sx:submit parseContent="true" executeScripts="true"
                                       formId="discountGroupForm" loadingText="Đang thực hiện..."
                                       cssStyle="width:80px;"
                                       showLoadingText="true" targets="divDisplayInfo"
                                       value="Thêm mới"  beforeNotifyTopics="discountGroupAction/prepareAddDiscountGroup"/>--%>
                        </div>
                    </td>
                </tr>
            </table>
        </s:form>
    </div>

    <div id="divDiscountGroup">
        <fieldset class="imFieldset">
            <legend>Danh sách mặt hàng</legend>
            <s:if test="#request.listDiscountGroups != null && #request.listDiscountGroups.size() > 0">
                <display:table id="tblListDiscountGroups" name="listDiscountGroups"
                               requestURI="${contextPath}/discountGroupAction!pageNagivatorDiscountGroup.do"
                               pagesize="15"
                               targets="divDisplayInfo"
                               class="dataTable" cellpadding="1" cellspacing="1" >
                    <display:column title="STT" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListDiscountGroups_rowNum)}</display:column>
                    <display:column title="Tên nhóm chiết khấu" sortable="false" headerClass="tct">
                        <s:property escapeJavaScript="true"  value="#attr.tblListDiscountGroups.name"/>
                    </display:column>
                    <display:column escapeXml="true"  property="discountPolicyName" title="Chính sách chiết khấu" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true"  property="telecomServiceName" title="Dịch vụ viễn thông" sortable="false" headerClass="tct"/>
                    <display:column title="Trạng thái" sortable="false" headerClass="tct">
                        <s:if test="#attr.tblListDiscountGroups.status.equals(1L)">
                            Hiệu lực
                        </s:if>
                        <s:else>
                            Hết hiệu lực
                        </s:else>
                    </display:column>
                    <display:column escapeXml="true"  property="notes" title="Ghi chú" sortable="false" headerClass="tct"/>
                    <display:column title="Chi tiết" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                        <s:url action="discountGroupAction!displayDiscountGroup" id="URL1">
                            <s:param name="selectedDiscountGroupId" value="#attr.tblListDiscountGroups.discountGroupId"/>
                        </s:url>
                        <sx:a targets="divDisplayInfo" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                            <img src="${contextPath}/share/img/accept.png" title="Thông tin chi tiết nhóm CK" alt="Chi tiết"/>
                        </sx:a>
                    </display:column>
                </display:table>
            </s:if>
            <s:else>
                <table class="dataTable">
                    <tr>
                        <th class="tct">STT</th>
                        <th class="tct">Tên nhóm chiết khấu</th>
                        <th class="tct">Trạng thái</th>
                        <th class="tct">Ghi chú</th>
                        <th class="tct">Chi tiết</th>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>
            </s:else>
        </fieldset>
    </div>



    <script language="javascript">
        //lang nghe, xu ly su kien khi click nut "Them" (them discountGroup)
        dojo.event.topic.subscribe("discountGroupAction/prepareAddDiscountGroup", function(event, widget){
            setAction(widget,'divDiscountGroup','discountGroupAction!prepareAddDiscountGroup.do?'+  token.getTokenParamString());
        });
        dojo.event.topic.subscribe("discountGroupAction/searchDiscountGroup", function(event, widget){
            setAction(widget,'divDiscountGroup','discountGroupAction!searchDiscountGroup.do?'+  token.getTokenParamString());
        });
    </script>

</tags:imPanel>
