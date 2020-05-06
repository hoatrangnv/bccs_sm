<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ListChangeInfoStaffAP
    Created on : Jul 30, 2010, 9:40:48 AM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%
            String pageId = request.getParameter("pageId");
            if (request.getAttribute("listStaff") == null) {
                request.setAttribute("listStaff", request.getSession().getAttribute("listStaff" + pageId));
            }
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("typeId", request.getSession().getAttribute("typeId" + pageId));
            request.setAttribute("flag", request.getSession().getAttribute("flag" + pageId));
            request.setAttribute("changeStatus", request.getSession().getAttribute("changeStatus" + pageId));
            request.setAttribute("changeInfo", request.getSession().getAttribute("changeInfo" + pageId));
%>
<div style="width:100%">
    <s:hidden name="AddStaffCodeCTVDBForm.staffId" id="AddStaffCodeCTVDBForm.staffId"/>
    <display:table id="coll" name="listStaff" class="dataTable" pagesize="10"
                   targets="searchArea" requestURI="changeInformationStaffAction!selectPageAP.do"
                   cellpadding="0" cellspacing="0">
        <display:column  title="${fn:escapeXml(imDef:imGetText('MES.CHL.054'))}" sortable="false" headerClass="tct" style="width:20px;text-align:center">
            <s:property escapeJavaScript="true"  value="%{#attr.coll_rowNum}"/>
        </display:column>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.074'))}" property="staffCode" style="width:130px;text-align:left"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.075'))}" property="name" style="width:240px;text-align:left"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.015'))}" property="shopCode" style="width:130px;text-align:center"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.057'))}" property="idNo" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.058'))}" property="nameChannelType" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.059'))}" property="nameManagement" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.060'))}" property="statusName" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.061'))}" sortable="false" headerClass="tct" style="width:20px;text-align:center">
            <div align="center">
                <s:url action="changeInformationStaffAction!clickStaffAP" id="URL" encode="true" escapeAmp="false">
                    <s:param name="staffId" value="#attr.coll.staffId"/>
                </s:url>
                <sx:a targets="searchArea" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                    <%--<img src="${contextPath}/share/img/accept.png" title="Cập nhật/Sửa" alt="Sửa"/>--%>
                    <img src="${contextPath}/share/img/accept.png" title="<s:property escapeJavaScript="true"  value="getError(MES.CHL.061)"/>"  alt="<s:property escapeJavaScript="true"  value="getError(MES.CHL.062)"/>"/>
                </sx:a>
            </div>
        </display:column>
        <display:footer> <tr> <td colspan="11" style="color:green"><s:property escapeJavaScript="true"  value ="getText('MSG.totalRecord')"/><s:property escapeJavaScript="true"  value="%{#request.listStaff.size()}" /></td> <tr> </display:footer>
        </display:table>

    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MES.CHL.076'))}</legend>
        <div>
            <tags:displayResult id="displayResultMsgUpdate" property="messageUpdate" propertyValue="paramMsg" type="key" />
        </div>
        <table class="inputTbl8Col">
            <sx:div id="displayParamDetail">
                <tr>
                    <%--<td class="label">Mã cửa hàng </td>--%>
                    <td class="label"><tags:label key="MES.CHL.015" /></td>
                    <td class="text">
                        <s:textfield theme="simple" name="AddStaffCodeCTVDBForm.shopCode" id="shopCode" maxlength="14" cssClass="txtInputFull" readonly="true"/>

                    </td>
                    <td class="text" colspan="2">
                        <s:textfield theme="simple" name="AddStaffCodeCTVDBForm.shopName" id="shopName" maxlength="100" cssClass="txtInputFull" readonly="true"/>
                    </td>
                    <%--<td class="label">Mã CTV_AP </td>--%>
                    <td class="label"><tags:label key="MES.CHL.074" /></td>
                    <td class="text">
                        <s:textfield name="AddStaffCodeCTVDBForm.staffCode" id="staffCode" theme="simple" maxlength="100" cssClass="txtInputFull" readonly="true"/>
                    </td>
                    <td class="text" colspan="2">
                        <s:textfield name="AddStaffCodeCTVDBForm.staffName" id="staffName" theme="simple" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <%--<td>NVQL (<font color="red">*</font>)</td>--%>
                    <td class="label"><tags:label key="MES.CHL.059" required="true"/></td>
                    <td colspan="3">
                        <tags:imSearch codeVariable="AddStaffCodeCTVDBForm.staffManagementCode" nameVariable="AddStaffCodeCTVDBForm.staffManagementName"
                                       codeLabel="MES.CHL.064" nameLabel="MES.CHL.065"
                                       searchClassName="com.viettel.im.database.DAO.ChangeInformationStaffDAO"
                                       otherParam="shopCode"
                                       readOnly="${fn:escapeXml(requestScope.changeInfo)}"
                                       searchMethodName="getListStaffManage"
                                       getNameMethod="getListStaffManageName"
                                       roleList=""/>
                    </td>
                </tr>
                <tr>
                    <%--<td class="label">Số CMT (<font color="red">*</font>)</td>--%>
                    <td class="label"><tags:label key="MES.CHL.057" required="true"/></td>
                    <td class="text">
                        <s:textfield name="AddStaffCodeCTVDBForm.idNo" theme="simple" id="idNo" maxlength="14" cssClass="txtInputFull" />
                    </td>
                    <%--<td class="label">Nơi cấp (<font color="red">*</font>)</td>--%>
                    <td class="label"><tags:label key="MES.CHL.066" required="true"/></td>
                    <td class="text">
                        <s:textfield name="AddStaffCodeCTVDBForm.makeAddress" theme="simple" id="makeAddress" maxlength="100" cssClass="txtInputFull" />
                    </td>
                    <%--<td class="label">Ngày Cấp (<font color="red">*</font>)</td>--%>
                    <td class="label"><tags:label key="MES.CHL.067" required="true"/></td>
                    <td class="text">
                        <tags:dateChooser property="AddStaffCodeCTVDBForm.makeDate"  id="makeDate"  styleClass="txtInput"  insertFrame="false"/>
                    </td>

                    <%--<td class="label">Trạng thái (<font color="red">*</font>)</td>--%>
                    <td class="label"><tags:label key="MES.CHL.060" required="true"/></td>
                    <td class="text">
                        <%--<s:select name="AddStaffCodeCTVDBForm.status"
                                  id="status"
                                  cssClass="txtInputFull"
                                  theme="simple"
                                  list="#{'1':'Hoạt động','0':'Tạm ngưng'}"
                                  />--%>
                        <tags:imSelect name="AddStaffCodeCTVDBForm.status"
                                       id="status"
                                       cssClass="txtInputFull"
                                       theme="simple"
                                       list="1:MES.CHL.068,0:MES.CHL.069"
                                       />
                    </td>
                </tr>
                <tr>
                    <%--<td class="label">Địa chỉ (<font color="red">*</font>)</td>--%>
                    <td class="label"><tags:label key="MES.CHL.070" required="true"/></td>
                    <td class="text" colspan="3">
                        <s:textfield name="AddStaffCodeCTVDBForm.address" theme="simple" id="address" maxlength="100" cssClass="txtInputFull" />
                    </td>
                    <%--<td class="label">Số điện thoại </td>--%>
                    <td class="label"><tags:label key="MES.CHL.139"/></td>
                    <td class="text">
                        <s:textfield name="AddStaffCodeCTVDBForm.isdn" theme="simple" id="isdn" maxlength="15" cssClass="txtInputFull" />
                    </td>
                    <%--<td class="label">Chính sách giá (<font color="red">*</font>)</td>
                    <td class="text">
                        <s:select name="AddStaffCodeCTVDBForm.pricePolicy"
                                  id="pricePolicy"
                                  theme="simple"
                                  disabled="true"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="--Chọn chính sách giá--"
                                  list="%{#session.listPricePolicy != null ? #session.listPricePolicy :#{}}"
                                  listKey="id.code" listValue="name"/>
                    </td>
                    <td class="label">Chính sách CK (<font color="red">*</font>)</td>
                    <td class="text">
                        <s:select name="AddStaffCodeCTVDBForm.discountPolicy"
                                  id="discountPolicy"
                                  theme="simple"
                                  disabled="true"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="--Chọn chính sách CK--"
                                  list="%{#session.listDiscountPolicy != null ? #session.listDiscountPolicy :#{}}"
                                  listKey="id.code" listValue="name"/>
                    </td>--%>

                </tr>

            </sx:div>
        </table>

        <div style="height:10px">
        </div>
        <div align="center" style="padding-bottom:5px;">
            <td class="text" colspan="8" align="center" theme="simple">
                <tags:submit id="update" confirm="false" formId="addStaffCodeCTVDBForm"
                             showLoadingText="true" targets="searchArea" value="MES.CHL.071"
                             cssStyle="width:60px;"
                             disabled="${fn:escapeXml(requestScope.changeStatus)}"
                             validateFunction="checkupdate()"
                             confirm="true" confirmText="MES.CHL.077"
                             preAction="changeInformationStaffAction!updateInfoAP.do"
                             />
            </td>
            <td class="text" colspan="8" align="center" theme="simple">
                <tags:submit id="cancel" confirm="false" formId="addStaffCodeCTVDBForm"
                             cssStyle="width:60px;"
                             disabled="${fn:escapeXml(requestScope.changeStatus)}"
                             showLoadingText="true" targets="searchArea" value="MES.CHL.073"
                             preAction="changeInformationStaffAction!cancelAP.do"
                             />
            </td>
        </div>
    </fieldset>

