<%--
  Created by IntelliJ IDEA.
  User: zhangyunpeng
  Date: 2021/8/6
  Time: 8:51 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
      <a href="room/list">机房管理</a>
      <a > 设置状态 </a>
    </span>
<form class="layui-form" lay-filter="form" action="room/edit" method="post">
    <input type="hidden" name="id" value="${formData.id}"/>
    <div class="layui-form-item">
        <label class="layui-form-label">机房名称</label>
        <div class="layui-input-block">
            <input class="layui-input"
                   required
                   name="name"
                   readonly
                   autocomplete="off"
                   value="${formData.name}"
                   type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">机房状态</label>
        <div class="layui-input-block">
            <select class="layui-select" name="status"
                    required
                    lay-verify="required"
                    lay-verType="tips"
                    lay-reqText="请选择用户"
                    lay-search
                    autocomplete="off">
                <option value="">请选择</option>
                <option value="0" ${formData.status == 0?'selected':''}>待开放</option>
                <option value="1" ${formData.status == 1?'selected':''}>运行中</option>
                <option value="2" ${formData.status == 2?'selected':''}>已关闭</option>
            </select>
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
