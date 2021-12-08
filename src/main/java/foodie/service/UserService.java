package foodie.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface UserService {

    JSONObject login(JSONObject user);
    //for customer
    JSONObject getUserInfo(int id);
    void updateBusinessUserInfo(JSONObject user);
    void updateUserInfo(JSONObject user);
    void deleteUser(int id);
    void createUser(JSONObject user);

    //for businuess user
    void createBusinessUser(JSONObject user);
}
