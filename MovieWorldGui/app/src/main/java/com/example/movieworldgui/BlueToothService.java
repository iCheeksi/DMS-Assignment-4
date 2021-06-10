package com.example.movieworldgui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.example.movieworldgui.ui.ownedticket.placeholder.PlaceholderContent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

public class BlueToothService {

    private UUID appBlueToothID = UUID.fromString("1ccb43a8-34fd-49d6-a8fa-acf1b480c28c");

    public void shareTicket(String item) {
        new Thread(new ServerConnection(item)).start();
    }

    public void getTicket(BluetoothDevice device) {
        new Thread((new ClientConnection(device))).start();
    }

    private class ServerConnection extends Thread {
        private final BluetoothServerSocket serverSocket;
        private final String sentItem;

        public ServerConnection(String item) {

            sentItem = item;
            BluetoothServerSocket temp = null;
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            try {
                temp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("MovieWorldApp", appBlueToothID);
            } catch (IOException e) {
                Log.e(BlueToothService.class.getSimpleName(), "Sorry something went wrong when we were setting up the bluetooth connection", e);
            }
            serverSocket = temp;
        }

        @Override
        public void run() {
            BluetoothSocket socket = null;

            while (true) {
                try {
                    if (serverSocket != null) socket = serverSocket.accept();
                } catch (IOException e) {
                    Log.e(BlueToothService.class.getSimpleName(), "Sorry the other device was unable to connect ", e);
                    break;
                }

                if (socket != null) {
                    sendMessage(socket, sentItem);
                    closeConnection();
                    break;
                }
            }
        }

        public void closeConnection() {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                Log.e(BlueToothService.class.getSimpleName(), "Could not close this connection", e);
            }
        }

        private void sendMessage(BluetoothSocket socket, String item) {

            new Thread(() -> {
                try {
                    ObjectOutputStream clientOutput = new ObjectOutputStream(socket.getOutputStream());
                    clientOutput.writeObject(item);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }

    private class ClientConnection extends Thread {
        private final BluetoothSocket socket;
        private final BluetoothDevice sendingDevice;

        public ClientConnection(BluetoothDevice device) {
            sendingDevice = device;
            BluetoothSocket temp = null;

            try {
                temp = sendingDevice.createRfcommSocketToServiceRecord(appBlueToothID);
            } catch (IOException e) {
                Log.e(BlueToothService.class.getSimpleName(), "Sorry something went wrong when we were setting up the bluetooth connection", e);
            }

            socket = temp;
        }

        @Override
        public void run() {
//            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery();

            try {
                if (socket != null) socket.connect();
            } catch (IOException connException) {
                Log.e(BlueToothService.class.getSimpleName(), "Sorry we can't connect to the other device", connException);
                closeConnection();

                return;
            }

            receiveMessage(socket);
        }

        public void closeConnection() {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
                Log.e(BlueToothService.class.getSimpleName(), "Could not close this connection", e);
            }
        }

        private void receiveMessage(BluetoothSocket socket) {

            new Thread(() -> {
                try {
                    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

                    while (true) {
                        try {
                            Object message = input.readObject();

                            if (message instanceof String) {
                                // TODO - send this to the api
                                int count = PlaceholderContent.ITEMS.size();

                                PlaceholderContent.PlaceholderItem newItem =
                                        new PlaceholderContent.PlaceholderItem(String.valueOf(count), (String) message, "details about " + message);

                                PlaceholderContent.ITEMS.add(newItem);
                                PlaceholderContent.ITEM_MAP.put(newItem.id, newItem);
                            }
                            closeConnection();
                            break;

                        } catch (ClassNotFoundException e) {
                            Log.e(BlueToothService.class.getSimpleName(), "Sorry something went wrong while we were getting the shared ticket", e);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}