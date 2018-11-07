package cwh.order.producer.service;

import cwh.order.producer.util.HandleException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by 曹文豪 on 2018/10/30.
 */
public interface FoodService {

    void add(String openid, String name, String description, BigDecimal price, MultipartFile file) throws HandleException;

    List<Map<String,Object>> getFoods(String openid);
}
