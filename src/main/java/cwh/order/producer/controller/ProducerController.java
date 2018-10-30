package cwh.order.producer.controller;

import cwh.order.producer.service.FoodService;
import cwh.order.producer.util.Constant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 曹文豪 on 2018/10/30.
 */
@RestController
@RequestMapping("producer")
public class ProducerController {

    @Resource
    private FoodService foodService;

    @RequestMapping("addFood")
    public String addFood(HttpServletRequest request) {
        try {
            foodService.add(request);
        } catch (Exception e) {
            return e.getMessage();
        }
        return Constant.OK;
    }
}
