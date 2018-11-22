package cwh.order.producer.service.impl;

import com.alibaba.fastjson.JSON;
import cwh.order.producer.dao.FoodClassifyDao;
import cwh.order.producer.dao.FoodDao;
import cwh.order.producer.dao.FoodDeleteDao;
import cwh.order.producer.dao.FoodTableDao;
import cwh.order.producer.model.Food;
import cwh.order.producer.model.FoodClassify;
import cwh.order.producer.model.FoodDelete;
import cwh.order.producer.model.FoodTable;
import cwh.order.producer.service.FoodService;
import cwh.order.producer.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


/**
 * Created by 曹文豪 on 2018/10/30.
 */
@Service
public class FoodServiceImpl implements FoodService {

    private static final Logger logger = LoggerFactory.getLogger(FoodServiceImpl.class);
    @Resource
    private FoodDao foodDao;
    @Resource
    private FoodClassifyDao foodClassifyDao;
    @Resource
    private FoodDeleteDao foodDeleteDao;
    @Resource
    private FoodTableDao foodTableDao;
    @Resource
    private IdWorker idWorker;

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
        FoodClassify foodClassify = new FoodClassify();
        foodClassify.setId(classifyId);
        foodClassify.setOpenid(openid);
        if (foodClassifyDao.queryExistId(foodClassify) == 0) {
            throw new HandleException("分类不存在");
        }
        Map<String, String> map = new HashMap<>();
        map.put("openid", openid);
        map.put("name", name);
        if (foodDao.queryExistName(map) != 0) {
            throw new HandleException("菜品名称已存在");
        }
        Food food = new Food();
        try {
            food.setPicture_url(FileUtil.save(file));
        } catch (IOException e) {
            logger.error("upload food picture IOException,openid is {},food is {},file is {}", openid, name, file.getOriginalFilename());
            throw new HandleException(Constant.ERROR);
        }
        food.setF_name(name);
        food.setDescription(description);
        food.setPrice(price.setScale(2, BigDecimal.ROUND_HALF_UP));
        food.setClassify_id(classifyId);
        food.setId(idWorker.nextId());
        foodDao.insert(food);
    }

    @Override
    @Transactional
    public void delete(String openid, String ids) throws HandleException {
        if (ids == null || ids.equals("")) {
            throw new HandleException("菜品不能为空");
        }
        List list = JSON.parseObject(ids, List.class);
        for (Object id : list) {
            Food food = new Food();
            food.setOpenid(openid);
            food.setId(Long.parseLong(id.toString()));
            Food queryFood = foodDao.queryOne(food);
            if (queryFood != null) {
                if (queryFood.getStatus() == 1) {
                    throw new HandleException("菜品“" + queryFood.getF_name() + "”处于在售状态，无法删除");
                }
                foodDao.delete(food);
                FoodDelete foodDelete = new FoodDelete();
                foodDelete.setId(queryFood.getId());
                foodDelete.setOpenid(openid);
                foodDelete.setDescription(queryFood.getDescription());
                foodDelete.setPicture_url(queryFood.getPicture_url());
                foodDelete.setClassify_name(queryFood.getClassify_name());
                foodDelete.setPrice(queryFood.getPrice());
                foodDelete.setF_name(queryFood.getF_name());
                foodDelete.setDelete_time(new Date());
                foodDeleteDao.insert(foodDelete);
            }
        }
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
        foodClassify.setId(idWorker.nextId());
        foodClassifyDao.insert(foodClassify);
    }

    @Override
    @Transactional
    public void deleteFoodClassify(String openid, long id) throws HandleException {
        if (foodDao.queryCountByClassify(id) > 0) {
            throw new HandleException("分类下存在菜品，无法删除");
        }
        FoodClassify foodClassify = new FoodClassify();
        foodClassify.setId(id);
        foodClassify.setOpenid(openid);
        int result = foodClassifyDao.delete(foodClassify);
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
    @Transactional(rollbackFor = {HandleException.class})
    public void classifySort(String openid, String listStr) throws HandleException {
        List<Map> list = JSON.parseArray(listStr, Map.class);
        if (list == null || list.size() <= 1) {
            throw new HandleException("分类无需移动");
        }
        for (Map map : list) {
            long id = Long.parseLong(map.get("id").toString());
            int sort = Integer.parseInt(map.get("index").toString());
            FoodClassify foodClassify = new FoodClassify();
            foodClassify.setId(id);
            foodClassify.setClassify_sort(sort);
            foodClassify.setOpenid(openid);
            int result = foodClassifyDao.updatePosition(foodClassify);
            if (result == 0) {
                throw new HandleException("分类不存在");
            }
        }
//        List<FoodClassify> list = foodClassifyDao.queryAll(openid);
//        if (list == null || list.size() < position || position < 1) {
//            throw new HandleException("不存在的位置，无法移动");
//        }
//        int move = 0, old_position = 0;
//        for (FoodClassify foodClassify : list) {
//            old_position++;
//            long classify_id = foodClassify.getId();
//            if (id == classify_id) {
//                move = foodClassify.getClassify_sort();
//                break;
//            }
//        }
//        if (move == 0) {
//            throw new HandleException("分类不存在");
//        }
//        if (old_position == position) return;
//
//        if (old_position > position) {
//            int current = 0, position_sort = 0;
//            for (FoodClassify foodClassify : list) {
//                current++;
//                if (current == position) {
//                    position_sort = foodClassify.getClassify_sort();
//                }
//                if (current >= position && current < old_position) {
//                    foodClassify.setClassify_sort(foodClassify.getClassify_sort() + 1);
//                    foodClassifyDao.updatePosition(foodClassify);
//                } else if (old_position == current) {
//                    foodClassify.setClassify_sort(position_sort);
//                    foodClassifyDao.updatePosition(foodClassify);
//                    break;
//                }
//            }
//        } else if (old_position < position) {
//            int current = 0;
//            for (FoodClassify foodClassify : list) {
//                current++;
//                if (current == old_position) {
//                    foodClassify.setClassify_sort(list.get(position - 1).getClassify_sort());
//                    foodClassifyDao.updatePosition(foodClassify);
//                }
//                if (current > old_position && current <= position) {
//                    foodClassify.setClassify_sort(foodClassify.getClassify_sort() - 1);
//                    foodClassifyDao.updatePosition(foodClassify);
//                } else if (current > position) {
//                    break;
//                }
//            }
//        }
    }

    @Override
    @Transactional
    public void updateClassifyName(String openid, long id, String name) throws HandleException {
        if (name.length() > 10) {
            throw new HandleException("分类名称不能超过10个字符");
        }
        FoodClassify foodClassify = new FoodClassify();
        foodClassify.setId(id);
        foodClassify.setClassify_name(name);
        foodClassify.setOpenid(openid);
        if (foodClassifyDao.queryExistName(foodClassify) != 0) {
            throw new HandleException("分类名称已存在");
        }
        int result = foodClassifyDao.updateName(foodClassify);
        if (result == 0) {
            throw new HandleException("分类不存在");
        }
    }

    @Override
    public List<Food> getFoods(String openid, String name, String ids, int status, int page, int count) throws HandleException {
        List<Food> foods;
        PageQuery pageQuery = new PageQuery();
        pageQuery.setCount(count);
        pageQuery.setStart(page * count);
        pageQuery.setString_param(openid);
        pageQuery.setInt_param(status);
        pageQuery.setString_param1("%" + name + "%");
        List list = JSON.parseObject(ids, List.class);
        if (list == null || list.size() == 0) {
            throw new HandleException("所选分类不能为空");
        }
        if (list.contains(0)) {
            foods = foodDao.queryAll(pageQuery);
        } else {
            pageQuery.setList(list);
            foods = foodDao.queryByClassify(pageQuery);
        }
        return foods == null ? new ArrayList<>() : foods;
    }

    @Override
    @Transactional
    public void foodStatusChange(String openid, String ids, int status) throws HandleException {
        if (status != 0 && status != 1) {
            throw new HandleException("不支持的操作方式");
        }
        if (ids == null || ids.equals("")) {
            throw new HandleException("菜品不能为空");
        }
        List longList = JSON.parseObject(ids, List.class);
        for (Object id : longList) {
            Food food = new Food();
            food.setId(Long.parseLong(id.toString()));
            food.setStatus(status);
            food.setOpenid(openid);
            int result = foodDao.updateStatus(food);
            if (result == 0) {
                throw new HandleException("菜品不存在");
            }
        }
    }

    @Override
    @Transactional
    public void updateName(String openid, long id, String name) throws HandleException {
        if (name.equals("")) {
            throw new HandleException("名称不能为空");
        }
        if (name.length() > 20) {
            throw new HandleException("名称不能超过20个字符");
        }
        Map<String, String> map = new HashMap<>();
        map.put("openid", openid);
        map.put("name", name);
        if (foodDao.queryExistName(map) != 0) {
            throw new HandleException("菜品名称已存在");
        }
        Food food = new Food();
        food.setId(id);
        food.setOpenid(openid);
        food.setF_name(name);
        int result = foodDao.updateName(food);
        if (result == 0) {
            throw new HandleException("处于在售状态，无法编辑");
        }
    }

    @Override
    public void updateDescription(String openid, long id, String description) throws HandleException {
        if (description.length() > 30) {
            throw new HandleException("描述不能超过30个字符");
        }
        Food food = new Food();
        food.setId(id);
        food.setOpenid(openid);
        food.setDescription(description);
        int result = foodDao.updateDescription(food);
        if (result == 0) {
            throw new HandleException("处于在售状态，无法编辑");
        }
    }

    @Override
    public void updatePrice(String openid, long id, BigDecimal price) throws HandleException {
        if (price.compareTo(new BigDecimal(0)) <= 0) {
            throw new HandleException("菜品价格必须大于0");
        }
        Food food = new Food();
        food.setId(id);
        food.setOpenid(openid);
        food.setPrice(price.setScale(2, BigDecimal.ROUND_HALF_UP));
        int result = foodDao.updatePrice(food);
        if (result == 0) {
            throw new HandleException("处于在售状态，无法编辑");
        }
    }

    @Override
    public void updatePicture(String openid, long id, MultipartFile file) throws HandleException {
        if (file == null) {
            throw new HandleException("图片不能为空");
        }
        String url;
        try {
            url = FileUtil.save(file);
        } catch (IOException e) {
            logger.error("upload food picture IOException,openid is {},food id is {},file is {}", openid, id, file.getOriginalFilename());
            throw new HandleException(Constant.ERROR);
        }
        Food food = new Food();
        food.setId(id);
        food.setOpenid(openid);
        food.setPicture_url(url);
        int result = foodDao.updatePicture(food);
        if (result == 0) {
            throw new HandleException("处于在售状态，无法编辑");
        }
    }

    @Override
    @Transactional
    public void updateClassify(String openid, long id, long classifyId) throws HandleException {
        FoodClassify foodClassify = new FoodClassify();
        foodClassify.setId(classifyId);
        foodClassify.setOpenid(openid);
        if (foodClassifyDao.queryExistId(foodClassify) == 0) {
            throw new HandleException("分类不存在");
        }
        Food food = new Food();
        food.setId(id);
        food.setOpenid(openid);
        food.setClassify_id(classifyId);
        int result = foodDao.updateClassify(food);
        if (result == 0) {
            throw new HandleException("处于在售状态，无法编辑");
        }
    }

    @Override
    public List<FoodTable> getFoodTables(String openid) {
        return foodTableDao.queryAll(openid);
    }

    @Override
    public void updateTableName(String openid, long id, String name) throws HandleException {
        if(name.equals("")){
            throw new HandleException("名称不能为空");
        }
        if(name.length() > 10){
            throw new HandleException("名称不能超过10个字符");
        }
        FoodTable foodTable=new FoodTable();
        foodTable.setId(id);
        foodTable.setOpenid(openid);
        foodTable.setT_name(name);
        int result = foodTableDao.updateName(foodTable);
        if(result == 0){
            throw new HandleException("此餐桌不存在");
        }
    }

}
