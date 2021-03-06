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

    List<FoodClassify> queryAll(String openid);

    int queryExistName(FoodClassify foodClassify);

    int queryExistId(FoodClassify foodClassify);

    int updatePosition(FoodClassify foodClassify);

    int queryMaxSort(String openid);

    int updateName(FoodClassify foodClassify);
}
