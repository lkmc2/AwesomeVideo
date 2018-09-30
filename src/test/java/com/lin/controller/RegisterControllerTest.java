package com.lin.controller;

import com.alibaba.fastjson.JSONObject;
import com.lin.BaseControllerTest;
import com.lin.model.User;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.IsNull.notNullValue;


import static org.hamcrest.core.IsNull.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author lkmc2
 * @date 2018/9/30
 * @description
 */
public class RegisterControllerTest extends BaseControllerTest {

    @Test
    public void register() throws Exception {
        User user = new User();
        user.setUsername("Java");
        user.setPassword("123456");

        String requestJson = JSONObject.toJSONString(user);

        String responseString = mockMvc.perform
                (
                        MockMvcRequestBuilders
                                .post("http://127.0.0.1/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("Java"))
                .andExpect(jsonPath("$.data.password").value(""))
                .andDo(print())
//                .andExpect(status().isBadRequest()) 400请求
                .andReturn().getResponse().getContentAsString();

        System.out.println("返回的结果：" + responseString);
    }

}