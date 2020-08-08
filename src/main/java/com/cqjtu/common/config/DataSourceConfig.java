package com.cqjtu.common.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/** 数据源配置 @Author: cxx @Date: 2019/1/1 17:30 */
@Configuration
public class DataSourceConfig {

  @Bean
  @ConfigurationProperties("spring.datasource.druid.first")
  public DataSource firstDataSource() {
    return DruidDataSourceBuilder.create().build();
  }
}
