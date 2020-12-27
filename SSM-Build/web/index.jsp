<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <style>
      a{
        /*消除下划线*/
        text-decoration: none;
        color: black;
        font-size: 18px;
      }
      h3{
        width: 180px;
        height: 38px;
        margin: 100px auto;
        text-align: center;
        /*上下居中*/
        line-height: 38px;
        background: skyblue;
        border-radius: 5px;
      }
    </style>
  </head>
  <body>
  <%--为了发布没有问题，需要使用绝对路径--%>
  <h3><a href="${pageContext.request.contextPath}/book/allBook">进入书籍页面</a></h3>
  </body>
</html>
