package com.example.sontbv.wallpaper.Webservices.Responses;

import com.example.sontbv.wallpaper.Models.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sontbv on 12/4/17.
 */

public class PhotosResponse {

    private List<Photo> photos = new ArrayList<>();

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
