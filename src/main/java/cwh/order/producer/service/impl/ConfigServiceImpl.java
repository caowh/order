package cwh.order.producer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import cwh.order.producer.dao.DAANDao;
import cwh.order.producer.dao.SellUserDao;
import cwh.order.producer.dao.StorePictureDao;
import cwh.order.producer.model.DAAN;
import cwh.order.producer.model.SellUser;
import cwh.order.producer.model.StorePicture;
import cwh.order.producer.service.ConfigService;
import cwh.order.producer.util.Constant;
import cwh.order.producer.util.FileUtil;
import cwh.order.producer.util.HandleException;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by 曹文豪 on 2018/11/1.
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    private final OkHttpClient client = new OkHttpClient();
    private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private SellUserDao sellUserDao;
    @Resource
    private StorePictureDao storePictureDao;
    @Resource
    private DAANDao daanDao;

    @Override
    public String getToken(String code, String appid) throws HandleException {
        String url;
        if (appid == null || appid.equals("")) {
            url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code"
                    , Constant.APPID, Constant.APPSECRET, code);
        } else {
            url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code"
                    , "wxe04421d5815c3259", "cb34637b94c0274bcac143cea554b72f", code);
        }
        Request get = new Request.Builder().url(url).build();
        try {
            ResponseBody responseBody = client.newCall(get).execute().body();
            JSONObject jsonObject = JSONObject.parseObject(responseBody.string());
            Object errcode = jsonObject.get("errcode");
            if (errcode == null) {
                String openid = jsonObject.getString("openid");
                String uuid = UUID.randomUUID().toString().toLowerCase();
                redisTemplate.opsForValue().set(uuid, openid, Constant.TIMEOUT, TimeUnit.MINUTES);
                return uuid;
            } else {
                logger.error("getToken get errcode,code is {},error is {}", code, jsonObject.getString("errmsg"));
                throw new HandleException(Constant.ERROR);
            }
        } catch (IOException e) {
            logger.error("getToken raise IOException,code is {},error is {}", code, e.getMessage());
            throw new HandleException(Constant.ERROR);
        }
    }

    @Override
    public void sendPhoneKey(String openid, String phoneNumber) throws HandleException {
        String phone = this.getBindPhone(openid);
        if (phone.equals(phoneNumber)) {
            throw new HandleException("手机号不能和变更前一致");
        }
        Object obj = redisTemplate.opsForValue().get(openid + Constant.separator + Constant.phoneKey);
        if (obj != null) {
            String[] strings = obj.toString().split(Constant.separator);
            if (phoneNumber.equals(strings[0])) {
                if (System.currentTimeMillis() - Long.parseLong(strings[2]) <= 60 * 1000) {
                    throw new HandleException("手机号码刚获取过验证码，请一分钟后再试");
                }
            } else {
                if (System.currentTimeMillis() - Long.parseLong(strings[2]) <= 10 * 1000) {
                    throw new HandleException("获取验证码过于频繁，请10S后再试");
                }
            }
        }
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
        final String accessKeyId = "LTAIvSdYeu6DFyXK";//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = "Gb0XOQoH4TfmQ0kEyMtpPU6NTokEWq";//你的accessKeySecret，参考本文档步骤2
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            logger.error("sendPhoneKey addEndpoint raise ClientException,openid is {},phoneNumber is {},error is {}", openid, phoneNumber, e.getErrMsg());
            throw new HandleException(Constant.ERROR);
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        request.setPhoneNumbers(phoneNumber);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("点不点商家版");
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode("SMS_150172310");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);//生成短信验证码
        request.setTemplateParam("{\"code\":\"" + verifyCode + "\"}");
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            logger.error("sendPhoneKey getAcsResponse raise ClientException,openid is {},phoneNumber is {},error is {}", openid, phoneNumber, e.getErrMsg());
            throw new HandleException(Constant.ERROR);
        }
        String code = sendSmsResponse.getCode();
        if (code != null && code.equals("OK")) {
            redisTemplate.opsForValue().set(openid + Constant.separator + Constant.phoneKey,
                    phoneNumber + Constant.separator + verifyCode + Constant.separator + System.currentTimeMillis(),
                    5, TimeUnit.MINUTES);
        } else {
            logger.error("sendPhoneKey getCode not ok,openid is {},phoneNumber is {},code is {}", openid, phoneNumber, code);
            String error = Constant.ERROR;
            switch (code) {
                case "isv.BUSINESS_LIMIT_CONTROL":
                    error = "发送失败，过于频繁（5条/小时，10条/天）";
                    break;
                case "isv.BLACK_KEY_CONTROL_LIMIT":
                    error = "发送失败，黑名单管控";
                    break;
                case "isv.AMOUNT_NOT_ENOUGH":
                    error = "发送失败，账户余额不足";
                    break;
            }
            throw new HandleException(error);
        }
    }

    @Override
    public void bindingPhone(String openid, String phoneNumber, String verifyCode) throws HandleException {
        Object obj = redisTemplate.opsForValue().get(openid + Constant.separator + Constant.phoneKey);
        if (obj == null) {
            throw new HandleException("验证码无效");
        }
        String[] strings = obj.toString().split(Constant.separator);
        if (!phoneNumber.equals(strings[0])) {
            throw new HandleException("验证码无效");
        }
        if (!verifyCode.equals(strings[1])) {
            throw new HandleException("验证码错误");
        }
        this.bindingPhone(openid, phoneNumber);
    }

    @Override
    public String getBindPhone(String openid) {
        String phone = sellUserDao.queryPhone(openid);
        return phone == null ? "" : phone;
    }

    @Override
    public String getRegion(String openid) {
        String region = sellUserDao.getRegion(openid);
        return region == null ? "" : region;
    }

    @Override
    public String getAddress(String openid) {
        String address = sellUserDao.getAddress(openid);
        return address == null ? "" : address;
    }

    @Override
    public String getHeadPictureUrl(String openid) {
        String headPicture_url = sellUserDao.getHeadPictureUrl(openid);
        return headPicture_url == null ? "" : headPicture_url;
    }

    @Override
    public String getStoreName(String openid) {
        String storeName = sellUserDao.getStoreName(openid);
        return storeName == null ? "" : storeName;
    }

    @Override
    public String getDescription(String openid) {
        String description = sellUserDao.getDescription(openid);
        return description == null ? "" : description;
    }

    @Override
    public List<String> getStorePictureUrls(String openid) {
        List<String> list = storePictureDao.getStorePictureUrls(openid);
        return list == null ? new ArrayList<>() : list;
    }

    @Override
    @Transactional
    public int checkStoreNameByRegion(String openid, String name) throws HandleException {
        String region = checkStoreName(openid, name);
        SellUser sellUser = new SellUser();
        sellUser.setRegion(region);
        sellUser.setStore_name(name);
        return sellUserDao.queryNameCountByRegion(sellUser);
    }

    @Transactional
    private String checkStoreName(String openid, String name) throws HandleException {
        if (name == null || name.equals("")) {
            throw new HandleException("名称不能为空");
        }
        if (name.length() > 20) {
            throw new HandleException("名称长度不能超过20个字符");
        }
        String region = sellUserDao.getRegion(openid);
        if (region == null) {
            throw new HandleException("请先设置地区");
        }
        String storeName = sellUserDao.getStoreName(openid);
        if (storeName != null && storeName.equals(name)) {
            throw new HandleException("名称修改前后不能一致");
        }
        return region;
    }

    @Override
    public Map<String, String> getStore(String openid) {
        Map<String, String> map = new HashMap<>();
        SellUser sellUser = sellUserDao.getStore(openid);
        if (sellUser != null) {
            String address = sellUser.getAddress() == null ? "" : sellUser.getAddress();
            map.put("address", address);
            String region = sellUser.getRegion() == null ? "" : sellUser.getRegion();
            map.put("region", region);
            String storeName = sellUser.getStore_name() == null ? "" : sellUser.getStore_name();
            map.put("storeName", storeName);
            String headPicture_url = sellUser.getHeadPictureUrl() == null ? "" : sellUser.getHeadPictureUrl();
            map.put("headPictureUrl", headPicture_url);
            String description = sellUser.getDescription() == null ? "" : sellUser.getDescription();
            map.put("description", description);
            map.put("storePictureUrls", String.valueOf(storePictureDao.getStorePictureCount(openid)));
        }
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> getSettingStatus(String openid) {
        Map<String, Object> map = new HashMap<>();
        Boolean store = checkStore(openid);
        map.put("phone", this.getBindPhone(openid));
        map.put("store", store);
        Map<String, Object> approvalMap = new HashMap<>();
        int approval = sellUserDao.getApproval(openid);
        approvalMap.put("status", approval);
        if (approval == 3) {
            approvalMap.put("reason", sellUserDao.getApprovalMsg(openid));
        }
        map.put("approval", approvalMap);
        map.put("business", sellUserDao.getBusiness(openid) == 1);
        return map;
    }

    private Boolean checkStore(String openid) {
        SellUser sellUser = sellUserDao.getStore(openid);
        Boolean store = false;
        if (sellUser != null && sellUser.getAddress() != null && sellUser.getRegion() != null && sellUser.getDescription() != null
                && sellUser.getStore_name() != null && sellUser.getHeadPictureUrl() != null) {
            store = true;
        }
        return store;
    }

    @Override
    public void configRegion(String openid, String region) throws HandleException {
        if (region == null || region.equals("")) {
            throw new HandleException("地区不能为空");
        }
        SellUser sellUser = new SellUser();
        sellUser.setOpenid(openid);
        sellUser.setRegion(region);
        sellUserDao.updateRegion(sellUser);
    }

    @Override
    public void configAddress(String openid, String address) throws HandleException {
        if (address == null || address.equals("")) {
            throw new HandleException("地址不能为空");
        }
        if (address.length() > 30) {
            throw new HandleException("地址长度不能超过30个字符");
        }
        SellUser sellUser = new SellUser();
        sellUser.setOpenid(openid);
        sellUser.setAddress(address);
        sellUserDao.updateAddress(sellUser);
    }

    @Override
    @Transactional
    public void configStoreName(String openid, String store_name) throws HandleException {
        checkStoreName(openid, store_name);
        SellUser sellUser = new SellUser();
        sellUser.setOpenid(openid);
        sellUser.setStore_name(store_name);
        sellUserDao.updateStoreName(sellUser);
    }

    @Override
    public void configDescription(String openid, String description) throws HandleException {
        if (description == null || description.equals("")) {
            throw new HandleException("简介不能为空");
        }
        if (description.length() > 100) {
            throw new HandleException("简介长度不能超过100个字符");
        }
        SellUser sellUser = new SellUser();
        sellUser.setOpenid(openid);
        sellUser.setDescription(description);
        sellUserDao.updateDescription(sellUser);
    }

    @Override
    @Transactional
    public void configHeadPicture(String openid, MultipartFile file) throws HandleException {
        if (file == null) {
            throw new HandleException("头像不能为空");
        }
        String url = sellUserDao.getHeadPictureUrl(openid);
        SellUser sellUser = new SellUser();
        sellUser.setOpenid(openid);
        try {
            sellUser.setHeadPictureUrl(FileUtil.save(file));
        } catch (IOException e) {
            logger.error("configHeadPicture throw IOException,openid is {},filename is {},error is {}", openid, file.getOriginalFilename(), e.getMessage());
            throw new HandleException(Constant.ERROR);
        }
        sellUserDao.updateHeadPictureUrl(sellUser);
        if (url != null) {
            if (!FileUtil.delete(url)) {
                logger.warn("delete file not success,openid is {},url is {}", openid, url);
            }
        }
    }

    @Override
    @Transactional
    public void configStorePicture(String openid, MultipartFile file) throws HandleException {
        if (file == null) {
            throw new HandleException("图片不能为空");
        }
        if (storePictureDao.getStorePictureCount(openid) >= 6) {
            throw new HandleException("最多只能添加六张图片");
        }
        StorePicture storePicture = new StorePicture();
        try {
            storePicture.setPic_url(FileUtil.save(file));
        } catch (IOException e) {
            logger.error("configHeadPicture throw IOException,openid is {},filename is {},error is {}", openid, file.getOriginalFilename(), e.getMessage());
            throw new HandleException(Constant.ERROR);
        }
        storePicture.setOpenid(openid);
        storePictureDao.insert(storePicture);
    }

    @Override
    @Transactional
    public void deleteStorePictures(String openid, String urls) throws HandleException {
        if (urls == null || urls.equals("")) {
            throw new HandleException("请至少选择一张图片");
        }
        String[] arr = urls.split(",");
        for (String url : arr) {
            StorePicture storePicture = new StorePicture();
            storePicture.setOpenid(openid);
            storePicture.setPic_url(url);
            storePictureDao.delete(storePicture);
            if (!FileUtil.delete(url)) {
                logger.warn("delete file not success,openid is {},url is {}", openid, url);
            }
        }
    }

    @Override
    @Transactional
    public void initiateApproval(String openid) throws HandleException {
        if (!checkStore(openid)) {
            throw new HandleException("请先完善门店信息");
        }
        int approval = sellUserDao.getApproval(openid);
        if (approval == 1) {
            throw new HandleException("审批中，请等待");
        } else if (approval == 2) {
            throw new HandleException("审批已通过");
        }
        SellUser sellUser = new SellUser();
        sellUser.setOpenid(openid);
        sellUser.setApproval(1);
        sellUserDao.updateApproval(sellUser);
    }

    @Override
    public int getBusiness(String openid) {
        return sellUserDao.getBusiness(openid);
    }

    @Override
    public void configBusiness(String openid, int business) throws HandleException {
        if (business != 0 && business != 1) {
            throw new HandleException("不支持该操作");
        }
        SellUser sellUser = new SellUser();
        sellUser.setOpenid(openid);
        sellUser.setBusiness(business);
        sellUserDao.updateBusiness(sellUser);
    }

    @Override
    public void addDAAN(String openid, String daan) {
        DAAN daan1 = new DAAN();
        daan1.setD_data(daan);
        daan1.setOpenid(openid);
        daan1.setD_time(new Date());
        daanDao.insert(daan1);
    }

    @Transactional(rollbackFor = {Exception.class})
    private void bindingPhone(String openid, String phoneNumber) {
        String phone = sellUserDao.queryPhone(openid);
        SellUser sellUser = new SellUser();
        sellUser.setOpenid(openid);
        sellUser.setPhone(phoneNumber);
        if (phone == null) {
            sellUserDao.insert(sellUser);
        } else {
            sellUserDao.updatePhone(sellUser);
        }
    }

}
