package cn.itcast.rpc.server;

import cn.itcast.rpc.registry.ServerRegistry;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/22.
 */
public class RpcServer implements ApplicationContextAware,InitializingBean{

    private final static Logger log = LoggerFactory.getLogger(RpcServer.class);
    private String serverAddress;
    private ServerRegistry serverRegistry;
    //存储业务接口和实现类的实例对象
    private Map<String,Object> handlerMap = new HashMap<String,Object>();

    public RpcServer(String serverAddress) {
        this.serverAddress = serverAddress;
    }
    public RpcServer(String serverAddress, ServerRegistry serverRegistry) {
        this.serverAddress = serverAddress;
        this.serverRegistry = serverRegistry;
    }

    public void afterPropertiesSet() throws Exception {

    }

    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        Map<String, Object> beansWithAnnotation = ctx.getBeansWithAnnotation(RpcService.class);
        if(!MapUtils.isEmpty(beansWithAnnotation)){
            for (Object o : beansWithAnnotation.values()) {
                String interfaceName = o.getClass().getAnnotation(RpcService.class).value().getName();
                handlerMap.put(interfaceName,o);
            }
        }
    }
}
