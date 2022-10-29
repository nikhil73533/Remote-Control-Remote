package com.example.autometedcar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class RcCar extends AppCompatActivity {

    // Initializing the variables
    ImageButton up;
    ImageButton down;
    ImageButton right;
    ImageButton left;
    ImageButton up_left;
    ImageButton up_right;
    ImageButton down_left;
    ImageButton down_right;
    ImageButton stop;


    Socket MyAppSocket = null;
    EditText textaddress;
    public static String wifiModuleIp = "";
    public static int wifiModulePort = 2156;
    static String CMD = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rc_car);

        up = (ImageButton) findViewById(R.id.arrow_up);
        down =(ImageButton) findViewById(R.id.arrow_down);
        right  =(ImageButton) findViewById(R.id.arrow_right);
        left = (ImageButton) findViewById(R.id.arrow_left);
        up_left=(ImageButton) findViewById(R.id.arrow_up_left);
        up_right=(ImageButton) findViewById(R.id.arrow_up_right);
        down_left=(ImageButton) findViewById(R.id.arrow_down_left);
        down_right=(ImageButton) findViewById(R.id.arrow_down_right);
        stop =(ImageButton) findViewById(R.id.stop);
        textaddress = findViewById(R.id.IpAddress);





        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD = "U";
                Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
                cmd_increase_servo.execute();
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD = "D";
                Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
                cmd_increase_servo.execute();
            }
        });






        left.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getIPandPort();
                        CMD = "L";
                        Socket_AsyncTask cmd_move_left = new Socket_AsyncTask();
                        cmd_move_left.execute();
                    }
                }
        );

        right.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getIPandPort();
                        CMD = "R";
                        Socket_AsyncTask cmd_move_right = new Socket_AsyncTask();
                        cmd_move_right.execute();
                    }
                }
        );

        up_left.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getIPandPort();
                        CMD = "UL";
                        Socket_AsyncTask cmd_move_forward_left = new Socket_AsyncTask();
                        cmd_move_forward_left.execute();
                    }
                }
        );

        up_right.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getIPandPort();
                        CMD = "UR";
                        Socket_AsyncTask cmd_move_forward_right = new Socket_AsyncTask();
                        cmd_move_forward_right.execute();
                    }
                }
        );

        down_left.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getIPandPort();
                        CMD = "DL";
                        Socket_AsyncTask cmd_move_back_left = new Socket_AsyncTask();
                        cmd_move_back_left.execute();
                    }
                }
        );

        down_right.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getIPandPort();
                        CMD = "DR";
                        Socket_AsyncTask cmd_move_back_right = new Socket_AsyncTask();
                        cmd_move_back_right.execute();
                    }
                }
        );

        stop.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getIPandPort();
                        CMD = "S";
                        Socket_AsyncTask cmd_stop = new Socket_AsyncTask();
                        cmd_stop.execute();
                    }
                }
        );




    }

    public void getIPandPort()
    {
        String iPandPort = textaddress.getText().toString();
        Log.d("MYTEST","IP String: "+ iPandPort);
        wifiModuleIp = iPandPort;
        wifiModulePort = 2156;
        Log.d("MY TEST","IP:" +wifiModuleIp);
        Log.d("MY TEST","PORT:"+wifiModulePort);
    }
    public class Socket_AsyncTask extends AsyncTask<Void,Void,Void>
    {
        Socket socket;

        @Override
        protected Void doInBackground(Void... params){
            try{
                InetAddress inetAddress = InetAddress.getByName(RcCar.wifiModuleIp);
                socket = new java.net.Socket(inetAddress,RcCar.wifiModulePort);
                try (DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {
                    dataOutputStream.writeBytes(CMD);
                    dataOutputStream.close();
                }
                socket.close();
            }catch (UnknownHostException e){e.printStackTrace();}catch (IOException e){e.printStackTrace();}
            return null;
        }
    }

    }
