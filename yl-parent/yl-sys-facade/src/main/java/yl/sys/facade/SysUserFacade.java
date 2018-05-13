package yl.sys.facade;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import yl.sys.entity.SysUser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("sysUserService")
/*指定处理请求的提交内容类型*/
@Consumes({MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
/*返回以json或者xml格式*/
@Produces({ContentType.APPLICATION_JSON_UTF_8,ContentType.TEXT_XML_UTF_8})
public interface SysUserFacade {

    @GET
    @Path("testGet")
    public void testGet();

    @GET
    @Path("getUser")
    public SysUser getUser();

    @GET
    @Path("/get/{id:\\d+}")
    public SysUser getUser(@PathParam(value="id")Integer id);


}
