package com.izerofx.wenku.configuration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * 
 * 类名称：DruidConfig<br>
 * 类描述：druid数据源配置<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月3日 上午9:33:04<br>
 * @version v1.0
 *
 */
@Configuration
public class DruidConfiguration {

    private static final String DB_PREFIX = "spring.datasource.";

    @Autowired
    private Environment environment;

    /**
     * druid数据源
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = DB_PREFIX)
    public DataSource druidDataSource() {

        Properties dbProperties = new Properties();
        Map<String, Object> map = new HashMap<>();
        for (Iterator<PropertySource<?>> it = ((AbstractEnvironment) environment).getPropertySources().iterator(); it.hasNext();) {
            PropertySource<?> propertySource = it.next();
            getPropertiesFromSource(propertySource, map);
        }
        dbProperties.putAll(map);

        DruidDataSource dds = null;
        try {
            dds = (DruidDataSource) DruidDataSourceFactory.createDataSource(dbProperties);
            if (null != dds) {
                dds.init();
            }
        } catch (Exception e) {
            throw new RuntimeException("load datasource error, dbProperties is :" + dbProperties, e);
        }
        return dds;
    }

    private void getPropertiesFromSource(PropertySource<?> propertySource, Map<String, Object> map) {
        if (propertySource instanceof MapPropertySource) {
            for (String key : ((MapPropertySource) propertySource).getPropertyNames()) {
                if (key.startsWith(DB_PREFIX)) {
                    map.put(key.replaceFirst(DB_PREFIX, ""), propertySource.getProperty(key));
                }
            }
        }

        if (propertySource instanceof CompositePropertySource) {
            for (PropertySource<?> s : ((CompositePropertySource) propertySource).getPropertySources()) {
                getPropertiesFromSource(s, map);
            }
        }
    }

    /**
     * 注册druid数据源监控 Servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    }

    /**
     * 注册druid 过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
