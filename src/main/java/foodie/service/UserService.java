package foodie.service;

import com.alibaba.fastjson.JSONObject;
import foodie.domain.model.UserInfo;

public interface UserService {
    JSONObject getUserInfo();
}
