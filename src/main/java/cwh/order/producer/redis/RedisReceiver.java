package cwh.order.producer.redis;

//import com.alibaba.fastjson.JSONObject;
//import cwh.order.producer.util.Constant;
//import cwh.order.producer.util.VXUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by 曹文豪 on 2018/11/26.
 */
@Component
public class RedisReceiver {

    private static final Logger logger = LoggerFactory.getLogger(RedisReceiver.class);
//    @Resource
//    private VXUtil vxUtil;

    public void receiveMessage(String message) {
        logger.info("orderNew receive message:{}", message);
//        JSONObject jsonObject = JSONObject.parseObject(message);
//        String order_id = jsonObject.getString("order_id");
//        String content = String.format("您有新的订单（%s），请注意查看！<a data-miniprogram-appid=\"%s\" data-miniprogram-path=\"pages/orderDetail/orderDetail?order=%s\">点击跳小程序</a>",
//                order_id, Constant.APPID, order_id);
//        vxUtil.sendTemplateMessage(jsonObject.getString("store_id"), content);
    }
}
