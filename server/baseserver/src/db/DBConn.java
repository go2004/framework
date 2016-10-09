package db;

/**
 * Created by Administrator on 2016/10/9.
 */
import db.model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
public class DBConn {
    //static Logger log = Logger.getLogger(DBConn.class);
    private static DBConn ourInstance = new DBConn();

    public static DBConn getInstance() {
        return ourInstance;
    }

    private SqlSession session;
    private SqlSessionFactory sessionFactory;
    private DBConn() {
        //mybatis的配置文件
        String resource = "dbconf.xml";
        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream is = DBConn.class.getClassLoader().getResourceAsStream(resource);
        //构建sqlSession的工厂
         sessionFactory = new SqlSessionFactoryBuilder().build(is);
        //使用MyBatis提供的Resources类加载mybatis的配置文件（它也加载关联的映射文件）
        //Reader reader = Resources.getResourceAsReader(resource);
        //构建sqlSession的工厂
        //SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        //创建能执行映射文件中sql的sqlSession
        session = sessionFactory.openSession();
    }
    public void close(){

        session.close();
    }

    //测试使用
    public  User selectUsers()
    {
        String statement = "db.dao.UserMapper.selectUsers";//映射sql的标识字符串
        return  session.selectOne(statement, 5);
    }
}


