package cwh.order.producer.dao;

import cwh.order.producer.model.Food;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 曹文豪 on 2018/10/30.
 */
@Repository
public interface FoodDao {

    void insert(Food food);
    List<Food> queryFoodsByUser(String openid);
}
