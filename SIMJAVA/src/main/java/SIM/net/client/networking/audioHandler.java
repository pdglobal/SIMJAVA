/*
 * Decompiled with CFR 0.139.
 */
package SIM.net.client.networking;


import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class audioHandler
implements HttpHandler {
    public static String audiodata;

    public static void setdata(String imgdta) {
        audiodata = imgdta;
    }

    public void handle(HttpExchange he) throws IOException {
        HashMap parameters = new HashMap();

    }
    
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(9991), 0);
        server.createContext("/audio", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        audioServer.start(new String[3]);
    }
    
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange he) throws IOException {
            URI requestedUri = he.getRequestURI();
            String query = requestedUri.getRawQuery();
            he.sendResponseHeaders(200, audiodata.length());
            OutputStream os = he.getResponseBody();
            os.write(audiodata.toString().getBytes());
            os.close();
        }
    }
}

