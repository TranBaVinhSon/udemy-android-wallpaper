package com.example.sontbv.wallpaper.Utils;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;

import com.example.sontbv.wallpaper.R;

import java.io.IOException;

/**
 * Created by sontbv on 12/3/17.
 */

public class Functions {
    public static void changeMainFragment(FragmentActivity fragmentActivity, Fragment fragment){
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

    public static boolean setWallpaper(Activity activity, Bitmap bitmap){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        Bitmap tempBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(activity);
        wallpaperManager.setWallpaperOffsetSteps(1, 1);
        wallpaperManager.suggestDesiredDimensions(width, height);
        try {
            wallpaperManager.setBitmap(tempBitmap);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
