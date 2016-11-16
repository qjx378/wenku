package com.izerofx.wenku.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.AbstractProcessorDialect;

import com.izerofx.framework.core.dialect.ThymeleafProcessorDialect;

/**
 * 
 * 类名称：ThymeleafConfig<br>
 * 类描述：Thymeleaf模版配置<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月7日 下午2:45:00<br>
 * @version v1.0
 *
 */
@Configuration
public class ThymeleafConfiguration {
    
    @Bean
    public AbstractProcessorDialect thymeleafProcessorDialect() {
        return new ThymeleafProcessorDialect();
    }

}
