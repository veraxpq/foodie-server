package foodie.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import foodie.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RestController
public class RestaurantController {

    @Autowired
    private APIService apiService;

    @GetMapping("/getRestaurant")
    public JSONObject getRestaurant(HttpServletRequest request, HttpServletResponse response, @RequestParam String cityName) {
        return apiService.searchRestaurantsByLocation(cityName);
    }


    @RequestMapping(value = "/demo/test", method = RequestMethod.POST)
    public JSONObject reco(HttpServletRequest request, HttpServletResponse response,
                           @RequestBody String input) {
        JSONObject obj = new JSONObject();
        obj.put("input", input);
        return obj;
    }
}
