package com.bj.xnbb.register2zk;

import com.bj.xnbb.Constant.Constant;
import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.CountDownLatch;

/**向zk注册web服务
 * Created by hasee on 2017/7/20.
 */
public class RegisterServer implements InitializingBean {

    final   Logger log =  Logger.getLogger(RegisterServer.class.getName());
    CountDownLatch coutDown = new CountDownLatch(1);
    final private String zkAddr="192.168.121.111";
    String host;
    /*public RegisterServer(String zkAddr) {
        this.zkAddr = zkAddr;
    }*/

    public void afterPropertiesSet() throws Exception {

        String host = "123";
         if(null!=host && !"".equals(host)){
             ZooKeeper zk = getZookeeperConnect();
             if(zk!=null){
                 createNode(zk,host);
             }
         }
    }

    private void createNode(ZooKeeper zk, String host) {

        try {
            if(null==zk.exists(Constant.ZK_REGIST_PATH,null)){
                zk.create(Constant.ZK_REGIST_PATH,null, ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            }
            zk.create(Constant.ZK_REGIST_DATA,host.getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
           log.debug("成功创建节点："+zkAddr+";host:"+host);
        } catch (Exception e) {
           log.error("节点注册失败："+host);
           e.printStackTrace();
        }

    }


    //连接zk
    private ZooKeeper getZookeeperConnect() {
        ZooKeeper zk = null;
        try {
           zk = new ZooKeeper(zkAddr, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
                public void process(WatchedEvent event) {
                    if(event.getState() == Event.KeeperState.SyncConnected){
                        coutDown.countDown();
                    }
                }
            });
            coutDown.await();
        } catch (Exception e) {
            log.error("zk连接失败....");
           e.printStackTrace();
        }
        return zk;
    }


}
