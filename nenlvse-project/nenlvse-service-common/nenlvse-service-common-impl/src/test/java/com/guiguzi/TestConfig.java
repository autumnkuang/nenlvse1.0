package com.guiguzi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2015/4/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:config/applicationContext*.xml"})
public class TestConfig {

    @Test
    public void testConfig(){
        System.out.println("上下文加载成功");

        System.out.println("执行成功");

    }
}
