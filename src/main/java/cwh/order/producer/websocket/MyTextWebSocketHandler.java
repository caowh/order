//package cwh.order.producer.websocket;
//
//import com.alibaba.fastjson.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.io.EOFException;
//import java.io.IOException;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * Created by Administrator on 2018/11/18 0018.
// */
//@Component
//public class MyTextWebSocketHandler extends TextWebSocketHandler {
//
//    private static final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
//    private static final Logger logger = LoggerFactory.getLogger(MyTextWebSocketHandler.class);
//
//    //处理文本消息
//    @Override
//    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
//
//    }
//
//    public static void sendMessage(String openid,JSONObject jsonObject){
//        WebSocketSession socketSession = sessionMap.get(openid);
//        if(socketSession != null){
//            try {
//                socketSession.sendMessage(makeMessage(jsonObject));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private static TextMessage makeMessage(JSONObject jsonObject) {
//        String message = jsonObject.toJSONString();
//        logger.info("send message {}", message);
//        return new TextMessage(message);
//    }
//
//    //连接建立后处理
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        super.afterConnectionEstablished(session);
//        String openid = session.getAttributes().get("openid").toString();
//        logger.info("connection opened,openid is {}", openid);
//        sessionMap.put(openid, session);
//    }
//
//    //连接关闭后处理
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        String openid = session.getAttributes().get("openid").toString();
//        sessionMap.remove(openid);
//        logger.info("connection closed,openid is {}", openid);
//        super.afterConnectionClosed(session, status);
//    }
//
//    //抛出异常时处理
//    @Override
//    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
//        super.handleTransportError(session, exception);
//        String openid = session.getAttributes().get("openid").toString();
//        logger.warn("handleTransportError,openid is {}", openid);
//        if (session.isOpen())
//            session.close();
//        if (exception instanceof EOFException) {
//            logger.warn("read time out,auto close the session!");
//        } else {
//            exception.printStackTrace();
//        }
//
//    }
//
//
//    //是否支持局部消息
//    @Override
//    public boolean supportsPartialMessages() {
//        return false;
//    }
//
//}