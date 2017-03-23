package com.example.alexispointurier.app4_eolienne;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.UUID;

/**
 * Created by Vivien BLUM on 22/03/2017.
 */

public class ConnectThread extends Thread {
    private static BluetoothSocket mSocket;
    private static BluetoothDevice mDevice;
    private static OutputStream mOutStream;
    //private Handler mHandler;
    public static final java.util.UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private static String address = "00:06:66:72:66:60";    // TODO change with the correct mac adress

    public static void Init(BluetoothAdapter bluetoothAdapter) {

        BluetoothSocket tmp = null;
        OutputStream tmpOut = null;

        mDevice = bluetoothAdapter.getRemoteDevice(address);

        try {
            tmp = mDevice.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {}

        mSocket = tmp;

        try {
            mSocket.connect();
        } catch (IOException e) { }

        try {
            tmpOut = tmp.getOutputStream();
        } catch (IOException e) { }

        mOutStream = tmpOut;
    }

    public static void write(String message) {
        try {
            mOutStream.write(message.getBytes());
        } catch (IOException e) {

            String msg = "An exception occurred during write: " + e.getMessage();
            if (address.equals("00:00:00:00:00:00"))
                msg = msg + ".\n\nUpdate your server address from 00:00:00:00:00:00 to the correct address on line 35 in the java code";
            msg = msg +  ".\n\nCheck that the SPP UUID: " + MY_UUID.toString() + " exists on server.\n\n";
            Log.e("Error", msg);
        }
    }

    public static void sendGrid(int[][] grid) {

        int[] grid1D = new int [grid.length * grid[0].length];

        for (int i = 0; i < grid.length; i ++) {
            for (int j = 0; j < grid[0].length; j ++) {
                grid1D[i * grid[0].length + j] = grid[i][j];
            }
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(grid1D.length * 4);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(grid1D);

        byte[] array = byteBuffer.array();

        try {
            mOutStream.write(array);
        } catch (IOException e) {

            String msg = "An exception occurred during write: " + e.getMessage();
            if (address.equals("00:00:00:00:00:00"))
                msg = msg + ".\n\nUpdate your server address from 00:00:00:00:00:00 to the correct address on line 35 in the java code";
            msg = msg +  ".\n\nCheck that the SPP UUID: " + MY_UUID.toString() + " exists on server.\n\n";
            Log.e("Error", msg);
        }
    }

    public void cancel() {
        try {
            mSocket.close();
        } catch (IOException e) { }
    }
}