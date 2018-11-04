package cwh.order.producer.service;


import cwh.order.producer.util.HandleException;

/**
 * Created by 曹文豪 on 2018/11/1.
 */
public interface LoginService {

    String getToken(String code) throws HandleException;

    void sendPhoneKey(String openid,String phoneNumber) throws HandleException;

    void bindingPhone(String openid,String phoneNumber,String verifyCode) throws HandleException;

    String getBindPhone(String openid);
}
