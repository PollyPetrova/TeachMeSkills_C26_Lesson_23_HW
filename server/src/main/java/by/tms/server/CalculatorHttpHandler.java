package by.tms.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.by.tms.core.Operation;
import org.by.tms.core.OperationService;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class CalculatorHttpHandler implements HttpHandler {

	private final OperationService service = new OperationService();
	private final Gson gson = new Gson();

	private final InMemoryOperationStorage storage=new InMemoryOperationStorage();

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		InputStream requestBody = exchange.getRequestBody();
		byte[] bytes = requestBody.readAllBytes();

		Operation operation = gson.fromJson(new String(bytes), Operation.class);

		Operation calculate = service.calculate(operation);

		storage.save(calculate);

		String json = gson.toJson(calculate);

		exchange.getResponseHeaders().add("Content-Type", "application/json");
		exchange.sendResponseHeaders(200, json.length());

		PrintWriter printWriter = new PrintWriter(exchange.getResponseBody());
		printWriter.print(json);
		printWriter.close();
	}
}
