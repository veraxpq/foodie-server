package foodie.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import foodie.common.Result;

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

    JSONArray getReviewsByUserId(int id);

    //for businuess user
    void createBusinessUser(JSONObject user);
    void postRestaurant(JSONObject restaurant);
    JSONObject getRestaurantByUserId(int id);
    void updateRestaurantByRestaurantId(int id);
    void deleteRestaurantByRestaurantId(int id);

}
