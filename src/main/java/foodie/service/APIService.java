package foodie.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface APIService {
    JSONObject searchRestaurantsByLocation(String cityName);
}
