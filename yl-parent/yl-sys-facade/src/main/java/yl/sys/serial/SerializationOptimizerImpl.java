package yl.sys.serial;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import yl.sys.entity.SysUser;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SerializationOptimizerImpl implements SerializationOptimizer{

    @Override
    public Collection<Class> getSerializableClasses() {
        List<Class> classes = new LinkedList<Class>();
        classes.add(SysUser.class);
        return classes;
    }
}
