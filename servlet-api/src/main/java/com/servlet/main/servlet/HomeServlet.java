package com.servlet.main.servlet;

import com.jee.basic.sevice.BaseService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public HomeServlet() {
        super();
    }

    // 在Controller中使用Service
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. Create the data and add it to the request
        BaseService baseService = new BaseService();
        List<String> helloMessages = baseService.getHelloMessages();
        request.setAttribute("helloMessages", helloMessages);

        // 2. Retrieve request dispatcher 提取请求的分发器: 负责分发请求到指定的资源
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("home.jsp");

        // 3. Forward request to the view 转发请求到view界面
        requestDispatcher.forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
