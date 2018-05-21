package practicaltest02.pdsd.systems.cs.pub.ro.practicaltest02;

import android.provider.SyncStateContract;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;

public class ServerThead implements Runnable {

    ServerSocket serverSocket;
    Integer port;
    HashMap data = new HashMap<String, String>();

    ServerThead(Integer port) {
        this.port = port;


        try {
            serverSocket = new ServerSocket(); // <-- create an unbound socket first
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(port));
            Log.e("SERVER", "Server started");
        } catch (IOException ioException) {
            Log.e("SERVER", "Exception in server " + ioException);
        }
    }

    @Override
    public void run() {

        try {
            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = serverSocket.accept();
                InputStream is = clientSocket.getInputStream();
                OutputStream os = clientSocket.getOutputStream();
                PrintWriter out = new PrintWriter(os);
                byte[] buffer = new byte[1024];
                int read;
                while((read = is.read(buffer)) != -1) {
                    String output = new String(buffer, 0, read);
                    Log.e("SERVER", "Iau data pentru " + output);

                    String requestResult;
                    if (data.containsKey(output))
                        requestResult = (String)data.get(output);
                    else {
                        requestResult = getInternetData(output);
                        data.put(output, requestResult);
                    }

                    if (requestResult == null)
                        requestResult = "NOT GOOD";
                    out.write(requestResult);
                    out.flush();
                    out.close();
                    break;

                }
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public String getInternetData(String urlSTring) {

        Log.e("SERVER", "Fac cerere http pentru " + urlSTring);
        URL url = null;
        try {
            url = new URL(urlSTring);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String content = "", line;
            while ((line = rd.readLine()) != null) {
                content += line + "\n";
            }
            return content;
        } catch (MalformedURLException e) {
            Log.e("SERVER", "Eroare " + e);
            e.printStackTrace();
        } catch (ProtocolException e) {
            Log.e("SERVER", "Eroare " + e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("SERVER", "Eroare " + e);
            e.printStackTrace();
        }

        return null;

    }
}
