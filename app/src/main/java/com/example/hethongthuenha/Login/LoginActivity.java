package com.example.hethongthuenha.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hethongthuenha.R;
import com.example.hethongthuenha.Register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    private EditText etEmail, etPassword;
    private Button btnLogin,btnRegister;
    private LoginPresenter loginPresenter;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        etEmail = findViewById(R.id.loginEmail);
        etPassword = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);
        loginPresenter = new LoginPresenter(this);



        btnLogin.setOnClickListener(v -> HandlingLogin());
        btnRegister.setOnClickListener(v->startActivity(new Intent(LoginActivity.this,RegisterActivity.class)));
    }

    private void HandlingLogin() {
        String email= etEmail.getText().toString();
        String password= etPassword.getText().toString();
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            loginPresenter.LoginWithEmail(email, password);
        }
    }

    @Override
    public void loginSuccess() {
        progressDialog.dismiss();
        //progressBarLogin.setVisibility(android.view.View.INVISIBLE);
    }

    @Override
    public void loginFail(String error) {
        progressDialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        //progressBarLogin.setVisibility(android.view.View.INVISIBLE);
    }

    @Override
    public void loginPeding() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Đang chờ xử lý");
        progressDialog.show();
    }

}