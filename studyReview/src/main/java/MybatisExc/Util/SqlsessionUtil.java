package MybatisExc.Util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @Description sqlSession访问测试工具 从 XML 中构建 SqlSessionFactory
 * @Author pengrj
 * @CreateDate 2018-05-15 上午 11:23
 * @Version 1.0
 */
public class SqlsessionUtil {

    private static SqlSessionFactory sqlSessionFactory;

    private  static  Reader reader ;

    private static SqlSession sqlSession;

    static {
        try {
            reader=Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory=new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static SqlSession getSqlSession() throws IOException{
        sqlSession=sqlSessionFactory.openSession();
        return sqlSession;
    }

    //获取mappper接口
    public static  <T> T getMapperByClass(Class<T> clazz)  throws IOException{
        return  (T) getSqlSession().getMapper(clazz);
    }

    //提交操作
    public static void commit(){
        sqlSession.commit();
    }

    public static   void closeSession(){
        sqlSession.close();
    }

}
