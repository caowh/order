package cwh.order.producer.filter;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 曹文豪 on 2018/6/15.
 */
public class ResponseFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletResponse instanceof HttpServletResponse) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "content-type");
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            if (httpServletRequest.getMethod().equals("OPTIONS")) {
                httpServletResponse.setStatus(200);
                httpServletResponse.setHeader("Access-Control-Max-Age", "1728000"); //20
                httpServletResponse.getWriter().write("OPTIONS returns OK");
                return;
            }
            servletRequest.setCharacterEncoding("utf-8");
            httpServletResponse.setCharacterEncoding("utf-8");
            filterChain.doFilter(servletRequest, httpServletResponse);
        }
    }


    @Override
    public void destroy() {

    }
}
