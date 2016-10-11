package common;

import net.NettyChannel;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.Date;

/**
 * Created by Administrator on 2016/9/28.
 */
public class NettyChannelPool {
    private static NettyChannelPool ourInstance = new NettyChannelPool();
    public GenericObjectPool<NettyChannel> objectPool;

    public static NettyChannelPool getInstance() {
        return ourInstance;
    }

//    private ChannelFactory() {
//    }

    private NettyChannelPool() {
        //工厂
        NettyChannelFactory factory = new NettyChannelFactory();
        //资源池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(20);
        poolConfig.setMinIdle(6);
        poolConfig.setMaxIdle(8);
        poolConfig.setMaxWaitMillis(10000);
        //创建资源池
        objectPool = new GenericObjectPool<>(factory, poolConfig);
    }

    public NettyChannel getObject() {
        try {

            return objectPool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //将获取的资源对象，返还给资源池
    public void freeObject(NettyChannel Object) {
        objectPool.returnObject(Object);
    }

    public int getNumActive() {
        return objectPool.getNumActive();
    }

    public int getNumIdle() {
        return objectPool.getNumIdle();
    }

}

class NettyChannelFactory
        extends BasePooledObjectFactory<NettyChannel> {
    @Override
    public NettyChannel create() {
        return new NettyChannel(null, new Date());
    }

    /**
     * Use the default PooledObject implementation.
     */
    @Override
    public PooledObject<NettyChannel> wrap(NettyChannel buffer) {
        return new DefaultPooledObject<NettyChannel>(buffer);
    }

    /**
     * When an object is returned to the pool, clear the buffer.
     */
    @Override
    public void passivateObject(PooledObject<NettyChannel> pooledObject) {
        System.out.println("passivate Object");
    }

    @Override
    public void activateObject(PooledObject<NettyChannel> pooledObject) {
        System.out.println("activate Object");
    }
}
