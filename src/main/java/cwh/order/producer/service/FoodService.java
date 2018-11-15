package cwh.order.producer.service;

import cwh.order.producer.model.Food;
import cwh.order.producer.model.FoodClassify;
import cwh.order.producer.util.HandleException;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 曹文豪 on 2018/10/30.
 */
public interface FoodService {

    void add(String openid, String name, long classifyId) throws HandleException;

    void delete(String openid, String ids) throws HandleException;

    void addFoodClassify(String openid, String name) throws HandleException;

    void deleteFoodClassify(String openid, long id) throws HandleException;

    List<FoodClassify> getFoodClassifies(String openid);

    void classifySort(String openid, String listStr) throws HandleException;

    void updateClassifyName(String openid, long id, String name) throws HandleException;

    List<Food> getFoods(String openid, String name, String ids, int status, int page, int count) throws HandleException;

    void foodStatusChange(String openid, String ids, int status) throws HandleException;

    void updateName(String openid, long id, String name) throws HandleException;

    void updateDescription(String openid, long id, String description) throws HandleException;

    void updatePrice(String openid, long id, BigDecimal price) throws HandleException;

    void updatePicture(String openid, long id, MultipartFile file) throws HandleException;

    void updateClassify(String openid, long id, long classifyId) throws HandleException;
}
