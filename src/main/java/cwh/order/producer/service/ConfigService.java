package cwh.order.producer.service;


import cwh.order.producer.util.HandleException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by 曹文豪 on 2018/11/1.
 */
public interface ConfigService {

    String getToken(String code, String appid) throws HandleException;

    void sendPhoneKey(String openid, String phoneNumber) throws HandleException;

    void bindingPhone(String openid, String phoneNumber, String verifyCode) throws HandleException;

    String getBindPhone(String openid);

    String getRegion(String openid);

    String getAddress(String openid);

    String getHeadPictureUrl(String openid);

    String getStoreName(String openid);

    String getDescription(String openid);

    List<String> getStorePictureUrls(String openid);

    int checkStoreNameByRegion(String openid, String name) throws HandleException;

    Map<String, String> getStore(String openid);

    Map<String, Object> getSettingStatus(String openid);

    void configRegion(String openid, String region) throws HandleException;

    void configAddress(String openid, String address) throws HandleException;

    void configStoreName(String openid, String store_name) throws HandleException;

    void configDescription(String openid, String description) throws HandleException;

    void configHeadPicture(String openid, MultipartFile file) throws HandleException;

    void configStorePicture(String openid, MultipartFile file) throws HandleException;

    void deleteStorePictures(String openid, String urls) throws HandleException;

    void initiateApproval(String openid) throws HandleException;

    int getBusiness(String openid);

    void configBusiness(String openid, int business) throws HandleException;

    void addDAAN(String openid, String daan);
}
