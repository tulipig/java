package com;

import ch.qos.logback.core.pattern.util.IEscapeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TestJavaFeature {

    @Test
    void testList(){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        String[] arrays = list.toArray(new String[list.size()]);
        for (int i = 0; i < list.toArray().length; i++) {
            log.info(arrays[i]);
        }
    }

    @Test
    void testListIterator() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            log.info(item);
            if ("2".equals(item)) {
                //java.util.ConcurrentModificationException
                //list.remove(item);

                iterator.remove();
            }
        }

        //java.util.ConcurrentModificationException
//        for(String item : list){
//            if("1".equals(item)){
//                list.remove(item);
//            }
//        }

        log.info(list.toString());

        //遍历list
        list.forEach((item)->{
            log.info(item);
        });
    }

    @Test
    void testMap() {
        //Ref: https://blog.csdn.net/w605283073/article/details/80708943
        Map<String, String> map = new HashMap<>();
        map.put("tulip", "18");
        map.put("adelaide", "17");

        //遍历k-v 方法一
        log.info("遍历k-v 方法一");
        for (String key : map.keySet()) {
            log.info(key + ": " + map.get(key));
        }

        //遍历v
        log.info("遍历v");
        for (String value : map.values()) {
            log.info(value);
        }

        //遍历k-v  方法二
        log.info("遍历k-v 方法二");
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> item = iterator.next();
            log.info(item.getKey() + ": " + item.getValue());
        }

        //遍历k-v  方法三  推荐
        log.info("遍历k-v 方法三");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            log.info(entry.getKey() + ": " + entry.getValue());
        }

        //遍历k-v  方法四  推荐
        log.info("遍历k-v 方法四");
        map.forEach((k, v) -> {
            log.info(k + ": " + v);
        });
    }

    @Test
        //如果是 JDK8 的应用，可以使用 Instant 代替 Date，LocalDateTime 代替 Calendar，
        // DateTimeFormatter 代替 SimpleDateFormat，
        // 官方给出的解释:simple beautiful strong immutable thread-safe。
    void testDate() {
        //当前时间戳
        log.info(String.valueOf(System.currentTimeMillis()));
        log.info(String.valueOf(System.nanoTime()));

        //https://www.cnblogs.com/lgjava/p/11175082.html
        log.info(Instant.now().toString());
        //Instant.now()使用等是UTC时间, +8h 对齐北京时间
        log.info(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)).toString());

        log.info(LocalDateTime.now().toString());

        //格式化时间
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.now() , ZoneId.systemDefault());
        String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss"));
        log.info(format);
    }

    @Test
    void testRandom(){
        //【强制】注意 Math.random() 这个方法返回是 double 类型，注意取值的范围 0≤x<1(能够 取到零值，注意除零异常)，
        // 如果想获取整数类型的随机数，不要将 x 放大 10 的若干倍然后 取整，
        // 直接使用 Random 对象的 nextInt 或者 nextLong 方法。
        log.info(String.valueOf(Math.random()));

        Random random = new Random();
        log.info(String.valueOf(random.nextInt()));
        log.info(String.valueOf(random.nextLong()));

        log.info(UUID.randomUUID().toString());
    }

    @Test
    void testException(){
        Map<String,String> map = new HashMap<>();

        //log.info(map.get("1").toString()); // 不会往下走了！！！

        try{
            log.info(map.get("1").toString());
        } catch (NullPointerException e){
            log.error("NullPointerException occured");
            e.printStackTrace(); //还会往下走
        }

        map.put("1","adelaide");
        log.info(map.get("1").toString());

        try{
            String[] uids = {"1","2","3"};
            log.info(uids[3]);
        }catch (IndexOutOfBoundsException e){
            log.error("IndexOutOfBoundsException occured");
            e.printStackTrace();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Fruit{
        String name;
        Integer price;
        Integer number;
        public Integer getSalePrice(){
            return price * number;
        }

    }

    @Data
    class Apple extends Fruit{
        public Apple(String name, Integer freight, Integer price, Integer number, String colour) {
            super(name, price, number);
            this.colour = colour;
            this.freight = freight;
        }

        @Override
        public Integer getSalePrice() {
            return super.getSalePrice() + freight;
        }

        public String getColour(){
            return colour;
        }

        String colour;
        Integer freight;

    }

    @Test
    void testFruitClass(){
        Fruit fruit = new Apple("apple",8,10,5,"red");
        log.info(String.valueOf(fruit.getSalePrice()));
    }

    //https://www.jianshu.com/p/abbb4d5d51ae
    //Java实现接口中的部分方法
    interface Order{
        void createOrder(int payment);
        void cancelOrder(int dealCode);
    }

    class OrderImpl implements Order{
        @Override
        public void createOrder(int payment){
            log.info("OrderImpl::createOrder");
        }

        @Override
        public void cancelOrder(int dealCode){
            log.info("OrderImpl::cancelOrder");
        }
    }

    class OrderImpl2 extends OrderImpl{
        @Override
        public void cancelOrder(int dealCode){
            log.info("OrderImpl2::cancelOrder");
        }
    }

    //接口继承
    interface Order2 extends Order{
        void getOrder(int dealCode);
    }
    //接口实现，必须同时实现子接口和父接口
    class OrderImpl3 implements Order2{
        @Override
        public void createOrder(int payment) {
            log.info("OrderImpl3::createOrder");
        }

        @Override
        public void cancelOrder(int dealCode) {
            log.info("OrderImpl3::cancelOrder");
        }

        @Override
        public void getOrder(int dealCode) {
            log.info("OrderImpl3::getOrder");
        }
    }

    //只能单继承，允许多实现
    class OrderImpl4 extends OrderImpl2 implements Order, Order2{
        @Override
        public void getOrder(int dealCode) {

        }
    }

    //抽象类
    abstract class Shape{
        public Shape(String name, int area) {
            this.name = name;
            this.area = area;
        }

        String name;
        int area;
        public abstract int getArea();
        public String getName() { return name;};
    }

    class Circle extends Shape{
        Circle(String name, int area){
            super(name, area);
        }

        @Override
        public int getArea(){
            return area;
        }
    }

    //String/StringBuffer/StringBuilder
    @Test
    void testString(){
        String a = "1" + "2" + "3";
        log.info(a.toString());

        //不需要重新构建string对象，比String快，线程安全
        StringBuffer b = new StringBuffer();
        b.append("1").append("2").append("3");
        log.info(b.toString());

        //不需要重新构建string对象，比String快，线程不安全
        StringBuffer c = new StringBuffer();
        c.append("1").append("2").append("3").append("5").append("6");
        log.info(c.toString());
    }


}
