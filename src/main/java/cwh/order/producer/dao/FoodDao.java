package cwh.order.producer.dao;

import cwh.order.producer.model.Food;
import cwh.order.producer.util.PageQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 曹文豪 on 2018/10/30.
 */
@Repository
public interface FoodDao {

    void insert(Food food);

    List<Food> queryByClassify(PageQuery pageQuery);

    int queryCountByClassify(long classify_id);

    int queryExistName(Map<String,String> map);

    void updateStatus(Food food);

    int queryExistId(Food food);

    List<Food> queryAll(PageQuery pageQuery);
}
