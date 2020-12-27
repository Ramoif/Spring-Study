<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>书籍展示页面</title>
    <%--引入BootStrap--%>
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <%--清除浮动--%>
        <div class="row clearfix">
            <%--把屏幕等分12份--%>
            <div class="col-md-12 column">
                <div class="page-header">
                    <h1>
                        <small>书籍列表 —— </small>
                    </h1>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 column">
                    <%--指定到bookController的RequestMapping函数2--%>
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/toAddBook" style="margin-left: 20px;">新增书籍</a>
                </div>
                <div class="col-md-4 column"></div>
                <div class="col-md-4 column">
                    <%--查询书籍--%>
                    <form action="${pageContext.request.contextPath}/book/queryBook" method="post" class="form-inline" style="float: right; margin-right: 20px;">
                        <input type="text" class="form-control" name="queryBookName" placeholder="请输入要查询的书籍名称">
                        <input type="submit" value="查询" class="btn btn-primary">
                    </form>
                </div>
            </div>
        </div>

        <div class="row clearfix">
            <div class="col-md-12 column">
                <%--这里引用了而两个效果：hover特效，隔行变色--%>
                <table class="table table-hover table-striped">
                    <thead>
                        <tr>
                            <th>书籍编号</th>
                            <th>书籍名称</th>
                            <th>书籍数量</th>
                            <th>书籍详情</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <%--书籍是从数据库中查询出来，从list中遍历出来。(foreach)--%>
                    <tbody>
                        <c:forEach var="book" items="${list}">
                            <tr>
                                <td>${book.bookID}</td>
                                <td>${book.bookName}</td>
                                <td>${book.bookCounts}</td>
                                <td>${book.detail}</td>
                                <td>
                                    <%--这里?id=来实现传递参数给修改页面显示数据--%>
                                    <a href="${pageContext.request.contextPath}/book/toUpdate?id=${book.bookID}">修改</a>
                                    &nbsp; | &nbsp;
                                    <a href="${pageContext.request.contextPath}/book/deleteBook/${book.bookID}">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

    </div>

</body>
</html>
