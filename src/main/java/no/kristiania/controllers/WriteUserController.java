package no.kristiania.controllers;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.utils.Cookie;

import java.io.IOException;
import java.sql.SQLException;

public class WriteUserController implements HttpController {

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {

        String messageBody;
        String firstName;
        String lastName;


        if (Cookie.getUser(request.headerFields.get("Cookie").split("=")[1].split(";")[0]) != null) {

            // Intellij keeps adding default cookie?

            firstName = Cookie.getUser(request.headerFields.get("Cookie").split("=")[1].split(";")[0]).getFirstName();
            lastName = Cookie.getUser(request.headerFields.get("Cookie").split("=")[1].split(";")[0]).getLastName();

        } else {
            firstName = "Anonymous";
            lastName = "";
        }

        messageBody = firstName + " " + lastName;

        return new HttpMessage("HTTP/1.1 200 OK", messageBody);
    }
}
