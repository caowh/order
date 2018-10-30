package cwh.order.producer.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 曹文豪 on 2018/6/15.
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean responseFilter() {
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        frBean.setOrder(1);
        frBean.setFilter(new ResponseFilter());
        frBean.addUrlPatterns("/*");
        return frBean;
    }

    @Bean
    public FilterRegistrationBean requestAutoFilter() {
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        frBean.setOrder(2);
        frBean.setFilter(new RequestAuthFilter());
        frBean.addUrlPatterns("/*");
        return frBean;
    }

}