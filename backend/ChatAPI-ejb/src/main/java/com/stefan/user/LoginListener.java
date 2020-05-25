package com.stefan.user;

import com.stefan.data.User;

public interface LoginListener {
    public void userLoggedIn(User user);
    public void userLoggedOut(User user);
}