</div>

<script language="javascript">
    checkupdate = function(){
        var staffName = document.getElementById("staffName");
        if (trim(staffName.value).length ==0){
    <%--$('displayResultMsgUpdate').innerHTML="Bạn phải nhập tên CTV"--%>
                $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.025')"/>';
                staffName.focus();
                return false;
            }
            if (isHtmlTagFormat(trim(staffName.value))){
    <%--$('displayResultMsgUpdate').innerHTML="Tên CTV không được nhập định dạng thẻ HTML"--%>
                $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.026')"/>';
                staffName.focus();
                return false;
            }
            if (staffName.value ==null || staffName.value ==""){
    <%--$('displayResultMsgUpdate' ).innerHTML='Bạn phải nhập tên CTV';--%>
                $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.025')"/>';s
                staffName.focus();
                return false;
            }
            if (staffName.value == 'MÃ CHƯA CẬP NHẬT THÔNG TIN'){
    <%--$('displayResultMsgUpdate' ).innerHTML='Bạn không được để tên CTV mặc định';--%>                
                $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.027')"/>';
                staffName.focus();
                return false;
            }
            var staffManagementCode = document.getElementById("AddStaffCodeCTVDBForm.staffManagementCode");
            if (staffManagementCode.value ==null || staffManagementCode.value ==""){
    <%--$('displayResultMsgUpdate' ).innerHTML='Bạn phải chọn mã nhân viên quản lý';--%>
                $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.015')"/>';
                staffManagementCode.focus();
                return false;
            }
            var idNo = document.getElementById("idNo");
            if (trim(idNo.value).length ==0){
    <%--$('displayResultMsgUpdate').innerHTML="Bạn phải chọn số CMT"--%>
                $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.016')"/>';
                idNo.focus();
                return false;
            }
            if (isHtmlTagFormat(trim(idNo.value))){
    <%--$('displayResultMsgUpdate').innerHTML="Số CMT không được nhập định dạng thẻ HTML"--%>
                $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.017')"/>';
                idNo.focus();
                return false;
            }
            if (idNo.value ==null || idNo.value ==""){
    <%--$('displayResultMsgUpdate' ).innerHTML='Bạn phải chọn số CMT';--%>
                $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.016')"/>';
                idNo.focus();
                return false;
            }
            var makeAddress = document.getElementById("makeAddress");
            if (trim(makeAddress.value).length ==0){
    <%--$('displayResultMsgUpdate').innerHTML="Bạn phải nhập địa chỉ nơi cấp"--%>
                $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.018')"/>';
                makeAddress.focus();
                return false;
            }
            if (isHtmlTagFormat(trim(makeAddress.value))){
    <%--$('displayResultMsgUpdate').innerHTML="Địa chỉ không được nhập định dạng thẻ HTML"--%>
                $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.019')"/>';
                makeAddress.focus();
                return false;
            }
            if (makeAddress.value ==null || makeAddress.value ==""){
    <%--$('displayResultMsgUpdate' ).innerHTML='Bạn phải nhập địa chỉ nơi cấp';--%>
                $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.018')"/>';
                makeAddress.focus();
                return false;
            }
            var makeDate = dojo.widget.byId("makeDate");
            if(makeDate.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgUpdate' ).innerHTML='Bạn phải nhập ngày cấp CMT';--%>
                $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.020')"/>';
                makeDate.domNode.childNodes[1].focus();
                return false;
            }
            if(compareDates(GetDateSys(),makeDate.domNode.childNodes[1].value)){
    <%--$('displayResultMsgUpdate').innerHTML='Ngày cấp CMT không được lớn hơn ngày hiện tại';--%>
                $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.021')"/>';
                makeDate.domNode.childNodes[1].focus();
                return false;
            }
            if(!isDateFormat(makeDate.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgUpdate' ).innerHTML='Ngày cấp CMT chưa hợp lệ';--%>
                $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.022')"/>';
                $('makeDate').focus();
                return false;
            }
            var isdn = document.getElementById("isdn");
            isdn.value=trim(isdn.value);
            if(isdn != null && trim(isdn.value).length > 0 && !isInteger( trim(isdn.value) ) ){
    <%--$('displayResultMsgUpdate').innerHTML="Trường Isdn phải là số"--%>
                $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.023')"/>';
                isdn.focus();
                return false;
            }


    <%--var pricePolicy = document.getElementById("pricePolicy");
    if (pricePolicy.value ==null || pricePolicy.value ==""){
        $('displayResultMsgUpdate' ).innerHTML='Bạn phải chọn chính sách giá';
        pricePolicy.focus();
        return false;
    }
    var discountPolicy = document.getElementById("discountPolicy");
    if (discountPolicy.value ==null || discountPolicy.value ==""){
        $('displayResultMsgUpdate' ).innerHTML='Bạn phải chọn chính sách triết khấu';
        discountPolicy.focus();
        return false;
    }--%>
            var address = document.getElementById("address");
            if (trim(address.value).length ==0){
    <%--$('displayResultMsgUpdate').innerHTML="Bạn phải nhập địa chỉ"--%>
                $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.024')"/>';
                address.focus();
                return false;
            }
            if (isHtmlTagFormat(trim(address.value))){
    <%--$('displayResultMsgUpdate').innerHTML="Số CMT không được nhập định dạng thẻ HTML"--%>
                $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.017')"/>';
                address.focus();
                return false;
            }
            if (address.value ==null || address.value ==""){
    <%--$('displayResultMsgUpdate' ).innerHTML='Bạn phải nhập địa chỉ';--%>
                $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.024')"/>';
                address.focus();
                return false;
            }
            return true;
        }
        function GetDateSys() {
            var time = new Date();
            var dd=time.getDate();
            var mm=time.getMonth()+1;
            var yy=time.getFullYear() ;
            var temp = '';
            if (dd < 10) dd = '0' + dd;
            if (mm < 10) mm = '0' + mm;
            temp = dd + "/" + mm + "/" + yy ;
            return(temp);
        }
</script>
