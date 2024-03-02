package by.tms.client.server;

import by.tms.client.console.ConsoleReader;
import by.tms.client.console.ConsoleWriter;
import com.google.gson.Gson;
import org.by.tms.core.Operation;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClientApplication {

	private static final ConsoleReader consoleReader = new ConsoleReader();
	private static final ConsoleWriter consoleWriter = new ConsoleWriter();

	public static void main(String[] args) {

        Gson gson = new Gson();
        boolean stopCalculating = true;
        while (stopCalculating) {
            consoleWriter.write("Enter num1: ");
            double num1 = consoleReader.readNum();
            consoleWriter.write("Enter num2: ");
            double num2 = consoleReader.readNum();
            consoleWriter.write("Enter operation: ");
            String type = consoleReader.readOperationType();
            Operation operation = new Operation(num1, num2, type);

            String json = gson.toJson(operation);
            HttpRequest httpRequest;
            HttpResponse<String> httpResponse;
            try {
                httpRequest = HttpRequest.newBuilder().
                        uri(new URI("http://localhost:8080/calc")).
                        header("Content-type", "Application/json").
                        POST(HttpRequest.BodyPublishers.ofString(json))
                        .build();
                HttpClient httpClient = HttpClient.newHttpClient();
                httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                Operation operation1 = gson.fromJson(httpResponse.body(), Operation.class);
                consoleWriter.write("Rezult: " + operation1);
                consoleWriter.write("Do you want to keep calculating (yes/no) ?");
                String choice = consoleReader.readNeedToCalculate();
                if (choice.equalsIgnoreCase("no")) {
                    stopCalculating = false;
                }
            } catch (URISyntaxException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
	}
}
