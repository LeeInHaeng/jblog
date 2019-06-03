package com.cafe24.jblog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.cafe24.jblog.config.webconfig.FileUploadConfig;
import com.cafe24.jblog.config.webconfig.InterceptorConfig;
import com.cafe24.jblog.config.webconfig.MVCConfig;
import com.cafe24.jblog.config.webconfig.MessageConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.cafe24.jblog.controller"})
@Import({FileUploadConfig.class, InterceptorConfig.class, MessageConfig.class, MVCConfig.class})
public class WebConfigImport {

}
