package cwh.order.producer.service;

import cwh.order.producer.model.FoodOrder;
import cwh.order.producer.util.HandleException;

import java.util.List;

/**
 * Created by 曹文豪 on 2018/11/26.
 */
public interface OrderService {

    List<FoodOrder> getByStatus(String openid, int status, int page, int count) throws HandleException;

    FoodOrder getDetail(String openid, long order_id) throws HandleException;
}
