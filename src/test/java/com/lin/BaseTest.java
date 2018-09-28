package com.lin;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * @author lkmc2
 * @date 2018/4/25.
 * spring和junit整合，junit启动时加载spring
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AwesomeVideoApplication.class})
@WebAppConfiguration
public class BaseTest {

}
