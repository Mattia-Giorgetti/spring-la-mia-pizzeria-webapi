package org.learning.springpizzeria.SpringPizzeria.model;

import java.awt.*;

public class FlashMessage {

    public enum FlashMessageType {
        SUCCESS, ERROR
    }
    private FlashMessageType type;
    private String text;

    public FlashMessage(FlashMessageType type, String text) {
        this.type = type;
        this.text = text;
    }

    public FlashMessage(){}

    public FlashMessageType getType() {
        return type;
    }

    public void setType(FlashMessageType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "FlashMessage{" +
                "type=" + type +
                ", text='" + text + '\'' +
                '}';
    }
}
