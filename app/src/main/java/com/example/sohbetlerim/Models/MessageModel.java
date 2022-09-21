package com.example.sohbetlerim.Models;

public class MessageModel {
    String  text, time, type,from;
    Boolean seen;
    public MessageModel(){

    }
    public MessageModel(String from,Boolean seen,String text,String time,String type) {

        this.text = text;
        this.time = time;
        this.type = type;
        this.seen = seen;
        this.from=from;
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    public Boolean getSeen() { return seen; }
    public void setSeen(Boolean seen) { this.seen = seen; }
}
