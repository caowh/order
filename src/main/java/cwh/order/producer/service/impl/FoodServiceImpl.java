package cwh.order.producer.service.impl;

import cwh.order.producer.dao.FoodClassifyDao;
import cwh.order.producer.dao.FoodDao;
import cwh.order.producer.dao.FoodPictureDao;
import cwh.order.producer.model.Food;
import cwh.order.producer.model.FoodClassify;
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
import java.util.ArrayList;
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
    @Resource
    private FoodClassifyDao foodClassifyDao;

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

    @Override
    @Transactional
    public void addFoodClassify(String openid, String name) throws HandleException {
        checkClassifyName(name);
        List<String> names = foodClassifyDao.queryNames(openid);
        if (names != null && names.contains(name)) {
            throw new HandleException("名称已存在");
        }
        FoodClassify foodClassify = new FoodClassify();
        foodClassify.setClassify_name(name);
        foodClassify.setOpenid(openid);
        foodClassifyDao.insert(foodClassify);
    }

    private void checkClassifyName(String name) throws HandleException {
        if (name == null || name.equals("")) {
            throw new HandleException("名称不能为空");
        }
        if (name.length() > 10) {
            throw new HandleException("名称不能超过10个字符");
        }
    }

    @Override
    @Transactional
    public void deleteFoodClassify(String openid, String name) throws HandleException {
        checkClassifyName(name);
        FoodClassify foodClassify = new FoodClassify();
        foodClassify.setClassify_name(name);
        foodClassify.setOpenid(openid);
        int result = foodClassifyDao.delete(foodClassify);
        if (result == 0) {
            throw new HandleException("名称不存在");
        }
    }

    @Override
    public List<String> getFoodClassifyNames(String openid) {
        List<String> names = foodClassifyDao.queryNames(openid);
        return names == null ? new ArrayList<>() : names;
    }


}
