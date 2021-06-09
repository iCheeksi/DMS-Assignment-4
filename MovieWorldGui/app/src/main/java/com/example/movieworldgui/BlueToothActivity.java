package com.example.movieworldgui;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class BlueToothActivity extends AppCompatActivity {

    private UUID appBlueToothID = UUID.fromString("1ccb43a8-34fd-49d6-a8fa-acf1b480c28c");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

        Intent serveBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        serveBluetooth.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,300);
        startActivityForResult(serveBluetooth,1);

        new Thread(new ServerConnection()).start();
    }

    private class ServerConnection extends Thread {
        private final BluetoothServerSocket serverSocket;

        public ServerConnection() {

            BluetoothServerSocket temp = null;
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            try {
                temp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("MovieWorldApp", appBlueToothID);
            } catch (IOException e) {
                Log.e(BlueToothActivity.class.getSimpleName(), "Socket's listen() method failed", e);
            }
            serverSocket = temp;
        }

        public void run() {
            BluetoothSocket socket = null;

            while (true) {
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    Log.e(BlueToothActivity.class.getSimpleName(), "Client connection to server failed", e);
                    break;
                }

                if (socket != null) {
                    connectToClient(socket);

                    this.closeConnection();
                    break;
                }
            }
        }

        private void connectToClient(BluetoothSocket socket) {

        }

        public void closeConnection() {
            try {
                serverSocket.close();
            } catch (IOException e) {
                Log.e(BlueToothActivity.class.getSimpleName(), "Could not close the server socket", e);
            }
        }
    }
}