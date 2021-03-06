package foodie.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import foodie.common.Result;
import foodie.domain.client.BusinessInfoMapper;
import foodie.domain.client.RestaurantInfoMapper;
import foodie.domain.client.ReviewInfoMapper;
import foodie.domain.client.UserInfoMapper;
import foodie.domain.model.*;
import foodie.model.BusinessInfo;
import foodie.model.ReviewReturnInfo;
import foodie.model.UserLoginInfo;
import foodie.model.UserText;
import foodie.service.UserService;
import foodie.util.CipherHelper;
import foodie.util.HttpUtils;
import foodie.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.jws.soap.SOAPBinding;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final String RESTAURANT_DETAIL_API = "https://api.yelp.com/v3/businesses/";

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private BusinessInfoMapper businessInfoMapper;

    @Autowired
    private RestaurantInfoMapper restaurantInfoMapper;

    @Autowired
    private ReviewInfoMapper reviewInfoMapper;

    @Override
    public JSONObject getUserInfo(int id) {
        UserInfo userInfos = userInfoMapper.selectByPrimaryKey(id);
        return (JSONObject) JSONObject.toJSON(userInfos);
    }

    @Override
    public void updateUserInfo(JSONObject user) {
        UserInfo userInfo = user.toJavaObject(UserInfo.class);
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(userInfo.getId());
        userInfoMapper.updateByExampleSelective(userInfo, example);
    }

    @Override
    public void deleteUser(int id) {
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        userInfoMapper.deleteByExample(example);
    }

    @Override
    public void createUser(JSONObject user) throws DuplicateKeyException {
        UserInfo userInfo = user.toJavaObject(UserInfo.class);
        String passWdHash = CipherHelper.getSHA256(userInfo.getPassword());
        userInfo.setPassword(passWdHash);
        userInfoMapper.insert(userInfo);
    }

    @Override
    public void createBusinessUser(JSONObject user) {
        UserInfo businessInfo = user.toJavaObject(UserInfo.class);
        String passWdHash = CipherHelper.getSHA256(businessInfo.getPassword());
        businessInfo.setPassword(passWdHash);
        userInfoMapper.insert(businessInfo);
    }

    @Override
    public Result<JSONObject> login(JSONObject user) {
        UserLoginInfo userLoginInfo = user.toJavaObject(UserLoginInfo.class);
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andEmailEqualTo(userLoginInfo.getEmail());
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        if (userInfos == null || userInfos.size() == 0) {
            return new Result("This email does not register.", 0 );
        } else {
            String passWdHash = CipherHelper.getSHA256(userLoginInfo.getPassword());
            if (userInfos.get(0).getPassword().equals(passWdHash)) {
                UserInfo userInfo = userInfos.get(0);
                UserText userText = new UserText();
                userText.setZipCode(userInfo.getZipCode());
                userText.setUserType(userInfo.getUserType());
                userText.setUsername(userInfo.getUsername());
                userText.setEmail(userInfo.getEmail());
                userText.setToken(JwtUtils.createToken(userLoginInfo));
                userText.setId(userInfo.getId());
                return new Result(userText,1);
            } else {
                return new Result("The password is not correct.", 0);
            }
        }
    }

    @Override
    public void updateBusinessUserInfo(JSONObject user) {
        UserInfo userInfo = user.toJavaObject(UserInfo.class);
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(userInfo.getId());
        userInfoMapper.updateByExample(userInfo, example);
    }

    @Override
    public void postRestaurant(JSONObject restaurant) {
        RestaurantInfo restaurantInfo = restaurant.toJavaObject(RestaurantInfo.class);
        restaurantInfoMapper.insert(restaurantInfo);
    }

    @Override
    public void saveRestaurant(JSONObject obj) {
        int userId = obj.getInteger("userId");
        String restaurantId = obj.getString("restaurantId");
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        String savedRestaurants = userInfo.getSavedRestaurants();
        JSONArray restaurants;
        if (savedRestaurants == null || savedRestaurants.length() == 0) {
            restaurants = new JSONArray();
            restaurants.add(restaurantId);
        } else {
            restaurants = (JSONArray) JSONArray.parse(userInfo.getSavedRestaurants());
            restaurants.add(restaurantId);
        }
        userInfo.setSavedRestaurants(restaurants.toString());
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(userId);
        userInfoMapper.updateByExampleSelective(userInfo, example);
    }

    @Override
    public JSONArray getSavedRestaurantsByUserId(int id) {
        UserInfo user = userInfoMapper.selectByPrimaryKey(id);
        String savedRestaurants = user.getSavedRestaurants();
        JSONArray restaurantIds = (JSONArray) JSONArray.parse(savedRestaurants);
        JSONArray res = new JSONArray();
        for (int i = 0; i < restaurantIds.size(); i++) {
            String str = restaurantIds.get(i).toString();
            if (StringUtils.isEmpty(str) || str.length() < 10) {
                RestaurantInfo restaurantInfo = restaurantInfoMapper.selectByPrimaryKey(Integer.valueOf(str));
                res.add(restaurantInfo);
            } else {
                String url = RESTAURANT_DETAIL_API + str;
                String restaurantInfo = HttpUtils.getRequest(url, null);
                JSONObject resObj = (JSONObject) JSONObject.parse(restaurantInfo);
                if (resObj.getString("error") == null) {
                    res.add(resObj);
                }
            }
        }
        return res;
    }

    @Override
    public void deleteSavedRestaurant(JSONObject obj) {
        int userId = obj.getInteger("userId");
        String resId = obj.getString("restaurantId");
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        String str = userInfo.getSavedRestaurants();
        JSONArray array = (JSONArray) JSONArray.parse(str);
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).equals(resId)) {
                array.remove(i);
                break;
            }
        }
        userInfo.setSavedRestaurants(array.toString());
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(userId);
        userInfoMapper.updateByExampleSelective(userInfo, example);
    }

    @Override
    public void postReviews(JSONObject obj) {
        ReviewInfo reviewInfo = new ReviewInfo();
        reviewInfo.setRestaurantName(obj.getString("restaurantName"));
        reviewInfo.setRating(obj.getInteger("rating"));
        reviewInfo.setText(obj.getString("text"));
        reviewInfo.setRestaurantId(obj.getString("restaurantId"));
        reviewInfo.setUserId(obj.getInteger("userId"));
        JSONObject user = obj.getJSONObject("user");
        reviewInfo.setUser(user.toJSONString());
        reviewInfoMapper.insert(reviewInfo);
    }

    @Override
    public JSONArray getReviewsByUserId(int id) throws ParseException {
        ReviewInfoExample example = new ReviewInfoExample();
        ReviewInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(id);
        List<ReviewInfo> reviewInfos = reviewInfoMapper.selectByExample(example);
        JSONArray resArray = new JSONArray();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (ReviewInfo info : reviewInfos) {
            ReviewReturnInfo resultInfo = new ReviewReturnInfo();
            resultInfo.setRestaurantId(info.getRestaurantId());
            resultInfo.setRestaurantName(info.getRestaurantName());
            resultInfo.setId(info.getId());
            resultInfo.setRating(info.getRating());
            resultInfo.setText(info.getText());
            resultInfo.setUser((JSONObject) JSONObject.parse(info.getUser()));
            String createTime = simpleDateFormat.format(info.getTimeCreated());
            resultInfo.setTime_created(createTime);
            resultInfo.setUserId(info.getUserId());
            resArray.add(resultInfo);
        }
        return resArray;
    }

    @Override
    public JSONArray getRestaurantByUserId(int id) {
        RestaurantInfoExample example = new RestaurantInfoExample();
        RestaurantInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(id);
        List<RestaurantInfo> restaurantInfos = restaurantInfoMapper.selectByExample(example);
        JSONArray object = (JSONArray) JSONArray.toJSON(restaurantInfos);
        return object;
    }

    @Override
    public void updateRestaurantByRestaurantId(JSONObject obj) {
        RestaurantInfo restaurantInfo = obj.toJavaObject(RestaurantInfo.class);
        RestaurantInfoExample example = new RestaurantInfoExample();
        RestaurantInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(restaurantInfo.getId());
        restaurantInfoMapper.updateByExampleSelective(restaurantInfo, example);
    }

    @Override
    public void deleteRestaurantByRestaurantId(int id) {
        restaurantInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteReviewById(int id) {
        reviewInfoMapper.deleteByPrimaryKey(id);
    }
}
