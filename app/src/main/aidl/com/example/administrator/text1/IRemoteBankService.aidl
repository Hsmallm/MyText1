// IRemoteBankService.aidl
package com.example.administrator.text1;
import com.example.administrator.text1.User;
import com.example.administrator.text1.RemoteClientCallBack;

interface IRemoteBankService {

   //存钱
   boolean despoistMoney(int money);

   //取钱
   int drawMoney(int money);

   //当前存取款用户
   User getUser();

   //—-以上方法都是客户端直接调用，将数据传给服务端，顺带从服务端拿取数据—–类似，客户端发送请求，拿取数据

   //客户端注册回调接口，用以服务端主动通过调用回调方法，向客户端发送消息—类似服务端主动推送数据给客户端/
   void registerClientOberser(RemoteClientCallBack callBack);
}
