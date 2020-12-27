<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
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
                    <small>添加书籍</small>
                </h1>
            </div>
        </div>
    </div>
        <%--添加书籍的请求form表单提交，这里的input标签中的name属性一定要和pojo中Books类的成员名称对应--%>
    <form action="${pageContext.request.contextPath}/book/updateBook" method="post">
        <div class="form-group">
            <label for="bkname">书籍名称：</label>
            <%--添加required属性，强制填写内容后提交。--%>
            <input type="text" name="bookName" class="form-control" id="bkname" required>
        </div>
        <div class="form-group">
            <label for="bkcount">书籍数量：</label>
            <input type="text" name="bookCounts" class="form-control" id="bkcount" required>
        </div>
        <div class="form-group">
            <label for="bkdetail">书籍描述：</label>
            <input type="text" name="detail" class="form-control" id="bkdetail" required>
        </div>
        <div class="form-group">
            <input type="submit" class="form-control btn-info" value="添加">
        </div>
    </form>
</div>
</body>
</html>
