package com.izerofx.wenku.service;

import java.io.IOException;

import org.zbus.net.Sync.ResultCallback;
import org.zbus.net.http.Message;

/**
 * 
 * 类名称：ZbusProducerService<br>
 * 类描述：zbus生产者服务接口<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月9日 上午10:09:33<br>
 * @version v1.0
 *
 */
public interface ProducerService {

    /**
     * 
     * @param msg
     * @param callback
     * @throws IOException
     */
    public void sendAsync(Message msg, final ResultCallback<Message> callback) throws IOException;

    /**
     * 
     * @param msg
     * @throws IOException
     */
    public void sendAsync(Message msg) throws IOException;

    /**
     * 
     * @param msg
     * @param timeout
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public Message sendSync(Message msg, int timeout) throws IOException, InterruptedException;

    /**
     * 
     * @param msg
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public Message sendSync(Message msg) throws IOException, InterruptedException;
}
