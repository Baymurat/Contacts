package com.itechart.contacts.core.utils.error;

public class CustomException extends Exception {
    public CustomException(String massage, Exception e) {
        super(massage, e);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
