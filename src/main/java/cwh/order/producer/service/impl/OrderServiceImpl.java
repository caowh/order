package cwh.order.producer.service.impl;

import cwh.order.producer.dao.EvaluatePictureDao;
import cwh.order.producer.dao.FoodOrderDao;
import cwh.order.producer.dao.FoodSaleDao;
import cwh.order.producer.dao.OrderEvaluateDao;
import cwh.order.producer.model.FoodOrder;
import cwh.order.producer.model.OrderEvaluate;
import cwh.order.producer.service.OrderService;
import cwh.order.producer.util.HandleException;
import cwh.order.producer.util.PageQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 曹文豪 on 2018/11/26.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private FoodOrderDao foodOrderDao;
    @Resource
    private FoodSaleDao foodSaleDao;
    @Resource
    private OrderEvaluateDao orderEvaluateDao;
    @Resource
    private EvaluatePictureDao evaluatePictureDao;

    @Override
    public List<FoodOrder> getByStatus(String openid, int status, int page, int count) throws HandleException {
        if (status == 0) {
            throw new HandleException("不支持的操作类型");
        }
        PageQuery pageQuery = new PageQuery();
        pageQuery.setString_param(openid);
        pageQuery.setInt_param(status);
        pageQuery.setStart(page * count);
        pageQuery.setCount(count);
        return foodOrderDao.query(pageQuery);
    }

    @Override
    public FoodOrder getDetail(String openid, long order_id) throws HandleException {
        FoodOrder foodOrder = new FoodOrder();
        foodOrder.setStore_id(openid);
        foodOrder.setId(order_id);
        FoodOrder foodOrder1 = foodOrderDao.queryDetail(foodOrder);
        if (foodOrder1 == null) {
            throw new HandleException("订单不存在");
        }
        foodOrder1.setFoodSales(foodSaleDao.queryByOrder(order_id));
        return foodOrder1;
    }

    @Override
    public OrderEvaluate getEvaluate(String openid, long id) throws HandleException {
        FoodOrder foodOrder=new FoodOrder();
        foodOrder.setId(id);
        foodOrder.setOpenid(openid);
        OrderEvaluate orderEvaluate = orderEvaluateDao.query(foodOrder);
        if(orderEvaluate == null){
            throw new HandleException("无法获取此订单评价信息");
        }
        orderEvaluate.setPictures(evaluatePictureDao.query(id));
        orderEvaluate.setFoodSales(foodSaleDao.queryEvaluate(id));
        return orderEvaluate;
    }
}
