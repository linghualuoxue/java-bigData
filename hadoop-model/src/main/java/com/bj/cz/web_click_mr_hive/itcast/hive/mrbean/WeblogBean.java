package com.bj.cz.web_click_mr_hive.itcast.hive.mrbean;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**贴源表
 * Created by Administrator on 2017/4/19.
 */
public class WeblogBean implements Writable{
    private boolean valid = true;//判断数据是否合法
    private String remote_addr;//记录客户的ip地址
    private String remote_user;
    private String time_local;//记录访问时间和时区
    private String request;//记录请求url合同http协议
    private String status;//返回的状态
    private String boty_bytes_sent;//发送给客户端的文件内容大小
    private String http_referer;//从哪个页面链接过来的
    private String http_user_agent;//记录客户端的浏览器信息

    public void write(DataOutput out) throws IOException {
          out.writeBoolean(this.valid);
        out.writeUTF(this.remote_addr    ==null?"":remote_addr     );
        out.writeUTF(this.remote_user    ==null?"":remote_user     );
        out.writeUTF(this.time_local     ==null?"":time_local      );
        out.writeUTF(this.request        ==null?"":request         );
        out.writeUTF(this.status         ==null?"":status          );
        out.writeUTF(this.boty_bytes_sent==null?"":boty_bytes_sent);
        out.writeUTF(this.http_referer   ==null?"":http_referer   );
        out.writeUTF(this.http_user_agent==null?"":http_user_agent);
    }

    public void readFields(DataInput input) throws IOException {
                this.valid = input.readBoolean();
                this.remote_addr    =input.readUTF();
                this.remote_user    =input.readUTF();
                this.time_local     =input.readUTF();
                this.request        =input.readUTF();
                this.status         =input.readUTF();
                this.boty_bytes_sent=input.readUTF();
                this.http_referer   =input.readUTF();
                this.http_user_agent=input.readUTF();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.valid);
        sb.append("\001").append(this.remote_addr    );
        sb.append("\001").append(this.remote_user    );
        sb.append("\001").append(this.time_local     );
        sb.append("\001").append(this.request        );
        sb.append("\001").append(this.status         );
        sb.append("\001").append(this.boty_bytes_sent);
        sb.append("\001").append(this.http_referer   );
        sb.append("\001").append(this.http_user_agent);
        return sb.toString();
    }

    public void set(boolean valid, String remote_addr, String remote_user, String time_local, String request, String status, String boty_bytes_sent, String http_referer, String http_user_agent) {
        this.valid = valid;
        this.remote_addr = remote_addr;
        this.remote_user = remote_user;
        this.time_local = time_local;
        this.request = request;
        this.status = status;
        this.boty_bytes_sent = boty_bytes_sent;
        this.http_referer = http_referer;
        this.http_user_agent = http_user_agent;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getRemote_addr() {
        return remote_addr;
    }

    public void setRemote_addr(String remote_addr) {
        this.remote_addr = remote_addr;
    }

    public String getRemote_user() {
        return remote_user;
    }

    public void setRemote_user(String remote_user) {
        this.remote_user = remote_user;
    }

    public String getTime_local() {
        return time_local;
    }

    public void setTime_local(String time_local) {
        this.time_local = time_local;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBoty_bytes_sent() {
        return boty_bytes_sent;
    }

    public void setBoty_bytes_sent(String boty_bytes_sent) {
        this.boty_bytes_sent = boty_bytes_sent;
    }

    public String getHttp_referer() {
        return http_referer;
    }

    public void setHttp_referer(String http_referer) {
        this.http_referer = http_referer;
    }

    public String getHttp_user_agent() {
        return http_user_agent;
    }

    public void setHttp_user_agent(String http_user_agent) {
        this.http_user_agent = http_user_agent;
    }


}
