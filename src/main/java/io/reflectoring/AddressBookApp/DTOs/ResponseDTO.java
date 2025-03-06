package io.reflectoring.AddressBookApp.DTOs;

import lombok.*;

public class ResponseDTO {
    private String message;
    private Object data; // Instead of generics, using Object type

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public ResponseDTO() {
    }

    public ResponseDTO(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
