<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <html:form action = "/authenticateAction.do" styleId="frm">
            <html:hidden property="className" value="AuthenticateDAO"/>
            <html:hidden property="methodName" value="actionLogin"/>
            <script>
                document.getElementById('frm').submit();
            </script>
        </html:form>
    </body>
</html>
