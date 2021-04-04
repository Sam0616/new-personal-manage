<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>考勤添加页面</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/font.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/xadmin.css">
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/lib/layui/layui.js"
            charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/xadmin.js"></script>

    <script type="text/javascript">
        $(function () {
            $("#deptForm").submit(function () {
                var employee_name = $("#employee_name");
                var workingdays = $("#workingdays");
                var daysleave = $("#leavedays");
                var daysout = $("#daysout");
                var msg = "";
                if ("未登录" == $.trim(employee_name.val())) {
                    msg = "未登录";
                    employee_name.focus();
                } else if (!/^[0-9]*$/.test($.trim(workingdays.val()))) {
                    msg = "上班天数只能填写数字！";
                    workingdays.focus();
                } else if (!/^[0-9]*$/.test($.trim(daysleave.val()))) {
                    msg = "请假天数只能填写数字！";
                    daysleave.focus();
                } else if (!/^[0-9]*$/.test($.trim(daysout.val()))) {
                    msg = "迟到天数只能填写数字！";
                    daysout.focus();
                }
                if (msg != "") {
                    alert(msg);
                    return false;
                } else {
                    return true;
                    $("#deptForm").submit();
                }
            });
        });

        var msg = "${msg}";

        if (msg != null && msg != "") {
            layui.use('layer', function () {
                var layer = layui.layer;

                if (msg == "未登录") {
                    layer.msg('未登录!无法请假', {icon: 2, time: 1000});
                } else if (msg == "不是员工") {
                    layer.msg('不是员工，无法请假', {icon: 2, time: 1000});
                } else if (msg = "已申请") {
                    layer.msg('已申请,等待批准!', {icon: 1, time: 1000});
                } else {
                    layer.msg('身份合法!', {icon: 1, time: 1000});
                }
            });
        }
    </script>
</head>

<body>
<div class="x-body">
    <form class="layui-form" method="POST" id="deptForm"
          action="${pageContext.request.contextPath}/checkwork/leaveInsert">
        <!-- id为员工信息id（也就是全局id） -->

        <div class="layui-form-item">
            <label for="employee_name" class="layui-form-label">
                <span class="x-red">*</span>姓名
            </label>
            <div class="layui-input-inline">
                <input type="hidden" id="empId" name="empId" value="${employee.id}">
                <input type="text" id="employee_name" name="employee_name" required="" lay-verify="required"
                       autocomplete="off" class="layui-input"
                       value="${sessionScope.user_session.id != null ? employee.name:'未登录'}">
                <p class="x-red" id="employee_message">${message}</p>
            </div>
        </div>

        <div class="layui-form-item">
            <label for="leavedays" class="layui-form-label">
                <span class="x-red">*</span>请假天数
            </label>
            <div class="layui-input-inline">
                <input type="text" id="leavedays" name="leavedays" required="" lay-verify="required"
                       autocomplete="off" class="layui-input" value="${job.daysleave}">
            </div>

        </div>

        <div class="layui-form-item" >
            <label for="startdata" class="layui-form-label">
                <span class="x-red">*</span>请假时间
            </label>
            <div class="layui-input-inline">
                <input type="datetime-local" id="startdata" name="startdata" required="" lay-verify="required"
                       autocomplete="off" class="layui-input" value="${job.daysleave}">
            </div>

        </div>


        <div class="layui-form-item">
            <label for="leavetype" class="layui-form-label">
                <span class="x-red">*</span>请假类型
            </label>
            <div class="layui-input-inline">
                <select id="leavetype" name="leavetype" class="valid">
                    <c:forEach items="${leavetype_list}" var="leavetype">
                        <option value="${leavetype.id}">${leavetype.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label for="content" class="layui-form-label">
                <span class="x-red">*</span>请假原因
            </label>
            <div class="layui-input-inline">
                    <textarea type="text" id="content" name="content" rows="20" required="" lay-verify="required"
                              autocomplete="off" class="layui-input" style="height: 100px">

                    </textarea>
            </div>

        </div>


        <div class="layui-form-item">
            <label for="L_repass" class="layui-form-label">
            </label>
            <input type="submit" id="L_repass" value=" 提交" class="layui-btn" lay-filter="add" lay-submit=""/>

        </div>
    </form>
</div>
</body>

</html>