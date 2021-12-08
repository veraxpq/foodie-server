package foodie.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import foodie.common.Result;
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

    @GetMapping("/getRestaurantByLocation")
    public Result<JSONObject> getRestaurantByLocation(HttpServletRequest request, HttpServletResponse response, @RequestParam String cityName) {
        return new Result<>(apiService.searchRestaurantsByLocation(cityName), 1);
    }

    @PostMapping("/getRestaurantByTermAndLocation")
    public Result<JSONObject> getRestaurantByTermAndLocation(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject obj) {
        return new Result<>(apiService.searchRestaurantsByLocationAndTerm(obj), 1);
    }

    @GetMapping("/getRestaurantById")
    public Result<JSONObject> getRestaurantById(HttpServletRequest request, HttpServletResponse response, @RequestParam String restaurantId) {
        return new Result<>(apiService.getRestaurantInfoById(restaurantId), 1);
    }

    @GetMapping("/getReviewsByRestaurantId")
    public Result<JSONArray> getReviewsByRestaurantId(HttpServletRequest request, HttpServletResponse response, @RequestParam String restaurantId) {
        return new Result<>(apiService.getReviewsByRestaurantId(restaurantId), 1);
    }
}
