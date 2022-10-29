package com.example.autometedcar;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class voiceControl extends AppCompatActivity {
    private TextView text_to_speach;
    private Button Speak;

String CMD = "0";

    Socket MyAppSocket = null;
    EditText textaddress;
    public static String wifiModuleIp = "";
    public static int wifiModulePort = 2156;
    EditText ipaddress;

    String text = "0";

    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Speak = findViewById(R.id.button);
        text_to_speach = findViewById(R.id.textView4);
        ipaddress = findViewById(R.id.Ip);


        Speak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak to text");
                try {
                    startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
                }
                catch (Exception e)
                {
                    Toast.makeText(voiceControl.this," " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode,int resultCode,
                                    @Nullable Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUEST_CODE_SPEECH_INPUT){
            if(resultCode == RESULT_OK && data != null)
            {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
               text =  Objects.requireNonNull(result).get(0);
               text_to_speach.setText(Objects.requireNonNull(result).get(0));

                if(text =="Stop")
                {
                    getIPandPort();
                    CMD = "S";
                    voiceControl.Socket_AsyncTask cmd_stop = new voiceControl.Socket_AsyncTask();
                    cmd_stop.execute();
                }
                else if(text =="Forward" || text == "forward")
                {
                    getIPandPort();
                    CMD = "UP";
                    voiceControl.Socket_AsyncTask cmd_move_up = new voiceControl.Socket_AsyncTask();
                    cmd_move_up.execute();
                }

                else if(text =="Backward" ||text =="backward")
                {
                    getIPandPort();
                    CMD = "D";
                    voiceControl.Socket_AsyncTask cmd_move_down = new voiceControl.Socket_AsyncTask();
                    cmd_move_down.execute();
                }
            }

        }
    }








    public void getIPandPort()
    {
        String iPandPort = ipaddress.getText().toString();
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