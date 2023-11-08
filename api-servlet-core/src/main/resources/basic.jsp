<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
    <!-- SrciptLet: a block of Java Code 不推荐将Java嵌入到HTML页面中 -->
    <%
      String[] messages = (String[]) request.getAttribute("helloMessages");
      String result = "";
      for(String message: messages) {
    	  result += message + " ";
      }
      out.print(result); // 直接显示在界面
    %>
</body>
</html>