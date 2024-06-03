package http_ch01;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SimpleHttpServer {

	public static void main(String[] args) {

		try {
			HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);

			httpServer.createContext("/test", new MyTestHandler());
			httpServer.createContext("/hello", new HelloHandler());

			httpServer.start();
			System.out.println(">> My Http Server started on port 8080");
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // end of main

	// http://localhost:8080/test
	static class MyTestHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange exchange) throws IOException {

			String method = exchange.getRequestMethod();
			System.out.println("method : " + method);

			if ("GET".equalsIgnoreCase(method)) {
				handleGetRequest(exchange);
			} else if ("POST".equalsIgnoreCase(method)) {
				handleGetRequest(exchange);
			} else {
				String response = "Unsupported Method : " + method;
				exchange.sendResponseHeaders(405, response.length());

				OutputStream os = exchange.getResponseBody();
				os.write(response.getBytes());
				os.flush();
				os.close();
			}
		}

		private void handleGetRequest(HttpExchange exchange) throws IOException {
			String response = """
					<!DOCTYPE html>
					<html lang=ko>
							<head></head>
							<body>
								<h1 style="background-color:red"> Hello path by /test </h1>
							<body>
						</html>
					""";
			exchange.sendResponseHeaders(200, response.length());
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		} // end of handleGetRequest

		private void handlePostRequest(HttpExchange exchange) throws IOException {
			String response = """
					<!DOCTYPE html>
					<html lang=ko>
							<head></head>
							<body>
								<h1 style="background-color:red"> Hello path by /test </h1>
							<body>
						</html>
					""";

			exchange.setAttribute("Content-Type", "text/html; charset=UTF-8");
			exchange.sendResponseHeaders(200, response.length());

			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.flush();
			os.close();
		} // end of handlePostRequest
		
	} // end of MyTestHandler

	static class HelloHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange exchange) throws IOException {

			String method = exchange.getRequestMethod();
			System.out.println("heool method : " + method);
		}

	} // end of HelloHandler

} // end of class
