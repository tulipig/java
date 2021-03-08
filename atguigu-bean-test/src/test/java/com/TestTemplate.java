package com;


import org.junit.jupiter.api.Test;

/*
模板方法模式在一个方法中定义一个算法骨架，并将某些步骤推迟到子类中实现。
模板方法模式可以让子类在不改变算法整体结构的情况下，重新定义算法中的某些步骤。
 */
abstract class AbstractClass {
    public final void templateMethod() {
        //...
        method1();
        //...
        method2();
        //...
    }

    protected abstract void method1();
    protected abstract void method2();
}

class ConcreteClass1 extends AbstractClass {
    @Override
    protected void method1() {
        System.out.println("call ConcreteClass1::method1....");
    }

    @Override
    protected void method2() {
        System.out.println("call ConcreteClass1::method2....");
    }
}

class ConcreteClass2 extends AbstractClass {
    @Override
    protected void method1() {
        System.out.println("call ConcreteClass2::method1....");
    }

    @Override
    protected void method2() {
        System.out.println("call ConcreteClass2::method2....");
    }
}

public class TestTemplate {
    @Test
    void testTemplate(){
        AbstractClass demo = new ConcreteClass1();
        demo.templateMethod();
    }
}

/*
回调:
回调基于组合关系来实现，把一个对象传递给另一个对象，是一种对象之间的关系；
模板模式基于继承关系来实现，子类重写父类的抽象方法，是一种类之间的关系。

组合优于继承。在代码实现上，回调相对于模板模式会更加灵活，主要体现在下面几点。
像 Java 这种只支持单继承的语言，基于模板模式编写的子类，已经继承了一个父类，不再具有继承的能力。
回调可以使用匿名类来创建回调对象，可以不用事先定义类；而模板模式针对不同的实现都要定义不同的子类。
如果某个类中定义了多个模板方法，每个方法都有对应的抽象方法，那即便我们只用到其中的一个模板方法，子类也必须实现所有的抽象方法。
而回调就更加灵活，我们只需要往用到的模板方法中注入回调对象即可。
 */

interface ICallback {
    void methodToCallback();
}

class BClass {
    public void process(ICallback callback) {
        //...
        callback.methodToCallback();
        //...
    }
}

class TestCallback {
    @Test
    void testCallback() {
        BClass b = new BClass();
        b.process(new ICallback() { //回调对象
            @Override
            public void methodToCallback() {
                System.out.println("Call back me.");
            }
        });
    }
}


