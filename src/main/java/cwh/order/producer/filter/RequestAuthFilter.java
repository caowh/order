package cwh.order.producer.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.*;
import javax.servlet.FilterConfig;
import java.io.IOException;


/**
 * Created by 曹文豪 on 2018/7/13.
 */
public class RequestAuthFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestAuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
