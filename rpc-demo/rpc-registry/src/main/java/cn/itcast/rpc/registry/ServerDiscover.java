package cn.itcast.rpc.registry;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Administrator on 2017/3/30.
 */
public class ServerDiscover {

    private static final Logger logger = LoggerFactory.getLogger(ServerDiscover.class);
    List<String> serverList = new ArrayList<String>();
    private CountDownLatch latch = new CountDownLatch(1);
    private String zkAddr;

    public ServerDiscover(String zkAddr) {
        this.zkAddr = zkAddr;
        if(null!=zkAddr){
            ZooKeeper zk = getConnection();
            if(zk!=null){
                watchNode(zk);
            }
        }
    }

    private void watchNode(final ZooKeeper zk) {
        try {
            List<String> nodeList =  zk.getChildren(Constan.ZK_REGISTRY_PATH, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getType()== Event.EventType.NodeChildrenChanged){
                        watchNode(zk);
                    }
                }
            });
            ArrayList<String> dataList = new ArrayList<String>();
            for (String node : nodeList) {
                byte[] data = zk.getData(Constan.ZK_REGISTRY_PATH + "/" + node, false, null);
                dataList.add(new String(data));
            }
            logger.debug("node data:"+serverList);
            this.serverList = dataList;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ZooKeeper getConnection() {
        ZooKeeper zk = null;
        try {
           zk =  new ZooKeeper(zkAddr, Constan.ZK_SESSION_TIMEOUT, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    if(watchedEvent.getState()==Event.KeeperState.SyncConnected){
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return zk;
    }

    public String discover() {
        String date = "";
        int size = serverList.size();
        if(size>0){
            if(size==1){
                logger.debug("只有一个可用服务器节点:"+serverList.get(0));
                date =  serverList.get(0);
            }else {
                date = serverList.get(ThreadLocalRandom.current().nextInt(size));
            }
        }
        return date;
    }
}
