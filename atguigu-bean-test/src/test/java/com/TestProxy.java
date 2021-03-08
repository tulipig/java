package com;

import org.junit.jupiter.api.Test;
import org.omg.PortableInterceptor.RequestInfo;
import org.omg.PortableInterceptor.RequestInfoOperations;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface IUserController{
    void register(Integer uid);
}

class UserController implements IUserController{

    @Override
    public void register(Integer uid) {
        System.out.println("register uid:" + String.valueOf(uid));
    }
}

class UserControllerProxy implements IUserController{
    private UserController userController;

    //注入
    public UserControllerProxy(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void register(Integer uid) {
        //增强功能
        long startTimeStamp = System.currentTimeMillis();

        userController.register(uid);  //委托

        long endTimeStamp = System.currentTimeMillis();
        System.out.println("elapsed time:" + String.valueOf(endTimeStamp-startTimeStamp));
    }
}

//动态代理
class UserControllerDynamicProxy{

    public Object createProxy(Object proxiedObject) {
        Class<?>[] interfaces = proxiedObject.getClass().getInterfaces();
        UserControllerDynamicProxy.DynamicProxyHandler handler = new UserControllerDynamicProxy.DynamicProxyHandler(proxiedObject);
        return Proxy.newProxyInstance(proxiedObject.getClass().getClassLoader(), interfaces, handler);
    }

    private class DynamicProxyHandler implements InvocationHandler {
        private Object proxiedObject;

        public DynamicProxyHandler(Object proxiedObject) {
            this.proxiedObject = proxiedObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            long startTimeStamp = System.currentTimeMillis();
            Object result = method.invoke(proxiedObject, args);
            long endTimeStamp = System.currentTimeMillis();
            System.out.println("elapsed time:" + String.valueOf(endTimeStamp-startTimeStamp));
            return result;
        }

    }
}

public class TestProxy {
    @Test
    void testProxy(){
        UserControllerProxy userControllerProxy = new UserControllerProxy(new UserController());
        userControllerProxy.register(19016476);
    }

    @Test
    void testDynamicProxy(){
        //
        UserControllerDynamicProxy proxy = new UserControllerDynamicProxy();
        IUserController userController = (IUserController) proxy.createProxy(new UserController());
        userController.register(19016476);
    }
}
