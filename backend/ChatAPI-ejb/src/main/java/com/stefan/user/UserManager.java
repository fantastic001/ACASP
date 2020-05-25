package com.stefan.user;

import com.stefan.data.User;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ListCellRenderer;

public class UserManager {
    private ArrayList<User> users;
    private ArrayList<LoginListener> loginListeners;
    private ArrayList<User> online;
    private static UserManager instance = null; 
    private UserManager() {
        this.users = new ArrayList<>();
        online = new ArrayList<>();
        loginListeners = new ArrayList<>();
    }

    public static UserManager getInstance() {
        if (UserManager.instance == null) UserManager.instance = new UserManager();
        return UserManager.instance;
    }

    public void registerUser(User user) throws UserExistsException {
        for (User u : this.users) {
            if (u.getUsername().equals(user.getUsername())) throw new UserExistsException();
        }
        System.out.println("Registering user");
        this.users.add(user);
    }

    public void login(User user) throws AuthErrorException {
        for (User currentUser : this.users) {
            if (user.getUsername().equals(currentUser.getUsername()) && user.getPassword().equals(currentUser.getPassword())) {
                System.out.println("Logging user in");
                for (LoginListener listener : this.loginListeners) {
                    listener.userLoggedIn(user);
                }
                // if already logged in ignore it 
                int count = 0;
                for (User u : online) {
                    if (u.getUsername().equals(user.getUsername())) count++;
                }
                if (count == 0) {
                    System.out.println("Adding user to online list");
                    online.add(user);
                }
                return;
            }
        }
        throw new AuthErrorException();
    }

    public void logout(User user) {
        User found = null;
        for (User currentUser : this.users) {
            if (user.getUsername().equals(currentUser.getUsername()) && user.getPassword().equals(currentUser.getPassword())) {
                for (LoginListener listener : this.loginListeners) {
                    listener.userLoggedOut(user);
                }
                // remove it from logged users 
                for (User u : online) {
                    if (u.getUsername().equals(user.getUsername())) {
                        found = u;
                    }
                }
            }
        }
        if (found != null) online.remove(found);
    }

    public Collection<User> getOnlineUsers() {
        return this.online;
    }
    public void addLoginListener(LoginListener listener) {
        this.loginListeners.add(listener);
    }

    public Collection<User> getUsers() {
        return this.users;
    }
}