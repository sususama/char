package edu.xatu.sever;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChatSever {//服务器
    private static final int port =8888;
    /*
     *监听
     */
    public void start() throws Exception {//主内容
        //启动发送消息线程
        new Thread(new SendService()).start();
        ServerSocket serverSocket = null;
        Socket client=null;
        //申请端口
        serverSocket = new ServerSocket(port);
        while (true) {
            System.out.println("开始监听新的客户端连接...");
            client=serverSocket.accept();
            System.out.println("客户已连接【"+client.getInetAddress().getHostAddress()+":"
                    +client.getPort()+"】");
            //提供服务线程
            String key=client.getInetAddress().getHostAddress()+":"+client.getPort();

            new ReceiveService(client).start();
            //把socket放进客户socket集合，以便发送
            allCustomer.put(key,client);
        }
        //监听下一个
    }
    //所有客户端连接集合
    private ConcurrentHashMap<String, Socket> allCustomer=new ConcurrentHashMap<>();
    //存放消息队列
    private ConcurrentLinkedQueue<String> messageQueue=new ConcurrentLinkedQueue<>();
    //创建接收线程
    private class SendService implements Runnable{

        @Override
        public void run() {
            //取消息队列中的消息
            try {
                PrintWriter pw=null;
                while (true) {
                    String mesg=messageQueue.poll();
                    synchronized (messageQueue) {
                    if (mesg!=null) {
                        //遍历客户端连接
                        for (Socket socket : allCustomer.values()) {//Socket socket : allCustomer.values()迭代Socket
                            //创建字符输出流
                            pw=new PrintWriter(socket.getOutputStream());
                            //向客户端发送消息

                                pw.println(mesg);
                                pw.flush();
                            }
                        }else {
                        messageQueue.wait();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private class ReceiveService extends Thread{//服务器的接收线程
        //持有消息队列的引用
        //内部可以至直接访问，所以不需要传参
//        private ConcurrentLinkedQueue<String> messageQueue=null;
//        private ReceiveService(ConcurrentLinkedQueue<String> messageQueue){
//            this.messageQueue=messageQueue;
//        }
        private Socket client=null;
        private ReceiveService(Socket client) {
            this.client = client;
        }
        public void run(){
            //接收字符，选择字符流
            //Buffer字符流的readLine（）好用，所以选择BufferedRead
            BufferedReader br=null;
            try {
                br=new BufferedReader(
                        new InputStreamReader(client.getInputStream()));
                //接收消息
                while (true) {

                        System.out.println("等待客户端【" + client.getInetAddress().getHostAddress() + "】消息");
                        String mesg = br.readLine();
                        //放入消息队列
                        System.out.println("接收到客户端【" + client.getInetAddress().getHostAddress() + "】消息");
                    synchronized (messageQueue) {
                        messageQueue.offer(mesg);
                        messageQueue.notify();
                        //接收下一条
//                messageQueue.peek();//看看放的什么数据
//                messageQueue.poll();//删除放的数据
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
