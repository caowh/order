package cwh.order.producer.service.impl;

import com.alibaba.fastjson.JSONObject;
import cwh.order.producer.service.LoginService;
import cwh.order.producer.util.Constant;
import okhttp3.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by 曹文豪 on 2018/11/1.
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final OkHttpClient client = new OkHttpClient();

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String getToken(String code) throws Exception {
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code"
                , Constant.APPID, Constant.APPSECRET, code);
        Request get = new Request.Builder().url(url).build();
        try {
            ResponseBody responseBody = client.newCall(get).execute().body();
            if (responseBody == null) {
                throw new Exception("wx server return body is null");
            }
            JSONObject jsonObject = JSONObject.parseObject(responseBody.string());
            int errcode = jsonObject.getInteger("errcode");
            if (errcode == 0) {
                String openid = jsonObject.getString("openid");
                String uuid = UUID.randomUUID().toString().toLowerCase();
                redisTemplate.opsForValue().set(uuid, openid, Constant.TIMEOUT, TimeUnit.MINUTES);
                return uuid;
            } else {
                throw new Exception(jsonObject.getString("errMsg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("wx server return ioe error:" + e.getMessage());
        }
    }
}
