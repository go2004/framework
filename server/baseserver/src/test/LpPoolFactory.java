package test;

/**
 * Created by Administrator on 2016/9/27.
 */
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

class User {
    //姓名
    private String name;
    //年龄
    private int age;
    //身高
    private String height;

    public User(String name, int age, String height) {
        super();
        this.name = name;
        this.age = age;
        this.height = height;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(super.toString());
        str.append(" ");
        str.append("name:" + name);
        str.append(",age:" + age);
        str.append(",height:" + height);
        return str.toString();
    }

}
public class LpPoolFactory
        extends BasePooledObjectFactory<User> {
    @Override
    public User create() {
        return new User("AB",1,"A");
    }

    /**
     * Use the default PooledObject implementation.
     */
    @Override
    public PooledObject<User> wrap(User buffer) {
        return new DefaultPooledObject<User>(buffer);
    }
    /**
     * When an object is returned to the pool, clear the buffer.
     */
    @Override
    public void passivateObject(PooledObject<User> pooledObject) {
        System.out.println("passivate Object");
    }

    @Override
    public void activateObject(PooledObject<User> pooledObject) {
        System.out.println("activate Object");
    }

    public static void main(String[] args) throws Exception{
        //工厂
        LpPoolFactory factory = new LpPoolFactory();
        //资源池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMinIdle(2);
        poolConfig.setMaxIdle(8);
        poolConfig.setMaxWaitMillis(10000);
        //创建资源池
        GenericObjectPool<User> objectPool = new GenericObjectPool<User>(factory,poolConfig);

        for(int i=0; i<30; i++) {
            Thread.sleep(10);
            //获取资源对象
            User user = objectPool.borrowObject();
            //将获取的资源对象，返还给资源池
            //objectPool.returnObject(user);
            //输出资源对象
            System.out.print(i+" -> ");
            System.out.println(user);

        }

    }
}
