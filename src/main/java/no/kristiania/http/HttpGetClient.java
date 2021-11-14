package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;

public class HttpGetClient {
    private final HttpMessage httpMessage;
    private final int statusCode;

    public HttpGetClient(String host, int port, String requestTarget) throws IOException {
        Socket socket = new Socket(host, port);

        String request = "GET " + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "Connection: close\r\n" +
                "Content-Length: " + 0 + "\r\n" +
                "\r\n" +
                "\r\n";
        socket.getOutputStream().write(request.getBytes());

        httpMessage = new HttpMessage(socket);
        String[] statusLine = httpMessage.startLine.split(" ");
        this.statusCode = Integer.parseInt(statusLine[1]);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
