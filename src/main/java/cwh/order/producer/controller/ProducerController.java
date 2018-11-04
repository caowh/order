package cwh.order.producer.controller;

import cwh.order.producer.service.FoodService;
import cwh.order.producer.service.LoginService;
import cwh.order.producer.util.Constant;
import cwh.order.producer.util.HandleException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 曹文豪 on 2018/10/30.
 */
@RestController
@RequestMapping("producer")
public class ProducerController {

//    @Resource
//    private FoodService foodService;
    @Resource
    private LoginService loginService;

    @GetMapping("getToken")
    public Map<String, Object> getToken(@RequestParam("code") String code) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("token", loginService.getToken(code));
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

//    @PostMapping("addFood")
//    public Map<String, Object> addFood(HttpServletRequest request) {
//        Map<String, Object> map = new HashMap<>();
//        try {
//            foodService.add(request);
//            map.put("status", Constant.CODE_OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("status", Constant.CODE_ERROR);
//            map.put("error_message", e.getMessage());
//        }
//        return map;
//    }
//
//    @GetMapping("queryFoods")
//    public Map<String, Object> queryFoods(HttpServletRequest request) {
//        Map<String, Object> map = new HashMap<>();
//        try {
//            String openid = request.getAttribute("openid").toString();
//            List<Map> foods = foodService.queryFoodsByUser(openid);
//            map.put("status", Constant.CODE_OK);
//            map.put("message", foods);
//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("status", Constant.CODE_ERROR);
//            map.put("error_message", e.getMessage());
//        }
//        return map;
//    }

    @PostMapping("sendPhoneKey")
    public Map<String, Object> sendPhoneKey(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            String openid = request.getAttribute("openid").toString();
            loginService.sendPhoneKey(openid, request.getParameter("phone"));
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @PostMapping("bindPhone")
    public Map<String, Object> bindPhone(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            String openid = request.getAttribute("openid").toString();
            loginService.bindingPhone(openid, request.getParameter("phone"), request.getParameter("code"));
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @PostMapping("getBindPhone")
    public Map<String, Object> getBindPhone(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        map.put("message", loginService.getBindPhone(openid));
        map.put("status", Constant.CODE_OK);
        return map;
    }

}
