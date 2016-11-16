package com.izerofx.framework.basic.util;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 类名称：StringEscapeEditor<br>
 * 类描述：字符串转义<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2015年10月28日 下午12:39:02<br>
 * @version v1.0
 *
 */
public class StringEscapeEditor extends PropertyEditorSupport {
    private boolean escape;

    /**
     * 
     * 创建一个新的实例 StringEscapeEditor.
     */
    public StringEscapeEditor() {
        super();
    }

    /**
     * 
     * 创建一个新的实例 StringEscapeEditor.
     * @param escape
     */
    public StringEscapeEditor(boolean escape) {
        super();
        this.escape = escape;
    }

    @Override
    public void setAsText(String text) {
        if (StringUtils.isEmpty(text)) {
            setValue(text);
        } else {
            String value = text;
            if (escape) {
                value = StringEscapeUtils.escapeHtml4(value);
            }
            setValue(value);
        }
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        return value != null ? value.toString() : "";
    }
}
