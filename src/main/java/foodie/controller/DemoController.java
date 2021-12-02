package foodie.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class DemoController {
    //private static Logger logger = LoggerFactory.getLogger(DemoController.class);


    /*@RequestMapping(value = "/demo/test", method = RequestMethod.POST)
    public JSONObject reco(HttpServletRequest request, HttpServletResponse response,
                           @RequestBody String input) {
        JSONObject obj = new JSONObject();
        obj.put("input", input);
        return obj;
    }*/
}
