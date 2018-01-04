package com.example.rummenigged.daggertest.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rummenigged.daggertest.R;
import com.example.rummenigged.daggertest.domain.LoginUseCase;

/**
 * Created by rummenigged on 04/01/18.
 */

public class LoginActivity extends AppCompatActivity{
    private AutoCompleteTextView usernameActv;
    private EditText passwordEt;
    private TextView errorTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameActv = findViewById(R.id.login_username_actv);
        passwordEt = findViewById(R.id.login_password_et);
        passwordEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login_password_et || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        errorTv = findViewById(R.id.login_error_tv);

        Button usernameSignInButton = findViewById(R.id.login_sign_in_bt);
        usernameSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        errorTv.setVisibility(View.GONE);
        String username = usernameActv.getText().toString();
        String password = passwordEt.getText().toString();

        LoginUseCase uc = new LoginUseCase();
        String token = uc.login(username, password);

        if (token != null) {
            FavoritesActivity.launch(this, token, false);
        } else {
            errorTv.setVisibility(View.VISIBLE);
        }
    }
}
