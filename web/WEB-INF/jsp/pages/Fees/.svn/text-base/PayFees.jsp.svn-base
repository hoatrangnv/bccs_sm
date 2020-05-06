<%-- 
    Document   : PayFees
    Created on : Feb 18, 2009, 11:43:14 AM
    Author     : tuannv1
    Modified by NamNX
--%>

<%--
    Note: Thanh toan hoa hong

--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%@ taglib uri="VTdisplaytaglib" prefix="display"%>


<%
            request.setAttribute("contextPath", request.getContextPath());
            if (request.getAttribute("payed") == null) {
                request.setAttribute("payed", request.getSession().getAttribute("payed"));
            }

%>

<s:form action="commPayFeesAction" id="payFeesForm" method="post" theme="simple">
<s:token/>

    <tags:imPanel title="Tìm kiếm khoản mục">
    
  
        <!-- phan hien thi message -->
        <div>
            <tags:displayResult id="message" property="message" propertyValue="messageParam"/>
        </div>
        
        <!-- phan thong tin khoan muc can thanh toan -->
        <table class="inputTbl6Col">
            <tr>
                <td class="label">Loại đối tượng(<font color="red">*</font>)</td>
                <td class="text">
                    <s:select name="payFeesForm.payTypeCode" id="payTypeCode"
                              cssClass="txtInputFull"
                              list="%{#request.lstChannelType != null ? #request.lstChannelType : #{}}"
                              listKey="channelTypeId" listValue="name" headerKey="" headerValue="--Chọn loại đối tượng--"/>
                </td>
                <td class="label">Mã đối tượng(<font color="red">*</font>)</td>
                <td class="text">
                    <sx:autocompleter name="payFeesForm.shopCode" id = "shopCode"
                                      cssClass="txtInputFull"
                                      href="commPayFeesAction!getShopCode.do"
                                      formId="payFeesForm"
                                      loadOnTextChange="true" loadMinimumCount="2"
                                      valueNotifyTopics="commPayFeesAction/displayShopName"/>
                </td>
                <td class="label">Tên đối tượng</td>
                <td class="text">
                    <s:textfield name="payFeesForm.shopName" id="shopName" readonly="true" maxlength="80"  cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td class="label">Chu kỳ tính(<font color="red">*</font>)</td>
                <td class="text">
                    <tags:dateChooser property="payFeesForm.billcycle" styleClass="txtInputFull"/>
                </td>
            </tr>
        </table>
        
        <!-- phan nut tac vu -->
        <div align="center" style="padding-bottom:5px; ">
            <sx:submit  parseContent="true" executeScripts="true"
                        formId="payFeesForm" loadingText="Đang thực hiện..."
                        showLoadingText="true" targets="bodyContent"
                        cssStyle="width:80px; "
                        value="Tìm kiếm"  beforeNotifyTopics="commPayFeesAction/searchCOMM"/>
        </div>
        
    </tags:imPanel>
    
    <br />
    <tags:imPanel title="Danh sách khoản mục cần thanh toán">
    
        <div id="lstCalulate">
            <jsp:include page="PayFeesResult.jsp"/>
        </div>
    </tags:imPanel>
    <br />
    <br />
    
    <tags:imPanel title="Thông tin chứng từ">
    
 
 
        <!-- phan thong tin -->
        <table class="inputTbl6Col">
            <tr>
                <td class="label">Mã phiếu(<font color="red">*</font>)</td>
                <td class="text">
                    <s:textfield name="payFeesForm.billCode" id="billCode" maxlength="80"  cssClass="txtInputFull"/>
                </td>
                <td class="label">Tên đối tượng</td>
                <td class="text">
                    <s:textfield name="payFeesForm.billObjectName" id="billObjectName" maxlength="80"  cssClass="txtInputFull"/>
                </td>
                <td class="label">Người nhận</td>
                <td class="text">
                    <s:textfield name="payFeesForm.billContactName" id="billContactName" maxlength="80"  cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                
                <td class="label">Tổng số tiền</td>
                <td class="text">
                    <s:textfield name="payFeesForm.billTotalMoney" id="billTotalMoney"  readonly="true" maxlength="80"  cssClass="txtInputFull"/>
                </td>
                <td class="label">Ngày thanh toán</td>
                <td class="text">
                    <tags:dateChooser property="payFeesForm.billPayDate" styleClass="txtInputFull"/>
                </td>
            </tr>
        </table>
        <div align="center" style="padding-bottom:5px; padding-top:10px; ">
            <s:if test="#request.lstCalulate!=null && #request.lstCalulate.size()>0">                
                <sx:submit  parseContent="true" executeScripts="true"
                            formId="payFeesForm" loadingText="Đang thực hiện..."
                            showLoadingText="true" targets="bodyContent"
                            cssStyle="width:80px; "
                            value="Thanh toán"  beforeNotifyTopics="commPayFeesAction/payCOMM"/>                                                                         
                
<%--                <sx:submit  parseContent="true" executeScripts="true"
                            formId="payFeesForm" loadingText="Đang thực hiện..."
                            showLoadingText="true" targets="bodyContent"
                            cssStyle="width:80px; " disabled="true"    
                            value="Xuất phiếu"  beforeNotifyTopics="commPayFeesAction/exportCOMM" />        --%>
             <input type="button" value="Xuất phiếu" style="width:80px;" disabled/>

            </s:if>
            <s:else>                
                <input type="button" value="Thanh toán" style="width:80px;" disabled/>
                <!--s:if test="#request.payed != null && #request.payed  != 0"-->      
                    <sx:submit  parseContent="true" executeScripts="true"
                                formId="payFeesForm" loadingText="Đang thực hiện..."
                                showLoadingText="true" targets="bodyContent"
                                cssStyle="width:80px; " 
                                value="Xuất phiếu"  beforeNotifyTopics="commPayFeesAction/export" />                                                                        
                
            </s:else>
            
        </div>
        <!-- phan link download bao cao -->
        <div align="center">
            <s:if test="payFeesForm.exportUrl !=null && payFeesForm.exportUrl!=''">
                <script>
                    window.open('${contextPath}<s:property escapeJavaScript="true"  value="payFeesForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
                </script>
                <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="payFeesForm.exportUrl"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về</a></div>
            </s:if>
        </div>
    </tags:imPanel>
    <%--sx:div id="resultArea">
        <jsp:include page="../../Fees/PayFeesReturn.jsp"></jsp:include>
    </sx:div--%>
</s:form>


<script type="text/javascript">
    
    //
    dojo.event.topic.subscribe("commPayFeesAction/displayShopName", function(value, key, text, widget){
        $('shopName').value = key;
    });
    
    //
    dojo.event.topic.subscribe("commPayFeesAction/searchCOMM", function(event, widget){
        widget.href = "commPayFeesAction!searchCOMM.do?"+ token.getTokenParamString();
    });
    
    //
    dojo.event.topic.subscribe("commPayFeesAction/payCOMM", function(event, widget){
        widget.href = "commPayFeesAction!payCOMM.do?"+ token.getTokenParamString();
    });
    dojo.event.topic.subscribe("commPayFeesAction/export", function(event, widget){        
        widget.href = "commPayFeesAction!exportCOMM.do?"+ token.getTokenParamString();
    });
    </script>




