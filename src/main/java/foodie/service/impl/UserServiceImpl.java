package foodie.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.rowset.CachedRowSetImpl;
import foodie.domain.client.BusinessInfoMapper;
import foodie.domain.client.UserInfoMapper;
import foodie.domain.model.BusinessInfo;
import foodie.domain.model.BusinessInfoExample;
import foodie.domain.model.UserInfo;
import foodie.domain.model.UserInfoExample;
import foodie.model.UserLoginInfo;
import foodie.service.UserService;
import foodie.util.ExceptionUtil;
import foodie.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private BusinessInfoMapper businessInfoMapper;

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
        userInfoMapper.updateByExample(userInfo, example);
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
        } else {
            if (userInfos.get(0).getPassword().equals(userLoginInfo.getPassword())) {
                JSONObject userJson = (JSONObject) JSONObject.toJSON(userInfos.get(0));
                userJson.put("token", JwtUtils.createToken(userLoginInfo));
                res.put("status", 1);
                res.put("data", userJson);
            } else {
                res.put("data", "The password is not correct.");
            }
        }
        return res;
    }

    @Override
    public void updateBusinessUserInfo(JSONObject user) {
        BusinessInfo userInfo = user.toJavaObject(BusinessInfo.class);
        BusinessInfoExample example = new BusinessInfoExample();
        BusinessInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(userInfo.getId());
        businessInfoMapper.updateByExample(userInfo, example);
    }
}
