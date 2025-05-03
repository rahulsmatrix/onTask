package com.pvsnpexchange.ontask_restful_webservice.payload;

public class BasicAuthenticationBean {

    private String message;

    public BasicAuthenticationBean(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("BasicAuthentication [message=%s]", message);
    }
}
