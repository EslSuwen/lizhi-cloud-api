package com.cqjtu;

import com.cqjtu.common.event.MyEvent;
import com.cqjtu.common.logger.LogUtil;
import com.cqjtu.common.logger.LoggerMarkers;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.event.EventListener;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * lizhiCloudAdminApplication 启动类
 *
 * @author suwen
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.cqjtu.modules.*.dao"})
@EnableSwagger2
public class LizhiCloudApiApplication extends SpringBootServletInitializer {
  private static final Logger logger = LoggerFactory.getLogger(LizhiCloudApiApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(LizhiCloudApiApplication.class, args);
    LogUtil.debug(logger, LoggerMarkers.BUSINESS, "启动成功....");
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(LizhiCloudApiApplication.class);
  }

  @EventListener
  public void listenEvent(MyEvent<String> event) {
    System.out.println(
        "监听到PersonSaveEvent事件; 接收到的值：" + event.getMsg() + "；发布的时间为" + System.currentTimeMillis());
  }
}
