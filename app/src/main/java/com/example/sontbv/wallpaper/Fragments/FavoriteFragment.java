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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sontbv.wallpaper.Adapters.PhotosAdatper;
import com.example.sontbv.wallpaper.Models.Photo;
import com.example.sontbv.wallpaper.R;
import com.example.sontbv.wallpaper.Utils.RealmController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by sontbv on 12/2/17.
 */

public class FavoriteFragment extends Fragment {
    @BindView(R.id.fragment_favorite_recyclerview)
    RecyclerView photosRecyclerView;
    @BindView(R.id.fragment_favorite_notification)
    TextView notification;

    private PhotosAdatper photosAdatper;
    private List<Photo> photos = new ArrayList<>();
    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        unbinder = ButterKnife.bind(this, view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        photosRecyclerView.setLayoutManager(linearLayoutManager);
        photosAdatper = new PhotosAdatper(getActivity(), photos);
        photosRecyclerView.setAdapter(photosAdatper);
        Log.d("Favorite", "Favorite");
        getPhotos();
        return view;
    }

    private void getPhotos(){
        RealmController realmController = new RealmController();
        photos.addAll(realmController.getPhotos());
        if(photos.size() == 0){
            notification.setVisibility(View.VISIBLE);
            photosRecyclerView.setVisibility(View.GONE);
        }else{
            photosAdatper.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
