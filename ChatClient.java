package edu.xatu.client;

import edu.xatu.util.MessageVO;
import edu.xatu.util.JSONUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class ChatClient {//客户端
    /*
    *聊天服务器的地址和端口
     */
    Socket s=null;
     private String addr="127.0.0.1";
     private static int port=8888;
     public void start(){
         try {
             s=new Socket(addr,port);
             //知道地址和端口，直接创建套接字
             //启动两个监听服务线程
             new ReceiveService().start();
             new SendService().start();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
     /*
        创建监听服务器消息线程
    */
     private class ReceiveService extends Thread{
         private BufferedReader br=null;
         public void run(){
             try {
                 while (true) {
                     br=new BufferedReader(
                             new InputStreamReader(s.getInputStream()));
                     String jsonStr=br.readLine();
                     //把json串转换成对象
                     MessageVO mvo= JSONUtil.json2obj(jsonStr,MessageVO.class);
                     //在控制台输出
                     System.out.println("info："+mvo.getMesg()+"【时间："+mvo.getDate()+"】");
                 }
                 //再监听有没有下一个
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
     }
/*
*创建键盘监听类
 */
    private class SendService extends Thread{
        private PrintWriter pw=null;
        public void run(){
            try {
                while (true) {
                    Scanner in=new Scanner(System.in);
                    //监听键盘消息
                    String mesg=in.nextLine();
                    //封装MessageVo
                    MessageVO vo=new MessageVO(mesg,new Date());
                    //解析成json串
                    String jsonstr=JSONUtil.boj2json(vo);
                    //发送到服务器
                    pw=new PrintWriter(s.getOutputStream());
                    pw.println(jsonstr);
                    pw.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}