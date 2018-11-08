package cwh.order.producer.service;

import cwh.order.producer.model.FoodClassify;
import cwh.order.producer.util.HandleException;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by 曹文豪 on 2018/10/30.
 */
public interface FoodService {

    void add(String openid, String name, String description, BigDecimal price, long classifyId, MultipartFile file) throws HandleException;

    List<Map<String, Object>> getFoods(String openid);

    void addFoodClassify(String openid, String name) throws HandleException;

    void deleteFoodClassify(String openid, long id) throws HandleException;

    List<FoodClassify> getFoodClassifies(String openid);

    void classifySort(String openid, long id, int position) throws HandleException;

    void classifyPositionExchange(String openid, long id1, long id2) throws HandleException;
}
