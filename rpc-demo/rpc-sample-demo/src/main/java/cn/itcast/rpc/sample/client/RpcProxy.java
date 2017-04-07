package cn.itcast.rpc.sample.client;

import cn.itcast.rpc.common.RpcRequest;
import cn.itcast.rpc.common.RpcResponse;
import cn.itcast.rpc.registry.ServerDiscover;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2017/3/29.
 */
public class RpcProxy {
    private String serverAddress;
    private ServerDiscover serverDiscover;

    public RpcProxy(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RpcProxy(ServerDiscover serverDiscover) {
        this.serverDiscover = serverDiscover;
    }

    public  <T>T create(Class<?>  interfaceClass){
         return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                 new Class<?>[]{interfaceClass},new InvocationHandler(){
                     public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                         RpcRequest request = new RpcRequest();
                         request.setClassName(method.getDeclaringClass().getName());
                         request.setMethodName(method.getName());
                         request.setParameterType(method.getParameterTypes());
                         request.setParameters(args);

                         if(serverDiscover!=null){
                             serverAddress = serverDiscover.discover();
                         }
                         String[] split = serverAddress.split(":");
                         String addr = split[0];
                         int port = Integer.valueOf(split[1]);
                         RpcClient rpcClient = new RpcClient(addr,port);
                         RpcResponse response = rpcClient.send(request);
                         if(response.isError()){
                             throw response.getError();
                         }else{
                             return response.getResult();
                         }
                        // return null;
                     }
                 });
    }
}
