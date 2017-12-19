package com.example.sontbv.wallpaper.Utils;

import android.app.Activity;
import android.app.Application;

import com.example.sontbv.wallpaper.Models.Photo;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by sontbv on 12/9/17.
 */

public class RealmController {
    private final Realm realm;

    public RealmController(){
        realm = Realm.getDefaultInstance();
    }

    public void savePhoto(Photo photo){
        realm.beginTransaction();
        realm.copyToRealm(photo);
        realm.commitTransaction();
    }

    public void deletePhoto(final Photo photo){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Photo resultPhoto = realm.where(Photo.class).equalTo("id", photo.getId()).findFirst();
                resultPhoto.deleteFromRealm();
            }
        });
    }

    public boolean isPhotoExist(String photoId){
        Photo checkedPhoto = realm.where(Photo.class).equalTo("id", photoId).findFirst();
        if(checkedPhoto == null)
            return false;
        return true;
    }

    public List<Photo> getPhotos(){
        return realm.where(Photo.class).findAll();
    }
}
