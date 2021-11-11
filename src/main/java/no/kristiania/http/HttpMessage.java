package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpMessage {
    public String startLine;
    public Map<String, String> headerFields = new HashMap<>();
    public String messageBody;

    public HttpMessage(Socket socket) throws IOException {
        startLine = HttpMessage.readLine(socket);
        readHeaders(socket);
        if (headerFields.containsKey("Content-Length")) {
            messageBody = HttpMessage.readBytes(socket, getContentLength());
        }
    }

    public HttpMessage(String startLine, String messageBody) {
        this.startLine = startLine;
        this.messageBody = messageBody;
    }

    public HttpMessage(String startLine, String headerField, String headerValue) {
        this.startLine = startLine;
        this.headerFields.put(headerField, headerValue);
    }

    public HttpMessage(String startLine, String locationName, String locationValue, String cookieName, String cookieValue) {
        this.startLine = startLine;
        this.headerFields.put(locationName, locationValue);
        this.headerFields.put(cookieName, cookieValue);
    }


    public static Map<String, String> parseRequestParameters(String query) {
        Map<String, String> queryMap = new HashMap<>();

        if (query != null && !query.contains("?")) {
            for (String queryParameter : query.split("&")) {
                int equalsPos = queryParameter.indexOf('=');
                String parameterName = queryParameter.substring(0, equalsPos).replaceAll("\\+", " ");
                String parameterValue = queryParameter.substring(equalsPos + 1).replaceAll("\\+", " ");
                queryMap.put(parameterName, parameterValue);

            }
        } else {
            assert query != null;
            String queryParameter = query.split("\\?")[1];
            String queryParameterName = queryParameter.split("=")[0];
            String queryParameterValue = queryParameter.split("=")[1].split("HTTP")[0];


            queryMap.put("title", queryParameterName);
            queryMap.put("id", queryParameterValue);

        }
        return queryMap;
    }



    static String readBytes(Socket socket, int contentLength) throws IOException {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < contentLength; i++) {
            buffer.append((char) socket.getInputStream().read());
        }
        return java.net.URLDecoder.decode(buffer.toString(), StandardCharsets.UTF_8);
    }

    static String readLine(Socket socket) throws IOException {
        StringBuilder buffer = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != '\r') {
            buffer.append((char) c);
        }
        int expectedNewline = socket.getInputStream().read();
        assert expectedNewline == '\n';

        return java.net.URLDecoder.decode(buffer.toString(), StandardCharsets.UTF_8);
    }


    public int getContentLength() {
        return Integer.parseInt(getHeader("Content-Length"));
    }

    public String getHeader(String headerName) {
        return headerFields.get(headerName);
    }

    private void readHeaders(Socket socket) throws IOException {
        String headerLine;
        while (!(headerLine = HttpMessage.readLine(socket)).isBlank()) {
            int colonPos = headerLine.indexOf(':');
            String headerField = headerLine.substring(0, colonPos);
            String headerValue = headerLine.substring(colonPos + 1).trim();
            headerFields.put(headerField, headerValue);
        }
    }

    public void write(Socket clientSocket) throws IOException {
        String response;

        if (headerFields.isEmpty()) {
            response = startLine + "\r\n" +
                    "Content-Length: " + messageBody.length() + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    messageBody;
        } else if (headerFields.containsKey("Set-cookie")) {
            response = "HTTP/1.1 303 See Other" + "\r\n" +
                    "Content-length: 0" + "\r\n" +
                    "Location: " + headerFields.get("Location") + "\r\n" +
                    "Set-cookie: " + headerFields.get("Set-cookie") + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    "\r\n";
        } else {
            response = "HTTP/1.1 303 See Other" + "\r\n" +
                    "Content-length: 0" + "\r\n" +
                    "Location: " + headerFields.get("Location") + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    "\r\n";
        }

        clientSocket.getOutputStream().write(response.getBytes());
    }

}
