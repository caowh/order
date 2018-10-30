package cwh.order.producer.dao;

import cwh.order.producer.model.FoodPicture;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/10/30 0030.
 */
@Repository
public interface FoodPictureDao {

    void insert(FoodPicture foodPicture);
}
