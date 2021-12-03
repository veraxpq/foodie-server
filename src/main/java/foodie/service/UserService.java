package foodie.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface UserService {
    JSONArray getUserInfo();
    void updateUserInfo(JSONObject user);
    void deleteUser(int id);
    void createUser(JSONObject user);
}
