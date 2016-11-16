package com.izerofx.wenku.configuration;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.zbus.broker.Broker;
import org.zbus.broker.BrokerConfig;
import org.zbus.broker.SingleBroker;
import org.zbus.mq.Consumer;
import org.zbus.mq.MqConfig;
import org.zbus.mq.Producer;

import com.izerofx.wenku.domain.UploadProperties;
import com.izerofx.wenku.handler.ZbusConsumerHandler;
import com.izerofx.wenku.interceptor.UserSecurityInterceptor;
import com.izerofx.wenku.service.OfficeConService;

/**
 * 
 * 类名称：WebAppConfiguration<br>
 * 类描述：应用mvc配置<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月7日 上午10:38:37<br>
 * @version v1.0
 *
 */
@Configuration
public class WebAppConfiguration extends WebMvcConfigurerAdapter {

    @Resource
    private UploadProperties uploadProperties;

    @Value("${izerofx.config.zbus.brokerAddress}")
    private String brokerAddress;

    @Value("${izerofx.config.office.port}")
    private String officePort;
    @Value("${izerofx.config.office.home}")
    private String officeHome;

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserSecurityInterceptor()).addPathPatterns("/user/**", "/doc/upload", "/doc/upload/", "/doc/save", "/doc/save/", "/doc/delfile", "/doc/delfile/").excludePathPatterns("/user/u/**", "/user/doc_list", "/user/doc_list/");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (uploadProperties.getOsType() == 1) {
            registry.addResourceHandler("/upload/**").addResourceLocations("file:///" + uploadProperties.getPath());
        } else {
            registry.addResourceHandler("/upload/**").addResourceLocations("file:///" + uploadProperties.getPath() + "/");
        }
    }

    /**
     * 注册zbus Broker中间消息服务器
     * @return
     * @throws IOException
     */
    @Bean
    @Order(0)
    @ConditionalOnClass({ Broker.class })
    public Broker broker() {
        BrokerConfig brokerConfig = new BrokerConfig();
        brokerConfig.setBrokerAddress(brokerAddress);
        brokerConfig.setMaxTotal(20);
        Broker broker = null;
        try {
            broker = new SingleBroker(brokerConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return broker;
    }

    /**
     * 注册zbus MQ生产者
     * @return
     */
    @Bean
    @Order(1)
    @ConditionalOnClass({ Producer.class })
    public Producer producer() {
        Producer producer = null;
        try {
            producer = new Producer(broker(), "wenku-MQ");
            producer.createMQ();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return producer;
    }

    /**
     * 注册zbus MQ消费者
     * @return
     */
    @Bean
    @Order(2)
    @ConditionalOnClass({ Consumer.class })
    public Consumer consumer() {
        MqConfig mqConfig = new MqConfig();
        mqConfig.setBroker(broker());
        mqConfig.setMq("wenku-MQ");
        Consumer consumer = new Consumer(mqConfig);
        try {
            consumer.start(new ZbusConsumerHandler());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return consumer;
    }

    /**
     * 注册office转换服务
     * @return
     */
    @Bean(name = "officeConService", initMethod = "init", destroyMethod = "destroy")
    @ConditionalOnClass({ OfficeConService.class })
    public OfficeConService officeConService() {
        return new OfficeConService(officePort, officeHome);
    }
}
