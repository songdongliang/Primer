package com.sdl.primer.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by 11870 on 2017/2/14.
 */

public class TCPServerService extends Service {

    private boolean mIsServiceDestory ;

    private String[] mDefinedMessages = new String[]{
            "你优秀吗？","这个世界会好吗","天涯何处无芳草，有草必有丰田车",
            "生活不止眼前的苟且，还有诗和远方的田野",
            "黑白古天乐","春天来了，花儿开了，我起飞了..."
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new TcpServer()).start();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestory = true ;
        super.onDestroy();
    }

    private class TcpServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;

            try {
                serverSocket = new ServerSocket(8088);
            } catch (IOException e) {
                System.err.println("establish tcp server failed, port:8088");
                e.printStackTrace();
                return;
            }

            while (!mIsServiceDestory){
                try {
                    Socket client = serverSocket.accept();
                    System.out.println("accept");
                    //单独开线程与客户端通信
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void responseClient(Socket client) throws IOException {
        //用于接收客户端消息
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //用于向客户端发送消息
        PrintWriter pw = new PrintWriter(client.getOutputStream());
        pw.println("welcome to qq");
        while (!mIsServiceDestory){
            String msg = br.readLine();
            System.out.println("msg from client: " + msg);
            if(msg == null){
                //客户端断开连接
                break;
            }
            int i = new Random().nextInt(mDefinedMessages.length);
            String reply = mDefinedMessages[i];
            pw.println(reply);
            pw.flush();
            System.out.println(reply);
        }
        System.out.println("client quit ...");
        //关闭流
        pw.close();
        //关闭socket
        br.close();
        client.close();
    }
}
