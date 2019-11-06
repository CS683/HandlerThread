package edu.bu.metcs.handlerthread;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

        delayTime = (EditText) findViewById(R.id.delayTimeid);
        displayText = (TextView) findViewById(R.id.displayTextid);
        displayText.setText("");

        // create a handler for main thread
        // Upon receiving the message, it will dispaly the message and also send an broadcast message
        mainHandler = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message m) {
                displayText.setText(
                        m.getData().getString("Status"));


                //test sendBroadcast()
                Intent intent = new Intent("edu.bu.threadhandleexample.broadcastintenttest");
                sendBroadcast(intent);



                return true;
            }
        });

        // To do: create a handler thread mHandlerThread

        mHandlerThread = new HandlerThread("background handler thread");

        // To do: start mHandlerThread  by calling its start() method

        mHandlerThread.start();



        // To do: Create a threadHandler that is associated with the mHandlerThread,

        threadHandler = new Handler(mHandlerThread.getLooper (), new Handler.Callback(){
            public boolean handleMessage(Message m){

                long delay = m.getData ().getLong("delay");

                long i = longOperation (delay);

                Message msg = Message.obtain ();
                Bundle bundle = new Bundle();
                bundle.putString("Status", " Thread " + Thread.currentThread ().getId () +
                        " Done : i ="  + i);
                msg.setData (bundle);

                mainHandler.sendMessage(msg);






                return true;
            }
        } );
        // passing with two parameters: a looper and a callback object.
        // The callback object defines a single method handleMessage(Message)
        // In this message, it will get the delay value from the message,
        // call the longOperation(delay) method, and then send an message to
        // the main thread to let it know the long operation is finished.
        // Ask the main thread to show the done message for delay seconds


    }



    // called when clicking the display button
    public void display(View v) {
        // get the delay value from the editview
        long delay = 1;
        try {
            delay = Integer.parseInt (delayTime.getText ().toString ());
        } catch (Exception e) {
            Log.d (TAG, "invalid number");
        }

        displayText.setText ("execute \n for (i = 0; i <" + delay * 100000000 + "; i++) ;");


        // To do: create a message with the delay value
        // and send the message to mHandlerthread


        Message msg = Message.obtain ();
        Bundle bundle = new Bundle();
        bundle.putLong("delay", delay);
        msg.setData (bundle);

        threadHandler.sendMessage (msg);


    }


    public long longOperation(long threadDelay) {
        Log.d(TAG, " delay "+ threadDelay);
        long i;
        for (i = 0; i < threadDelay * 100000000; i++);
        Log.d(TAG, " delay "+ threadDelay);
        return i;
    }



}


