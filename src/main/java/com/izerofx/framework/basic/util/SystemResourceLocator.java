package com.izerofx.framework.basic.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 
 * 类名称：SystemResourceLocator<br>
 * 类描述：系统属性资源存储类.<br>
 * 存储系统属性配置文件system-config.properties中值;也可以添加自己的属性到该类中<br>
 * 便于在其他地方调用,但添加的时候不能有重名.除非显示调用需要覆盖的接口<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年4月6日 下午3:13:26<br>
 * @version v1.0
 *
 */
@Component
@Lazy(false)
public class SystemResourceLocator implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(SystemResourceLocator.class);

    /** 系统资源map */
    private static final Map<Object, Object> resourceMap = new Hashtable<Object, Object>();

    private static final List<ResourceReader> resourceReaders = new ArrayList<ResourceReader>();

    private static final String CONFIG_PREFIX = "izerofx.config.";

    // spring的容器上下文
    private static ApplicationContext context;

    public static void setContext(ApplicationContext context) {
        SystemResourceLocator.context = context;
    }

    /**
     * 实例化,执行非静态变量块
     */
    private static SystemResourceLocator instance = new SystemResourceLocator();

    public SystemResourceLocator() {
        // 初始化属性来源,如果里面的key值和前面部分来源的key值重复 ,会覆盖原来的值
        resourceReaders.add(new ResourceReaderFile());// 来源于属性文件
    }

    public static final SystemResourceLocator getInstance() {
        return instance;
    }

    /**
     * 刷新系统的配置属性.清空原来的属性值,并从系统属性文件和属性表中读取最新值.
     */
    public synchronized static void refresh() {
        resourceMap.clear();
        for (Iterator<ResourceReader> iterator = resourceReaders.iterator(); iterator.hasNext();) {
            ResourceReader resourceReader = (ResourceReader) iterator.next();
            resourceMap.putAll(resourceReader.readResource());
        }
    }

    /**
     * 取值
     * @param key
     * @return
     */
    public synchronized static Object getValue(Object key) {
        // 如果为空,就先刷新
        if (isEmpty()) refresh();

        Object result = null;
        if (resourceMap.containsKey(key)) result = resourceMap.get(key);
        return result;
    }

    /**
     * 添加系统属性
     * @param key
     * @param value
     */
    public synchronized static void setValue(Object key, Object value) {
        setValue(key, value, false);
    }

    /**
     * 添加系统属性
     * @param key
     * @param value
     * @param isCover 是否可以覆盖
     */
    public synchronized static void setValue(Object key, Object value, boolean isCover) {
        if (!isCover && resourceMap.containsKey(key)) throw new RuntimeException("系统已存在属性" + key + ",不能覆盖该项值.");
        resourceMap.put(key, value);
    }

    public synchronized static boolean isEmpty() {
        return resourceMap.isEmpty();
    }

    /**
     * 
     * 类名称：ResourceReaderFile<br>
     * 类描述：从属性文件中读取配置项目.<br>
     * 创建人：qinjiaxue<br>
     * 创建时间：2016年4月6日 下午3:17:51<br>
     * @version v1.0
     *
     */
    private class ResourceReaderFile implements ResourceReader {
        private static final String DEFAULTFILENAME = "application.properties";

        private final Map<Object, Object> map = new HashMap<Object, Object>();

        private ClassLoader getClassLoader() {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if (cl == null) {
                cl = PropertiesUtil.class.getClassLoader();
            }
            return cl;
        }

        private InputStream getInputStream(String fileName) throws IOException {
            if (fileName == null) fileName = DEFAULTFILENAME;

            InputStream in = getClassLoader().getResourceAsStream(fileName);
            if (in == null) {
                in = ClassLoader.getSystemResourceAsStream(fileName);
            }
            if (in == null) {
                String msg = "找不到属性配置文件:   " + fileName;
                throw new IOException(msg);
            }
            return in;

        }

        private void loadProperties(String fileName) {
            Properties propertie = new Properties();
            try {
                InputStream inputStream = getInputStream(fileName);
                propertie.load(inputStream);
                inputStream.close();
                // 如果属性文件没有转码的话,这里就需要转码
                Set<Object> set = propertie.keySet();
                for (Object keyObj : set) {
                    if (StringUtils.startsWith(CastUtil.castString(keyObj), CONFIG_PREFIX)) {
                        map.put(keyObj, PropertiesUtil.isoToUTF((String) propertie.get(keyObj)));
                    }
                }
            } catch (FileNotFoundException ex) {
                log.info("工作流属性配置文件不存在！");
                ex.printStackTrace();
            } catch (IOException ex) {
                log.info("装载属性配置文件失败!");
                log.info("请检查文件名和路径是否正确！");
                ex.printStackTrace();
            } catch (Exception eee) {
                log.info("读取属性配置文件失败！\n - 可能原因：文件不存在。");
                eee.printStackTrace();
            }
        }

        public Map<Object, Object> readResource() {
            loadProperties(DEFAULTFILENAME);
            return map;
        }

    }

    private interface ResourceReader {
        Map<Object, Object> readResource();
    }

    @Override
    @SuppressWarnings("static-access")
    public void setApplicationContext(ApplicationContext contex) throws BeansException {
        this.context = contex;
    }

    /**
     * 通过bean名称获取bean
     * @param beanName
     * @return
     */
    public synchronized static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    /**
     * 通过bean名称及类型获取bean
     * @param beanName
     * @param requiredType
     * @return
     */
    public synchronized static Object getBean(String beanName, Class<?> requiredType) {
        return context.getBean(beanName, requiredType);
    }

}
