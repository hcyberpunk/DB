package service;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.util.logging.Logger;

public class SignalService {
    private final Algo algo;

    public SignalService(Algo algo) {
        this.algo = algo;
    }
    private static final Logger logger = Logger.getLogger(SignalService.class.getName());


    public void start(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/signal", new SignalHandler());
        server.start();
    }
    private class SignalHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                String signal = new String(exchange.getRequestBody().readAllBytes());
                processSignal(signal);
                exchange.sendResponseHeaders(200, 0);
                logger.info("test");
            } else {
                exchange.sendResponseHeaders(405, 0); // Method Not Allowed
            }
            exchange.close();
        }
    }

    private void processSignal(String signal) {
        // Parse the signal and perform the corresponding action
        if (signal.equals("doAlgo")) {
            algo.doAlgo();
        } else if (signal.equals("cancelTrades")) {
            algo.cancelTrades();
        } else if (signal.equals("reverse")) {
            algo.reverse();
        } else if (signal.equals("submitToMarket")) {
            algo.submitToMarket();
        } else if (signal.equals("performCalc")) {
            algo.performCalc();
        } else if (signal.startsWith("setAlgoParam")) {
            String[] parts = signal.split(",");
            if (parts.length == 3) {
                int param = Integer.parseInt(parts[1]);
                int value = Integer.parseInt(parts[2]);
                algo.setAlgoParam(param, value);
            }
        }
    }
}
