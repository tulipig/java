package com;

import ch.qos.logback.core.pattern.util.IEscapeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Person322{
    public Person322(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String name;
    public Integer age;
}

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
    void testLinkedHashMap(){
        Map<Integer,String> map = new LinkedHashMap<>(16,0.75f, true);
        map.put(4,"adelaide52");
        map.put(2,"tulip");
        map.put(3,"amy");

        map.forEach((k,v) -> {
            log.info(k + ": " + v);
        });
        log.info("--------------------");

//        map.get(3);
//        map.get(4);
        map.get(2);
        map.forEach((k,v) -> {
            log.info(k + ": " + v);
        });

        log.info("--------------------");

        // 反向遍历，使用的是迭代器 ListIterator
        ListIterator<Map.Entry> i= new ArrayList<Map.Entry>(map.entrySet()).listIterator(map.size());
        while(i.hasPrevious()) {
            Map.Entry entry=i.previous();
            log.info(entry.getKey() + ": " + entry.getValue());
        }
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
            log.info("OrderImpl4::getOrder");
        }
    }

    @Test
    void testInterface(){
        Order order = new OrderImpl3();
        order.cancelOrder(1234);

        OrderImpl4 order1 = new OrderImpl4();
        order1.createOrder(100);
        order1.cancelOrder(1234);
        order1.getOrder(1234);
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

    //https://zhuanlan.zhihu.com/java8
    interface Formula {
        double calculate(int a);
        double add(int a, int b);

        default double sqrt(int a) {
            return Math.sqrt(a);
        }
    }

    @Test
    void testInterfaceDefault(){
        //formula对象以匿名对象的形式实现了Formula接口
        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return sqrt(a*10);
            }

            @Override
            public double add(int a, int b) {
                return a+b;
            }
        };

        System.out.println(formula.sqrt(4));
        System.out.println(formula.calculate(4));
        System.out.println(formula.add(5,10));
    }

    @FunctionalInterface
    interface Converter<F, T> {
        T convert(F from);
    }
    static class Something {
        static String startsWith(String s) {
            return String.valueOf(s.charAt(0));
        }
    }
    @Test
    void testInterfaceLambda(){
        //任意只包含一个抽象方法的接口，我们都可以用来做成lambda表达式
        Converter<String, Integer> converter = from -> Integer.valueOf(from);
        Integer integer = converter.convert("1234");
        System.out.println(integer);

        //方法引用
        Converter<String, Integer> converter1 = Integer::valueOf;
        System.out.println(converter1.convert("5678"));

        //方法引用2，自定义方法
        Converter<String, String> converter2 = Something::startsWith;
        System.out.println(converter2.convert("JAVA"));

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


    @Test
    void testLambda1(){
        //https://zhuanlan.zhihu.com/p/33477686
        //Java 8方式：
        new Thread(()->{
            log.info("thread1");
        },"AA").start();

        // Java 8之前：
        new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("thread2");
            }
        },"BB").start();
    }

    @Test
    void testLambda2(){
        List features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        // Java 8之前：
        for (Object feature : features) {
            System.out.println(feature);
        }
        System.out.println("----------------------------->>>>>>>>>>>");

        // Java 8之后：
        features.forEach(n -> System.out.println(n));
        System.out.println("----------------------------->>>>>>>>>>>");

        // 使用Java 8的方法引用更方便，方法引用由::双冒号操作符标示，
        // 看起来像C++的作用域解析运算符
        features.forEach(System.out::println);
    }

