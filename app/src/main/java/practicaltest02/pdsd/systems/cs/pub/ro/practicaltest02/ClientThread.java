package practicaltest02.pdsd.systems.cs.pub.ro.practicaltest02;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

class ClientThread implements Runnable{

    Socket socket;
    Integer port;
    String url;

    ClientThread(Integer port, String url) {
        this.port = port;
        this.url = url;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(InetAddress.getLocalHost(), port);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            InputStream is = socket.getInputStream();
            Log.e("SERVER", "M-am conectat, trimit " + url);
            out.write(url);
            out.flush();

            String result = "";
            byte[] buffer = new byte[1024];
            int read;
            while((read = is.read(buffer)) != -1) {
                Log.e("SERVER", "Am primit " + buffer);
                result += new String(buffer, 0, read);
            }

            Log.e("SERVER", "Am primit de la server " + result);
            MainActivity.urlBody.setText(result);
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
