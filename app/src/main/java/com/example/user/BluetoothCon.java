package com.example.user.opencv_app1;

import android.app.Application;
import android.bluetooth.BluetoothSocket;

public class BluetoothCon extends Application {

    private static BluetoothCon sInstance;

    public static BluetoothCon getApplication() {
        return sInstance;
    }

    BluetoothSocket btSocket = null;

    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }

    public void setupBluetoothConnection()
    {

    }

    public BluetoothSocket getCurrentBluetoothConnection () {
        return btSocket;
    }
}
