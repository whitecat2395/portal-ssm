<%--
  Created by IntelliJ IDEA.
  User: zhangyunpeng
  Date: 2021/8/6
  Time: 8:51 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="../../basepath.jsp"%>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="css/common.css?v=<%=Math.random()%>"/>
</head>
<body>
<span class="layui-breadcrumb">
      <a href="room/list">机房管理</a>
      <a href="">机房列表</a>
    </span>
<table id="t" lay-filter="table"></table>
<script type="text/javascript" src="layui/layui.js"></script>
<script type="text/javascript"  src="js/jquery.min.js"></script>
<script type="text/javascript"  src="js/qs.min.js"></script>
<script id="query-form" type="text/html">
    <form class="layui-form" id="form">
        <div class="layui-inline">
            <label class="layui-form-label" style="width: auto">名称</label>
            <div class="layui-input-inline">
                <input class="layui-input"
                       name="name" type="text" placeholder="请输入机房名称"
                       value="{{d.where.name}}"
                />
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label" style="width: auto">电话</label>
            <div class="layui-input-inline">
                <input class="layui-input"
                       name="phone" type="text" placeholder="请输入责任人电话"
                       value="{{d.where.phone}}"
                />
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label" style="width: auto">区域</label>
            <div class="layui-input-inline">
                <select name="areaId" class="layui-select">
                    <option value="">请选择</option>
                    <c:forEach items="${areaList}" var="item">
                        <option value="${item.id}" {{d.where.areaId == ${item.id}?'selected':''}} >${item.areaName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <shiro:hasPermission name="permission:query">
                <button type="button"
                        lay-event="query"
                        class="layui-btn ">查询</button>
            </shiro:hasPermission>
            <shiro:hasPermission name="permission:insert">
                <a href="room/add/page" class="layui-btn ">新增</a>
            </shiro:hasPermission>
        </div>
    </form>
</script>
<script type="text/html" id="tool">
    <shiro:hasPermission name="permission:update">
        <a href="room/edit/page?id={{d.id}}" class="layui-btn layui-btn-warm layui-btn-xs" >修改</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="permission:delete">
        <button type="button" lay-event="delete"
                class="layui-btn layui-btn-danger layui-btn-xs">删除</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="permission:update">
        <a href="room/bind/user/page?id={{d.id}}" class="layui-btn  layui-btn-xs" >关联责任人</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="permission:update">
        <a href="room/set/status?id={{d.id}}" class="layui-btn  layui-btn-normal layui-btn-xs" >设置状态</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="permission:update">
        <a href="room/equipment/list?roomId={{d.id}}" class="layui-btn  layui-btn-primary layui-btn-xs" >设备管理</a>
    </shiro:hasPermission>
</script>
<script type="text/html" id="status-tool">
    {{# if(d.status == 0){ }}
        <span class="layui-badge layui-bg-orange">待开放</span>
    {{# }else if(d.status == 1){ }}
        <span class="layui-badge layui-bg-green">运行中</span>
    {{# }else if(d.status == 2){ }}
         <span class="layui-badge">已关闭</span>
    {{# } }}
</script>
<script type="text/javascript">
    layui.use('table',function(){
        var table = layui.table
        table.init('table',{
            elem: '#t',
            height: 'full-80',
            autoSort: false ,
            toolbar: '#query-form',
            cols:[[
                {field:'id',title:'主键',sort: true},
                {field:'name',title:'机房名称'},
                {field:'areaName',title:'机房区域'},
                {field:'username',title:'责任人'},
                {field:'phone',title:'责任人电话'},
                {field:'status',title:'机房状态',templet: '#status-tool'},
                {field:'size',title:'机房面积'},
                {field:'remark',title:'备注'},
                {title:'操作',templet: '#tool',width:400}
            ]],
            page:true,
            url:'room/list/page',
            response: {
                statusCode: 200
            },
            request: {
                pageName: 'pno', //页码的参数名称，默认：page
                limitName: 'psize' //每页数据量的参数名，默认：limit
            },
            where:{
                name:'',
                areaId:'',
                phone:''
            }

        })
        table.on('toolbar(table)',function(obj){
            if(obj.event == 'query'){
                var queryForm = $('#form').serialize()
                var queryFormObj = Qs.parse(queryForm)
                console.log(queryFormObj)
                table.reload('t',{
                    where:queryFormObj
                })
            }
        })
        var layer = layui.layer
        table.on('tool(table)',function(obj){

            //当点击了删除按钮时触发的事件
            if(obj.event == 'delete'){
                //获取本行数据的id
                var id = obj.data.id
                layer.confirm('正在删除当前数据是否继续?',{
                    icon:3,
                    title:'提示'
                },function(index){
                    location.href = 'room/delete?id='+id
                    layer.close(index)
                })
            }
        })
        table.on('sort(table)',function(obj){
            console.log(obj)
            var queryForm = $('#form').serialize()
            var queryFormObj = Qs.parse(queryForm)
            table.reload('t',{
                initSort: obj,
                where:{
                    ...queryFormObj,
                    sortField:obj.field,
                    sortType:obj.type
                }
            })
        })
    })
</script>
</body>
</html>
