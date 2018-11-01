package cwh.order.producer.service;


/**
 * Created by 曹文豪 on 2018/11/1.
 */
public interface LoginService {

    String getToken(String code) throws Exception;
}
