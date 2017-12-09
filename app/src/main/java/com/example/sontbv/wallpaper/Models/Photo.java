package com.example.sontbv.wallpaper.Models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sontbv on 12/3/17.
 */

public class Photo extends RealmObject{
    @SerializedName("id")
    @PrimaryKey
    private String id;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("description")
    private String description;
    @SerializedName("likes")
    private int likes;
    @SerializedName("urls")
    private PhotoUrl url = new PhotoUrl();
    @SerializedName("user")
    private User user = new User();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PhotoUrl getUrl() {
        return url;
    }

    public void setUrl(PhotoUrl url) {
        this.url = url;
    }

    public class PhotoUrl {
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
}
