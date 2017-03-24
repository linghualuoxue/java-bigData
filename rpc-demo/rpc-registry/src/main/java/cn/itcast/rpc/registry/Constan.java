package cn.itcast.rpc.registry;

/**
 * Created by user on 2017/3/24.
 */
public class Constan {
    public static final int ZK_SESSION_TIMEOUT = 5000;//zk超时连接时间
    public static final String ZK_REGISTRY_PATH = "/registry";
    public static final String ZK_DATA_PATH = ZK_REGISTRY_PATH+"/data";//节点
}
