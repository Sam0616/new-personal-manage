<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<%@ page isELIgnored="false" %>


<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>所有员工培训信息页面</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/public/logo.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/font.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/xadmin.css">
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/lib/layui/layui.js"
            charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/xadmin.js"></script>
    <script type="text/javascript">
        $(function () {


        })
    </script>

</head>

<body>
<div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="">首页</a>
        <a>
          <cite>培训详情</cite></a>
      </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right"
       href="${pageContext.request.contextPath }/train/admintrainlist" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
</div>
<div class="x-body">
    <div class="layui-row" style="" align="center">
        <form class="layui-form layui-col-md12 x-so" method="get"
              action="${pageContext.request.contextPath }/train/admintrainlist">

            <input type="text" name="content" style="width:50%;" placeholder="请输入查找内容" autocomplete="off"
                   class="layui-input">
            <button class="layui-btn"><i class="layui-icon">&#xe615;</i></button>
        </form>
    </div>
    <table class="layui-table">
        <thead>
        <tr>
            <th>
                <div class="layui-unselect header layui-form-checkbox" lay-skin="primary"><i
                        class="layui-icon">&#xe605;</i></div>
            </th>
            <th>姓名</th>
            <th>职位</th>
            <th>部门</th>
            <th>培训内容</th>
            <th>开始日期</th>
            <th>结束日期</th>
            <th>培训时长</th>
            <th>完成情况</th>
            <th>成绩评定</th>
            <th>创建时间</th>
            <th>操作</th>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="train" varStatus="stat">
            <tr>
                <td>
                    <div class="layui-unselect layui-form-checkbox" lay-skin="primary" data-id='2'><i
                            class="layui-icon">&#xe605;</i></div>
                </td>
                <td>${train.ename}</td>
                <td>${train.jname}</td>
                <td>${train.dname}</td>
                <td>${train.content}</td>
                <td>${train.startdate}</td>
                <td>${train.enddate}</td>
                <td>${train.totallength}天</td>
                <td>${train.completionName}</td>
                <td>${train.grade}</td>
                <td><f:formatDate value="${train.traintime}"/></td>
                <td class="td-manage">

                    <%--<a title="编辑" href='${pageContext.request.contextPath}/train/toedit?id=${train.id}'>
                        <i class="layui-icon">&#xe642;</i>
                    </a>--%>

                    <a title="删除" onclick="member_del(this,'${train.id}')" href="javascript:;">
                        <i class="layui-icon">&#xe640;</i>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>

    </table>
    <!-- 分页标签 -->
    <div style="margin-left: 400px;" id="count1">

    </div>
    <div style="margin-left: 500px;" id="count2">
        <p style="color: rgb(0,97,222)">共查询到<font color="red">${count}</font>条数据</p>
    </div>


</div>
<script>

    /*用户-删除*/
    function member_del(obj, id) {
        layer.confirm('确认要删除吗？', function (index) {
            //发异步删除数据
            //等以后再使用异步，这里先使用
            $.get("${pageContext.request.contextPath}/train/delete?id=" + id);
            $(obj).parents("tr").remove();
            layer.msg('已删除!', {icon: 1, time: 1000});
        });
    }


    layui.use('laypage', function () {
        var laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'count1'
            , count:${count}//数据总数，从服务端得到
            , curr:${pageNo}
            , limit:${pageSize}
            , jump: function (obj, first) {
                //obj包含了当前分页的所有参数，比如：
                console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                console.log(obj.limit); //得到每页显示的条数

                //首次不执行
                if (!first) {
                    //do something
                    //调用后台
                    window.location.href = "${pageContext.request.contextPath}/contract/list?pageNo=" + obj.curr + "&pageSize=" + obj.limit;
                }
            }
        });
    });
</script>

</body>

</html>