//    public static void filter(List<String> names, Predicate condition) {
//        for(String  name: names)  {
//            if(condition.test(name)) {
//                System.out.println(name + " ");
//            }
//        }
//    }

    // 更好的办法
    public static void filter(List names, Predicate condition) {
        names.stream().filter((name) -> (condition.test(name))).forEach((name) -> {
            System.out.println(name + " ");
        });
    }

    @Test
    void testLambda3(){
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        System.out.println("Languages which starts with J :");
        filter(languages, (str)->((String)str).startsWith("J"));

        System.out.println("Languages which ends with a ");
        filter(languages, (str)->((String)str).endsWith("a"));

        System.out.println("Print all languages :");
        filter(languages, (str)->true);

        System.out.println("Print no language : ");
        filter(languages, (str)->false);

        System.out.println("Print language whose length greater than 4:");
        filter(languages, (str)->((String)str).length() > 4);
    }


    public static void filter2(List names) {
        // 甚至可以用and()、or()逻辑函数来合并Predicate，
// 例如要找到所有以J开始，长度为四个字母的名字，你可以合并两个Predicate并传入
        Predicate<String> startsWithJ = (n) -> n.startsWith("J");
        Predicate<String> fourLetterLong = (n) -> n.length() == 4;
        names.stream()
                .filter(startsWithJ.and(fourLetterLong))
                .forEach((n) -> System.out.print("nName, which starts with 'J' and four letter long is : " + n));
    }
    @Test
    void testLambda4(){
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
        filter2(languages);
    }


    @Test
    void testLambda5(){
        // 不使用lambda表达式为每个订单加上12%的税
//        List costBeforeTaxArrays = Arrays.asList(100, 200, 300, 400, 500);
//        List<Integer> costBeforeTax = new ArrayList<>();
//        costBeforeTax.addAll(costBeforeTaxArrays);
        List<Integer> costBeforeTax = new ArrayList<>(Arrays.asList(100, 200, 300, 400, 500));
        for (Integer cost : costBeforeTax) {
            double price = cost + .12*cost;
            System.out.println(price);
        }

        // 使用lambda表达式
        List<Integer> costBeforeTax2 = new ArrayList<>(Arrays.asList(100, 200, 300, 400, 500));
        costBeforeTax2.stream().map((cost) -> cost + .12*cost).forEach(System.out::println);
    }

    @Test
    void testLambda6(){
        // 为每个订单加上12%的税老方法：
        List<Integer> costBeforeTax = new ArrayList<>(Arrays.asList(100, 200, 300, 400, 500));
        double total = 0;
        for (Integer cost : costBeforeTax) {
            double price = cost + .12*cost;
            total = total + price;
        }
        System.out.println("Total : " + total);

        // 新方法：
        List<Integer> costBeforeTax2 = new ArrayList<>(Arrays.asList(100, 200, 300, 400, 500));
        double bill = costBeforeTax2.stream().map((cost) -> cost + .12*cost).reduce((sum, cost) -> sum + cost).get();
        System.out.println("Total : " + bill);
    }

    @Test
    void testLambda7(){
        // 创建一个字符串列表，每个字符串长度大于2
        List<String> strList = new ArrayList<>(Arrays.asList("abc","" , "bcd", "", "defg", "jk"));

        List<String> filtered = strList.stream().filter(x -> x.length()> 2).collect(Collectors.toList());
        System.out.printf("Original List : %s, filtered list : %s %n", strList, filtered);
    }

    @Test
    void testLambda8(){
        // 将字符串换成大写并用逗号链接起来
        List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
        String G7Countries = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
        System.out.println(G7Countries);

        // 复制不同的值，创建一个子列表
        List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4);
        List<Integer> distinct = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
        System.out.printf("Original List : %s,  Square Without duplicates : %s %n", numbers, distinct);
    }

    @Test
    void testLambda9(){
        //获取数字的个数、最小值、最大值、总和以及平均值
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        IntSummaryStatistics stats = primes.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("Highest prime number in List : " + stats.getMax());
        System.out.println("Lowest prime number in List : " + stats.getMin());
        System.out.println("Sum of all prime numbers : " + stats.getSum());
        System.out.println("Average of all prime numbers : " + stats.getAverage());
    }

    @Test
    void testNewInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        //Class classPerson = Class.forName("com.Person322"); //要使用全路径名？
        Person322 person322 = Person322.class.getDeclaredConstructor(String.class, Integer.class).newInstance("HERESMYARG", 17);
        System.out.println(person322.toString());
    }

    interface A1{
        void fun1();
    }
    interface A2{
        void fun2();
    }
    class AImpl implements A1,A2{
        @Override
        public void fun1() {
            System.out.println("fun1");
        }
        @Override
        public void fun2() {
            System.out.println("fun2");
        }
    }
    @Test
    void testAImpl(){
        A1 a1 = new AImpl(); //发生了切割？
        a1.fun1(); //只有fun1

        A2 a2 = new AImpl();
        a2.fun2();
    }

    @Test
    void testInnerClassInFunction(){
        class A341{
            public A341() {
                System.out.println("createing A341");
            }
        }

        A341 a341 = new A341();
    }

    //collections.sort
    @AllArgsConstructor
    @Data
    class Student{
        String name;
        Integer age;
        float score;
    }
    public static void print(List<Student> students) {
        for (Student s : students) {
            System.out.println(s.getName() + " " + s.getAge() + " " + s.getScore());
        }
    }

    public static class AgeAscComparator implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return o1.getAge() - o2.getAge();//小到大
        }
    }

    public static class NameAscComparator implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    public static class ScoreDescComparator implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            if (Math.abs(o1.getScore() - o2.getScore()) < 0.001) {
                return 0;
            } else if (o1.getScore() < o2.getScore()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    @Test
    void testCollectionsSort(){
        List<Student> students = new ArrayList<>();
        students.add(new Student("Alice", 19, 89.0f));
        students.add(new Student("Peter", 20, 78.0f));
        students.add(new Student("Leo", 18, 99.0f));

        Collections.sort(students, new AgeAscComparator());
        print(students);

        Collections.sort(students, new NameAscComparator());
        print(students);

        Collections.sort(students, new ScoreDescComparator());
        print(students);

    }


}
