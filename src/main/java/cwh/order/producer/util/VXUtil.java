//package cwh.order.producer.util;
//
//import com.alibaba.fastjson.JSONObject;
//import okhttp3.FormBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.ResponseBody;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import java.io.IOException;
//
///**
// * Created by 曹文豪 on 2018/11/28.
// */
//@EnableScheduling
//@Configuration
//public class VXUtil {
//
//    private static final Logger logger = LoggerFactory.getLogger(VXUtil.class);
//    private final OkHttpClient client = new OkHttpClient();
//    private String token;
//
//    private Boolean getAccessToken() {
//        String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s"
//                , Constant.APPID, Constant.APPSECRET);
//        Request get = new Request.Builder().url(url).build();
//        try {
//            ResponseBody responseBody = client.newCall(get).execute().body();
//            JSONObject jsonObject = JSONObject.parseObject(responseBody.string());
//            Object errcode = jsonObject.get("errcode");
//            if (errcode == null || errcode.toString().equals("0")) {
//                this.token = jsonObject.getString("access_token");
//                logger.info("get client token success");
//                return true;
//            } else {
//                logger.error("get client token get errcode,errcode is {},error is {}", errcode, jsonObject.getString("errmsg"));
//                return false;
//            }
//        } catch (IOException e) {
//            logger.error("get client token throw IOException,error is {}", e.getMessage());
//            return false;
//        }
//    }
//
//    @Scheduled(fixedRate = 1000 * 7000)
//    public void getTokenSchedule() {
//        Boolean bl = false;
//        while (!bl) {
//            bl = this.getAccessToken();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void sendTemplateMessage(String openid, String content) {
//        if (this.token == null) {
//            logger.error("sendTemplateMessage token is null");
//            return;
//        }
//        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";
//        FormBody responseBody = new FormBody.Builder()
//                .add("access_token", this.token)
//                .add("touser", openid)
//                .add("template_id", "text")
//                .add("content", content)
//                .build();
//        Request get = new Request.Builder()
//                .url(url)
//                .post(responseBody)
//                .build();
//        try {
//            ResponseBody body = client.newCall(get).execute().body();
//            JSONObject jsonObject = JSONObject.parseObject(body.string());
//            Object errcode = jsonObject.get("errcode");
//            if (errcode != null && !errcode.toString().equals("0")) {
//                logger.error("sendTemplateMessage get errcode,errcode is {},error is {}", errcode, jsonObject.getString("errmsg"));
//            }
//        } catch (IOException e) {
//            logger.error("sendTemplateMessage throw IOException,error is {}", e.getMessage());
//        }
//    }
//}
