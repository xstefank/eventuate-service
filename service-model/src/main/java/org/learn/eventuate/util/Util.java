package org.learn.eventuate.util;

import java.util.UUID;

public class Util {

    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
