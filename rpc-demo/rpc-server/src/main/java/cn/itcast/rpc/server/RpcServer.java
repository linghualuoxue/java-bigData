package cn.itcast.rpc.server;

import cn.itcast.rpc.common.RpcDecoder;
import cn.itcast.rpc.common.RpcEncoder;
import cn.itcast.rpc.common.RpcRequest;
import cn.itcast.rpc.common.RpcResponse;
import cn.itcast.rpc.registry.ServerRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
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
           EventLoopGroup bossGroup = new NioEventLoopGroup();
           EventLoopGroup workerGroup = new NioEventLoopGroup();
           ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new RpcDecoder(RpcRequest.class))
                                    .addLast(new RpcEncoder(RpcResponse.class))
                                    .addLast(new RpcHandler(handlerMap));
                        }
                    }).option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);
            String[] split = serverAddress.split(":");
            String addr = split[0];
            int port = Integer.parseInt(split[1]);
            ChannelFuture channelFuture = bootstrap.bind(addr, port).sync();
            if(serverRegistry!=null){
                serverRegistry.register(serverAddress);
            }
            log.debug("服务启动,端口为:"+port);
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("服务启动异常,异常信息为:"+e.getMessage());
        } finally {
            if(bossGroup!=null){
                bossGroup.shutdownGracefully().sync();
            }
            if(workerGroup!=null){
                workerGroup.shutdownGracefully().sync();
            }
        }
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
