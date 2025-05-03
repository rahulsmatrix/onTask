package com.pvsnpexchange.ontask_restful_webservice.payload;

public class JwtTokenResponse {

    private String Token;

    public JwtTokenResponse(String token) {
        Token = token;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
