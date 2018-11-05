package cwh.order.producer.service;


import cwh.order.producer.util.HandleException;

import java.util.List;
import java.util.Map;

/**
 * Created by 曹文豪 on 2018/11/1.
 */
public interface ConfigService {

    String getToken(String code) throws HandleException;

    void sendPhoneKey(String openid, String phoneNumber) throws HandleException;

    void bindingPhone(String openid, String phoneNumber, String verifyCode) throws HandleException;

    String getBindPhone(String openid);

    Map<String, String> getRegionAddress(String openid);

    String getHeadPictureUrl(String openid);

    String getStoreName(String openid);

    String getDescription(String openid);

    List<String> getStorePictureUrls(String openid);

    void configStore(Map<String, Object> map) throws HandleException;

    int checkStoreNameByRegion(String region, String name) throws HandleException;

    Map<String,String> getStore(String openid);

    Map<String,Object> getSettingStatus(String openid);
}
