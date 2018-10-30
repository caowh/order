package cwh.order.producer.service;

import cwh.order.producer.dao.FoodDao;
import cwh.order.producer.model.Food;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * Created by 曹文豪 on 2018/10/30.
 */
@Service
public class FoodServiceImpl implements FoodService {

    @Resource
    private FoodDao foodDao;

    public void add(HttpServletRequest request) {
        /*
          1、添加菜，初始数量为0
          2、上传图片，生成访问url，如果上传失败，允许菜添加成功，图片可以后续上传变更
          3、url生成成功后，根据food_id，建立菜和图片的关联关系；每上传成功一张图，建立一次关联关系，捕获异常互相不影响
          */
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        Food food = new Food();
        food.setF_name(name);
        food.setDescription(description);
        food.setPrice(price);
        food.setSurplusCount(0);
        foodDao.insert(food);


    }

}
