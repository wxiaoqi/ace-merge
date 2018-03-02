package com.github.wxiaoqi.ace.merge.provider.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ace
 * @create 2017/11/20.
 */
@RestController
@RequestMapping("data")
public class DataRest {

    private Logger logger = LoggerFactory.getLogger(DataRest.class);

    @RequestMapping("sex")
    public Map<String, String> getGenders(String key) {
        if ("sex_key".equals(key)) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("man", "男");
            map.put("woman", "女");
            return map;
        } else {
            return new HashMap<String, String>();
        }
    }

    @RequestMapping("city")
    public Map<String, String> getCities(String ids) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "广州");
        map.put("2", "武汉");
        return map;
    }
}
