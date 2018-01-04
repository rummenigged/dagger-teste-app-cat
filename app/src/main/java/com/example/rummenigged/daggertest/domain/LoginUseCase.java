package com.example.rummenigged.daggertest.domain;

import android.util.Log;

import com.example.rummenigged.daggertest.service.LoginService;

/**
 * Created by rummenigged on 04/01/18.
 */

public class LoginUseCase {
    /**
     * @param username
     * @param password
     * @return A unique identifiable token representing user associated to the given username.
     * Will return null if username or password are invalid.
     */
    public String login(String username, String password) {
        Log.d("LoginUseCase", "Login: " + username + ", " + password);
        try {
            LoginService service = new LoginService();
            String token = service.login(username, password);
            Log.d("LoginUseCase", "Login token: " + token);
            return token;
        } catch (Exception e) {
            Log.e("LoginUseCase", "Login failed");
            return null;
        }
    }
}
