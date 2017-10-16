package cn.com.flaginfo.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.taobao.pandora.boot.PandoraBootstrap;

import cn.com.flaginfo.platform.common.support.PropertiesSourceInitializer;

/**
 * 启动类
 *
 * @author Rain
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
@ServletComponentScan
@EnableTransactionManagement
//加载HSF配置
@ImportResource(locations = {"classpath:hsf-beans.xml","classpath*:sentinel-tracer.xml"}) 
public class WebApplication {

    public static void main(String[] args) {
        PandoraBootstrap.run(args);

        SpringApplication sa = new SpringApplication(WebApplication.class);

        sa.addInitializers(new PropertiesSourceInitializer());

        sa.run(args);

        // 标记服务启动完成,并设置线程wait。 通常在main函数最后一行调用。
        PandoraBootstrap.markStartupAndWait();
    }

}  