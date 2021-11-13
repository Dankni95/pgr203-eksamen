package no.kristiania.controllers;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;


public class ErrorControllerTest implements HttpController {

    @Override
    public HttpMessage handle(HttpMessage request) {

        return new HttpMessage("HTTP/1.1 500 INTERNAL SERVER ERROR", "500 INTERNAL SERVER ERROR");
    }
}
