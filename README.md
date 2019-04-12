# Yohttp
[![license](https://user-gold-cdn.xitu.io/2019/4/12/16a10ca2e67ffa39?w=278&h=20&f=svg&s=984)](https://github.com/doocs/advanced-java/blob/master/LICENSE)
[![original](https://user-gold-cdn.xitu.io/2019/4/12/16a10ca2f3ccb249?w=105&h=20&f=svg&s=927)](https://github.com/doocs/advanced-java)
[![open-source-organization](https://user-gold-cdn.xitu.io/2019/4/12/16a10ca5a6ebc35d?w=125&h=20&f=svg&s=925)](https://github.com/doocs/intro)
[![reading](https://user-gold-cdn.xitu.io/2019/4/12/16a10ca2f659a83c?w=128&h=20&f=svg&s=923)](https://github.com/doocs/technical-books)
[![leetcode](https://user-gold-cdn.xitu.io/2019/4/12/16a10ca2f0dcac21?w=154&h=20&f=svg&s=933)](https://github.com/doocs/leetcode)
[![stars](https://user-gold-cdn.xitu.io/2019/4/12/16a10ca357eccf04?w=105&h=20&f=svg&s=913)](https://github.com/doocs/advanced-java/stargazers)
[![forks](https://user-gold-cdn.xitu.io/2019/4/12/16a10ca4142a339d?w=105&h=20&f=svg&s=913)](https://github.com/doocs/advanced-java/network/members)
[![help-wanted](https://user-gold-cdn.xitu.io/2019/4/12/16a10ca428b33f4d?w=105&h=20&f=svg&s=913)](https://github.com/doocs/advanced-java/labels/help%20wanted)
[![issues](https://user-gold-cdn.xitu.io/2019/4/12/16a10ca45062cdd5?w=105&h=20&f=svg&s=913)](https://github.com/doocs/advanced-java/issues)
[![PRs Welcome](https://user-gold-cdn.xitu.io/2019/4/12/16a10ca43ff2e5fa?w=89&h=20&f=svg&s=903)](http://makeapullrequest.com)

## 软件架构

![](https://user-gold-cdn.xitu.io/2019/4/12/16a103d340d01c10?w=1856&h=771&f=png&s=114329)

笔者将软件大概设计成五大模块：  
1. **请求信息**   
这部分即对应上图的`Request`，用于用户构建请求信息，如`URL`、`method`、请求头等。这部分是用户可以操作的。
2. **Yohttp客户端**   
用户创建一个`YoHttp`，然后将请求信息注入到Yohttp即可以开始使用请求功能，请求包括同步请求和异步请求，其中一个`YoHttp`包含一个调度中心、一个连接池，所以对于一个项目来说，维护着一个`YoHttp`客户端就足以。  
3. **处理链**  
这里是请求的具体实现操作，笔者将一个一个操作封装成一个拦截器，如把获取`Socket`连接的操作封装成连接拦截器、把`Socket`流的读写封装成收发拦截器，然后我们请求需要用到哪些操作，即可把这些拦截器一个一个拼接起来组合成一个处理链(Chain)，**一个处理链对应着一个请求**。执行处理链中的一个个拦截器，直到执行完所有的拦截器，也对应着一个请求的完成。这也是为什么我们需要将收发拦截器放在最后，因为一个请求的最后一个操作肯定是进行Socket流的写和读。   

![](https://user-gold-cdn.xitu.io/2019/4/12/16a10501db8d922b?w=492&h=254&f=png&s=11619)

4. **调度中心**  
调度中心在使用异步请求的时候用到，调度中心维护着一个请求队列和一个线程池，请求队列里面存储的是处理链`Chain`。线程池负责执行队列中的处理链。  


5. **连接池**   
每个请求都是去连接池获取`Socket`连接，如果连接池中存在`IP`、`PORT`相同的连接则直接返回，否则创建一个`Socket`连接存储到连接池然后返回，而连接池中的连接**闲置时间超过最大允许闲置的时间后就会被关闭**。   

## 使用教程
1. 同步请求   
```
Request request = new Request.Builder()
        .url("www.baidu.com")
        .get()
        .build();
YoHttpClient httpClient = new YoHttpClient();
Response response = httpClient.SyncCall(request).executor();
System.out.println(response.getBody());
```
第一步新建个请求信息`Request`，填写请求的`URL`、请求方法、请求头等信息。  
第二步新建个`YoHttp`客户端，**选择同步于请求**并将请求信息注入，执行请求。

2. 异步请求  
```
Request request = new Request.Builder()
        .url("www.baidu.com")
        .get()
        .build();
YoHttpClient httpClient = new YoHttpClient();
httpClient.AsyncCall(request).executor(new CallBack() {
    @Override
    public void onResponse(Response response) {
        System.out.println(response.getBody());
    }
});
```
第一步新建个请求信息`Request`，填写请求的`URL`、请求方法、请求头等信息。  
第二步新建个`YoHttp`客户端，**选择异步于请求**并将请求信息注入，执行请求，当请求有响应的时候，会通过回调异步请求的`onResponse`方法来反馈响应内容。  

