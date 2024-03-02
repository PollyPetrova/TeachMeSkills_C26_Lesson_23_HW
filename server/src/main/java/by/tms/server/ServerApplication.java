package by.tms.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServerApplication {

    public static void main(String[] args) throws IOException {
        HttpServer httpServer=
                HttpServer.create(new InetSocketAddress("localhost",8080),0);
        httpServer.createContext("/calc", new CalculatorHttpHandler());
        httpServer.createContext("/history", new HistoryHttpHandler());
        httpServer.setExecutor(Executors.newFixedThreadPool(10));
        httpServer.start();
    }

}
