package foodie.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import foodie.domain.client.UserInfoMapper;
import foodie.domain.model.UserInfo;
import foodie.domain.model.UserInfoExample;
import foodie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    public JSONObject getUserInfo() {
        UserInfoExample example = new UserInfoExample();
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        JSONObject user = (JSONObject) JSONObject.toJSON(userInfos.get(0));
        return user;
    }
}
