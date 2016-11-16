package com.izerofx.framework.core.dialect;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.processor.AbstractStandardExpressionAttributeTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

import com.izerofx.framework.basic.util.CastUtil;
import com.izerofx.framework.basic.util.PropertiesUtil;

/**
 * 
 * 类名称：PrettytimeProcessor<br>
 * 类描述：友好时间显示<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月7日 下午2:34:04<br>
 * @version v1.0
 *
 */
@Component
public class PropertiesProcessor extends AbstractStandardExpressionAttributeTagProcessor {

    public static final int PRECEDENCE = 10000; // 优先级
    public static final String DEFAULT_DIALECT_PREFIX = "izerofx"; // 默认前缀
    public static final String ATTR_NAME = "getValue"; // 属性名称

    /**
     * 
     * 创建一个新的实例 PrettytimeProcessor.
     */
    public PropertiesProcessor() {
        super(TemplateMode.HTML, DEFAULT_DIALECT_PREFIX, ATTR_NAME, PRECEDENCE, true);
    }

    @Override
    protected void doProcess(final ITemplateContext context, final IProcessableElementTag tag, final AttributeName attributeName, final String attributeValue, final Object expressionResult, final IElementTagStructureHandler structureHandler) {
        structureHandler.setBody(PropertiesUtil.getValue(CastUtil.castString(expressionResult)), false);
    }
}
