package cn.itcast.rpc.common;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.sun.activation.registries.MailcapParseException;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by user on 2017/3/24.
 */
public class SerializetionUtils {

    static Objenesis objenesis =  new ObjenesisStd(true);
    private final static Map<Class<?>,Schema<?>> schemaMap = new ConcurrentHashMap<Class<?>, Schema<?>>();

    public static <T> Schema<T> getSchema(Class<T> genercClass){
        Schema<T> schema = (Schema<T>)schemaMap.get(genercClass);
        if(schema==null){
            schema = RuntimeSchema.getSchema(genercClass);
            schemaMap.put(genercClass,schema);
        }
        return schema;
    }

    public static <T> T  covertToObject(byte[] bytes,Class<T> genercClass){
        T t = (T)objenesis.newInstance(genercClass);
        Schema<T> schema = getSchema(genercClass);
        ProtostuffIOUtil.mergeFrom(bytes,t,schema);
        return t;
    }

    public static <T> byte[] serialize(T inob) {
        Class<T> inobClass = (Class<T>)inob.getClass();
        LinkedBuffer buff = null;
        try {
            buff = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
            Schema<T> schema = getSchema(inobClass);
            return ProtostuffIOUtil.toByteArray(inob,schema,buff);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            buff.clear();
        }
        return null;
    }
}
