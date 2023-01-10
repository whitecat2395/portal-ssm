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
      <a href="equipment/list">设备管理</a>
      <a href="">设备列表</a>
    </span>
<table id="t" lay-filter="table"></table>
<script type="text/javascript" src="layui/layui.js"></script>
<script type="text/javascript"  src="js/jquery.min.js"></script>
<script type="text/javascript"  src="js/qs.min.js"></script>
<script id="query-form" type="text/html">
    <form class="layui-form" id="form">
        <div class="layui-inline">
            <label class="layui-form-label" style="width: auto">设备名称</label>
            <div class="layui-input-inline">
                <input class="layui-input"
                       name="name" type="text" placeholder="请输入设备名称"
                       value="{{d.where.name}}"
                />
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label" style="width: auto">设备状态</label>
            <div class="layui-input-inline">
                <select class="layui-select" name="brandId">
                    <option value="">请选择</option>
                    <c:forEach items="${equipmentBrandList}" var="item">
                        <option value="${item.id}" {{d.where.brandId==${item.id}?'selected':''}}>${item.brandName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label" style="width: auto">设备品牌</label>
            <div class="layui-input-inline">
                <select class="layui-select" name="statusId">
                    <option value="">请选择</option>
                    <c:forEach items="${equipmentStatusList}" var="item">
                        <option value="${item.id}" {{d.where.statusId==${item.id}?'selected':''}}>${item.statusName}</option>
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
                <a href="equipment/add/page" class="layui-btn ">新增</a>
            </shiro:hasPermission>
        </div>
    </form>
</script>
<script type="text/html" id="tool">
    <shiro:hasPermission name="permission:update">
        <a href="equipment/edit/page?id={{d.id}}" class="layui-btn layui-btn-warm layui-btn-xs" >修改</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="permission:update">
        <a href="equipment/set/status?id={{d.id}}" class="layui-btn layui-btn-primary layui-btn-xs" >设置状态</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="permission:delete">
        <button type="button" lay-event="delete"
                class="layui-btn layui-btn-danger layui-btn-xs">删除</button>
    </shiro:hasPermission>
</script>
<script type="text/html" id="img-templet">
    {{# if(d.img==undefined||d.img == ''){ }}
        暂无
    {{# }else{ }}
        <a href="{{d.img}}" target="_blank">
            <button class="layui-btn layui-btn-xs">预览</button>
        </a>
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
                {field:'name',title:'设备名称'},
                {field:'img',title:'设备图片',templet:'#img-templet'},
                {field:'brandName',title:'设备品牌'},
                {field:'equipmentNo',title:'设备编号'},
                {field:'insertTime',title:'创建时间'},
                {field:'description',title:'描述'},
                {field:'statusName',title:'设备状态'},
                {field:'roomName',title:'所属机房'},
                {field:'factory',title:'厂家'},
                {field:'remark',title:'备注'},
                {title:'操作',templet: '#tool',fixed:'right',minWidth:200}
            ]],
            // data:[
            //     {id:'1',sexName:'男'},
            //     {id:'2',sexName:'女'}
            // ],
            page:true,
            url:'equipment/list/page',
            response: {
                statusCode: 200
            },
            request: {
                pageName: 'pno', //页码的参数名称，默认：page
                limitName: 'psize' //每页数据量的参数名，默认：limit
            },
            where:{
                name:'',
                brandId:'',
                statusId:''
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
                console.log(id)
                layer.confirm('正在删除当前数据是否继续?',{
                    icon:3,
                    title:'提示'
                },function(index){
                    location.href = 'equipment/delete?id='+id
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
