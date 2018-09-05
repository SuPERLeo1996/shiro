package com.Leo.dao;

import com.Leo.vo.User;

import java.util.List;

/**
 * @Auther: Leo
 * @Date: 2018/9/5 15:32
 * @Description:
 */
public interface UserDao {
    User getUserByUserName(String username);

    List<String> queryRolesByUserName(String username);
}
