package com.example.user.opencv_app1.bluetooth.exception;

/**
 * By Siarhei Charkes in 2016
 * http://privateblog.info
 */
public class BluetoothException extends Exception {
    public BluetoothException(Throwable throwable) {
        super(throwable);
    }
    public BluetoothException(String message) {
        super(message);
    }
    public BluetoothException() {
    }
}