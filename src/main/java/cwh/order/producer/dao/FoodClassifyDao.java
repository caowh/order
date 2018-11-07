package cwh.order.producer.dao;

import cwh.order.producer.model.FoodClassify;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/11/7 0007.
 */
@Repository
public interface FoodClassifyDao {

    void insert(FoodClassify foodClassify);

    int delete(FoodClassify foodClassify);

    List<String> queryNames(String openid);
}
