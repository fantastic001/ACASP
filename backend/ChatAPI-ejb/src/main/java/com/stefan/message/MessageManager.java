package com.stefan.message;

import com.stefan.data.Message;
import com.stefan.data.User;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import javax.ejb.EJB;

import com.stefan.cluster.Control;
import com.stefan.cluster.Node;

import com.stefan.data.User;
import com.stefan.user.UserManager;

public class MessageManager {


    private static MessageManager instance = null; 
    public static MessageManager getInstance() {
        if (instance == null) instance = new MessageManager();
        return instance;
    }

    private ArrayList<Message> messages;
    private MessageManager() {
        this.messages = new ArrayList<>();
    }

    public ArrayList<Message> getInbox(User user) {
        ArrayList<Message> result = new ArrayList<>();
        for (Message msg : this.messages) {
            if (msg.getToUsername().equals(user.getUsername())) {
                result.add(msg);
            }
        }
        return result;
    }

    public ArrayList<Message> getOutbox(User user) {
        ArrayList<Message> result = new ArrayList<>();
        for (Message msg : this.messages) {
            if (msg.getFromUsername().equals(user.getUsername())) {
                result.add(msg);
            }
        }
        return result;
    }

    public Collection<Message> getMessages(User user) {
        Collection<Message> union = getInbox(user);
        union.addAll(getOutbox(user));
        ArrayList<Message> result = new ArrayList<>(union);
        Collections.sort(result, (c1, c2) -> c1.getCreationTime().compareTo(c2.getCreationTime()));
        return result;
    }

    public Message createMessage(String from, String to, String subject, String text) {
        Message msg = new Message(from, to, LocalDateTime.now(), subject, text);
        this.messages.add(msg);
        return msg;
    }

    public Message createMessage(Message msg) {
        return createMessage(
            msg.getFromUsername(), 
            msg.getToUsername(), 
            msg.getSubject(), msg.getText()
        );
    }



    public void broadcastMessage(String from, String subject, String text, Collection<User> users) {
        for (User user : users) {
            if (! user.getUsername().equals(from))
                this.createMessage(from, user.getUsername(), subject, text);
        }
    }
    public void broadcastMessage(Message message, Collection<User> users) {
        this.broadcastMessage(message.getFromUsername(), message.getSubject(), message.getText(), users);
    }
}