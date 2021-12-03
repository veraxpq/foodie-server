package foodie.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import foodie.domain.client.UserInfoMapper;
import foodie.domain.model.UserInfo;
import foodie.domain.model.UserInfoExample;
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
}
