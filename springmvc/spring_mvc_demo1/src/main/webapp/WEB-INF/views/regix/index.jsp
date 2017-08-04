<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/jquery-ui-themes-1.10.1/themes/redmond/jquery-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="${basePath}/css/ui.jqgrid.css" />
    <link rel="stylesheet" type="text/css" href="${basePath}/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${basePath}/easyui/themes/icon.css">
    <script type="text/javascript">
        var basePath = "${basePath}";
    </script>
    <script type="text/javascript" src="${basePath}/staticjs/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="${basePath}/staticjs/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="${basePath}/staticjs/jquery.jqGrid.src.js"></script>
    <script type="text/javascript" src="${basePath}/staticjs/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${basePath}/staticjs/jquery.json-2.4.min.js"></script>
    <script type="text/javascript" src="${basePath}/staticjs/jquery.cookie.js"></script>
    <script type="text/javascript" src="${basePath}/staticjs/jquery-ui-1.10.1.custom.min.js"></script>
    <script type="text/javascript" src="${basePath}/staticjs/uploadify3.2/jquery.uploadify.js"></script>
    <script type="text/javascript" src="${basePath}/staticjs/FusionCharts3.2/FusionCharts.js"></script>
    <script type="text/javascript" src="${basePath}/staticjs/jsrender.js"></script>
    <script type="text/javascript" src="${basePath}/js/regix/regix.js"></script>
    <link rel="stylesheet" href="${basePath}/staticjs/ztree/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${basePath}/staticjs/ztree/jquery.ztree.all-3.5.min.js"></script>
    <title>正则表达式</title>
</head>

<body>
   <div id="test" style="width: 100%;height: 400px;">
       <table id="regixTable"></table>
       <div id="pageTable"></div>
   </div>

</body>
</html>
