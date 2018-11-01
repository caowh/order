package cwh.order.producer.filter;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 曹文豪 on 2018/6/15.
 */
@Component
public class AuthFilter implements Filter {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletResponse instanceof HttpServletResponse) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "content-type,token");
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            if (httpServletRequest.getMethod().equals("OPTIONS")) {
                httpServletResponse.setStatus(200);
                httpServletResponse.setHeader("Access-Control-Max-Age", "1728000"); //20
                httpServletResponse.getWriter().write("OPTIONS returns OK");
                return;
            }
            httpServletRequest.setCharacterEncoding("utf-8");
            httpServletResponse.setCharacterEncoding("utf-8");
            String token = httpServletRequest.getHeader("token");

            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }


    @Override
    public void destroy() {

    }
}
