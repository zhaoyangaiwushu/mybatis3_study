package com.luftraveler.mybatis.proxy;

import com.luftraveler.mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

public class MapperProxy implements InvocationHandler {

    private SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理拦截啦？？？？");
        //方法的返回值
        Class returnType = method.getReturnType();
        //返回类型是不是集合类的子类 意思返回的是不是集合
        if(Collection.class.isAssignableFrom(returnType)){
            //查询多条数据 那么返回的是一个list
            return null;
        }else if(Map.class.isAssignableFrom(returnType)){
            //返回类型是不是map类的子类 意思返回的是不是map
            return null;
        }else {
            //返回对象
            Object paramter = args == null ? null : args[0];

            //根据命名空间和方法名
            String sqlId = method.getDeclaringClass().getName() +"."+ method.getName();
            return sqlSession.selectOne(sqlId,paramter);
        }
    }
}
