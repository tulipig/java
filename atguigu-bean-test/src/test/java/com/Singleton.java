package com;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@Slf4j
public class Singleton {
    @Test
    void testLogger() throws IOException {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            IntStream intStream = random.ints(0, 100);
            Logger.getInstance().log(LocalTime.now().toString() + " "
                    + String.valueOf(i) + "-" + intStream.toString() + " "
                    + UUID.randomUUID()
            );
        }
    }

    @Test
    void testIdGenerator(){
        // IdGenerator使用举例
        //for (int i = 0; i < 5; i++) {
//            log.info(String.valueOf(IdGenerator.getInstance().getId()));
//            log.info(String.valueOf(IdGenerator2.getInstance().getId()));
            //log.info(String.valueOf(IdGenerator3.getInstance().getId()));
            //log.info(String.valueOf(IdGenerator4.INSTANCE.getId()));
            //log.info(String.valueOf(IdGenerator5.getInstance().getId()));
       // }

        for (int i = 0; i < 5; i++) {
            if(BackendServer.getRandomInstance() == null){
                log.info("get null instance");
            }
            else{
                log.info(BackendServer.getRandomInstance().getServerAddress());
            }
        }
    }
}

class Logger {
    private FileWriter writer;

    private static final Logger instance = new Logger();

    private Logger() {
        File file = new File("/Users/huangyingping/log.txt");
        try {
            writer = new FileWriter(file, true); //true表示追加写入
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getInstance() {
        return instance;
    }

    public void log(String message) throws IOException {
        writer.write(message);
    }
}

//懒汉式
class IdGenerator {
    // AtomicLong是一个Java并发库中提供的一个原子变量类型,
    // 它将一些线程不安全需要加锁的复合操作封装为了线程安全的原子操作，
    // 比如下面会用到的incrementAndGet().
    private AtomicLong id = new AtomicLong(0);
    private static final IdGenerator instance = new IdGenerator();
    private IdGenerator() {}
    public static IdGenerator getInstance() {
        return instance;
    }
    public long getId() {
        return id.incrementAndGet();
    }
}

//饿汉式
class IdGenerator2 {
    private AtomicLong id = new AtomicLong(0);
    private static IdGenerator2 instance;
    private IdGenerator2() {}
    public static IdGenerator2 getInstance() {
        if (instance == null) {
            synchronized(IdGenerator2.class) { // 此处为类级别的锁
                if (instance == null) {
                    instance = new IdGenerator2();
                }
            }
        }
        return instance;
    }
    public long getId() {
        return id.incrementAndGet();
    }
}

//静态内部类
class IdGenerator3 {
    private AtomicLong id = new AtomicLong(0);
    private IdGenerator3() {}
    //SingletonHolder 是一个静态内部类，当外部类 IdGenerator 被加载的时候，
    // 并不会创建 SingletonHolder 实例对象。只有当调用 getInstance() 方法时，
    // SingletonHolder 才会被加载，这个时候才会创建 instance。
    // instance 的唯一性、创建过程的线程安全性，都由 JVM 来保证。所以，这种实现方法既保证了线程安全，又能做到延迟加载。
    private static class SingletonHolder{
        private static final IdGenerator3 instance = new IdGenerator3();
    }

    public static IdGenerator3 getInstance() {
        return SingletonHolder.instance;
    }

    public long getId() {
        return id.incrementAndGet();
    }
}

//枚举
enum IdGenerator4 {
    INSTANCE;
    private AtomicLong id = new AtomicLong(0);

    public long getId() {
        return id.incrementAndGet();
    }
}


//以上四种方式是 进程唯一
// 线程唯一单例实现方式
class IdGenerator5 {
    private AtomicLong id = new AtomicLong(0);

    private static final ConcurrentHashMap<Long, IdGenerator5> instances
            = new ConcurrentHashMap<>();

    private IdGenerator5() {}

    public static IdGenerator5 getInstance() {
        Long currentThreadId = Thread.currentThread().getId();
        instances.putIfAbsent(currentThreadId, new IdGenerator5());
        return instances.get(currentThreadId);
    }

    public long getId() {
        return id.incrementAndGet();
    }
}

//多例模式，比如同一个类，只能存在三个实例
class BackendServer {
    private long serverNo;
    private String serverAddress;

    private static final int SERVER_COUNT = 3;
    private static final Map<Long, BackendServer> serverInstances = new HashMap<>();

    static {
        serverInstances.put(1L, new BackendServer(1L, "192.134.22.138:8080"));
        serverInstances.put(2L, new BackendServer(2L, "192.134.22.139:8080"));
        serverInstances.put(3L, new BackendServer(3L, "192.134.22.140:8080"));
    }

    private BackendServer(long serverNo, String serverAddress) {
        this.serverNo = serverNo;
        this.serverAddress = serverAddress;
    }

    public static BackendServer getInstance(long serverNo) {
        return serverInstances.get(serverNo);
    }

    String getServerAddress(){
        return serverAddress;
    }

    public static BackendServer getRandomInstance() {
        Random r = new Random();
        int no = r.nextInt(SERVER_COUNT)+1;
        return serverInstances.get(new Long((long)no));
    }
}