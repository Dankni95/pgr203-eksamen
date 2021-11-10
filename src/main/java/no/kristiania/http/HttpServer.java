package no.kristiania.http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {

    private final ServerSocket serverSocket;
    private final Map<String, HttpController> controllers = new HashMap<>();
    private Socket clientSocket;
    private HttpMessage httpMessage;

    public HttpServer(int serverPort) throws IOException {
        serverSocket = new ServerSocket(serverPort);


        new Thread(this::handleClients).start();
    }

    private void handleClients() {

        try {
            while (true) {
                handleClient();
            }
        } catch (Exception e) {
            new Thread(this::handleError).start();
            new Thread(this::handleClients).start();
            e.printStackTrace();
        }

    }

    private void handleError() {
        try {
            HttpMessage response = controllers.get("/error").handle(httpMessage);
            response.write(clientSocket);
        } catch (Exception ignored) {
        }
    }


    private void handleClient() throws IOException, SQLException {
        clientSocket = serverSocket.accept();
        httpMessage = new HttpMessage(clientSocket);
        String[] requestLine = httpMessage.startLine.split(" ");
        String requestTarget = requestLine[1];


        int questionPos = requestTarget.indexOf('?');
        String fileTarget;
        String query = null;
        if (questionPos != -1) {
            fileTarget = requestTarget.substring(0, questionPos);
            query = requestTarget.substring(questionPos + 1);
        } else {
            fileTarget = requestTarget;
        }

        if (httpMessage.startLine.startsWith("GET")){
            if (controllers.containsKey("GET "+fileTarget)) {
                HttpMessage response = controllers.get("GET "+fileTarget).handle(httpMessage);
                response.write(clientSocket);
                return;
            }
        }else if (httpMessage.startLine.startsWith("POST")){
            if (controllers.containsKey("POST "+fileTarget)) {
                HttpMessage response = controllers.get("POST "+fileTarget).handle(httpMessage);
                response.write(clientSocket);
                return;
            }
        }


        if (controllers.containsKey(fileTarget)) {
            HttpMessage response = controllers.get(fileTarget).handle(httpMessage);
            response.write(clientSocket);
            return;
        }


        if (fileTarget.equals("/")) fileTarget = "/index.html";

        InputStream fileResource = getClass().getResourceAsStream(fileTarget);


        if (fileResource != null) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            fileResource.transferTo(buffer);
            String responseText = buffer.toString();

            String contentType = "text/plain";
            if (fileTarget.endsWith(".html") || fileTarget.equals("/index.html")) {
                contentType = "text/html";
            } else if (fileTarget.endsWith(".css")) {
                contentType = "text/css";
            } else if (fileTarget.endsWith(".ico")) contentType = "image/webp";

            writeOkResponse(clientSocket, responseText, contentType);
            return;
        }

        String responseText = "File not found: " + requestTarget;

        String response = "HTTP/1.1 404 Not found\r\n" +
                "Content-Length: " + responseText.length() + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                responseText;
        clientSocket.getOutputStream().write(response.getBytes());
    }

    private void writeOkResponse(Socket clientSocket, String responseText, String contentType) throws IOException {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: " + responseText.length() + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                responseText;
        clientSocket.getOutputStream().write(response.getBytes());
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void addController(String path, HttpController controller) {
        this.controllers.put(path, controller);
    }
}
