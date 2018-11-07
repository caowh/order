package cwh.order.producer.dao;

import cwh.order.producer.model.Food;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 曹文豪 on 2018/10/30.
 */
@Repository
public interface FoodDao {

    void insert(Food food);
    List<Map<String,Object>> queryFoods(String openid);
}
