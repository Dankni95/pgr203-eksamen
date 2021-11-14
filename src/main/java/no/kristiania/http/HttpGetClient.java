package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class HttpGetClient {
    private final HttpMessage httpMessage;
    private final int statusCode;
    private final String messageBody;
    private final Map<String, String> headerFields;

    public HttpGetClient(String host, int port, String requestTarget) throws IOException {
        Socket socket = new Socket(host, port);

        String request = "GET " + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "Connection: close\r\n" +
                "Referer: http://localhost:8081/survey.html?Test survey name=1\r\n" +
                "Cookie: user=a9f1e83a-f1b8-4e95-a598-5b740c65f907\r\n" +
                "\r\n" +
                "\r\n";
        socket.getOutputStream().write(request.getBytes());

        httpMessage = new HttpMessage(socket);
        String[] statusLine = httpMessage.startLine.split(" ");
        this.statusCode = Integer.parseInt(statusLine[1]);
        this.messageBody = httpMessage.messageBody;
        this.headerFields = httpMessage.headerFields;
    }

    public HttpGetClient(String host, int port, String requestTarget, String cookie) throws IOException {
        Socket socket = new Socket(host, port);

        String request = "GET " + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "Connection: close\r\n" +
                "Cookie: user=" + cookie + "\r\n" +
                "\r\n" +
                "\r\n";
        socket.getOutputStream().write(request.getBytes());

        httpMessage = new HttpMessage(socket);
        String[] statusLine = httpMessage.startLine.split(" ");
        this.statusCode = Integer.parseInt(statusLine[1]);
        this.messageBody = httpMessage.messageBody;
        this.headerFields = httpMessage.headerFields;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
