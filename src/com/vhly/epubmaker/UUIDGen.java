package com.vhly.epubmaker;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 13-12-3
 * Email: vhly@163.com
 */
public class UUIDGen {
    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        String ss = uuid.toString();
        ss = ss.replaceAll("-", "");
        System.out.println("UUID: " + ss);
    }
}
