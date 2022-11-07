package com.example.firebase_.model;

public class Message {
    private String sender;
    private String reciver;
    private String content;

    public Message(String sender, String reciver, String content) {
        this.sender = sender;
        this.reciver = reciver;
        this.content = content;
    }

    private Message(){

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
