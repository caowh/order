package cwh.order.producer.controller;

import cwh.order.producer.service.ConfigService;
import cwh.order.producer.util.Constant;
import cwh.order.producer.util.HandleException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
    private ConfigService configService;

    @GetMapping("getToken")
    public Map<String, Object> getToken(@RequestParam("code") String code) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("token", configService.getToken(code));
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
            configService.sendPhoneKey(openid, request.getParameter("phone"));
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
            configService.bindingPhone(openid, request.getParameter("phone"), request.getParameter("code"));
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @GetMapping("getBindPhone")
    public Map<String, Object> getBindPhone(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        map.put("message", configService.getBindPhone(openid));
        map.put("status", Constant.CODE_OK);
        return map;
    }

    @GetMapping("getStoreName")
    public Map<String, Object> getStoreName(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        map.put("message", configService.getStoreName(openid));
        map.put("status", Constant.CODE_OK);
        return map;
    }

    @GetMapping("getDescription")
    public Map<String, Object> getDescription(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        map.put("message", configService.getDescription(openid));
        map.put("status", Constant.CODE_OK);
        return map;
    }

    @GetMapping("getHeadPictureUrl")
    public Map<String, Object> getHeadPictureUrl(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        map.put("message", configService.getHeadPictureUrl(openid));
        map.put("status", Constant.CODE_OK);
        return map;
    }

    @GetMapping("getRegionAddress")
    public Map<String, Object> getRegionAddress(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        map.put("message", configService.getRegionAddress(openid));
        map.put("status", Constant.CODE_OK);
        return map;
    }

    @GetMapping("getStorePictureUrls")
    public Map<String, Object> getStorePictureUrls(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        map.put("message", configService.getStorePictureUrls(openid));
        map.put("status", Constant.CODE_OK);
        return map;
    }

    @GetMapping("getSettingStatus")
    public Map<String, Object> getSettingStatus(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        map.put("message", configService.getSettingStatus(openid));
        map.put("status", Constant.CODE_OK);
        return map;
    }

    @GetMapping("getStore")
    public Map<String, Object> getStore(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        map.put("message", configService.getStore(openid));
        map.put("status", Constant.CODE_OK);
        return map;
    }

    @GetMapping("checkStoreNameByRegion")
    public Map<String, Object> checkStoreNameByRegion(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String region = request.getParameter("region");
        String name = request.getParameter("name");
        try {
            map.put("message", configService.checkStoreNameByRegion(region, name));
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @PostMapping("configStore")
    public Map<String, Object> configStore(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> configMap = new HashMap<>();
        configMap.put("openid", request.getAttribute("openid").toString());
        configMap.put("name", request.getParameter("name"));
        configMap.put("address", request.getParameter("address"));
        configMap.put("region", request.getParameter("region"));
        configMap.put("description", request.getParameter("description"));
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        configMap.put("headPicture", multipartRequest.getFile("headPicture"));
        configMap.put("storePictures", multipartRequest.getFiles("storePictures"));
        try {
            configService.configStore(configMap);
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

}
