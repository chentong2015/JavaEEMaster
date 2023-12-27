package com.servlet.main.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RequestFilter implements Filter {

    private static final String ACCESS_CONNECTION = "index.jsp";

    /**
     * 针对于Http Request的过滤器：根据请求中Session来重定向页面
     * FilterChain是Servlet容器提供给开发人员的一个对象，它提供了对资源的已过滤请求的"调用链的视图"
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletReponse = (HttpServletResponse) response;
        HttpSession session = servletRequest.getSession();
        String requestFullPath = servletRequest.getRequestURI();
        String requestSubPath = requestFullPath.substring(servletRequest.getContextPath().length());

        if (session.getAttribute("user_session") == null) {
            // 拿到请求的分发器，然后重定向到指定的页面，同时带有之前的request和response
            request.getRequestDispatcher(ACCESS_CONNECTION).forward(servletRequest, servletReponse);
        } else {
            // 可以根据从Session中获取的用户信息来判断
            // User user = (User) session.getAttribute("user");
            if (requestSubPath.startsWith("/admin")) {
                // 调用调用链中的下一个过滤器
                chain.doFilter(servletRequest, servletReponse);
            } else {
                servletReponse.sendRedirect("../connection");
            }
        }
    }

    // 直接设置response返回的HTTP Status Code
    // 可以在filter过滤的过程中, 对捕获的异常做额外的处理
    public void doChainFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            chain.doFilter(request, response);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
        } catch (ServletException | IOException exception) {
            exception.printStackTrace();
        }
    }
}
