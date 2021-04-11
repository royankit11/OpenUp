package com.example.openup;

public class MessageData {

        private String message;
        private String id;

    public MessageData(String message, String id) {
        this.message = message;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }


}
