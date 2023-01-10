<%--
  Created by IntelliJ IDEA.
  User: zhangyunpeng
  Date: 2021/8/6
  Time: 8:51 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <%@include file="../../basepath.jsp"%>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="css/common.css?v=<%=Math.random()%>"/>
</head>
<body>
    <span class="layui-breadcrumb">
    <%--  <a href="">首页</a>--%>
      <a href="room-area/list">机房区域管理</a>
      <a > 机房区域新增 </a>
    </span>
    <form class="layui-form" lay-filter="form" action="room-area/add" method="post">
        <div class="layui-form-item">
            <label class="layui-form-label">区域名称</label>
            <div class="layui-input-block">
                <input class="layui-input"
                       required
                       name="areaName"
                       lay-verify="required"
                       lay-verType="tips"
                       lay-reqText="机房区域名称不可以为空"
                       placeholder="请输入机房区域名称"
                       autocomplete="off"
                       type="text">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
    <script type="text/javascript" src="layui/layui.js"></script>
    <script type="text/javascript"  src="js/jquery.min.js"></script>
    <script type="text/javascript"  src="js/qs.min.js"></script>
</body>
</html>
