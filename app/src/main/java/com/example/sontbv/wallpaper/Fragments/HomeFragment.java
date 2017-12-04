package com.example.sontbv.wallpaper.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sontbv.wallpaper.Adapters.PhotosAdatper;
import com.example.sontbv.wallpaper.Models.Photo;
import com.example.sontbv.wallpaper.R;
import com.example.sontbv.wallpaper.Webservices.ApiInterface;
import com.example.sontbv.wallpaper.Webservices.Responses.PhotosResponse;
import com.example.sontbv.wallpaper.Webservices.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sontbv on 12/2/17.
 */

public class HomeFragment extends Fragment {
    private final static String TAG = HomeFragment.class.getSimpleName();
    @BindView(R.id.fragment_home_recyclerview)
    RecyclerView homeRecycerView;

    private PhotosAdatper photosAdatper;
    private List<Photo> photos = new ArrayList<>();

    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        homeRecycerView.setLayoutManager(linearLayoutManager);

        photosAdatper = new PhotosAdatper(getActivity(), photos);
        homeRecycerView.setAdapter(photosAdatper);

        getPhotos();

        return view;
    }

    private void getPhotos(){
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Photo>> call = apiInterface.getPhotos();
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "Loading successfully, size: " + response.body().size());
                    for(Photo photo: response.body()){
                        photos.add(photo);
                        Log.d(TAG, photo.getUrl().getFull());
                    }
                    photosAdatper.notifyDataSetChanged();
                }else{
                    Log.d(TAG, "Fail" + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.d(TAG, "Fail: " + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
