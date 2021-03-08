package com;
/*
ref https://time.geekbang.org/column/article/211239
 */

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Beta
@interface Subscribe {}

//ObserverAction 类用来表示 @Subscribe 注解的方法，其中，target 表示观察者类，method 表示方法。
// 它主要用在 ObserverRegistry 观察者注册表中。
class ObserverAction {
    private Object target;
    private Method method;

    public ObserverAction(Object target, Method method) {
        this.target = Preconditions.checkNotNull(target);
        this.method = method;
        this.method.setAccessible(true);
    }

    public void execute(Object event) { // event是method方法的参数
        try {
            method.invoke(target, event);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}


class ObserverRegistry {
    private ConcurrentMap<Class<?>, CopyOnWriteArraySet<ObserverAction>> registry = new ConcurrentHashMap<>();

    public void register(Object observer) {
        //key是消息类型，比如Message; Collection<ObserverAction>是观察者列表
        Map<Class<?>, Collection<ObserverAction>> observerActions = findAllObserverActions(observer);
        for (Map.Entry<Class<?>, Collection<ObserverAction>> entry : observerActions.entrySet()) {
            Class<?> eventType = entry.getKey();
            Collection<ObserverAction> eventActions = entry.getValue();
            CopyOnWriteArraySet<ObserverAction> registeredEventActions = registry.get(eventType);
            if (registeredEventActions == null) {
                registry.putIfAbsent(eventType, new CopyOnWriteArraySet<>());
                registeredEventActions = registry.get(eventType);
            }
            registeredEventActions.addAll(eventActions);
        }
    }

    //根据消息类型，获取观察者列表
    public List<ObserverAction> getMatchedObserverActions(Object event) {
        List<ObserverAction> matchedObservers = new ArrayList<>();
        Class<?> postedEventType = event.getClass();
        for (Map.Entry<Class<?>, CopyOnWriteArraySet<ObserverAction>> entry : registry.entrySet()) {
            Class<?> eventType = entry.getKey();
            Collection<ObserverAction> eventActions = entry.getValue();
            if (postedEventType.isAssignableFrom(eventType)) {
                matchedObservers.addAll(eventActions);
            }
        }
        return matchedObservers;
    }

    private Map<Class<?>, Collection<ObserverAction>> findAllObserverActions(Object observer) {
        Map<Class<?>, Collection<ObserverAction>> observerActions = new HashMap<>();
        Class<?> clazz = observer.getClass();
        for (Method method : getAnnotatedMethods(clazz)) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            Class<?> eventType = parameterTypes[0];
            if (!observerActions.containsKey(eventType)) {
                observerActions.put(eventType, new ArrayList<>());
            }
            observerActions.get(eventType).add(new ObserverAction(observer, method));
        }
        return observerActions;
    }

    private List<Method> getAnnotatedMethods(Class<?> clazz) {
        List<Method> annotatedMethods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Preconditions.checkArgument(parameterTypes.length == 1,
                        "Method %s has @Subscribe annotation but has %s parameters."
                                + "Subscriber methods must have exactly 1 parameter.",
                        method, parameterTypes.length);
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods;
    }
}


class EventBus {
    private Executor executor;
    private ObserverRegistry registry = new ObserverRegistry();

//    public EventBus() {
//        this(MoreExecutors.directExecutor());
//    }

    protected EventBus(Executor executor) {
        this.executor = executor;
    }

    public void register(Object object) {
        registry.register(object);
    }

    public void post(Object event) {
        List<ObserverAction> observerActions = registry.getMatchedObserverActions(event);
        for (ObserverAction observerAction : observerActions) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    observerAction.execute(event);
                }
            });
        }
    }
}

class AsyncEventBus extends EventBus {
    public AsyncEventBus(Executor executor) {
        super(executor);
    }
}

@AllArgsConstructor
@Data
@ToString
class Message_1{
    Integer age;
    String name;
}

@AllArgsConstructor
@Data
@ToString
class Message_2{
    Integer price;
    String brand;
}

class AObserver{
    @Subscribe
    void function_1(Message_1 message){
        System.out.println("call AObserver::function_1..." + message.toString());
    }

    @Subscribe
    void function_2(Message_1 message){
        System.out.println("call AObserver::function_2..." + message.toString());
    }

    @Subscribe
    void function_3(Message_2 message){
        System.out.println("call AObserver::function_3..." + message.toString());
    }
}

class BObserver{
    @Subscribe
    void function_1(Message_2 message){
        System.out.println("call BObserver::function_1..." + message.toString());
    }

    @Subscribe
    void function_2(Message_1 message){
        System.out.println("call BObserver::function_2..." + message.toString());
    }
}

public class TestEventBus {
    @Test
    void testEventBus(){
        EventBus eventBus = new AsyncEventBus(
                new ThreadPoolExecutor(2,
                        5,
                        1,
                        TimeUnit.SECONDS,
                        new LinkedBlockingDeque<Runnable>(3),
                        Executors.defaultThreadFactory(),
                        new ThreadPoolExecutor.CallerRunsPolicy())
        );

        //注册观察者
        eventBus.register(new AObserver());
        eventBus.register(new BObserver());

        //发送消息
        eventBus.post(new Message_1(17,"adelaide52"));
        eventBus.post(new Message_2(180000,"BYD"));
    }
}
