package com.izerofx.framework.basic.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * 
 * 类名称：PropertiesLoader<br>
 * 类描述：Properties文件载入工具类，可载入多个properties文件<br>
 *        相同的属性在最后载入的文件中将会覆盖之前的值，但以System的properties值有限<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年4月6日 下午3:11:28<br>  
 * @version v1.0
 *
 */
public class PropertiesLoader {
	
	private static Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);
	
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();
	
	private final Properties properties;

	/**
	 * 
	 * 创建一个新的实例 PropertiesLoader.     
	 * @param resourcesPaths
	 */
	public PropertiesLoader(String ... resourcesPaths) {
		properties = loadProperties(resourcesPaths);
	}

	public Properties getProperties() {
		return properties;
	}
	
	/**
	 * 取出Property，但以System的Property优先，取不到返回空字符串。
	 * @param key
	 * @return
	 */
	private String getValue(String key){
		String systemProperty = System.getProperty(key);
		if(systemProperty != null){
			return systemProperty;
		}
		if(properties.containsKey(key)){
			return properties.getProperty(key);
		}
		return "";
	}
	
	/**
	 * 取出String类型的Property，但以System的Property优先，如果都为Null则抛出异常 
	 * @param key
	 * @return
	 */
	public String getProperty(String key){
		String value = getValue(key);
		if(value == null){
			throw new NoSuchElementException();
		}
		return value;		
	}
	
	/**
	 * 取出String类型的Property，但以System的Property优先，如果都为null则返回Default的值
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getProperty(String key, String defaultValue){
		String value = getValue(key);
		return value != null ? value : defaultValue;
	}
	
	/**
	 * 取出Integer类型的Property，但以System的Property优先。如果都为null或内容错误则抛出异常。
	 * @param key
	 * @return
	 */
	public Integer getInteger(String key){
		String value = getValue(key);
		if(value == null){
			throw new NoSuchElementException();
		}
		return Integer.valueOf(value);
	}
	
	/**
	 * 取出Integer类型的Property，但以System的Property优先。如果都为null则返回Default的值
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Integer getInteger(String key, Integer defaultValue){
		String value = getValue(key);
		return value != null ? Integer.valueOf(value) : defaultValue;
	}
	
	/**
	 * 取出Double类型的Property，但以System的Property优先。如果都为null或内容错误则抛出异常。
	 * @param key
	 * @return
	 */
	public Double getDouble(String key){
		String value = getValue(key);
		if(value == null){
			throw new NoSuchElementException();
		}
		return Double.valueOf(value);
	}
	
	/**
	 * 取出Double类型的Property，但以System的Property优先。如果都为null则返回Default的值
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Double getDouble(String key, Double defaultValue){
		String value = getValue(key);
		return value != null ? Double.valueOf(value) : defaultValue;
	}
	
	/**
	 * 取出Boolean类型的Property，但以System的Property优先。如果都为null或内容错误则抛出异常。
	 * @param key
	 * @return
	 */
	public Boolean getBoolean(String key){
		String value = getValue(key);
		if(value == null){
			throw new NoSuchElementException();
		}
		return Boolean.valueOf(value);
	}
	
	/**
	 * 取出Boolean类型的Property，但以System的Property优先。如果都为null则返回Default的值
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Boolean getBoolean(String key, Boolean defaultValue){
		String value = getValue(key);
		return value != null ? Boolean.valueOf(value) : defaultValue;
	}
	
	/**
	 * 载入多个文件，文件路径使用Spring Resource格式
	 * @param resourcePaths
	 * @return
	 */
	private Properties loadProperties(String ... resourcePaths){
		Properties props = new Properties();
		
		for(String location : resourcePaths){
			
			logger.debug("Loading properties file from" + location);
			
			InputStream is = null;	
			try {
				Resource resource = resourceLoader.getResource(location);
				is = resource.getInputStream();
				props.load(is);
			} catch (IOException e) {
				logger.error("could not load properties from path:" + location + "," + e.getMessage(),e);
			} finally {
				IOUtils.closeQuietly(is);
			}	
		}		
		return props;	
	}
}
