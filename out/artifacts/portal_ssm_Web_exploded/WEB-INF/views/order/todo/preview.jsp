<%--
  Created by IntelliJ IDEA.
  User: zhangyunpeng
  Date: 2021/8/6
  Time: 8:51 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
      <a href="order/todo/list">待办管理</a>
      <a > 预览</a>
    </span>
<form class="layui-form" lay-filter="form" action="order/todo/edit" method="post">
    <input type="hidden" name="id" value="${formData.id}"/>
    <div class="layui-form-item">
        <label class="layui-form-label">故障设备</label>
        <div class="layui-input-block">
            <select class="layui-select" name="equipmentId"
                    lay-search
            >

                <c:forEach items="${equipmentList}" var="item">
                    <c:if test="${formData.equipmentId==item.id}">
                        <option value="${item.id}" selected>${item.name}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">故障描述</label>
        <div class="layui-input-block">
                <textarea class="layui-textarea" name="description"
                          placeholder="请输入故障描述"
                          readonly
                >${formData.description}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
                <textarea class="layui-textarea" name="remark"
                          placeholder="请输入故障备注"
                          readonly
                >${formData.remark}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">处理人</label>
        <div class="layui-input-block">
            <select class="layui-select" name="targetUserId"
                    lay-search
            >
                <c:forEach items="${userList}" var="item">
                    <c:if test="${formData.targetUserId == item.id}">
                        <option value="${item.id}" selected>${item.username}</option>
                    </c:if>

                </c:forEach>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">故障时间</label>
        <div class="layui-input-block">
            <input class="layui-input" placeholder="请选择时间"
                   readonly type="text" name="problemTime" id="time"
                   value="<fmt:formatDate value="${formData.problemTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>"
            />

        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">故障原因</label>
        <div class="layui-input-block">
                <textarea class="layui-textarea" name="reason"
                          placeholder="请输入故障原因"
                    readonly
                >${formData.reason}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">处理结果</label>
        <div class="layui-input-block">
                <textarea class="layui-textarea" name="handleResult"
                          placeholder="请输入处理结果"
                        readonly
                >${formData.handleResult}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">工单状态</label>
        <div class="layui-input-block">
            <select class="layui-select" name="orderStatusId"
                    lay-search
            >
                <c:forEach items="${orderStatusList}" var="item">
                    <c:if test="${formData.orderStatusId == item.id}">
                        <option value="${item.id}" selected >${item.statusName}</option>
                    </c:if>



                </c:forEach>
            </select>
        </div>
    </div>
</form>
<script type="text/javascript" src="layui/layui.js"></script>
<script type="text/javascript"  src="js/jquery.min.js"></script>
<script type="text/javascript"  src="js/qs.min.js"></script>
<%--    <script>--%>
<%--        layui.use('laydate', function(){--%>
<%--            var laydate = layui.laydate;--%>

<%--            //执行一个laydate实例--%>
<%--            laydate.render({--%>
<%--                elem: '#time', //指定元素--%>
<%--                type:'datetime',--%>
<%--                format:'yyyy-MM-dd HH:mm:ss'--%>
<%--            });--%>
<%--        });--%>
<%--    </script>--%>
</body>
</html>
