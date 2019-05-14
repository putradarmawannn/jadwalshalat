package com.android.jadwalsholat;

import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.jadwalsholat.api.ApiService;
import com.android.jadwalsholat.api.ApiUrl;
import com.android.jadwalsholat.model.Modul;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    SimpleAdapter adapter;

    private EditText txt_tanggal;
    private ProgressDialog progressDialog;


    HashMap<String, String> map;
    ArrayList<HashMap<String, String>> mylist;
    String[] tanggal;
    String[] subuh; //deklarasi judul iem
    String[] dzuhur; //deklarasi keterangan item
    String[] ashar; //deklarasi image item
    String[] maghrib; //deklarasi image item
    String[] isya; //deklarasi image item


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Jadwal Sholat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab_refresh = findViewById(R.id.fab_refresh);
        txt_tanggal = findViewById(R.id.txt_kode);

        listView = (ListView)findViewById(R.id.ListView);


        getJadwal();


        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJadwal();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getJadwal () {

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please Wait / Silahkan tunggu ...");
        progressDialog.show();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final String kode = txt_tanggal.getText().toString();
        final Date current = new Date();
        SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
        final String tgl = frmt.format(current);

        ApiService apiService = retrofit.create(ApiService.class);
        Call<Modul> call = apiService.getJadwal(kode,tgl);

        call.enqueue(new Callback<Modul>() {
            @Override
            public void onResponse(Call<Modul> call, Response<Modul> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    tanggal = new String[] {
                            response.body().getQuery().getTanggal()
                    };
                    subuh = new String[] {
                            response.body().getJadwal().getData().getSubuh()
                    };
                    dzuhur = new String[]{
                            response.body().getJadwal().getData().getDzuhur()
                    };
                    ashar = new String[]{
                            response.body().getJadwal().getData().getAshar()
                    };
                    maghrib = new String[]{
                            response.body().getJadwal().getData().getMaghrib()
                    };
                    isya = new String[]{
                            response.body().getJadwal().getData().getIsya()
                    };
                    mylist = new ArrayList<HashMap<String, String>>();

                    for (int i=0; i<tanggal.length; i++){
                        map = new HashMap<String, String>();
                        map.put("Tanggal", tanggal[i]);
                        map.put("Subuh", subuh[i]);
                        map.put("Dzuhur", dzuhur[i]);
                        map.put("Ashar", ashar[i]);
                        map.put("Maghrib", maghrib[i]);
                        map.put("Isya", isya[i]);
                        mylist.add(map);
                    }
                    adapter = new SimpleAdapter(MainActivity.this, mylist, R.layout.list_item,
                            new String[]{"Tanggal", "Subuh", "Dzuhur", "Ashar", "Maghrib", "Isya"}, new int[]{R.id.tv_lokasi_value,(R.id.tv_fajr_value),(R.id.tv_dhuhr_value),(R.id.tv_asr_value),(R.id.tv_maghrib_value),(R.id.tv_isha_value)});
                    listView.setAdapter(adapter);

                } else {

                }
            }

            @Override
            public void onFailure(Call<Modul> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Sorry, please try again... server Down..", Toast.LENGTH_SHORT).show();
                Log.d("Gagal","Error : " + t);
            }
        });

    }
}
