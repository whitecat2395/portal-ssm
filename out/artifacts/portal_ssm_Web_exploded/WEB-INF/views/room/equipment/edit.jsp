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
    <style type="text/css">
        #equipment-img img{
            width: 150px;
            height: 150px;
            border: 1px dashed #444;
            border-radius:7px ;
            object-fit: contain;
        }
    </style>
</head>
<body>
<span class="layui-breadcrumb">
    <%--  <a href="">首页</a>--%>
      <a href="equipment/list">设备管理</a>
      <a > 修改新增 </a>
    </span>
<form class="layui-form" lay-filter="form" action="equipment/edit" method="post">
    <input type="hidden" name="id" value="${formData.id}"/>
    <div class="layui-form-item">
        <label class="layui-form-label">设备名称</label>
        <div class="layui-input-block">
            <input class="layui-input"
                   required
                   name="name"
                   value="${formData.name}"
                   lay-verify="required"
                   lay-verType="tips"
                   lay-reqText="设备名称不可以为空"
                   placeholder="请输入设备名称"
                   autocomplete="off"
                   type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">设备编号</label>
        <div class="layui-input-block">
            <input class="layui-input"
                   required
                   name="equipmentNo"
                   value="${formData.equipmentNo}"
                   lay-verify="required"
                   lay-verType="tips"
                   lay-reqText="设备编号不可以为空"
                   placeholder="请输入设备编号"
                   autocomplete="off"
                   type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">设备品牌</label>
        <div class="layui-input-block">
            <select
                    name="brandId"
                    class="layui-select"
                    lay-verify="required"
                    lay-verType="tips"
                    lay-reqText="设备品牌不可以为空"
                    placeholder="请选择设备品牌"
            >
                <option value="">请选择</option>
                <c:forEach items="${equipmentBrandList}" var="item">
                    <option value="${item.id}" ${formData.brandId==item.id?'selected':''}>${item.brandName}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">设备厂家</label>
        <div class="layui-input-block">
            <input class="layui-input"
                   required
                   name="factory"
                   value="${formData.factory}"
                   lay-verify="required"
                   lay-verType="tips"
                   lay-reqText="设备厂家不可以为空"
                   placeholder="请输入设备厂家"
                   autocomplete="off"
                   type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">设备图片</label>
        <div class="layui-input-block">
            <input name="img"
                   style="visibility: hidden;height: 0;width: 0"
                   lay-verify="required"
                   lay-verType="tips"
                   lay-reqText="设备图片不可以为空"
                   autocomplete="off"
            />
            <a id="equipment-img" href="${formData.img}" target="_blank">
                <img  src="${formData.img}"/>
            </a>

            <button type="button" class="layui-btn" id="upload">
                <i class="layui-icon">&#xe67c;</i>上传图片
            </button>
            <button type="button" class="layui-btn layui-btn-danger" id="del-img">删除图片</button>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">设备描述</label>
        <div class="layui-input-block">
                <textarea
                        class="layui-textarea"
                        name="description"
                        lay-verify="required"
                        lay-verType="tips"
                        lay-reqText="设备描述不可以为空"
                        placeholder="请输入设备描述"
                        autocomplete="off"
                >${formData.description}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">设备备注</label>
        <div class="layui-input-block">
                <textarea
                        class="layui-textarea"
                        name="remark"
                        placeholder="请输入设备备注"
                        autocomplete="off"
                >${formData.remark}</textarea>
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
    $("#equipment-img img").on("error",function(){
        $(this).prop('src','image/no-img.jpeg')
    })
    layui.use('form',function(){
        var upload = layui.upload;
        var uploadInst = upload.render({
            elem: '#upload' //绑定元素
            ,url: 'file/upload' //上传接口
            ,done: function(res){
                //上传完毕回调
                console.log(res)
                if(res.code == 200){
                    $("#equipment-img").prop('href',res.data.url)
                    $("#equipment-img img").prop('src',res.data.url)
                    $('[name="img"]').val(res.data.url)
                }
            }
            ,error: function(){
                //请求异常回调
                console.log('error')
            }
        });
        $('#del-img').on('click',function(){
            console.log('del')
            //调用删除接口
            $.ajax({
                url:'file/delete',
                method:'post',
                data:{
                    url:$('[name="img"]').val()
                },
                success(res){
                    if(res.code == 200){

                        $("#equipment-img").removeProp("href")
                        $("#equipment-img img").prop('src',"")
                        $('[name="img"]').val("")
                    }
                }
            })
        })
    })
</script>
</body>
</html>
