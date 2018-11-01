package cwh.order.producer.service;


import javax.servlet.http.HttpServletRequest;

/**
 * Created by 曹文豪 on 2018/11/1.
 */
public interface LoginService {

    String getToken(HttpServletRequest request) throws Exception;
}
