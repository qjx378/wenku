package com.izerofx.wenku.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;

/**
 * 
 * 类名称：FastJsonHttpMessageConvertersConfiguration<br>
 * 类描述：fastjson配置<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年8月11日 下午5:00:32<br>
 * @version v1.0
 *
 */
@Configuration
@ConditionalOnClass({ JSON.class })
//判断JSON这个类文件是否存在，存在才会创建配置
public class FastJsonHttpMessageConvertersConfiguration {
    
    @Configuration
    //判断是否存在类
    @ConditionalOnClass({ FastJsonHttpMessageConverter.class })
    //使用spring.http.converters.preferred-json-mapper属性来启动功能
    @ConditionalOnProperty(
            name = { "spring.http.converters.preferred-json-mapper" },
            havingValue = "fastjson",
            matchIfMissing = true
    )
    protected static class FastJson2HttpMessageConverterConfiguration {
        protected FastJson2HttpMessageConverterConfiguration() {}

        @Bean
        @ConditionalOnMissingBean({ FastJsonHttpMessageConverter4.class })
        //当没有注册这个类时，自动注册Bean
        public FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter() {
            FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();

            //使用最新的官方推荐配置对象的方式来注入fastjson的序列化特征
            FastJsonConfig fastJsonConfig = new FastJsonConfig();
            fastJsonConfig.setSerializerFeatures(
                    SerializerFeature.WriteDateUseDateFormat,
                    SerializerFeature.WriteNullListAsEmpty,
                    SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteNullBooleanAsFalse,
                    SerializerFeature.WriteNullStringAsEmpty
             );
            
            //添加对json值的过滤，因为像移动APP，服务端在传json值时最好不要传null，而是使用“”，这是一个演示
            ValueFilter valueFilter = new ValueFilter() {//5
                //o 是class
                //s 是key值
                //o1 是value值
                public Object process(Object o, String s, Object o1) {
                    if (null == o1) {
                        o1 = "";
                    }
                    return o1;
                }
            };
            fastJsonConfig.setSerializeFilters(valueFilter);

            converter.setFastJsonConfig(fastJsonConfig);

            return converter;
        }
    }
}
