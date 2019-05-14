package com.android.jadwalsholat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Modul {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("query")
    @Expose
    private Query query;
    @SerializedName("jadwal")
    @Expose
    private Jadwal jadwal;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Jadwal getJadwal() {
        return jadwal;
    }

    public void setJadwal(Jadwal jadwal) {
        this.jadwal = jadwal;
    }
}
