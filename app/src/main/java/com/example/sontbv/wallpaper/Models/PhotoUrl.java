package com.example.sontbv.wallpaper.Models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by sontbv on 12/9/17.
 */
public class PhotoUrl extends RealmObject {
    @SerializedName("raw")
    private String raw;
    @SerializedName("full")
    private String full;
    @SerializedName("regular")
    private String regular;

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }
}