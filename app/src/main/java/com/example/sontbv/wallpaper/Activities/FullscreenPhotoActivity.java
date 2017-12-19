package com.example.sontbv.wallpaper.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
//import com.example.sontbv.wallpaper.Adapters.GlideApp;
import com.example.sontbv.wallpaper.Adapters.GlideApp;
import com.example.sontbv.wallpaper.Models.Photo;
import com.example.sontbv.wallpaper.R;
import com.example.sontbv.wallpaper.Utils.Functions;
import com.example.sontbv.wallpaper.Utils.RealmController;
import com.example.sontbv.wallpaper.Webservices.ApiInterface;
import com.example.sontbv.wallpaper.Webservices.ServiceGenerator;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullscreenPhotoActivity extends AppCompatActivity {
    private final String TAG = FullscreenPhotoActivity.class.getSimpleName();
    @BindView(R.id.activity_fullscreen_photo_username)
    TextView username;
    @BindView(R.id.activity_fullscreen_photo_user_avatar)
    CircleImageView userAvatar;
    @BindView(R.id.activity_fullscreen_photo_photo)
    ImageView fullscreenPhoto;
    @BindView(R.id.activity_fullscreen_photo_fab_menu)
    FloatingActionMenu fabMenu;
    @BindView(R.id.activity_fullscreen_photo_fab_set_wallpaper)
    FloatingActionButton fabWallpaper;
    @BindView(R.id.activity_fullscreen_photo_fab_favorite)
    FloatingActionButton fabFavorite;

    private Unbinder unbinder;
    private Bitmap photoBitmap;
    private RealmController realmController;
    private Photo photo = new Photo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_photo);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Get Information of Photo
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        String photoId = intent.getStringExtra("photoId");
        getPhoto(photoId);

        realmController = new RealmController();
        if(realmController.isPhotoExist(photoId)){
            fabFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_favorited));
        }

    }

    private void getPhoto(String id){
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<Photo> call = apiInterface.getPhoto(id);
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "success");
                    photo = response.body();
                    updateUI(photo);
                }else{
                    Log.e(TAG, response.message());
                }
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void updateUI(final Photo photo){
        // Make sure that, if we have some errors here, our application will not crash
        try{
            username.setText(photo.getUser().getUsername());
            GlideApp.with(FullscreenPhotoActivity.this)
                    .load(photo.getUser().getProfileImage().getSmall())
                    .into(userAvatar);

            GlideApp
                    .with(FullscreenPhotoActivity.this)
                    .asBitmap()
                    .load(photo.getUrl().getRegular())
                    .centerCrop()
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            fullscreenPhoto.setImageBitmap(resource);
                            photoBitmap = resource;
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @OnClick(R.id.activity_fullscreen_photo_fab_set_wallpaper)
    public void setFabWallpaper(){
        if(photoBitmap != null){
            if(Functions.setWallpaper(FullscreenPhotoActivity.this, photoBitmap)){
                Toast.makeText(this, "Set Wallpaper successfully", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Set Wallpaper fail", Toast.LENGTH_LONG).show();
            }
        }
        fabMenu.close(true);
    }
    @OnClick(R.id.activity_fullscreen_photo_fab_favorite)
    public void setFabFavorite(){
        if(realmController.isPhotoExist(photo.getId())){
            realmController.deletePhoto(photo);
            fabFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_favorite));
            Toast.makeText(this, "Remove Favorite", Toast.LENGTH_SHORT).show();
        }else{
            realmController.savePhoto(photo);
            fabFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_favorited));
            Toast.makeText(this, "Favorited", Toast.LENGTH_SHORT).show();
        }
        fabMenu.close(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
