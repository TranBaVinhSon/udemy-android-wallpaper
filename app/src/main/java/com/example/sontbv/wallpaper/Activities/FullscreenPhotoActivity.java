package com.example.sontbv.wallpaper.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sontbv.wallpaper.Adapters.GlideApp;
import com.example.sontbv.wallpaper.Models.Photo;
import com.example.sontbv.wallpaper.R;
import com.example.sontbv.wallpaper.Webservices.ApiInterface;
import com.example.sontbv.wallpaper.Webservices.ServiceGenerator;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
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
    @BindView(R.id.activity_fullscreen_photo_fab_share)
    FloatingActionButton fabShare;
    @BindView(R.id.activity_fullscreen_photo_fab_set_wallpaper)
    FloatingActionButton fabWallpaper;
    @BindView(R.id.activity_fullscreen_photo_fab_favorite)
    FloatingActionButton fabFavorite;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_photo);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        String photoId = intent.getStringExtra("photoId");
        getPhoto(photoId);
    }
    private void getPhoto(String id){
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<Photo> call = apiInterface.getPhoto(id);
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "success");
                    updateUI(response.body());
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

    private void updateUI(Photo photo){
        // Make sure that, if we have some errors here, our application will not crash
        try{
            username.setText(photo.getUser().getUsername());
            GlideApp.with(FullscreenPhotoActivity.this)
                    .load(photo.getUser().getProfileImage().getSmall())
                    .into(userAvatar);


            GlideApp.with(FullscreenPhotoActivity.this)
                    .load(photo.getUrl().getFull())
                    .placeholder(R.drawable.placeholder)
                    .into(fullscreenPhoto);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnClick(R.id.activity_fullscreen_photo_fab_share)
    public void setFabShare(){
        Toast.makeText(this, "share", Toast.LENGTH_LONG).show();
    }
    @OnClick(R.id.activity_fullscreen_photo_fab_set_wallpaper)
    public void setFabWallpaper(){
        Toast.makeText(this, "Wallpaper", Toast.LENGTH_LONG).show();
    }
    @OnClick(R.id.activity_fullscreen_photo_fab_favorite)
    public void setFabFavorite(){
        Toast.makeText(this, "Favorite", Toast.LENGTH_LONG).show();
    }
}
