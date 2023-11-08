package com.servlet.main.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 1. HttpServlet 服务端的技术: 从tomcat/lib/添加serlet-api.jar到Java Build Path dfgsdfg
// 2. WebServlet: 声明servlet
// 3. "/BasicServlet" 这里提供的路径是URL访问的子路径: http://localhost:8080/JavaEEBasic/BasicServlet
// 3. 可以选择运行在指定的web container(在这里使用tomcat) 
@WebServlet("/BasicServlet")
public class BasicServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public BasicServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 默认提供的response
        // response.getWriter().append("Served at: ").append(request.getContextPath());

        // 1. 创建对象模型，添加到request object
        String[] messages = {"hello", "this is hello message"};
        request.setAttribute("helloMessages", messages);

        // 2. Retrieve request dispatcher 提取请求的分发器: 负责分发请求到指定的资源
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("basic.jsp");

        // 3. Forward request to the view 转发请求到view界面
        requestDispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
