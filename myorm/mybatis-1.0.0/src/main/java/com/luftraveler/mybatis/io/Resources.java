package com.luftraveler.mybatis.io;

import java.io.InputStream;

public class Resources {
    public static InputStream getResourceAsStream(String resource) {
        //InputStream is = Resources.class.getClassLoader().getResourceAsStream(resource);
        return new Resources().getClassLoaders().getResourceAsStream(resource);
    }


    public ClassLoader getClassLoaders (){
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader;
    }
}
