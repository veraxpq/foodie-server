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

@RestController
@RequestMapping("/foodie")
@Service
public class UserController {

    @Autowired
    private UserService userService;

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
        JSONObject res = userService.login(obj);
        return new Result(res, 1);

    }
}
