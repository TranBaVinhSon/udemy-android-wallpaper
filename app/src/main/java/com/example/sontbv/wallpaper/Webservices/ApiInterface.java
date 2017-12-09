package com.example.sontbv.wallpaper.Webservices;

import com.example.sontbv.wallpaper.Models.Collection;
import com.example.sontbv.wallpaper.Models.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sontbv on 12/4/17.
 */

public interface ApiInterface {

    @GET("photos")
    Call<List<Photo>> getPhotos();

    @GET("collections/featured")
    Call<List<Collection>> getCollections();

    @GET("collections/{id}")
    Call<Collection> getInformationOfCollection(@Path("id") int id);

    @GET("collections/{id}/photos")
    Call<List<Photo>> getPhotosOfCollection(@Path("id") int id);

    @GET("photos/{id}")
    Call<Photo> getPhoto(@Path("id") String id);

}
