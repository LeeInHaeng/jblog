package com.cafe24.jblog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.cafe24.jblog.config.appconfig.DBConfig;
import com.cafe24.jblog.config.appconfig.MyBatisConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.cafe24.jblog.service", "com.cafe24.jblog.dao"})
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfigImport {

}
