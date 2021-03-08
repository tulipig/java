package com;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Data
@ToString
class Message{
    Integer age;
    String name;
}

interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Message message);
}

interface Observer {
    void update(Message message);
}

class ConcreteSubject implements Subject {
    private List<Observer> observers = new ArrayList<Observer>();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Message message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}

class ConcreteObserverOne implements Observer {
    @Override
    public void update(Message message) {
        //TODO: 获取消息通知，执行自己的逻辑...
        System.out.println("ConcreteObserverOne is notified. message: " + message.toString());
    }
}

class ConcreteObserverTwo implements Observer {
    @Override
    public void update(Message message) {
        //TODO: 获取消息通知，执行自己的逻辑...
        System.out.println("ConcreteObserverTwo is notified. message: " + message.toString());
    }
}

//example
interface RegObserver {
    void handleRegSuccess(long userId);
}

class PromotionService{
    void issueNewUserExperienceCash(long userId){
        System.out.println("call issueNewUserExperienceCash..." + String.valueOf(userId));
    }
}

class NotificationService{
    void sendInboxMessage(long userId){
        System.out.println("call sendInboxMessage..." + String.valueOf(userId));
    }
}

class RegPromotionObserver implements RegObserver {
    private PromotionService promotionService; // 依赖注入

    public RegPromotionObserver() {
        this.promotionService = new PromotionService();
    }

    @Override
    public void handleRegSuccess(long userId) {
        promotionService.issueNewUserExperienceCash(userId);
    }
}

class RegNotificationObserver implements RegObserver {
    private NotificationService notificationService;

    public RegNotificationObserver() {
        this.notificationService = new NotificationService();
    }

    @Override
    public void handleRegSuccess(long userId) {
        notificationService.sendInboxMessage(userId);
    }
}

class UserService {
    public long register(String telephone, String password){
        System.out.println("call register... telephone:" + telephone + ", password" + password);
        Random random = new Random();
        return 10000000 + random.nextInt(10000000);
    }

}

class UserController2 {
    private UserService userService; // 依赖注入
    private List<RegObserver> regObservers = new ArrayList<>();

    public UserController2() {
        this.userService = new UserService();
    }

    // 一次性设置好，之后也不可能动态的修改
    public void setRegObservers(List<RegObserver> observers) {
        regObservers.addAll(observers);
    }

    public Long register(String telephone, String password) {
        //省略输入参数的校验代码
        //省略userService.register()异常的try-catch代码
        long userId = userService.register(telephone, password);

        for (RegObserver observer : regObservers) {
            observer.handleRegSuccess(userId);
        }

        return userId;
    }
}


public class TestObserver {
    @Test
    void testObserver(){
        ConcreteSubject subject = new ConcreteSubject();
        subject.registerObserver(new ConcreteObserverOne());
        subject.registerObserver(new ConcreteObserverTwo());
        subject.notifyObservers(new Message(17,"adelaide52"));
    }

    @Test
    void testObserver_2(){
        UserController2 userController2 = new UserController2();
        List<RegObserver> observers = Arrays.asList(new RegPromotionObserver(), new RegNotificationObserver());
        userController2.setRegObservers(observers);
        userController2.register("18601019090", "qdfneae");
        userController2.register("18634449090", "yyyyeae");
    }
}
