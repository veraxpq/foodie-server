package foodie.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import foodie.service.APIService;
import foodie.util.HttpUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class APIServiceImpl implements APIService {

    private final String LOCATION_API = "https://api.yelp.com/v3/businesses/search";
    @Override
    public JSONObject searchRestaurantsByLocation(String cityName) {
        Map<String, String> map = new HashMap<>();
        map.put("term", "restaurants");
        map.put("location", cityName);
        String result = HttpUtils.getRequest(LOCATION_API, map);
        JSONObject resultJson = (JSONObject) JSONObject.parse(result);
        return resultJson;
    }

    @Override
    public JSONObject searchRestaurantsByLocationAndTerm(JSONObject obj) {
        String location = obj.getString("location");
        JSONArray term = obj.getJSONArray("term");
        Map<String, String> map = new HashMap<>();
        map.put("term", term.toJSONString());
        map.put("location", location);
        String result = HttpUtils.getRequest(LOCATION_API, map);
        JSONObject resultJson = (JSONObject) JSONObject.parse(result);
        return resultJson;
    }
}
