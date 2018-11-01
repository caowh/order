package cwh.order.producer.service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by 曹文豪 on 2018/10/30.
 */
public interface FoodService {

    void add(HttpServletRequest request);

    List<Map> queryFoodsByUser(String openid);
}
