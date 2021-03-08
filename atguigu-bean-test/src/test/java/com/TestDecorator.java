package com;

import org.junit.jupiter.api.Test;

interface IA{
    void function1();
    void function2();
    void function3();
    void function4();
    void function5();
    void function6();
}

//抽象出IAImpl是为了装饰类不需要重新重写IA里面的接口（IA里面的不需要装饰的接口,比如function1）
class IAImpl implements IA{

    @Override
    public void function1() {
        System.out.println("call function1...");
    }

    @Override
    public void function2() {
        System.out.println("call function2...");
    }

    @Override
    public void function3() {
        System.out.println("call function3...");
    }

    @Override
    public void function4() {
        System.out.println("call function4...");
    }

    @Override
    public void function5() {
        System.out.println("call function5...");
    }

    @Override
    public void function6() {
        System.out.println("call function6...");
    }
}

//装饰function4
class IADecoratorImpl_1 extends IAImpl implements IA{
    private IA iAImpl;

    public IADecoratorImpl_1(IA iAImpl) {
        this.iAImpl = iAImpl;
    }

    @Override
    public void function4() {
        System.out.println("decorate function4 before...");
        iAImpl.function4();
        System.out.println("decorate function4 after...");

    }
}

//装饰function5
class IADecoratorImpl_2 extends IAImpl implements IA{
    private IA iAImpl;

    public IADecoratorImpl_2(IA iAImpl) {
        this.iAImpl = iAImpl;
    }

    public void function5() {
        System.out.println("decorate function5 before...");
        iAImpl.function5();
        System.out.println("decorate function5 after...");
    }
}

public class TestDecorator {
    @Test
    void testDecorator(){
        IA ia_1 = new IADecoratorImpl_1(new IAImpl());
        ia_1.function4(); //调用装饰的方法

        ia_1.function1();//调用未装饰的方法

        //装饰器类和原始类继承同样的父类，这样我们可以对原始类“嵌套”多个装饰器类。
        IA ia_2 = new IADecoratorImpl_2(ia_1);
        ia_2.function5(); //调用装饰的方法

        ia_2.function6();//调用未装饰的方法
    }
}
