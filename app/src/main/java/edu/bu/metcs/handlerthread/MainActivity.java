package edu.bu.metcs.handlerthread;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.IpSecManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    private EditText delayTime;
    private TextView displayText;

    private Handler mainHandler, threadHandler;
    private HandlerThread mHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        delayTime = findViewById(R.id.delayTimeid);
        displayText = findViewById(R.id.displayTextid);
        displayText.setText("");

        // create a handler for the main thread
        mainHandler = new Handler(Looper.getMainLooper());

        // or create a handler and pass a callback to handle the received message
//        mainHandler = new Handler(Looper.getMainLooper(),
//                new Handler.Callback() {
//            public boolean handleMessage(Message m) {
//                displayText.setText(
//                        m.getData().getString("Status"));
//
//                mainHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       displayText.setText("") ;
//                    }
//                }, 1000);
//
//                return true;
//            }
//        });

        // create a handler thread mHandlerThread
        mHandlerThread = new HandlerThread("background handler thread");
        // start mHandlerThread  by calling its start() method
        mHandlerThread.start();

        // Create a threadHandler that is associated with the mHandlerThread,
        threadHandler = new Handler(mHandlerThread.getLooper ());

    }


    // called when clicking the display button
    public void display(View v) {

        // get the delay value from the editview
        long delay = 1;
        try {
            delay = Integer.parseInt(delayTime.getText().toString());
        } catch (Exception e) {
            Log.d(TAG, "invalid number");
        }

        displayText.setText("execute \n for (i = 0; i <" + delay * 100000000 + "; i++) ;");

        final long mydelay = delay;

        // post a runnable on the handler thread
        threadHandler.post(new Runnable() {

            @Override
            public void run() {
                // perform long operation
                long i = LongOperation.run(mydelay);

                final String doneMsg =  "Thread " +
                        Thread.currentThread().getId() +
                        " Done : i =" + i;

                // send a message to the main thread
//                Message msg = Message.obtain();
//                Bundle bundle = new Bundle();
//                bundle.putString("status", doneMsg);
//                msg.setData(bundle);
//                mainHandler.sendMessage(msg);


                mainHandler.post(new Runnable(){
                    @Override
                    public void run() {
                        displayText.setText(doneMsg);

                        mainHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                displayText.setText("") ;
                            }
                        }, 1000);
                    }
                });
            }

        });
    }
}


