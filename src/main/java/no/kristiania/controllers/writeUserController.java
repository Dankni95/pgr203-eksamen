package no.kristiania.controllers;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.utils.Cookie;

import java.io.IOException;
import java.sql.SQLException;

public class writeUserController implements HttpController {

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {

        String messageBody;
        String firstName;
        String lastName;

        if (request.headerFields.get("Cookie").isEmpty()) {
            firstName = Cookie.getUser(request.headerFields.get("Cookie").split("=")[1]).getFirstName();
            lastName = Cookie.getUser(request.headerFields.get("Cookie").split("=")[1]).getLastName();
        } else {
            firstName = "Anonymous";
            lastName = "";
        }

        messageBody = firstName + " " + lastName;
        System.out.println(messageBody);

        return new HttpMessage("HTTP/1.1 200 OK", messageBody);
    }
}
