package com.izerofx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zbus.mq.server.MqServer;
import org.zbus.mq.server.MqServerConfig;

/**
 * 
 * 类名称：Application<br>
 * 类描述：应用主类<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月2日 下午5:16:32<br>
 * @version v1.0
 *
 */
@SpringBootApplication
public class Application {

    /**
     * 启动zbus服务
     */
    private static void startZbusServer() {
        MqServerConfig config = new MqServerConfig();
        config.serverPort = 15555;

        config.storePath = "./store";
        config.serverName = "wenku-demo";

        try {
            new MqServer(config).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 主方法
     * @param args
     */
    public static void main(String[] args) {
        // 启动zbus
        startZbusServer();

        SpringApplication.run(Application.class, args);

    }
}
