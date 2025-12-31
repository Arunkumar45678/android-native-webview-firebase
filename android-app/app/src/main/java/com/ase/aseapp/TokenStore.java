package com.ase.aseapp;

public class TokenStore {

    private static String idToken;

    public static void setIdToken(String token) {
        idToken = token;
    }

    public static String getIdToken() {
        return idToken;
    }
}
