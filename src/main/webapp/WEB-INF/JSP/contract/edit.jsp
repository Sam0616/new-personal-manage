<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>考勤编辑页面</title>
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
                var msg = "";
                if (!/^[\u4E00-\u9FA5]{2,4}$/.test($.trim(employee_name.val()))) {
                    msg = "姓名格式不正确！！！";
                    employee_name.focus();
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
    </script>
</head>

<body>
<div class="x-body">
    <form class="layui-form" method="POST" id="deptForm" action="${pageContext.request.contextPath}/contract/edit">
        <input type="hidden" name="id" id="id" value="${contract.id}">
        <div class="layui-form-item">
            <label for="username" class="layui-form-label">
                <span class="x-red">*</span>姓名
            </label>
            <div class="layui-input-inline">
                <input type="text" id="employee_name" name="empName" required="" lay-verify="required"
                       disabled="disabled"
                       autocomplete="off" class="layui-input" value="${empName}">
                <p class="x-red">请联系管理员修改姓名等信息</p>
            </div>

        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">
                <span class="x-red">*</span>职位
            </label>
            <div class="layui-input-inline">
                <select id="job_id" name="jobId" class="valid" disabled="disabled">
                    <c:forEach items="${requestScope.job_list}" var="job">
                        <c:choose>
                            <c:when test="${contract.jobId == job.id }">
                                <option value="${job.id }" selected="selected">${job.name }</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${job.id }">${job.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label for="username" class="layui-form-label">
                <span class="x-red">*</span>部门
            </label>
            <div class="layui-input-inline">
                <select name="deptId" class="valid" disabled="disabled">

                    <c:forEach items="${dept_list}" var="job">
                        <c:choose>
                            <c:when test="${contract.jobId==job.id}">
                                <option value="${job.id}" selected="selected">${job.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${job.id}">${job.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                </select>
            </div>
        </div>


        <div class="layui-form-item" >
            <label for="username" class="layui-form-label">
                <span class="x-red">*</span>劳动合同
            </label>
            <div class="layui-input-inline">
                <select id="trainContract_id" name="trainContract" class="valid">
                    <c:forEach items="${traincontract_list}" var="job">
                        <c:choose>
                            <c:when test="${contract.trainContract == job.id }">
                                <option value="${job.id }" selected="selected">${job.name }</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${job.id }">${job.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>

        </div>


        <div class="layui-form-item" >
            <label for="username" class="layui-form-label">
                <span class="x-red">*</span>培训合同
            </label>
            <div class="layui-input-inline">
                <select id="laborContract_id" name="laborContract" class="valid">
                    <c:forEach items="${laborcontract_list}" var="job">
                        <c:choose>
                            <c:when test="${contract.laborContract == job.id }">
                                <option value="${job.id }" selected="selected">${job.name }</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${job.id }">${job.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>

        </div>
        <div class="layui-form-item" >
            <label for="username" class="layui-form-label">
                <span class="x-red">*</span>保密，竞业合同
            </label>
            <div class="layui-input-inline">
                <select id="confidentialityContract_id" name="confidentialityContract" class="valid">
                    <c:forEach items="${confidentialitycontract_list}" var="job">
                        <c:choose>
                            <c:when test="${contract.confidentialityContract == job.id }">
                                <option value="${job.id }" selected="selected">${job.name }</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${job.id }">${job.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">
            </label>
            <input type="submit" value=" 提交" class="layui-btn" lay-filter="add" lay-submit=""/>
        </div>
    </form>
</div>
<%--<script>--%>
<%--    //监听提交--%>
<%--    form.on('submit(add)', function (data) {--%>
<%--        console.log(data);--%>
<%--        //发异步，把数据提交给php--%>
<%--        layer.alert("修改成功", {icon: 6}, function () {--%>
<%--            document.getElementById('deptForm').submit();--%>
<%--            // 获得frame索引--%>
<%--            var index = parent.layer.getFrameIndex(window.name);--%>
<%--            //关闭当前frame--%>
<%--            parent.layer.close(index);--%>

<%--        });--%>
<%--        return false;--%>
<%--    });--%>
<%--</script>--%>

</body>

</html>