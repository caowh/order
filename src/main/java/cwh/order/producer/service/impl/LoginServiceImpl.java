package cwh.order.producer.service.impl;

import com.alibaba.fastjson.JSONObject;
import cwh.order.producer.service.LoginService;
import cwh.order.producer.util.Constant;
import okhttp3.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 曹文豪 on 2018/11/1.
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public String getToken(HttpServletRequest request) throws Exception {
        String code = request.getParameter("code");
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code"
                , Constant.APPID, Constant.APPSECRET, code);
        Request get = new Request.Builder().url(url).build();
        try {
            String body = client.newCall(get).execute().body().string();
            JSONObject jsonObject = JSONObject.parseObject(body);
            String openid = jsonObject.getString("openid");
            String session_key = jsonObject.getString("session_key");
            HttpSession session = request.getSession();
            session.setAttribute("openid", openid);
            session.setAttribute("session_key", session_key);
            return session.getId();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("wx server return error");
        }
    }
}
