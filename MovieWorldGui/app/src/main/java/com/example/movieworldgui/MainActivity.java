package com.example.movieworldgui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.movieworldgui.databinding.ActivityMainBinding;
import com.example.movieworldgui.ui.ownedticket.placeholder.PlaceholderTickets;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private UUID appBlueToothID = UUID.fromString("1ccb43a8-34fd-49d6-a8fa-acf1b480c28c");

    public void shareTicket(PlaceholderTickets.TicketItem item) {
        new Thread(new ServerConnection(item)).start();
    }

    public void getTicket(BluetoothDevice device) {
        new Thread((new ClientConnection(device))).start();
    }

    public void sendToastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void sendToastFromChildThread(String message){
        Looper.prepare();
        sendToastMessage(message);
        Looper.loop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            sendToastFromChildThread("Your device has no bluetooth support");
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 1);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            sendToastFromChildThread("Please accept the request to use the app");
        }
    }

    private class ServerConnection extends Thread {
        private final BluetoothServerSocket serverSocket;
        private final PlaceholderTickets.TicketItem sentItem;

        public ServerConnection(PlaceholderTickets.TicketItem item) {

            sentItem = item;
            BluetoothServerSocket temp = null;
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            try {
                temp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("MovieWorldApp", appBlueToothID);
            } catch (IOException e) {
                Log.e(MainActivity.class.getSimpleName(), "Sorry something went wrong when we were setting up the bluetooth connection", e);
                sendToastFromChildThread("Sorry something went wrong when we were setting up the bluetooth connection");
            }
            serverSocket = temp;
        }

        @Override
        public void run() {

            while (true) {
                BluetoothSocket socket = null;
                try {
                    if (serverSocket != null) {
                        socket = serverSocket.accept();
                    }

                    if (socket != null) {
                        sendMessage(socket, sentItem);
                        closeConnection();
                        break;
                    }
                } catch (IOException e) {
                    Log.e(MainActivity.class.getSimpleName(), "Sorry the other device was unable to connect ", e);
                    sendToastFromChildThread("Sorry the other device was unable to connect");
                    closeConnection();
                    break;
                }
            }
        }

        public void closeConnection() {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                Log.e(MainActivity.class.getSimpleName(), "Could not close this connection", e);
            }
        }

        private void sendMessage(BluetoothSocket socket, PlaceholderTickets.TicketItem item) {

            new Thread(() -> {
                try {
                    ObjectOutputStream clientOutput = new ObjectOutputStream(socket.getOutputStream());
                    clientOutput.writeObject(item);
                    sendToastFromChildThread("Ticket sent successfully.");


                } catch (IOException e) {
                    e.printStackTrace();
                    sendToastFromChildThread("Sorry we weren't able to send the ticket this time.");
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
                Log.e(MainActivity.class.getSimpleName(), "Sorry something went wrong when we were setting up the bluetooth connection", e);
                sendToastFromChildThread("Sorry something went wrong when we were setting up the bluetooth connection");
            }

            socket = temp;
        }

        @Override
        public void run() {
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery();

            try {
                if (socket != null) socket.connect();
            } catch (IOException connException) {
                Log.e(MainActivity.class.getSimpleName(), "Sorry we can't connect to the other device", connException);
                sendToastFromChildThread("Sorry we can't connect to the other device");
                closeConnection();

                return;
            }

            receiveMessage(socket);
        }

        public void closeConnection() {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
                Log.e(MainActivity.class.getSimpleName(), "Could not close this connection", e);
            }
        }

        private void receiveMessage(BluetoothSocket socket) {

            new Thread(() -> {
                try {
                    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

                    while (true) {
                        try {
                            Object message = input.readObject();

                            if (message instanceof PlaceholderTickets.TicketItem) {
                                // TODO - send this to the api
                                PlaceholderTickets.TicketItem temp = (PlaceholderTickets.TicketItem) message;

                                PlaceholderTickets.TicketItem newTicket =
                                        new PlaceholderTickets.TicketItem(UUID.randomUUID().toString(), temp.content, temp.details);
                                PlaceholderTickets.addItem(newTicket);

                                sendToastFromChildThread("Shared ticket received successfully.");
                            }
                            closeConnection();
                            break;

                        } catch (ClassNotFoundException e) {
                            Log.e(MainActivity.class.getSimpleName(), "Sorry something went wrong while we were getting the shared ticket", e);
                            sendToastFromChildThread("Sorry we can't get the shared ticket this time.");
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    closeConnection();
                }
            }).start();
        }
    }
}