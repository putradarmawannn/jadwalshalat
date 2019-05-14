package com.android.jadwalsholat.api;

import com.android.jadwalsholat.model.Modul;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("kota/{kode}/tanggal/{tgl}")
    Call<Modul> getJadwal(@Path("kode") String kode, @Path("tgl") String tgl);

}
