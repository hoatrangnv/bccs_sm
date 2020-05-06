<%--
    Document   : imPanel
    Created on : Sep 29, 2009, 4:12:16 PM
    Author     : Doan Thanh 8 
--%>

<%@tag import="com.viettel.security.util.StringEscapeUtils"%>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="title" rtexprvalue="true"%>

<%-- any content can be specified here e.g.: --%>
<%
            try {
                com.viettel.database.DAO.BaseDAOAction baseDAOAction = new com.viettel.database.DAO.BaseDAOAction();
                title = baseDAOAction.getText(title);
            } catch (Exception ex) {
            }
%>
<div class="im-panel">
    <div class="im-panel-tl">
        <div class="im-panel-tr">
            <div class="im-panel-tc">
                <div class="x-panel-header x-unselectable" style="-moz-user-select: none; text-align:left; ">
                    <span class="x-panel-header-text"><%=StringEscapeUtils.escapeHtml(title)%></span>
                </div>
            </div>
        </div>
    </div>
    <div class="x-panel-bwrap">
        <div class="x-panel-ml">
            <div class="x-panel-mr">
                <div class="x-panel-mc">
                    <jsp:doBody/>
                </div>
            </div>
        </div>
        <div class="x-panel-bl">
            <div class="x-panel-br">
                <div class="x-panel-bc">
                    <div class="x-panel-footer">
                        <div class="x-panel-btns x-panel-btns-right">
                            <div class="x-panel-fbar x-small-editor x-toolbar-layout-ct" style="width: auto;">
                            </div>
                            <!--div class="x-clear"/-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
