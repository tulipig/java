package com;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/*
将请求的发送和接收解耦，让多个接收对象都有机会处理这个请求。
将这些接收对象串成一条链，并沿着这条链传递这个请求，直到链上的某个接收对象能够处理它为止。
 */
interface IHandler {
    boolean handle();
}

class HandlerA implements IHandler {
    @Override
    public boolean handle() {
        boolean handled = false;
        //...
        System.out.println("call HandlerA::doHandle....");
        return handled;
    }
}

class HandlerB implements IHandler {
    @Override
    public boolean handle() {
        boolean handled = false;
        //...
        System.out.println("call HandlerB::doHandle....");
        return handled;
    }
}

//数组实现
class HandlerChain {
    private List<IHandler> handlers = new ArrayList<>();

    public void addHandler(IHandler handler) {
        this.handlers.add(handler);
    }

    public void handle() {
        for (IHandler handler : handlers) {
            boolean handled = handler.handle();
            if (handled) {
                break;
            }
        }
    }
}

public class TestChainOfResponsibility {
    @Test
    void testChainOfResponsibility() {
        HandlerChain chain = new HandlerChain();
        chain.addHandler(new HandlerA());
        chain.addHandler(new HandlerB());
        chain.handle();
    }
}
