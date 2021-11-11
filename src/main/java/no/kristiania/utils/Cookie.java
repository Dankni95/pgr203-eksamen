package no.kristiania.utils;

import no.kristiania.entity.User;

import java.util.HashMap;
import java.util.UUID;

public class Cookie {
    public static HashMap<String,User> users = new HashMap();

    public static void setCookie(User user){
       user.setCookie(String.valueOf(UUID.randomUUID()));
       users.put(user.getCookie(),user);
    }

    public static User getUser(String cookie) {
        return users.get(cookie);
    }
}
