package cn.itcast.rpc.server;

import cn.itcast.rpc.common.RpcRequest;
import cn.itcast.rpc.common.RpcResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by user on 2017/3/23.
 */
public class RpcHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger logger = LoggerFactory.getLogger(RpcHandler.class);

    Map<String, Object> handlerMap = null;

    public RpcHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest rpcRequest) throws Exception {
        RpcResponse response = new RpcResponse();
        try {
            response.setRequestId(rpcRequest.getRequestId());
            response.setObj(handler(rpcRequest));
        } catch (Exception e) {
            response.setError(e);
        }
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private Object handler(RpcRequest rpcRequest)throws Exception {
        Object o = handlerMap.get(rpcRequest.getClassName());
        String methodName = rpcRequest.getMethodName();
        Class<?>[] parameterType = rpcRequest.getParameterType();
        Object[] parameters = rpcRequest.getParameters();
        Class<?> classForName = o.getClass();
        Method method = null;
        method = classForName.getMethod(methodName, parameterType);
        return    method.invoke(o,parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("error handler:"+cause);
        ctx.close();
    }
}
