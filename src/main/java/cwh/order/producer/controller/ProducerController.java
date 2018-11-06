package cwh.order.producer.controller;

import cwh.order.producer.service.ConfigService;
import cwh.order.producer.util.Constant;
import cwh.order.producer.util.HandleException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

    @GetMapping("getRegion")
    public Map<String, Object> getRegion(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        map.put("message", configService.getRegion(openid));
        map.put("status", Constant.CODE_OK);
        return map;
    }

    @GetMapping("getAddress")
    public Map<String, Object> getAddress(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        map.put("message", configService.getAddress(openid));
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
        String openid = request.getAttribute("openid").toString();
        String name = request.getParameter("name");
        try {
            map.put("message", configService.checkStoreNameByRegion(openid, name));
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @PostMapping("configRegion")
    public Map<String, Object> configRegion(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        try {
            configService.configRegion(openid, request.getParameter("region"));
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @PostMapping("configAddress")
    public Map<String, Object> configAddress(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        try {
            configService.configAddress(openid, request.getParameter("address"));
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @PostMapping("configDescription")
    public Map<String, Object> configDescription(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        try {
            configService.configDescription(openid, request.getParameter("description"));
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @PostMapping("configHeadPicture")
    public Map<String, Object> configHeadPicture(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        try {
            configService.configHeadPicture(openid, multipartRequest.getFile("headPicture"));
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @PostMapping("configStoreName")
    public Map<String, Object> configStoreName(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        try {
            configService.configStoreName(openid, request.getParameter("storeName"));
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @PostMapping("configStorePicture")
    public Map<String, Object> configStorePicture(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        try {
            configService.configStorePicture(openid, multipartRequest.getFile("storePicture"));
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @DeleteMapping("deleteStorePicture")
    public Map<String, Object> deleteStorePicture(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        try {
            configService.deleteStorePicture(openid, request.getParameter("storePictureUrl"));
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

}
