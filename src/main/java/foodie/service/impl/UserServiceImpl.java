package foodie.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import foodie.domain.client.BusinessInfoMapper;
import foodie.domain.client.UserInfoMapper;
import foodie.domain.model.BusinessInfo;
import foodie.domain.model.UserInfo;
import foodie.domain.model.UserInfoExample;
import foodie.domain.model.UserLoginInfo;
import foodie.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private BusinessInfoMapper businessInfoMapper;

    @Autowired
    public JSONArray getUserInfo() {
        UserInfoExample example = new UserInfoExample();
//        UserInfoExample.Criteria criteria = example.createCriteria();
//        criteria.andIdGreaterThan(1);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        JSONArray array = (JSONArray) JSONArray.toJSON(userInfos);
        return array;
    }

    @Override
    public void updateUserInfo(JSONObject user) {
        UserInfo userInfo = user.toJavaObject(UserInfo.class);
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(userInfo.getId());
        userInfoMapper.updateByExample(userInfo, example);
    }

    @Override
    public void deleteUser(int id) {
        userInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void createUser(JSONObject user) {
        UserInfo userInfo = user.toJavaObject(UserInfo.class);
        userInfoMapper.insert(userInfo);
    }

    @Override
    public void createBusinessUser(JSONObject user) {
        BusinessInfo businessInfo = user.toJavaObject(BusinessInfo.class);
        businessInfoMapper.insert(businessInfo);
    }

    @Override
    public JSONObject login(JSONObject user) {
        UserLoginInfo userLoginInfo = user.toJavaObject(UserLoginInfo.class);
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andEmailEqualTo(userLoginInfo.getEmail());
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        JSONObject res = new JSONObject();
        if (userInfos == null || userInfos.size() == 0) {
            res.put("data", "This email does not register.");
            res.put("status", 0);
        } else {
            if (userInfos.get(0).getPassword().equals(userLoginInfo.getPassword())) {
                res.put("status", 1);
                res.put("data", (JSONObject) JSONObject.toJSON(userInfos.get(0)));
            } else {
                res.put("status", 0);
                res.put("data", "The password is not correct.");
            }
        }
        return res;
    }
}
