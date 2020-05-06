
<%-- 
    Document   : addMerchandisePopupToAddListClose
    Created on : Oct 17, 2008, 5:29:06 PM
    Author     : Le Thanh Cong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<html>
    <head>
        <script type="text/javascript" language="javascript">
            window.close();
            <s:if test="#request.noUpdate == null">
                window.opener.updateStatus();
            </s:if>
        </script>
    </head>
</html>
