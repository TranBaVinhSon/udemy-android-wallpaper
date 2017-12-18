package com.example.sontbv.wallpaper.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.sontbv.wallpaper.Adapters.CollectionsAdapter;
import com.example.sontbv.wallpaper.Models.Collection;
import com.example.sontbv.wallpaper.R;
import com.example.sontbv.wallpaper.Utils.Functions;
import com.example.sontbv.wallpaper.Webservices.ApiInterface;
import com.example.sontbv.wallpaper.Webservices.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sontbv on 12/2/17.
 */

public class CollectionsFragment extends Fragment {
    private final String TAG = CollectionsFragment.class.getSimpleName();
    @BindView(R.id.fragment_collections_gridview)
    GridView gridView;
    @BindView(R.id.fragment_collections_processbar)
    ProgressBar progressBar;
    private Unbinder unbinder;

    private List<Collection> collections = new ArrayList<>();
    private CollectionsAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collections, container, false);
        unbinder = ButterKnife.bind(this, view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        adapter = new CollectionsAdapter(getActivity(), collections);
        gridView.setAdapter(adapter);
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Collection collection = collections.get(i);
//                Log.d(TAG, collection.getId() + "");
//                Bundle bundle = new Bundle();
//                bundle.putInt("collectionId", collection.getId());
//                CollectionFragment collectionFragment = new CollectionFragment();
//                collectionFragment.setArguments(bundle);
//                Functions.changeMainFragment(getActivity(), collectionFragment);
//            }
//        });

        getCollections();
        showProgressBar(true);
        return view;
    }

    @OnItemClick(R.id.fragment_collections_gridview)
    public void onItemClick(int position){
        Collection collection = collections.get(position);
        Log.d(TAG, collection.getId() + "");
        Bundle bundle = new Bundle();
        bundle.putInt("collectionId", collection.getId());
        CollectionFragment collectionFragment = new CollectionFragment();
        collectionFragment.setArguments(bundle);
        Functions.changeMainFragmentWithBack(getActivity(), collectionFragment);
    }

    private void getCollections(){
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Collection>> call = apiInterface.getCollections();
        call.enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if(response.isSuccessful()){
                    for(Collection collection: response.body()){
                        collections.add(collection);
                    }
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "size " + collections.size());
                }else{
                    Log.d(TAG, "fail " + response.message());
                }
                showProgressBar(false);
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                showProgressBar(false);
            }
        });
    }
    private void showProgressBar(boolean isShow){
        if(isShow){
            progressBar.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

