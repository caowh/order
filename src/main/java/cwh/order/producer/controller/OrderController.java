package cwh.order.producer.controller;

import cwh.order.producer.service.OrderService;
import cwh.order.producer.util.Constant;
import cwh.order.producer.util.HandleException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static cwh.order.producer.util.Constant.getSafeParameter;

/**
 * Created by 曹文豪 on 2018/11/26.
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("getByStatus")
    public Map<String, Object> getByStatus(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        int status = Integer.parseInt(getSafeParameter(request, "status"));
        int page = Integer.parseInt(getSafeParameter(request, "page"));
        int count = Integer.parseInt(getSafeParameter(request, "count"));
        try {
            map.put("message", orderService.getByStatus(openid, status, page, count));
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @GetMapping("getDetail")
    public Map<String, Object> getDetail(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        long id = Long.parseLong(getSafeParameter(request, "id"));
        try {
            map.put("message", orderService.getDetail(openid, id));
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }

    @GetMapping("getEvaluate")
    public Map<String, Object> getEvaluate(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String openid = request.getAttribute("openid").toString();
        long id = Long.parseLong(getSafeParameter(request, "id"));
        try {
            map.put("message", orderService.getEvaluate(openid, id));
            map.put("status", Constant.CODE_OK);
        } catch (HandleException e) {
            map.put("status", Constant.CODE_ERROR);
            map.put("error_message", e.getMessage());
        }
        return map;
    }
}
