package com.example.sontbv.wallpaper.Utils;

import android.app.Activity;
import android.app.Application;

import com.example.sontbv.wallpaper.Models.Photo;

import io.realm.Realm;

/**
 * Created by sontbv on 12/9/17.
 */

public class RealmController {
    private static RealmController realmController;
    private final Realm realm;

    public RealmController(){
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Activity activity){
        if(realmController == null){
            realmController = new RealmController();
        }
        return realmController;
    }
    public void closeRealm(){
        realm.close();
    }
    public void savePhoto(Photo photo){
        realm.beginTransaction();
        realm.copyToRealm(photo);
        realm.commitTransaction();
    }
    public void deletePhoto(Photo photo){
        realm.beginTransaction();
        photo.deleteFromRealm();
        realm.commitTransaction();
    }
    public boolean isPhotoExist(String photoId){
        Photo checkedPhoto = realm.where(Photo.class).equalTo("id", photoId).findFirst();
        if(checkedPhoto == null)
            return false;
        return true;
    }
}
