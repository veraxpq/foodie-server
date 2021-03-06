package foodie.controller;

import com.alibaba.fastjson.JSONArray;
import foodie.common.Result;
import foodie.service.UserService;
import com.alibaba.fastjson.JSONObject;
import foodie.wrapper.VerifyToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

@RestController
@RequestMapping("/foodie")
@Service
public class UserController {

    @Autowired
    private UserService userService;

    @VerifyToken
    @GetMapping(value="/userInfo")
    public Result<JSONObject> getUserInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id) {
        JSONObject user = userService.getUserInfo(id);
        return new Result<>(user, 1);
    }

    @VerifyToken
    @PutMapping(value = "/updateUserInfo")
    public Result updateUserInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject user) {
        userService.updateUserInfo(user);
        return new Result("", 1);
    }

    @VerifyToken
    @PutMapping(value = "/updateBusinessUserInfo")
    public Result updateBusinessUserInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject user) {
        userService.updateBusinessUserInfo(user);
        return new Result("", 1);
    }

    @PostMapping(value = "/createUser")
    public Result createUser(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject obj) {
        userService.createUser(obj);
        return new Result("", 1);
    }

    @VerifyToken
    @DeleteMapping(value = "/deleteUser")
    public Result deleteUser(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam("id") int id) {
        userService.deleteUser(id);
        return new Result("", 1);
    }

    @PostMapping(value = "/createNewBusinessUser")
    public Result createNewBusinessUser(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject obj) {
        userService.createBusinessUser(obj);
        return new Result("", 1);
    }

    @PostMapping(value = "/login")
    public Result<JSONObject> login(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject obj) {
        return userService.login(obj);
    }

    @VerifyToken
    @PostMapping(value = "/saveRestaurant")
    public Result<JSONObject> saveRestaurant(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject obj) {
        userService.saveRestaurant(obj);
        return new Result("", 1);
    }

    @VerifyToken
    @GetMapping(value="/getSavedRestaurantsByUserId")
    public Result<JSONArray> getSavedRestaurantsByUserId(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id) {
        JSONArray restaurants = userService.getSavedRestaurantsByUserId(id);
        return new Result<>(restaurants, 1);
    }

    @VerifyToken
    @DeleteMapping(value = "/deleteSavedRestaurant")
    public Result<JSONObject> deleteSavedRestaurant(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject obj) {
        userService.deleteSavedRestaurant(obj);
        return new Result("", 1);
    }

    @VerifyToken
    @PostMapping(value = "/postReviews")
    public Result<JSONObject> postReviews(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject obj) {
        userService.postReviews(obj);
        return new Result("", 1);
    }

    @VerifyToken
    @GetMapping(value="/getReviewsByUserId")
    public Result<JSONArray> getReviewsByUserId(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id) throws ParseException {
        JSONArray reviews = userService.getReviewsByUserId(id);
        return new Result<>(reviews, 1);
    }

    @VerifyToken
    @PostMapping(value = "/postRestaurant")
    public Result<JSONObject> postRestaurant(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject obj) {
        userService.postRestaurant(obj);
        return new Result("", 1);
    }

    @VerifyToken
    @GetMapping(value="/getRestaurantByUserId")
    public Result<JSONArray> getRestaurantByUserId(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id) {
        JSONArray obj = userService.getRestaurantByUserId(id);
        return new Result<>(obj, 1);
    }

    @VerifyToken
    @PostMapping(value = "/updateRestaurantByRestaurantId")
    public Result<JSONObject> updateRestaurantByRestaurantId(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject obj) {
        userService.updateRestaurantByRestaurantId(obj);
        return new Result("", 1);
    }

    @VerifyToken
    @DeleteMapping(value="/deleteRestaurantByRestaurantId")
    public Result deleteRestaurantByRestaurantId(HttpServletRequest request, HttpServletResponse response, @RequestParam("restaurantId") int restaurantId) {
        userService.deleteRestaurantByRestaurantId(restaurantId);
        return new Result("", 1);
    }

    @VerifyToken
    @DeleteMapping(value = "/deleteReviewById")
    public Result deleteReviewById(HttpServletRequest request, HttpServletResponse response, @RequestParam("reviewId") int reviewId) {
        userService.deleteReviewById(reviewId);
        return new Result("", 1);
    }
}