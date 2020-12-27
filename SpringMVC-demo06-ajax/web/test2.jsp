<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/statics/js/jquery-3.5.1.js"></script>
    <script>
        $(function () {
            $("#btn").click(function () {
                jQuery.post("${pageContext.request.contextPath}/a2",function (data) {
                    console.log(data);
                })
            })
        });
    </script>
</head>
<input type="button" value="加载数据" id="btn">
<body>
    <table>
        <tr>
            <td>姓名</td>
            <td>年龄</td>
            <td>性别</td>
        </tr>
        <tbody>
        <%--数据来源后台--%>

        </tbody>
    </table>
</body>
</html>
