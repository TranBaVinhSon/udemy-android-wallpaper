package com.example.sontbv.wallpaper.Webservices;

import com.example.sontbv.wallpaper.Models.Photo;
import com.example.sontbv.wallpaper.Webservices.Responses.PhotosResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sontbv on 12/4/17.
 */

public interface ApiInterface {

    @GET("photos")
    Call<List<Photo>> getPhotos();

}
