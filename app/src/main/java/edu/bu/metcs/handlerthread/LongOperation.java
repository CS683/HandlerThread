package edu.bu.metcs.handlerthread;

import android.util.Log;

public class LongOperation {
    private boolean stop;


    public LongOperation() {
        this.stop = false;
    }

    public synchronized boolean isStop() {
        return stop;
    }

    public synchronized void setStop(boolean stop) {
        this.stop = stop;
    }

    private static final String TAG = LongOperation.class.getSimpleName ();
    public long run(long threadDelay) {
        setStop(false);
        Log.d(TAG, " delay "+ threadDelay);
        long i;
        for (i = 0; i < threadDelay * 100000000; i++)
            if (isStop()) break;
            /*
            // in this case, another way is to use interrupt()
            if (Thread.interrupted ()) break;
             */
        Log.d(TAG, " delay "+ threadDelay);
        return i;
    }

}
