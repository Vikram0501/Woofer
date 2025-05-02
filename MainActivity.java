package com.example.woofer;


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


import com.example.woofer.R;

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
    public void login(View v){
        TextView usernametxt = (TextView) findViewById(R.id.Usernametxt);
        TextView passwordtxt = (TextView) findViewById(R.id.Passwordtxt);
        String username = usernametxt.getText().toString();
        String password = DigestUtils.sha256Hex(passwordtxt.getText().toString());
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
                                usernametxt.setText("");
                                passwordtxt.setText("");
                            }
                            else {
                                usernametxt.setText("user found");
                            }
                        }
                    });
                }
            }
        });
    }
}