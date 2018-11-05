package cwh.order.producer.service;


import cwh.order.producer.util.HandleException;

import java.util.Map;

/**
 * Created by 曹文豪 on 2018/11/1.
 */
public interface ConfigService {

    String getToken(String code) throws HandleException;

    void sendPhoneKey(String openid, String phoneNumber) throws HandleException;

    void bindingPhone(String openid, String phoneNumber, String verifyCode) throws HandleException;

    String getBindPhone(String openid);

    void configStore(Map<String, Object> map) throws HandleException;

    int checkStoreNameByRegion(String region, String name) throws HandleException;
}
