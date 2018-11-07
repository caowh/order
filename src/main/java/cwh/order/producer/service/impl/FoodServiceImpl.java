package cwh.order.producer.service.impl;

import cwh.order.producer.dao.FoodDao;
import cwh.order.producer.dao.FoodPictureDao;
import cwh.order.producer.model.Food;
import cwh.order.producer.model.FoodPicture;
import cwh.order.producer.service.FoodService;
import cwh.order.producer.util.Constant;
import cwh.order.producer.util.FileUtil;
import cwh.order.producer.util.HandleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * Created by 曹文豪 on 2018/10/30.
 */
@Service
public class FoodServiceImpl implements FoodService {

    private static final Logger logger = LoggerFactory.getLogger(FoodServiceImpl.class);
    @Resource
    private FoodDao foodDao;
    @Resource
    private FoodPictureDao foodPictureDao;

    @Override
    @Transactional
    public void add(String openid, String name, String description, BigDecimal price, MultipartFile file) throws HandleException {
        Food food = new Food();
        food.setF_name(name);
        food.setDescription(description);
        food.setPrice(price);
        food.setOpenid(openid);
        foodDao.insert(food);
        long food_id = food.getId();
        FoodPicture foodPicture = new FoodPicture();
        try {
            foodPicture.setPic_url(FileUtil.save(file));
        } catch (IOException e) {
            logger.error("upload food picture IOException,openid is {},food is {},file is {}", openid, name, file.getOriginalFilename());
            throw new HandleException(Constant.ERROR);
        }
        foodPicture.setFood_id(food_id);
        foodPictureDao.insert(foodPicture);
    }

    @Override
    public List<Map<String, Object>> getFoods(String openid) {
        foodDao.queryFoods(openid);
        return null;
    }


}
