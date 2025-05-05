package com.example.testing;

import android.os.Bundle;
import android.view.PixelCopy;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.loginactivity_main);

    }
    //-------Login Button-------//
    public void login(View v){
        TextView usernametxt =  findViewById(R.id.Usernametxt);
        TextView passwordtxt =  findViewById(R.id.Passwordtxt);
        //**do password checks**
        String username = usernametxt.getText().toString();
        String password = DigestUtils.sha256Hex(passwordtxt.getText().toString());
        //**change from _GET to _POST in PHP**
        String url = "https://lamp.ms.wits.ac.za/home/s2798790/login.php?username=" + username + "&password=" + password;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    final String responsebody = response.body().string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (responsebody.equals("ERR: USER NOT FOUND")){
                                //**add error message**
                                usernametxt.setText("");
                                passwordtxt.setText("");
                            }
                            else {
                                usernametxt.setText("user found");//**Placeholder**
                            }
                        }
                    });
                }
            }
        });
    }
    //-------Signup Button-------//
    public void signup(View v){
        TextView usernametxt =  findViewById(R.id.Usernametxt);
        TextView passwordtxt =  findViewById(R.id.Passwordtxt);
        TextView confirmpasstxt = findViewById(R.id.ConfirmPasswordtxt);
        TextView emailtxt = findViewById(R.id.EmailAddresstxt);

        String username = usernametxt.getText().toString();
        String password = DigestUtils.sha256Hex(passwordtxt.getText().toString());
        String confirm = DigestUtils.sha256Hex(confirmpasstxt.getText().toString());
        if (!password.equals(confirm)){
            usernametxt.setText("confirmed wrong");
            passwordtxt.setText("");
            confirmpasstxt.setText("");
            emailtxt.setText("");
            return;
        }

        String email = emailtxt.getText().toString();
        String url = "https://lamp.ms.wits.ac.za/home/s2798790/signup.php?username=" + username + "&password=" + password + "&email=" + email;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    final String responsebody = response.body().string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (responsebody.equals("ERR: USERNAME ALREADY IN USE")){
                                usernametxt.setText("user already exists");//**Placeholder**
                                passwordtxt.setText("");
                                confirmpasstxt.setText("");
                                emailtxt.setText("");
                            }
                            else if(responsebody.equals("ERR: MISSING FIELDS")){
                                usernametxt.setText("missing fields");//**Placeholder**
                                passwordtxt.setText("");
                                confirmpasstxt.setText("");
                                emailtxt.setText("");
                            }
                            else if (responsebody.equals("ERR: FAILED TO REGISTER")){
                                usernametxt.setText("failed to register");//**Placeholder**
                                passwordtxt.setText("");
                                confirmpasstxt.setText("");
                                emailtxt.setText("");
                            }
                            else if (responsebody.equals("SUCCESS: USER REGISTERED")){
                                usernametxt.setText("user added");//**Placeholder**
                                passwordtxt.setText("");
                                confirmpasstxt.setText("");
                                emailtxt.setText("");
                            }
                            else {
                                usernametxt.setText("unknown error");//**Placeholder**
                                passwordtxt.setText("");
                                confirmpasstxt.setText("");
                                emailtxt.setText("");
                            }
                        }
                    });
                }
            }
        });
    }
    //-------Buttons To Move Between Pages-------//
    public void signuppage(View v){
        setContentView(R.layout.signupactivity_main);
    }

    public void loginpage(View v){
        setContentView(R.layout.loginactivity_main);
    }
}