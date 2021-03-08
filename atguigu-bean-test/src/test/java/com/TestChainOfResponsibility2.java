package com;


import org.junit.jupiter.api.Test;

//使用链表
abstract class Handler {
    protected Handler successor = null;

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }

    public final void handle() {
        boolean handled = doHandle();
        if (successor != null && !handled) {
            successor.handle();
        }
    }

    protected abstract boolean doHandle();
}

class HandlerC extends Handler {
    @Override
    protected boolean doHandle() {
        boolean handled = false;
        //...
        System.out.println("call HandlerC::doHandle....");
        return handled;
    }
}

class HandlerD extends Handler {
    @Override
    protected boolean doHandle() {
        boolean handled = false;
        //...
        System.out.println("call HandlerD::doHandle....");
        return handled;
    }
}


class HandlerChain_2 {
    private Handler head = null;
    private Handler tail = null;

    public void addHandler(Handler handler) {
        handler.setSuccessor(null);

        if (head == null) {
            head = handler;
            tail = handler;
            return;
        }

        tail.setSuccessor(handler);
        tail = handler;
    }

    public void handle() {
        if (head != null) {
            head.handle();
        }
    }
}

public class TestChainOfResponsibility2 {
    @Test
    void testChainOfResponsibility() {
        HandlerChain_2 chain = new HandlerChain_2();
        chain.addHandler(new HandlerC());
        chain.addHandler(new HandlerD());
        chain.handle();
    }
}
