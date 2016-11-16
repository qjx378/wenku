package com.izerofx.wenku.domain;

/**
 * 
 * 类名称：ConvertResult<br>
 * 类描述：转换结果<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年3月31日 上午9:10:10<br>
 * @version v1.0
 *
 */
public class ConvertResult {

    private boolean succes;
    private String errorMsg;
    private Exception exception;

    /**
     * 
     * 创建一个新的实例 ConvertResult.
     */
    public ConvertResult() {
        super();
    }

    /**
     * 
     * 创建一个新的实例 ConvertResult.
     * @param succes
     * @param errorMsg
     * @param exception
     */
    public ConvertResult(boolean succes, String errorMsg, Exception exception) {
        super();
        this.succes = succes;
        this.errorMsg = errorMsg;
        this.exception = exception;
    }

    public boolean isSucces() {
        return succes;
    }
    public void setSucces(boolean succes) {
        this.succes = succes;
    }
    public String getErrorMsg() {
        return errorMsg;
    }
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public Exception getException() {
        return exception;
    }
    public void setException(Exception exception) {
        this.exception = exception;
    }
}
