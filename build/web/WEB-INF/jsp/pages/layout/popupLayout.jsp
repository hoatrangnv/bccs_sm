<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/config/vsa-defs.tld" prefix="vp"%>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<html xhtml="true">
    <head>
        <sx:head parseContent="true" />
        <title><tiles:getAsString name="title"/></title>
        <tiles:insertTemplate template="/WEB-INF/jsp/pages/layout/external_CSS_JS.jsp" />

        <!-- [BEGIN] MY CSS & MY JS -->

    <comment>
    </comment>
    <!-- [END] MY CSS & MY JS -->

    <script type="text/javascript" language="javascript">
        initWidget();
        // disableStart();
    </script>
</head>
<body id="minwidth" topmargin="0">
    <sx:div id="popupBody">
        <div id="token">
        </div>

        <%--div id="border-top"  topmargin="0">
            <div id="border-top"  topmargin="0">
                <div>
                    <div>
                        <tiles:insertAttribute name="header"/>
                    </div>
                </div>
            </div>
        </div--%>
        <table align="center" width="100%" border="0">
            <tr>
                <td></td>
                <td >
                    <div align="center" id="updateSuccess" style="position:absolute;display:none;border:1px solid #0e0;background-color:#efe;padding:2px;margin-top:8px;width:300px;font:normal 12px Arial;color:red"><h2>Thao tác được thực hiện thành công</h2></div>
                </td>
                <td ></td>
            </tr>
        </table>
        <s:if test="msgResultJavaScript == null">
            <table align="center" width="100%" border="0">
                <tr>
                    <td></td>
                    <td >
                        <div align="center" id="msgResultJavaScript" style="position:absolute;display:none;border:1px solid #0e0;background-color:#efe;padding:2px;margin-top:8px;width:300px;font:normal 12px Arial;color:red">
                            <s:set value="msgResultJavaScript" var="msg" />

                            <h2><s:property escapeJavaScript="true"  value="#msg"/></h2></div>
                    </td>
                    <td ></td>
                </tr>
            </table>
            <script language="javaScript">
                // showMessage();
            </script>
        </s:if>
        <div id="content-box">
            <div class="border">
                <div class="padding">
                    <div id="toolbar-box">
                        <div class="t">
                            <div class="t">
                                <div class="t"></div>
                            </div>
                        </div>
                        <div class="m">
                            <div class="toolbar" id="toolbar">
                                <table class="toolbar"><tbody><tr>
                                        </tr></tbody></table>
                            </div>
                            <!-- [BEGIN] Header -->
                            <div>
                                <div style="float:left" class="header" id="myHeader">
                                    
                                    <%--tiles:insertAttribute name="title"/--%>
                                </div>
                                <div style="float:right">
                                    <table class="toolbar">
                                        <tbody>
                                            <tr>
                                                <td class="button" align="left" width="100px">
                                                    <s:a href="" onclick="window.close()">
                                                        <span style="background-image: url('<%=StringEscapeUtils.escapeHtml(request.getContextPath())%>/share/img/home.png');" title="Về trang chủ">
                                                        </span>
                                                        <!--                                                            Về trang chủ-->
                                                        <s:text name="MSG.HEADER.GO.HOME"/>
                                                    </s:a>
                                                </td>
                                                <td></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <!-- [END] Header-->
                            <div class="clr"></div>
                        </div>
                        <div class="b">
                            <div class="b">
                                <div class="b"></div>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>

                    <div id="element-box">
                        <div class="t">
                            <div class="t">
                                <div class="t"></div>
                            </div>
                        </div>
                        <div class="m" align="center">
                            <tags:processing/>
                            <tiles:insertAttribute name="body"/>
                        </div>
                        <div class="b">
                            <div class="b">
                                <div class="b"></div>
                            </div>
                        </div>
                    </div>
                    <noscript>Must be enable JavaScript</noscript>
                    <div class="clr"></div>
                </div>
                <div class="clr"></div>
            </div>
        </div>
        <div id="border-bottom">
            <div>
                <div>
                </div>
            </div>
        </div>
        <div id="footer">
            <tiles:insertAttribute name="footer"/>
        </div>
    </sx:div>
    <script>

        var pageId =  window.opener.pageId;
        if(document.getElementById("pageId")!=null){
            document.getElementById("pageId").value=pageId;
        }


    </script>

<!--    TrongLV-->
     <script>
            var title='<tiles:getAsString name="title" ignore="true"/>';
            if(title!=null && title!=''){

            <s:set name="key">
                <tiles:getAsString name='title' ignore="true"/>
            </s:set>

                   document.title=getUnicodeMsg('<s:property escapeJavaScript="true"  value="getText(#attr.key)"/>');
                   document.getElementById("myHeader").innerHTML=getUnicodeMsg('<s:property escapeJavaScript="true"  value="getText(#attr.key)"/>');


                   //                    document.title=getUnicodeMsg('<s_:property value="getError(#attr.key)"/>');
                   //                    document.getElementById("myHeader").innerHTML=getUnicodeMsg('<s_:property value="getError(#attr.key)"/>');

                   //document.title=getUnicodeMsg('<s_:text name="#attr.key"/>');
                   //document.getElementById("myHeader").innerHTML=getUnicodeMsg('<s_:text name="#attr.key"/>');
                    

                   //document.title=title;
                   //document.getElementById("myHeader").innerHTML=title;
                }
        </script>
</body>

</html>
