package com.moshiur.ipinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import com.moshiur.ipinfo.Interface.ApiInterface;
import com.moshiur.ipinfo.Model.ServerResponse;
import com.moshiur.ipinfo.Network.RetrofitApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView ip, city, isp, latitude;
    private ProgressBar progressBar;
    private Group group;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ip = findViewById(R.id.ip);
        city = findViewById(R.id.city);
        isp = findViewById(R.id.isp);
        latitude = findViewById(R.id.latitude);

        progressBar = findViewById(R.id.progressBar);

        group = findViewById(R.id.group);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyIp();
            }
        });

    }

    private void showMyIp() {
        group.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);

        Call<ServerResponse> call = apiInterface.getMyIp();
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                progressBar.setVisibility(View.GONE);
                group.setVisibility(View.VISIBLE);

                ServerResponse serverResponse = response.body();

                if (response.code() == 200 && serverResponse != null) {
                    ip.setText("IP address: " + serverResponse.getIp());
                    city.setText("Location: " + serverResponse.getCity() + ", " + serverResponse.getCountry());
                    isp.setText("ISP: " + serverResponse.getIspProvider());
                    latitude.setText("Latitude & Longitude: " + serverResponse.getLatitude() + " & " + serverResponse.getLongitude());
                } else {
                    ip.setText("There is an error! please try again");
                    city.setText("");
                    isp.setText("");
                    latitude.setText("");
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                group.setVisibility(View.VISIBLE);

                ip.setText("There is an error! please try again");
                city.setText("");
                isp.setText("");
                latitude.setText("");
            }
        });

    }
}
