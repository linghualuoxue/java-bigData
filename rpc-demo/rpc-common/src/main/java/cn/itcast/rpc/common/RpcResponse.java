package cn.itcast.rpc.common;

/**
 * Created by user on 2017/3/23.
 */
public class RpcResponse {
    private String requestId;
    private Throwable error;
    private Object obj;

    public boolean isError(){
        return error!=null;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
