package com.lin.dao;

import com.lin.model.User;
import com.lin.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends MyMapper<User> {

    boolean login(@Param("username") String username, @Param("password") String password);

}