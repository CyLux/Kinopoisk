package ru.startandroid.kinopoiskapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {

    private TextView goToSingUp;
    private EditText email, password;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();

        goToSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, SignUp.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(SignIn.this, "Поля пустые", Toast.LENGTH_LONG).show();
                }
                else{
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setEmail(email.getText().toString());
                    loginRequest.setPassword(password.getText().toString());

                    SignInUsers(loginRequest);
                }
            }
        });
    }

    public void SignInUsers(LoginRequest loginRequest){
        Call<LoginResponse>loginResponseCall = ApiClient.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    LoginResponse loginResponse = response.body();
                    startActivity(new Intent(SignIn.this, MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(SignIn.this, "Возникла ощибка, повторите позже...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(SignIn.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void init(){
        goToSingUp = findViewById(R.id.tvGoToSignUp);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        signIn = findViewById(R.id.btnSignIn);
    }
}
