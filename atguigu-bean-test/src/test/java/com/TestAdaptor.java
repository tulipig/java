package com;

import org.junit.jupiter.api.Test;

//ITarget 表示要转化成的接口定义。Adaptee 是一组不兼容 ITarget 接口定义的接口，
// Adaptor 将 Adaptee 转化成一组符合 ITarget 接口定义的接口。
// 类适配器: 基于继承
interface ITarget {
    void f1();
    void f2();
    void fc();
}

class Adaptee {
    public void fa() {
        System.out.println("call fa...");
    }

    public void fb() {
        System.out.println("call fb...");
    }

    public void fc() {
        System.out.println("call fc...");
    }
}

class Adaptor extends Adaptee implements ITarget {
    public void f1() {
        super.fa();
    }

    public void f2() {
        //...重新实现f2()...
        System.out.println("重新实现f2...");
    }

//    @Override
//    public void fc() {
//        // 这里fc()不需要实现，直接继承自Adaptee，这是跟对象适配器最大的不同点
//    }
}


// 对象适配器：基于组合
class Adaptee2 {
    public void fa() {
        System.out.println("call fa...");
    }

    public void fb() {
        System.out.println("call fb...");
    }

    public void fc() {
        System.out.println("call fc...");
    }
}

class Adaptor2 implements ITarget {
    private Adaptee2 adaptee;

    public Adaptor2(Adaptee2 adaptee) {
        this.adaptee = adaptee;
    }

    public void f1() {
        adaptee.fa(); //委托给Adaptee2
    }

    public void f2() {
        //...重新实现f2()...
        System.out.println("重新实现f2...");
    }

    public void fc() {
        adaptee.fc();
    }
}

public class TestAdaptor {
    @Test
    void testAdaptor(){
        ITarget iTarget = new Adaptor();
        iTarget.f1();
        iTarget.f2();
        iTarget.fc();

        System.out.println("--------------------------->>>>>>>>>>>>>>>>>>>");

        ITarget iTarget_2 = new Adaptor2(new Adaptee2());
        iTarget_2.f1();
        iTarget_2.f2();
        iTarget_2.fc();
    }
}
