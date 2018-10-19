package com.itechart.contacts.core.utils.email;

import java.util.List;

public class CustomMessageHolder {
    private List<Integer> receivers;
    private String messageTheme;
    private String messageText;
    private List<String> emailOfReceivers;

    public List<Integer> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<Integer> receivers) {
        this.receivers = receivers;
    }

    public String getMessageTheme() {
        return messageTheme;
    }

    public void setMessageTheme(String messageTheme) {
        this.messageTheme = messageTheme;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public List<String> getEmailOfReceivers() {
        return emailOfReceivers;
    }

    public void setEmailOfReceivers(List<String> emailOfReceivers) {
        this.emailOfReceivers = emailOfReceivers;
    }
}