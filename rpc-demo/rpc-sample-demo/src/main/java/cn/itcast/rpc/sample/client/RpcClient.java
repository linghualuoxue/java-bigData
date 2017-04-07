package cn.itcast.rpc.sample.client;

import cn.itcast.rpc.common.RpcDecoder;
import cn.itcast.rpc.common.RpcEncoder;
import cn.itcast.rpc.common.RpcRequest;
import cn.itcast.rpc.common.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by user on 2017/3/28.
 */
public class RpcClient extends SimpleChannelInboundHandler<RpcResponse> {
    private final static Logger logger = LoggerFactory.getLogger(RpcClient.class);
    private String serverAddress;
    private int port;
    private RpcResponse response ;
    private final Object obj = new Object();

    public RpcClient(String serverAddress,int port){
        this.serverAddress = serverAddress;
        this.port = port;
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {
        this.response = rpcResponse;
        synchronized (obj){
            obj.notifyAll();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("客户端连接失败!");
        ctx.close();
    }

    public RpcResponse send(RpcRequest request) {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new RpcEncoder(RpcRequest.class)) //out
                                    .addLast(new RpcDecoder(RpcResponse.class))
                                    .addLast(RpcClient.this);
                        }
                    });
            //连接服务器
            ChannelFuture future = bootstrap.connect(serverAddress, port).sync();
            //发送对象
            future.channel().writeAndFlush(request).sync();

            synchronized (obj){
                obj.wait();
            }
        if(response!=null){
            future.channel().closeFuture().sync();
        }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return response;
    }
}
