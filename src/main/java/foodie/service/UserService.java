package foodie.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import foodie.common.Result;

import java.text.ParseException;

public interface UserService {

    Result login(JSONObject user);
    //for customer
    JSONObject getUserInfo(int id);
    void updateBusinessUserInfo(JSONObject user);
    void updateUserInfo(JSONObject user);
    void deleteUser(int id);
    void createUser(JSONObject user);
    void saveRestaurant(JSONObject obj);
    JSONArray getSavedRestaurantsByUserId(int id);
    void deleteSavedRestaurant(JSONObject obj);
    void postReviews(JSONObject obj);
    void deleteReviewById(int id);

    JSONArray getReviewsByUserId(int id) throws ParseException;

    //for businuess user
    void createBusinessUser(JSONObject user);
    void postRestaurant(JSONObject restaurant);
    JSONArray getRestaurantByUserId(int id);
    void updateRestaurantByRestaurantId(JSONObject obj);
    void deleteRestaurantByRestaurantId(int id);

}
