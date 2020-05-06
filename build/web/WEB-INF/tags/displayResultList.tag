<%-- 
    Document   : DislayResult
    Created on : Dec 1, 2008, 5:38:05 PM
    Author     : ThanhNC
    Purpose: Hien thi thong bao sau khi thuc hien 1 action
--%>

<%@tag import="com.viettel.im.database.BO.ShowMessage"%>
<%@tag import="java.util.List"%>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@attribute name="property" required="true"%>
<%@attribute name="type"%>

<%
            String displayType = "";
            if (type != null && !"".equals(type)) {
                displayType = type;
            }
            request.setAttribute("displayType", displayType);
            List<ShowMessage> lstMess = (List<ShowMessage>) request.getAttribute(property);
            if (lstMess != null && !lstMess.isEmpty()) {
                for (int i = 0; i < lstMess.size(); i++) {
                    ShowMessage showMessage = lstMess.get(i);
                    String message = showMessage.getMessage();
                    List params = showMessage.getParams();                    
                    request.setAttribute("msg", message);
%>

<div  align="center" class="txtError">
    <s:if test="#request.msg != null && #request.msg !=''">
        <s:text name="%{#request.msg}">
            <%
                                //phan them tham so vao message neu co truyen theo gia tri cua tham so
                                if (params != null && !params.isEmpty()) {
                                    for (int j = 0; j < params.size(); j++) {                                        
                                        request.setAttribute("strParamValue", params.get(j).toString());
            %>
            <s:param name="value" value="%{#request.strParamValue}"/>
            <%
                                    }
                                }
            %>
        </s:text>
    </s:if>
    <s:else>
        <s:property value="#request.msg"/>
    </s:else>
</div>

<%
                }
            }


%>




