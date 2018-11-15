package cwh.order.producer.controller;

import cwh.order.producer.service.FoodService;
import cwh.order.producer.util.Constant;
import cwh.order.producer.util.HandleException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static cwh.order.producer.util.Constant.getSafeParameter;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 曹文豪 on 2018/11/7.
 */
@RestController
@RequestMapping("food")
public class FoodController {

    @Resource
    private FoodService foodService;

    @PostMapping("add")
    public Map<String, Object> add(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        String name = getSafeParameter(request, "name");
        long classifyId = Long.parseLong(getSafeParameter(request, "classifyId"));
        try {
            foodService.add(openid, name, classifyId);
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @PostMapping("addClassify")
    public Map<String, Object> addClassify(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        String name = getSafeParameter(request, "name");
        try {
            foodService.addFoodClassify(openid, name);
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @GetMapping("getFoodClassifies")
    public Map<String, Object> getFoodClassifies(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        map.put("message", foodService.getFoodClassifies(openid));
        map.put("status", Constant.CODE_OK);
        return map;
    }

    @PostMapping("deleteClassify")
    public Map<String, Object> deleteClassify(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        long id = Long.parseLong(getSafeParameter(request, "id"));
        try {
            foodService.deleteFoodClassify(openid, id);
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @PostMapping("classifySort")
    public Map<String, Object> classifySort(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        String list = request.getParameter("list").replaceAll("\'", "‘");
        try {
            foodService.classifySort(openid, list);
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @PostMapping("updateClassifyName")
    public Map<String, Object> updateClassifyName(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        String name = getSafeParameter(request, "name");
        long id = Long.parseLong(getSafeParameter(request, "id"));
        try {
            foodService.updateClassifyName(openid, id, name);
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @PostMapping("getFoods")
    public Map<String, Object> getFoods(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        int status = Integer.parseInt(getSafeParameter(request, "status"));
        String ids = getSafeParameter(request, "ids");
        String name = getSafeParameter(request, "name");
        int page = Integer.parseInt(getSafeParameter(request, "page"));
        int count = Integer.parseInt(getSafeParameter(request, "count"));
        try {
            map.put("message", foodService.getFoods(openid, name, ids, status, page, count));
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }

        return map;
    }

    @PostMapping("updateFoodStatus")
    public Map<String, Object> updateFoodStatus(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        int status = Integer.parseInt(getSafeParameter(request, "status"));
        String ids = getSafeParameter(request, "ids");
        try {
            foodService.foodStatusChange(openid, ids, status);
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @PostMapping("delete")
    public Map<String, Object> delete(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        String ids = getSafeParameter(request, "ids");
        try {
            foodService.delete(openid, ids);
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @PostMapping("updateName")
    public Map<String, Object> updateName(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        long id = Long.parseLong(getSafeParameter(request, "id"));
        String name = getSafeParameter(request, "name");
        try {
            foodService.updateName(openid, id, name);
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @PostMapping("updateDescription")
    public Map<String, Object> updateDescription(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        long id = Long.parseLong(getSafeParameter(request, "id"));
        String description = getSafeParameter(request, "description");
        try {
            foodService.updateDescription(openid, id, description);
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @PostMapping("updatePrice")
    public Map<String, Object> updatePrice(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        long id = Long.parseLong(getSafeParameter(request, "id"));
        BigDecimal price = new BigDecimal(getSafeParameter(request, "price"));
        try {
            foodService.updatePrice(openid, id, price);
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @PostMapping("updateClassify")
    public Map<String, Object> updateClassify(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        long id = Long.parseLong(getSafeParameter(request, "id"));
        long classifyId = Long.parseLong(getSafeParameter(request, "classifyId"));
        try {
            foodService.updateClassify(openid, id, classifyId);
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @PostMapping("updatePicture")
    public Map<String, Object> updatePicture(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        long id = Long.parseLong(getSafeParameter(request, "id"));
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("foodPicture");
        try {
            foodService.updatePicture(openid, id, file);
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

}
