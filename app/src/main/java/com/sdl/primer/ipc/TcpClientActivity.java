package com.sdl.primer.ipc;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sdl.primer.R;
import com.sdl.primer.util.CloseableUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TcpClientActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;

    private TextView tv_container ;
    private EditText input_msg ;
    private Button btn_send ;

    private Socket mClientSocket ;

    private PrintWriter mPrintWriter ;

    private MyHandler mHandler ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_client);

        initView();
        Intent intent = new Intent(this,TCPServerService.class);
        startService(intent);

        mHandler = new MyHandler(this);
        new Thread(()->connectTcpServer()).start();
    }

    private void connectTcpServer() {
        Socket socket = null ;
        while (socket == null){
            try {
                socket = new Socket("localhost",8088);
                mClientSocket = socket ;
                mPrintWriter = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                System.out.println("connect server success !");
            } catch (IOException e) {
                e.printStackTrace();
                SystemClock.sleep(1000);
                System.out.println("connect tcp server failed, retry...");
            }
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!TcpClientActivity.this.isFinishing()){
                String msg = br.readLine() ;
                if(msg != null){
                    System.out.println("receive : " + msg);
                    String time = formatDateTime(System.currentTimeMillis());
                    String showMsg = "server " + time + ":" + msg + "\n";
                    mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG,showMsg).sendToTarget();
                }
            }
            System.out.println("quit...");
            CloseableUtil.close(mPrintWriter);
            CloseableUtil.close(br);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        tv_container = (TextView) findViewById(R.id.tv_container);
        input_msg = (EditText) findViewById(R.id.input_msg);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        btn_send.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                String msg = input_msg.getText().toString() ;
                if(!TextUtils.isEmpty(msg) && mPrintWriter != null){
                    mPrintWriter.println(msg);
                    input_msg.setText("");
                    String time = formatDateTime(System.currentTimeMillis());
                    String showMsg = "self " + time + ":" + msg + "\n";
                    tv_container.setText(tv_container.getText() + showMsg);
                }
                break;
        }
    }

    private String formatDateTime(long time){
        return new SimpleDateFormat("(HH:mm:ss)", Locale.CHINA).format(new Date(time));
    }

    @Override
    protected void onDestroy() {
        if(mClientSocket != null){
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    private void setBtnEnable(boolean enable){
        btn_send.setEnabled(enable);
    }

    private void setContainer(String container){
        tv_container.setText(tv_container.getText() + container);
    }

    private static class MyHandler extends Handler {

        private WeakReference<TcpClientActivity> mReference ;

        public MyHandler(TcpClientActivity activity){
            mReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            TcpClientActivity activity = mReference.get();
            if(activity != null){
                //处理数据
                switch (msg.what){
                    case MESSAGE_RECEIVE_NEW_MSG:
                        activity.setContainer((String) msg.obj);
                        break;
                    case MESSAGE_SOCKET_CONNECTED:
                        activity.setBtnEnable(true);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
