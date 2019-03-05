package com.example.user.opencv_app1.bluetooth.exception;

/**
 * By Siarhei Charkes in 2016
 * http://privateblog.info
 */
public class ConnectionBluetoothException extends BluetoothException {
    public ConnectionBluetoothException(Throwable throwable) {
        super(throwable);
    }
    public ConnectionBluetoothException(String message) {
        super(message);
    }
}
