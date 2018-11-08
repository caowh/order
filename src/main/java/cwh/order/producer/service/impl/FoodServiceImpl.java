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
    public void add(String openid, String name, String description, BigDecimal price, long classifyId, MultipartFile file) throws HandleException {
        if (name.equals("")) {
            throw new HandleException("名称不能为空");
        }
        if (name.length() > 20) {
            throw new HandleException("名称不能超过20个字符");
        }
        if (description.length() > 30) {
            throw new HandleException("描述不能超过30个字符");
        }
        if (file == null) {
            throw new HandleException("图片不能为空");
        }
        if (foodClassifyDao.queryExistId(classifyId) == 0) {
            throw new HandleException("分类不存在");
        }
        Food food = new Food();
        food.setF_name(name);
        food.setDescription(description);
        food.setPrice(price);
        food.setClassify_id(classifyId);
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
        if (name == null || name.equals("")) {
            throw new HandleException("分类名称不能为空");
        }
        if (name.length() > 10) {
            throw new HandleException("分类名称不能超过10个字符");
        }
        FoodClassify foodClassify = new FoodClassify();
        foodClassify.setClassify_name(name);
        foodClassify.setOpenid(openid);
        if (foodClassifyDao.queryExistName(foodClassify) != 0) {
            throw new HandleException("分类名称已存在");
        }
        foodClassify.setClassify_sort(foodClassifyDao.queryMaxSort(openid) + 1);
        foodClassifyDao.insert(foodClassify);
    }

    @Override
    public void deleteFoodClassify(String openid, long id) throws HandleException {
        int result = foodClassifyDao.delete(id);
        if (result == 0) {
            throw new HandleException("分类不存在");
        }
    }

    @Override
    public List<FoodClassify> getFoodClassifies(String openid) {
        List<FoodClassify> list = foodClassifyDao.queryAll(openid);
        return list == null ? new ArrayList<>() : list;
    }

    @Override
    @Transactional
    public void classifySort(String openid, long id, int position) throws HandleException {
        List<FoodClassify> list = foodClassifyDao.queryAll(openid);
        if (list == null || list.size() < position || position < 1) {
            throw new HandleException("不存在的位置，无法移动");
        }
        int move = 0, old_position = 0;
        for (FoodClassify foodClassify : list) {
            old_position++;
            long classify_id = foodClassify.getId();
            if (id == classify_id) {
                move = foodClassify.getClassify_sort();
                break;
            }
        }
        if (move == 0) {
            throw new HandleException("分类不存在");
        }
        if (old_position == position) return;

        if (old_position > position) {
            int current = 0, position_sort = 0;
            for (FoodClassify foodClassify : list) {
                current++;
                if (current == position) {
                    position_sort = foodClassify.getClassify_sort();
                }
                if (current >= position && current < old_position) {
                    foodClassify.setClassify_sort(foodClassify.getClassify_sort() + 1);
                    foodClassifyDao.updatePosition(foodClassify);
                } else if (old_position == current) {
                    foodClassify.setClassify_sort(position_sort);
                    foodClassifyDao.updatePosition(foodClassify);
                    break;
                }
            }
        } else if (old_position < position) {
            int current = 0;
            for (FoodClassify foodClassify : list) {
                current++;
                if (current == old_position) {
                    foodClassify.setClassify_sort(list.get(position - 1).getClassify_sort());
                    foodClassifyDao.updatePosition(foodClassify);
                }
                if (current > old_position && current <= position) {
                    foodClassify.setClassify_sort(foodClassify.getClassify_sort() - 1);
                    foodClassifyDao.updatePosition(foodClassify);
                } else if (current > position) {
                    break;
                }
            }
        }
    }


}
