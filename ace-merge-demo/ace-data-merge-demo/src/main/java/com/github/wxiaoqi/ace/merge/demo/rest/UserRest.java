package com.github.wxiaoqi.ace.merge.demo.rest;

import com.github.wxiaoqi.ace.merge.demo.biz.UserBiz;
import com.github.wxiaoqi.ace.merge.demo.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ace
 * @create 2017/11/20.
 */
@RestController
@RequestMapping("user")
public class UserRest {
    @Autowired
    private UserBiz userBiz;
    private Logger logger = LoggerFactory.getLogger(UserRest.class);

    @RequestMapping("/merge/auto_list")
    public List<User> getAutoMergeList() {
        return userBiz.getAotoMergeUser();
    }

    @RequestMapping("/merge/list")
    public List<User> getMergeList() {
        return userBiz.getMergeUser();
    }

    @RequestMapping("/list")
    public List<User> getList() {
        return userBiz.getUser();
    }

}
