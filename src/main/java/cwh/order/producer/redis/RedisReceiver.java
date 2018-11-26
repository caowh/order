package cwh.order.producer.redis;

import com.alibaba.fastjson.JSONObject;
import cwh.order.producer.util.Constant;
import cwh.order.producer.websocket.MyTextWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by 曹文豪 on 2018/11/26.
 */
@Component
public class RedisReceiver {

    private static final Logger logger = LoggerFactory.getLogger(RedisReceiver.class);

    public void receiveMessage(String message) {
        logger.info("orderNew receive message:{}", message);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", Constant.NEW_ORDER);
        MyTextWebSocketHandler.sendMessage(message, jsonObject);
    }
}
