package no.kristiania.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HttpClientTest {
    @BeforeAll
    static void init() throws IOException {
        HttpServer server = new HttpServer(8080);
    }

    @Test
    void shouldReturnStatusCode() throws IOException {
        assertEquals(200,
                new HttpClient("httpbin.org", 80, "/html")
                        .getStatusCode());
        assertEquals(404,
                new HttpClient("httpbin.org", 80, "/no-such-page")
                        .getStatusCode());
    }

    @Test
    void shouldReturnHeaders() throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");
        assertEquals("text/html; charset=utf-8", client.getHeader("Content-Type"));
    }

    @Test
    void shouldReadBody() throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");
        assertTrue(client.getMessageBody().startsWith("<!DOCTYPE html>"),
                "Expected HTML: " + client.getMessageBody());
    }

    @Test
    void shouldGetSuccessfulResponseCode() throws IOException {
        HttpClient client = new HttpClient("localhost", 8080, "/");
        assertEquals(200, client.getStatusCode());
    }


    @Test
    void shouldReadResponseHeaders() throws IOException {
        HttpClient client = new HttpClient("localhost", 8080, "/");
        assertEquals("text/html", client.getHeader("Content-Type"));
    }

    @Test
    void get404Error() throws IOException {
        HttpClient client = new HttpClient("localhost", 8080, "/nothing-here");
        assertEquals(404, client.getStatusCode());
    }

    @Test
    void shouldReadResponseBodyContentLength() throws IOException {
        HttpClient client = new HttpClient("localhost", 8080, "/");
        assertEquals(1795, client.getContentLength());
    }

    @Test
    void shouldReadCssResponseHeadersCorrectly() throws IOException {
        HttpClient client = new HttpClient("localhost", 8080, "/style.css");
        assertEquals("text/css", client.getHeader("Content-Type"));
    }

}
