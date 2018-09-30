package com.lin;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author lkmc2
 * @date 2018/4/25.
 * spring和junit整合，junit启动时加载spring
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Before()
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();  //初始化MockMvc对象
    }

}
