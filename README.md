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

## ����ܹ�

![](https://user-gold-cdn.xitu.io/2019/4/12/16a103d340d01c10?w=1856&h=771&f=png&s=114329)

���߽���������Ƴ����ģ�飺  
1. **������Ϣ**   
�ⲿ�ּ���Ӧ��ͼ��`Request`�������û�����������Ϣ����`URL`��`method`������ͷ�ȡ��ⲿ�����û����Բ����ġ�
2. **Yohttp�ͻ���**   
�û�����һ��`YoHttp`��Ȼ��������Ϣע�뵽Yohttp�����Կ�ʼʹ�������ܣ��������ͬ��������첽��������һ��`YoHttp`����һ���������ġ�һ�����ӳأ����Զ���һ����Ŀ��˵��ά����һ��`YoHttp`�ͻ��˾����ԡ�  
3. **������**  
����������ľ���ʵ�ֲ��������߽�һ��һ��������װ��һ������������ѻ�ȡ`Socket`���ӵĲ�����װ����������������`Socket`���Ķ�д��װ���շ���������Ȼ������������Ҫ�õ���Щ���������ɰ���Щ������һ��һ��ƴ��������ϳ�һ��������(Chain)��**һ����������Ӧ��һ������**��ִ�д������е�һ������������ֱ��ִ�������е���������Ҳ��Ӧ��һ���������ɡ���Ҳ��Ϊʲô������Ҫ���շ����������������Ϊһ����������һ�������϶��ǽ���Socket����д�Ͷ���   

![](https://user-gold-cdn.xitu.io/2019/4/12/16a10501db8d922b?w=492&h=254&f=png&s=11619)

4. **��������**  
����������ʹ���첽�����ʱ���õ�����������ά����һ��������к�һ���̳߳أ������������洢���Ǵ�����`Chain`���̳߳ظ���ִ�ж����еĴ�������  


5. **���ӳ�**   
ÿ��������ȥ���ӳػ�ȡ`Socket`���ӣ�������ӳ��д���`IP`��`PORT`��ͬ��������ֱ�ӷ��أ����򴴽�һ��`Socket`���Ӵ洢�����ӳ�Ȼ�󷵻أ������ӳ��е�����**����ʱ�䳬������������õ�ʱ���ͻᱻ�ر�**��   

## ʹ�ý̳�
1. ͬ������   
```
Request request = new Request.Builder()
        .url("www.baidu.com")
        .get()
        .build();
YoHttpClient httpClient = new YoHttpClient();
Response response = httpClient.SyncCall(request).executor();
System.out.println(response.getBody());
```
��һ���½���������Ϣ`Request`����д�����`URL`�����󷽷�������ͷ����Ϣ��  
�ڶ����½���`YoHttp`�ͻ��ˣ�**ѡ��ͬ��������**����������Ϣע�룬ִ������

2. �첽����  
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
��һ���½���������Ϣ`Request`����д�����`URL`�����󷽷�������ͷ����Ϣ��  
�ڶ����½���`YoHttp`�ͻ��ˣ�**ѡ���첽������**����������Ϣע�룬ִ�����󣬵���������Ӧ��ʱ�򣬻�ͨ���ص��첽�����`onResponse`������������Ӧ���ݡ�  

