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
      <a href="user/dept/list">部门员工管理</a>
      <a > 关联部门 </a>
    </span>
<form class="layui-form" lay-filter="form" action="user/bind/dept" method="post">
    <input type="hidden" name="id" value="${formData.id}"/>
    <div class="layui-form-item">
        <label class="layui-form-label">账号</label>
        <div class="layui-input-block">
            <input class="layui-input"
                   required
                   name="username"
                   value="${formData.username}"
                   readonly
                   autocomplete="off"
                   type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">部门</label>
        <div class="layui-input-block" id="select-container" lay-verify="checkRequired">
            <c:if test="${select != ''}">
                ${select}
            </c:if>
            <c:if test="${select == ''}">
                <select id="dept-base" lay-filter="dept-base" class="layui-select"></select>
            </c:if>

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
<script type="text/javascript">
    function getDeptListByPid(el,pid,callback){
        $.ajax({
            url:'dept/list/pid',
            data:{
                pid:pid
            },
            method:'post',
            success:function(res){
                if(res.code == 200){
                    if(res.data.length>0){
                        $(el).append('<option value="" >请选择</option>')
                        res.data.forEach(function(item){
                            $(el).append('<option value="'+item.id+'" data-isLeaf="'+item.isLeaf+'">'+item.name+'</option>')
                        })
                        layui.form.render('select')
                    }else{
                        $(el).remove()
                    }
                }
                if(callback){
                    callback(res)
                }
            }
        })
    }
    ${select == ""?"getDeptListByPid('#dept-base',-1,function(res){})":""}

    var baseId = 'dept-base'
    var count = 99999;
    function removeNext(item){
        if(item.next().length != 0){
            removeNext(item.next())
        }else{
            item.remove()
        }
    }
    layui.use('form',function(){
        var form = layui.form;
        form.on('select(dept-base)',function(data){
            var baseIndex = $(data.elem).index()
            console.log($('#select-container').children().length)
            $('#select-container').children().each(function(index,item){
                if(index>=baseIndex+2){
                    $(item).remove()
                }
            })
            $(data.elem).children().each(function(index,item){
                if(data.value == item.value){
                    // console.dir(item.dataset.isleaf)

                    if(item.dataset.isleaf == 0){
                        var newId = (baseId+count)
                        // console.log($(data.elem).index())
                        // console.log($(data.elem).next().next())

                        $(data.elem).after('<select id="'+newId+'" class="layui-select select-after" lay-filter="dept-base"></select>')
                        count++
                        getDeptListByPid('#'+newId,item.value,function(res){
                            if(res.data.length == 0){
                                console.log($(data.elem))
                                $('select').removeAttr('name')
                                $(data.elem).attr('name','deptId')
                            }
                        })
                    }else{
                        console.log($(data.elem))
                        $('select').removeAttr('name')
                        $(data.elem).attr('name','deptId')
                    }
                }
            })

        })
        form.verify({
            checkRequired:function(){
                if($('[name="deptId"]').length == 0||$('[name="deptId"]').val()==''){
                    return "请至少选择一个部门"
                }else{
                    return false
                }

            }
        })
    })
</script>
</body>
</html>
