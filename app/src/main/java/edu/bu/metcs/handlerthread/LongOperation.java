package edu.bu.metcs.handlerthread;

import android.util.Log;


public class LongOperation  {
    private static String TAG = "Long Operation";

    public static long run(long threadDelay) {
        Log.d(TAG, " delay "+ threadDelay);
        long i;
        for (i = 0; i < threadDelay * 100000000; i++);
        Log.d(TAG, " delay "+ threadDelay);
        return i;
    }


}
