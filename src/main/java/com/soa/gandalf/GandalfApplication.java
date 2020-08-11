package com.soa.gandalf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

/**
 * 主函数
 *
 * @author think <syj247@qq.com>、
 * @date 2020/8/10、9:01
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GandalfApplication {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(GandalfApplication.class, args);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
