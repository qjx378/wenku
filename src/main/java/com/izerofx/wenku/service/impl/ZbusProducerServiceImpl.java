package com.izerofx.wenku.service.impl;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.zbus.broker.Broker;
import org.zbus.mq.Producer;
import org.zbus.net.Sync.ResultCallback;
import org.zbus.net.http.Message;

import com.izerofx.wenku.service.ProducerService;

/**
 * 
 * 类名称：ZbusProducerServiceImpl<br>
 * 类描述：zbus生产者服务接口实现<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月9日 上午10:36:23<br>
 * @version v1.0
 *
 */
@Service
public class ZbusProducerServiceImpl implements ProducerService {

    @Resource
    private Broker broker;

    @Resource
    private Producer producer;

    @Override
    public void sendAsync(Message msg, ResultCallback<Message> callback) throws IOException {
        producer.sendAsync(msg, callback);
    }

    @Override
    public void sendAsync(Message msg) throws IOException {
        producer.sendAsync(msg);
    }

    @Override
    public Message sendSync(Message msg, int timeout) throws IOException, InterruptedException {
        return producer.sendSync(msg, timeout);
    }

    @Override
    public Message sendSync(Message msg) throws IOException, InterruptedException {
        return producer.sendSync(msg);
    }

}
