package com.bj.xnbb.es_search.util;

import com.bj.xnbb.es_search.constant.Constant;
import org.apache.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hasee on 2017/7/25.
 */
public class ClientHelper {

    Logger log = Logger.getLogger("ClientHelper.class");

    private LinkedBlockingQueue<Client> queue;
    private AtomicInteger atomicInteger;

    private ClientHelper(){
        init();
    }

    private void init() {
        int pool_size = Integer.parseInt(BasicConfiguration.getInstance().getValue(Constant.ES_POOL_SIZE));
        queue =  new LinkedBlockingQueue<Client>(pool_size);
        atomicInteger = new AtomicInteger(0);
        initPool(pool_size);
    }

    private void initPool(int i) {
        for (int j = 0; j < i; j++) {
            initClient();
        }
        log.info("连接池初始化完毕！");
    }

    /**
     * 获取client
     * @return
     */
    public  Client getClient()throws InterruptedException{
        Client client = queue.take();
        atomicInteger.getAndDecrement();
        log.info(Thread.currentThread().getName()+"获取了一个连接，连接池大小："+this.pooSize());
        return client;
    }

    /**
     * 归还client
     * @param client
     */
    public void closeClient(Client client)throws InterruptedException{
        queue.put(client);
        atomicInteger.getAndIncrement();
        log.info(Thread.currentThread().getName()+"释放了一个连接，连接池大小："+this.pooSize());
    }

    public static ClientHelper getInstance(){
      return  ClientHolder.instance;
    }

    /**
     * 通过配置文件获取客户端
     */
    private void initClient(){

        try {
            String cluter = BasicConfiguration.getInstance().getValue(Constant.ES_CLUSTER_NAME);
            String host = BasicConfiguration
                    .getInstance().getValue(Constant.ES_HOST);
            String port = BasicConfiguration.getInstance().getValue(Constant.ES_PORT);
            Settings settings = Settings.settingsBuilder()
                    .put("cluster.name", cluter)
                    .put("client.transport.ping_timeout",Long.MAX_VALUE).build();   //设置超时时间
            Client client  =  TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host),
                            Integer.parseInt(port)))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.0.123"),
                            Integer.parseInt(port)));
            queue.put(client);
            atomicInteger.getAndIncrement();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取池内容量
     * @return
     */
    public int pooSize() {
        return atomicInteger.get();
    }


    private static class ClientHolder{
        private static final ClientHelper instance =  new ClientHelper();
    }

}
