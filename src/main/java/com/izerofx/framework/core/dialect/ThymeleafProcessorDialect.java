package com.izerofx.framework.core.dialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

/**
 * 
 * 类名称：ThymeleafProcessorDialect<br>
 * 类描述：Thymeleaf模版自定义方言<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月7日 下午2:37:57<br>
 * @version v1.0
 *
 */
public class ThymeleafProcessorDialect extends AbstractProcessorDialect {
    private static final String DIALECT_NAME = "izerofx Dialect"; // 方言名称需要唯一。自定义方言名称
    public static final String DIALECT_PREFIX = "izerofx"; // 前缀

    /**
     * 
     * 创建一个新的实例 ThymeleafProcessorDialect.
     */
    public ThymeleafProcessorDialect() {
        super(DIALECT_NAME, DIALECT_PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

    /*
     * (non-Javadoc)
     * @see org.thymeleaf.dialect.IProcessorDialect#getProcessors(java.lang.String)
     */
    @Override
    public Set<IProcessor> getProcessors(final String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new PrettytimeProcessor()); // 日期美化 Processor
        processors.add(new PropertiesProcessor()); // 获取配置文件 Processor
        return processors;
    }
}
