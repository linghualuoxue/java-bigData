package cn.itcast.rpc.registry;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2017/3/22.
 */
public class ServerRegistry {

    private static Logger logger = LoggerFactory.getLogger(ServerRegistry.class);
    private CountDownLatch latch = new CountDownLatch(1);
    private String zkAddress;

    public ServerRegistry(String zkAddress) {
        this.zkAddress = zkAddress;
    }


    public void register(String data) {
      if(data!=null && !"".equals(data)){
          ZooKeeper zk = getZookeeperConnect();
          if(zk!=null){
              createNode(zk,data);
          }
      }
    }

    private ZooKeeper getZookeeperConnect() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(zkAddress, Constan.ZK_SESSION_TIMEOUT, new Watcher() {
                public void process(WatchedEvent event) {
                    if(event.getState() == Event.KeeperState.SyncConnected){
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (Exception e) {
            logger.error("zk 连接失败");
        }
        return zk;
    }

    private void createNode(ZooKeeper zk, String data) {
    }

}
