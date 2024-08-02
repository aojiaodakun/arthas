package com.hzk;

import com.taobao.arthas.core.GlobalOptions;
import com.taobao.arthas.core.server.ArthasBootstrap;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MathGameAttach {

    static {
//        String str1 = "attachmentServer.maxFileSize=1052428800\n" +
//                "attachmentServer.tcpUrl={{attachmentServer.ip}}:{{attachmentServer.tcpport}}\n" +
//                "attachmentServer.url=http://172.17.51.95:8100/fileserver\n" +
//                "attachment.fileserver=https://{{attachment.fileserver.ip_port}}/attachment/download.do?path=/\n" +
//                "fileserver.attachment.preview=true\n" +
//                "check.file.zip=";
//        System.setProperty("arthas.welcome.extend", str1);
        System.setProperty("arthas.debug", "true");
    }

    private static Random random = new Random();

    public static String name = "中文名称";

    private int illegalArgumentCount = 0;

    private static List<String> nameList = Arrays.asList("zhangsan", "lisi", "wangwu");

    private static Map<String, String> map = new HashMap(){
        {
            put("a", "a");
            put("b", "b");
            put("c", "c");
            put("d", "d");
        }
    };

    public static void main(String[] args) throws Exception {
        try {
            Class<?> aClass = ClassLoader.getSystemClassLoader().loadClass("com.taobao.arthas.agent334.AgentBootstrap");
            System.out.println(aClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        GlobalOptions.isDump = true;
        MathGameAttach game = new MathGameAttach();
        // TODO hzk,启动arthas-server
        // TODO,KD 设置properties名称
        System.setProperty("arthas.config.name", "kd_arthas");
//        new ArthasAgent("C:\\Users\\Administrator\\.arthas\\lib\\3.7.1\\arthas").init();
        HzkArthasAgent hzkArthasAgent = new HzkArthasAgent();
        hzkArthasAgent.init();
        String arthasPassword = HzkArthasAgent.getArthasPassword();

        new Thread(() -> {
            System.out.println("start");
            try {
                Thread.currentThread().sleep(1000 * 600);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
                e.printStackTrace();
            }
        },"hzk").start();
        while (true) {
            game.run();
//            game.isEmpty("靓仔");
            TimeUnit.SECONDS.sleep(5);
        }


    }

    public boolean isEmpty(String str) {
        System.setProperty("mail.smtp.auth", "a");
        System.setProperty("hzk1", "a");

        HzkArthasAgent arthasAgent = new HzkArthasAgent();
        arthasAgent.init();
        String errorMessage = arthasAgent.getErrorMessage();
        String name = getName();

        test1();
        test2();
        test3();
        return false;
    }

    public void test1(){
        System.out.println("test1");
    }
    public void test2(){
        System.out.println("test2");
    }
    public void test3(){
        System.out.println("test3");
    }

    public void run() throws InterruptedException {
        try {
            int number = random.nextInt()/10000;
            List<Integer> primeFactors = primeFactors(number);
            print(number, primeFactors);

        } catch (Exception e) {
            System.out.println(String.format("illegalArgumentCount:%3d, ", illegalArgumentCount) + e.getMessage());
        }
    }

    public static void print(int number, List<Integer> primeFactors) {
        StringBuffer sb = new StringBuffer(number + "=");
        for (int factor : primeFactors) {
            sb.append(factor).append('*');
        }
        if (sb.charAt(sb.length() - 1) == '*') {
            sb.deleteCharAt(sb.length() - 1);
        }
        System.out.println(sb);
    }

    public List<Integer> primeFactors(int number) {
        if (number < 2) {
            illegalArgumentCount++;
            try {
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new IllegalArgumentException("number is: " + number + ", need >= 2");
        }

        List<Integer> result = new ArrayList<Integer>();
        int i = 2;
        while (i <= number) {
            if (number % i == 0) {
                result.add(i);
                number = number / i;
                i = 2;
            } else {
                i++;
            }
        }

        return result;
    }

    public String getName(){return name;}
}
