import cn.xue8.http.Address;
import cn.xue8.http.CallBack;
import cn.xue8.http.Request;
import cn.xue8.http.YoHttpClient;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import net.dongliu.requests.Requests;
import okhttp3.*;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: http
 * @description:
 * @author: Xue 8
 * @create: 2019-04-06 18:50
 **/
public class Test {
    static private int port = 80;
    static private String host = "103.97.176.97";
    static private Socket socket;
    static private BufferedReader bufferedReader;
    static private BufferedWriter bufferedWriter;

    static public void sendGet() throws IOException {
        socket = new Socket(host, port);
        String path = "/";
//        SocketAddress dest = new InetSocketAddress(host, port);
//        socket.ct(dest);
        OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream());
        bufferedWriter = new BufferedWriter(streamWriter);

        bufferedWriter.write("GET" + path + " HTTP/1.1\r\n");
        bufferedWriter.write("Host:" + host + "\r\n");
        bufferedWriter.write("\r\n");
        bufferedWriter.flush();

        BufferedInputStream streamReader = new BufferedInputStream(socket.getInputStream());
        bufferedReader = new BufferedReader(new InputStreamReader(streamReader, "utf-8"));
        String line = null;
        while((line = bufferedReader.readLine())!= null){
            System.out.println(line);
        }
        bufferedReader.close();
        bufferedWriter.close();
        socket.close();
    }


    public static void main(String[] args) {
//        ConcurrentLinkedQueue blockingQueue = new ConcurrentLinkedQueue();
//        Thread t1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 10000; i++) {
//                    blockingQueue.add(i);
//                }
//            }
//        });
//
////        Thread t2 = new Thread(new Runnable() {
////            @Override
////            public void run() {
////                for (int i = 1; i < 10000; i++) {
////                    blockingQueue.add(i);
////                }
////            }
////        });
//
//        t1.start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
////        t2.start();
//        Thread t3 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 1000; i++) {
//                    System.out.println(blockingQueue.poll());
//                }
//            }
//        });
//
//        Thread t4 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 1000; i++) {
//                    System.out.println(blockingQueue.poll());
//                }
//            }
//        });
//
//        Thread t5 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 1000; i++) {
//                    System.out.println(blockingQueue.poll());
//                }
//            }
//        });
//
//        t3.start();
//        t4.start();
//        t5.start();
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        int i = blockingQueue.size();
//        String url = "http://biecheng.cn";
//        Address address = new Address("103.98.125.6", 80);
//        Address address1 = new Address("103.98.125.6", 80);
//        int a1 = address.hashCode();
//        int a2 = address1.hashCode();
//        if (address.equals(address1)) {
//            System.out.println("x");
//        }

        String url = "http://biecheng.cn";
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        YoHttpClient yoHttpClient = new YoHttpClient();
//        cn.xue8.http.Response response = yoHttpClient.SyncCall(request).executor();
        yoHttpClient.AsyncCall(request).executor(new CallBack() {
            @Override
            public void onResponse(cn.xue8.http.Response response) {
                System.out.println("545455");
            }
        });
        System.out.println("sd");

//
//        System.out.println("1");
//        try {
//            sendGet();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String resp = Requests.get(url).send().readToText();
//        System.out.println(resp);

//        Request.Builder builder = new Request.Builder();
//        Request req=new Request.Builder()
//                .url(url)
//                .build();
//        ExecutorService executors = Executors.newSingleThreadExecutor();
////        executors.
//
//        okhttp3.Request req1=new okhttp3.Request.Builder()
//                .url("http://biecheng.cn/question")
//                .get()
//                .head()
//                .build();
//
//        okhttp3.Request req2=new okhttp3.Request.Builder()
//                .url(url)
//                .get()
//                .build();
////        System.out.println("x");
//        OkHttpClient okHttpClient = new OkHttpClient();
//        System.out.println("-------");
//        try {  //同步请求
//            Response response1 = okHttpClient.newCall(req2).execute();
//            System.out.println(response1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        okHttpClient.newCall(req2)
//                .enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        System.out.println(call);
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        System.out.println(response.body());
//                        System.out.println("x");
//                    }
//                });
//        okHttpClient.newCall(req1)
//                .enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        System.out.println(call);
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
////                        System.out.println(response + "  222");
//                    }
//                });
//        System.out.println("-------");
//        Request request1 = new Request.Builder()
//                .url("x")
//                .method("4")
//                .build();
//        Request request2 = new Request.Builder()
//                .url("1")
//                .method("2")
//                .build();
//        System.out.println("x");
    }
}
