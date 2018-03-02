package com.github.wxiaoqi.ace.merge.demo.biz;

import com.github.wxiaoqi.ace.merge.demo.entity.User;
import com.github.wxiaoqi.ace.merge.demo.merge.TestMergeResultParser;
import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.github.wxiaoqi.merge.core.MergeCore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ace
 * @create 2018/2/1.
 */
@Service
@Slf4j
public class UserBiz {
    @Autowired
    private MergeCore mergeCore;

    /**
     * aop注解的聚合方式
     * 其中聚合的方法返回值必须为list,
     * 如果为复杂对象,则需要自定义自己的聚合解析器(实现接口IMergeResultParser)
     */
    @MergeResult(resultParser = TestMergeResultParser.class)
    public List<User> getAotoMergeUser() {
        ArrayList<User> users = new ArrayList<User>();
        for (int i = 1000; i > 0; i--) {
            users.add(new User("zhangsan" + i, "man", "1"));
            users.add(new User("lisi" + i, "woman", "2"));
            users.add(new User("wangwu" + i, "unkonwn", "2"));
        }
        return users;
    }

    public List<User> getUser() {
        ArrayList<User> users = new ArrayList<User>();
        for (int i = 1000; i > 0; i--) {
            users.add(new User("zhangsan" + i, "man", "1"));
            users.add(new User("lisi" + i, "woman", "2"));
            users.add(new User("wangwu" + i, "unkonwn", "2"));
        }
        return users;
    }

    /**
     * 手动聚合方式
     *
     * @return
     */
    public List<User> getMergeUser() {
        ArrayList<User> users = new ArrayList<User>();
        for (int i = 1000; i > 0; i--) {
            users.add(new User("zhangsan" + i, "man", "1"));
            users.add(new User("lisi" + i, "woman", "2"));
            users.add(new User("wangwu" + i, "unkonwn", "2"));
        }
        try {
            // list 聚合
            mergeCore.mergeResult(User.class, users);
            // 单个对象聚合
//            mergeCore.mergeOne(User.class,users.get(0));
        } catch (Exception e) {
            log.error("数据聚合失败", e);
        } finally {
            return users;
        }
    }
}
