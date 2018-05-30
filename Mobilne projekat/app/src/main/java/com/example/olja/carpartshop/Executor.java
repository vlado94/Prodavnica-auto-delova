package com.example.olja.carpartshop;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

/**
 * Created by Olja on 5/28/2018.
 */

public class Executor {

    private static final Object LOCK = new Object();
    private static Executor sInstance;
    private final java.util.concurrent.Executor diskIO;
    private final java.util.concurrent.Executor mainThread;
    private final java.util.concurrent.Executor networkIO;

    private Executor(java.util.concurrent.Executor diskIO, java.util.concurrent.Executor networkIO, java.util.concurrent.Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public static Executor getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new Executor(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return sInstance;
    }

    public java.util.concurrent.Executor diskIO() {
        return diskIO;
    }

    public java.util.concurrent.Executor mainThread() {
        return mainThread;
    }

    public java.util.concurrent.Executor networkIO() {
        return networkIO;
    }

    private static class MainThreadExecutor implements java.util.concurrent.Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
