package com.elastic.job.service;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huwenbin
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Override
    public List<User> getUserInfo(User user) {
        List<User> list = Lists.newArrayList();
        User u1 = new User();
        u1.setId(user.getId());
        u1.setName("张三");
        u1.setSex("男");
        User u2 = new User();
        u2.setId(user.getId());
        u2.setName("李四");
        u2.setSex("男");
        list.add(u1);
        list.add(u2);
        return list;
    }
}
