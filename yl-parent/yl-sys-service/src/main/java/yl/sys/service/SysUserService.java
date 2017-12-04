package yl.sys.service;

import org.springframework.stereotype.Service;
import yl.sys.entity.SysUser;
import yl.sys.facade.SysUserFacade;

@Service("sysUserService")
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = yl.sys.facade.SysUserFacade.class,protocol ={"rest","dubbo"})
public class SysUserService implements SysUserFacade {


    @Override
    public void testGet() {
        System.out.println("测试get............");
    }

    @Override
    public SysUser getUser() {
        SysUser user = new SysUser();
        user.setId("1");
        user.setName("张三");
        return user;
    }

    @Override
    public SysUser getUser(Integer id) {
        System.out.println("获取到的id是:"+id);
        SysUser user = new SysUser();
        user.setId("1");
        user.setName("张四");
        return user;
    }
}
