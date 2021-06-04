package ru.senina.itmo.lab8;

import org.apache.commons.codec.digest.DigestUtils;

public class Encryptor {
    public static String encrypt(String password) {
        String salt = "klj;kjgsdkj";
        return DigestUtils.md5Hex(salt + password);
    }
}

